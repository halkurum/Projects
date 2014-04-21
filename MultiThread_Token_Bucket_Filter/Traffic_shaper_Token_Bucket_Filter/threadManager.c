#include <stdio.h>
#include <pthread.h>
#include <stdlib.h>
#include <stdbool.h>
#include <sys/time.h>
#include <time.h>
#include <signal.h>

#include "generalHelper.h"
#include "displayManager.h"
#include "structDefinitions.h"
#include "clparser.h"

int totalPackets_n, bucketSize_B, tokensPerPacket_P, tokensInBucket, totalTokens, droppedTokens, packetsInSystem, droppedPackets;
double tokenArrivalRate_r, packetArrivalRate_lambda, packetServiceRate_mu, tokenInterArrivalTime, packetInterArrivalTime, packetServiceTime;
double emulationStartTime;
char *traceFileName_t;
bool traceFilePresent, tokenThreadDone, packetThreadDone, dropPacketSignal, ctrlCPressed;
struct timeval emulationStartTimeVal;
My402List *Q1, *Q2, *PacketDetailsList;
pthread_t tokenArrivalThread, packetArrivalThread, serverThread;
pthread_mutex_t mutexVar = PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t conditionalVar = PTHREAD_COND_INITIALIZER;
sigset_t ctrlCHandler;
struct sigaction packetAction, tokenAction, serverAction;

void TokenSIGINTHandler(int sig)
{
	My402ListElem *elem;
	ctrlCPressed = true;	
	tokenThreadDone = true;
	packetThreadDone = true;
	pthread_mutex_lock(&mutexVar);
	for(elem = My402ListFirst(Q1) ; elem != NULL ; elem = My402ListNext(Q1, elem))
		My402ListRemoveHead(Q1);
	for(elem = My402ListFirst(Q1) ; elem != NULL ; elem = My402ListNext(Q2, elem))
		My402ListRemoveHead(Q2);
	
	pthread_cond_signal(&conditionalVar);
	pthread_mutex_unlock(&mutexVar);
	
	pthread_cancel(packetArrivalThread);

	pthread_exit(0);
}

void ServerSIGINTHandler(int sig)
{
	My402ListElem *elem;
	ctrlCPressed = true;	
	tokenThreadDone = true;
	packetThreadDone = true;
	pthread_mutex_lock(&mutexVar);
	for(elem = My402ListFirst(Q1) ; elem != NULL ; elem = My402ListNext(Q1, elem))
		My402ListRemoveHead(Q1);
	for(elem = My402ListFirst(Q1) ; elem != NULL ; elem = My402ListNext(Q2, elem))
		My402ListRemoveHead(Q2);
	
	pthread_cond_signal(&conditionalVar);
	pthread_mutex_unlock(&mutexVar);

	pthread_cancel(tokenArrivalThread);
	pthread_cancel(packetArrivalThread);

	pthread_exit(0);
}

void PacketSIGINTHandler(int sig)
{	
	My402ListElem *elem;
	packetThreadDone = true;
	ctrlCPressed = true;
	pthread_mutex_lock(&mutexVar);
	for(elem = My402ListFirst(Q1) ; elem != NULL ; elem = My402ListNext(Q1, elem))
		My402ListRemoveHead(Q1);
	for(elem = My402ListFirst(Q1) ; elem != NULL ; elem = My402ListNext(Q2, elem))
		My402ListRemoveHead(Q2);
	pthread_cond_signal(&conditionalVar);
	pthread_mutex_unlock(&mutexVar);
	
	pthread_cancel(tokenArrivalThread);
	pthread_exit(0);
}

void CheckAndMovePacketToQ2()
{
	My402ListElem *elem;
	PacketData *firstInQ1;				
	elem = My402ListFirst(Q1);
	firstInQ1 = (PacketData *)elem->obj; 
	if(firstInQ1->tokensRequired <= tokensInBucket)
	{
		tokensInBucket -= firstInQ1->tokensRequired;
		My402ListRemoveHead(Q1);
		gettimeofday(&(firstInQ1->Q1DepartTimeStamp), NULL);
		firstInQ1->timeInQ1 = GetStructTimeDiff(firstInQ1->Q1DepartTimeStamp, firstInQ1->Q1ArrivalTimeStamp);
		DisplayPacketLeavesQueue1(firstInQ1->Q1DepartTimeStamp, firstInQ1->packetNumber, firstInQ1->timeInQ1);
		My402ListAppend(Q2, (void*)firstInQ1);
		gettimeofday(&(firstInQ1->Q2ArrivalTimeStamp), NULL);
		DisplayPacketEntersQueue(firstInQ1->Q2ArrivalTimeStamp, firstInQ1->packetNumber, Queue2);
		if(My402ListLength(Q2) == 1)
			pthread_cond_signal(&conditionalVar);
	}
}

void *tokenThreadFunctionality(void *arg)
{
	int lastState, lastType;
	struct timespec t1,t2;
	double sleepTime;
	struct timeval tokenArrivalTime;
	My402ListElem *elem;	

	tokenAction.sa_handler = TokenSIGINTHandler;
	tokenAction.sa_flags = 0;
	sigaction(SIGINT, &tokenAction, NULL);

	pthread_setcancelstate(PTHREAD_CANCEL_ENABLE, &lastState);
	pthread_setcanceltype(PTHREAD_CANCEL_ASYNCHRONOUS, &lastType);
	
	gettimeofday(&tokenArrivalTime, NULL);
	while(true)
	{
		pthread_sigmask(SIG_BLOCK, &ctrlCHandler, NULL);
		pthread_setcancelstate(PTHREAD_CANCEL_DISABLE, &lastState);
		pthread_mutex_lock(&mutexVar);
		if(packetThreadDone)
		{
			if(ctrlCPressed)
			{
				
				pthread_mutex_lock(&mutexVar);
				for(elem = My402ListFirst(Q1) ; elem != NULL ; elem = My402ListNext(Q1, elem))
					My402ListRemoveHead(Q1);
				for(elem = My402ListFirst(Q1) ; elem != NULL ; elem = My402ListNext(Q2, elem))
					My402ListRemoveHead(Q2);
	
				pthread_cond_signal(&conditionalVar);
				pthread_mutex_unlock(&mutexVar);

				pthread_exit(0);
			}
			pthread_sigmask(SIG_UNBLOCK, &ctrlCHandler, NULL);
		}
		if(packetThreadDone && (My402ListEmpty(Q1)))
		{
			pthread_mutex_unlock(&mutexVar);
			pthread_setcancelstate(PTHREAD_CANCEL_ENABLE, &lastState);
			break;
		}

		pthread_mutex_unlock(&mutexVar);
		pthread_setcancelstate(PTHREAD_CANCEL_ENABLE, &lastState);
		
		sleepTime = GetInterArrivalSleepTime(tokenArrivalTime, tokenInterArrivalTime);
		t1 = GetNSleepTime(sleepTime);
		if(nanosleep(&t1, &t2) != 0)
		{
			fprintf(stdout,"In Token Thread: Ctrl + C pressed. Thread Cancelled\n");
		}
		else
		{
			pthread_setcancelstate(PTHREAD_CANCEL_DISABLE, &lastState);
			pthread_mutex_lock(&mutexVar);
			if(packetThreadDone && (My402ListEmpty(Q1)))
			{
				pthread_mutex_unlock(&mutexVar);
				break;
			}
			pthread_mutex_unlock(&mutexVar);
			pthread_setcancelstate(PTHREAD_CANCEL_ENABLE, &lastState);

			totalTokens++;
			gettimeofday(&tokenArrivalTime, NULL);
			
			pthread_setcancelstate(PTHREAD_CANCEL_DISABLE, &lastState);
			pthread_mutex_lock(&mutexVar);
			if(tokensInBucket == bucketSize_B)
			{
				droppedTokens++;
				DisplayTokenDropped(tokenArrivalTime);
			}
			else
			{
				tokensInBucket++;
				DisplayTokenArrives(tokenArrivalTime);
				if(!My402ListEmpty(Q1))
				{
					CheckAndMovePacketToQ2();
				}
								
			}
			pthread_mutex_unlock(&mutexVar);
			pthread_setcancelstate(PTHREAD_CANCEL_ENABLE, &lastState);
		}
	}
	pthread_sigmask(SIG_BLOCK, &ctrlCHandler, NULL);
	tokenThreadDone = true;
	pthread_exit(0);	
}


void *packetThreadFunctionality(void *arg)
{
	FILE *fp;
	struct timespec t1,t2;
	double sleepTime;
	struct timeval packetArrivalTime, prevPacketArrivalTime;
	PacketData *packetDetails;
	
	packetAction.sa_handler = PacketSIGINTHandler;
	packetAction.sa_flags = 0;
	sigaction(SIGINT, &packetAction, NULL);
	pthread_sigmask(SIG_UNBLOCK, &ctrlCHandler, NULL);

	gettimeofday(&prevPacketArrivalTime, NULL);
	fp = (FILE *)arg;
	while(packetsInSystem < totalPackets_n)
	{
		if(traceFilePresent)
		{
			ReadLineInTraceFile(fp);
		}
		sleepTime = GetInterArrivalSleepTime(prevPacketArrivalTime, packetInterArrivalTime);
		t1 = GetNSleepTime(sleepTime);
		if(nanosleep(&t1, &t2) != 0)
		{
			fprintf(stdout,"In Packet Thread. Ctrl + C pressed \n");
		}
		else
		{
			if((packetDetails = (PacketData*)malloc(sizeof(PacketData))) == NULL)
			{
				fprintf(stderr, " Error in allocating memory. Exiting the program ");
				exit(-1);
			}
			InitializePacketData(packetDetails);
			
			pthread_sigmask(SIG_BLOCK, &ctrlCHandler, NULL);
			pthread_mutex_lock(&mutexVar);
			packetsInSystem++;
			
			gettimeofday(&packetArrivalTime, NULL);
			packetDetails->packetNumber = packetsInSystem;
			packetDetails->packetInterArrivalTime = packetInterArrivalTime;
			packetDetails->tokensRequired = tokensPerPacket_P;
			packetDetails->packetServiceTime = packetServiceTime;
			packetDetails->SystemArrivalTimeStamp = packetArrivalTime;
			packetDetails->interArrivalTime = GetStructTimeDiff(packetArrivalTime, prevPacketArrivalTime);
			
			pthread_mutex_unlock(&mutexVar);
			pthread_sigmask(SIG_UNBLOCK, &ctrlCHandler, NULL);
			
			if(tokensPerPacket_P > bucketSize_B)
			{
				DisplayPacketDropped(packetArrivalTime);
				prevPacketArrivalTime = packetArrivalTime;
				droppedPackets++;
				packetDetails->packetDropped = true;
			
				pthread_sigmask(SIG_BLOCK, &ctrlCHandler, NULL);
				pthread_mutex_lock(&mutexVar);
				if(packetsInSystem == totalPackets_n)
				{
					if(My402ListEmpty(Q1) && My402ListEmpty(Q2))
					{
						dropPacketSignal = true;
						pthread_cond_signal(&conditionalVar);
					}
					pthread_mutex_unlock(&mutexVar);
					pthread_sigmask(SIG_UNBLOCK, &ctrlCHandler, NULL);
			
					break;
				}
				pthread_mutex_unlock(&mutexVar);
				pthread_sigmask(SIG_UNBLOCK, &ctrlCHandler, NULL);
			}
			else
			{	
				pthread_sigmask(SIG_BLOCK, &ctrlCHandler, NULL);
				pthread_mutex_lock(&mutexVar);
				DisplayPacketArrives(packetArrivalTime, packetDetails->interArrivalTime);
				prevPacketArrivalTime = packetArrivalTime;
				My402ListAppend(PacketDetailsList, (void*)packetDetails);
				My402ListAppend(Q1, (void*)packetDetails);
				gettimeofday(&(packetDetails->Q1ArrivalTimeStamp), NULL);
				DisplayPacketEntersQueue(packetDetails->Q1ArrivalTimeStamp, packetDetails->packetNumber, Queue1);
				if(My402ListLength(Q1) == 1)
				{
					CheckAndMovePacketToQ2();
				}
				pthread_mutex_unlock(&mutexVar);
				pthread_sigmask(SIG_UNBLOCK, &ctrlCHandler, NULL);
			}
		}
	}
	pthread_sigmask(SIG_BLOCK, &ctrlCHandler, NULL);
	if(traceFilePresent)
		fclose(fp);
	packetThreadDone = true;
	pthread_exit(0);	
}

void *serverThreadFunctionality(void *arg)
{
	struct timespec t1,t2;
	My402ListElem *elem;
	PacketData *firstInQ2;
	
	serverAction.sa_handler = ServerSIGINTHandler;
	serverAction.sa_flags = 0;
	sigaction(SIGINT, &serverAction, NULL);
	pthread_sigmask(SIG_BLOCK, &ctrlCHandler, NULL);
	while(1)
	{
		if(ctrlCPressed)
			break;
		pthread_mutex_lock(&mutexVar);
		if(tokenThreadDone)
			pthread_sigmask(SIG_UNBLOCK, &ctrlCHandler, NULL);
		if(tokenThreadDone && My402ListEmpty(Q2))
		{
			pthread_mutex_unlock(&mutexVar);
			break;
		}
		while(My402ListEmpty(Q2) && !dropPacketSignal && !ctrlCPressed)
		{	
			pthread_cond_wait(&conditionalVar, &mutexVar);
		}
		if(dropPacketSignal)
		{
			pthread_mutex_unlock(&mutexVar);
			break;
		}
		if(ctrlCPressed)
		{
			pthread_mutex_unlock(&mutexVar);
			break;
		}
		elem = My402ListFirst(Q2);
		firstInQ2 = (PacketData *)elem->obj;
		My402ListRemoveHead(Q2);
		pthread_mutex_unlock(&mutexVar);
	
		gettimeofday(&(firstInQ2->ServiceStartTimeStamp), NULL);
		firstInQ2->timeInQ2 = GetStructTimeDiff(firstInQ2->ServiceStartTimeStamp, firstInQ2->Q2ArrivalTimeStamp);
		DisplayPacketStartsService(firstInQ2->ServiceStartTimeStamp, firstInQ2->packetNumber, firstInQ2->timeInQ2);
		t1 = GetNSleepTime(firstInQ2->packetServiceTime);
		
			pthread_sigmask(SIG_BLOCK, &ctrlCHandler, NULL);
		if(nanosleep(&t1, &t2) != 0)
		{
			fprintf(stdout," In Server Thread. Ctrl + C pressed \n");
			exit(-1);
		}
		else
		{
			
			gettimeofday(&(firstInQ2->ServiceEndTimeStamp), NULL);
			firstInQ2->serviceTime = GetStructTimeDiff(firstInQ2->ServiceEndTimeStamp, firstInQ2->ServiceStartTimeStamp);
			firstInQ2->timeInSystem = GetStructTimeDiff(firstInQ2->ServiceEndTimeStamp, firstInQ2->SystemArrivalTimeStamp);
			firstInQ2->packetServiced = true;
			DisplayPacketEndsService(firstInQ2->ServiceEndTimeStamp, firstInQ2->packetNumber, firstInQ2->serviceTime, firstInQ2->timeInSystem);
		}
		if(tokenThreadDone)
			pthread_sigmask(SIG_UNBLOCK, &ctrlCHandler, NULL);
	}
	pthread_exit(0);
}

void CreateAndRunThreads(FILE *fp)
{
	double emulationEndTime;
	struct timeval endTime;
	tokenThreadDone = false;
	packetThreadDone = false;
	dropPacketSignal = false;
	ctrlCPressed = false;
	
	if((Q1 = (My402List*)malloc(sizeof(My402List))) == NULL)
	{
		fprintf(stderr, " Error in Allocating memory for Q1. Exiting the program");
		return;
	}
	if((Q2 = (My402List*)malloc(sizeof(My402List))) == NULL)
	{
		fprintf(stderr, " Error in Allocating memory for Q2. Exiting the program");
		return;
	}
	if((PacketDetailsList = (My402List*)malloc(sizeof(My402List))) == NULL)
	{
		fprintf(stderr, " Error in Allocating memory for Packet Data structure. Exiting the program");
		exit(-1);
	}
	My402ListInit(Q1);
	My402ListInit(Q2);
	My402ListInit(PacketDetailsList);
	gettimeofday(&emulationStartTimeVal, NULL);
	DisplayEmulationStartTime(emulationStartTimeVal);
	
	sigemptyset(&ctrlCHandler);
	sigaddset(&ctrlCHandler, SIGINT);
	pthread_sigmask(SIG_BLOCK, &ctrlCHandler, NULL);
	
	pthread_create(&tokenArrivalThread, 0, tokenThreadFunctionality, 0);
	pthread_create(&packetArrivalThread, 0, packetThreadFunctionality, (void *)fp);
	pthread_create(&serverThread, 0, serverThreadFunctionality, 0);
	
	pthread_join(packetArrivalThread, 0);
	pthread_join(tokenArrivalThread, 0);
	pthread_join(serverThread, 0);
	
	gettimeofday(&endTime, NULL);
	emulationEndTime = GetTimeStampInUSeconds(endTime);
	emulationEndTime = GetTimeDifference(emulationEndTime, emulationStartTime);
	CalculateAndDisplayStatistics(emulationEndTime);
	RemoveAllMemoryAllocated();
}
