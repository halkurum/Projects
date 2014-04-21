#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <sys/time.h>
#include <time.h>
#include <string.h>
#include <math.h>

#include "cs402.h"
#include "my402list.h"
#include "structDefinitions.h"
#include "generalHelper.h"
#include "displayManager.h"

int totalPackets_n, bucketSize_B, tokensPerPacket_P, tokensInBucket, totalTokens, droppedTokens, droppedPackets, packetsInSystem;
double tokenArrivalRate_r, packetArrivalRate_lambda, packetServiceRate_mu, tokenInterArrivalTime, packetInterArrivalTime, packetServiceTime;
double emulationStartTime;
char *traceFileName_t;
bool traceFilePresent;
My402List *PacketDetailsList;

void Usage()
{
	fprintf(stderr, "Usage: warmup2 [-lambda lambda] [-mu mu] [-r r] [-B B] [-P P] [-n num] [-t tsfile] \n");
	exit(-1);
}

void CheckAllTimes()
{
	if(!traceFilePresent)
	{
		if(tokenInterArrivalTime > 10)
			tokenInterArrivalTime = 10;
	
		if(packetInterArrivalTime > 10)
			packetInterArrivalTime = 10;

		if(packetServiceTime > 10)
			packetServiceTime = 10;
	}
	else
	{
		if(tokenInterArrivalTime > 10)
			tokenInterArrivalTime = 10;
	}
}

void InitializeDefaultParameters()
{
	totalPackets_n = 20;
	tokensPerPacket_P = 3;	
	packetServiceRate_mu = 0.35;
	packetArrivalRate_lambda = 0.5;
	tokenArrivalRate_r = 1.5;
	bucketSize_B = 10;
	
	tokensInBucket = 0;
	totalTokens = 0;

	droppedTokens = 0;
	droppedPackets = 0;

	tokenInterArrivalTime = 1/tokenArrivalRate_r;
	packetInterArrivalTime = 1/packetArrivalRate_lambda;
	packetServiceTime = 1/packetServiceRate_mu;
	traceFilePresent = false;
}

void RemoveAllMemoryAllocated()
{
	My402ListElem *elem;
	PacketData *data;
	for(elem = My402ListFirst(PacketDetailsList) ; elem != NULL ; elem = My402ListNext(PacketDetailsList, elem))
	{
		data = (PacketData *)elem->obj;
		free(data);
		data = NULL;
	}
	My402ListUnlinkAll(PacketDetailsList);
	elem = NULL;
}

void CalculateAndDisplayStatistics(double emuEndTime)
{
	int packetsServiced;
	double avgPktInterArrivalTime, avgPktServiceTime, avgTimeInSystem, avgPktsAtQ1, avgPktsAtQ2, avgPktsAtServer, tokenDropProbability, packetDropProbability;
	double ePowerX2, eXPower2, standardDeviation;
	My402ListElem *elem;
	PacketData *data;
	avgPktInterArrivalTime = 0;
	avgPktServiceTime = 0;
	avgTimeInSystem = 0;
	avgPktsAtQ1 = 0;
	avgPktsAtQ2 = 0;
	avgPktsAtServer = 0;
	standardDeviation = 0;
	ePowerX2 = 0;
	eXPower2 = 0;
	tokenDropProbability = 0;
	packetDropProbability = 0;
	packetsServiced = 0;
	for(elem = My402ListFirst(PacketDetailsList) ; elem != NULL ; elem = My402ListNext(PacketDetailsList, elem))
	{
		data = (PacketData *)elem->obj;
		data->serviceTime /= 1000;
		data->interArrivalTime /= 1000;
		data->timeInSystem /= 1000;
		data->timeInQ1 /= 1000;
		data->timeInQ2 /= 1000;
	}
	for(elem = My402ListFirst(PacketDetailsList) ; elem != NULL ; elem = My402ListNext(PacketDetailsList, elem))
	{
		data = (PacketData *)elem->obj;
		avgPktInterArrivalTime += data->interArrivalTime;
		if(data->packetServiced == true)
		{
			packetsServiced++;
			avgPktServiceTime += data->serviceTime;
			avgTimeInSystem += data->timeInSystem;
			avgPktsAtQ1 += data->timeInQ1;
			avgPktsAtQ2 += data->timeInQ2;
			ePowerX2 += ((data->timeInSystem)*(data->timeInSystem));
		}
	}
	avgPktsAtServer = avgPktServiceTime;
	if(packetsInSystem != 0 && packetsServiced !=0)
	{
		emuEndTime = emuEndTime/1000;
		ePowerX2 /= packetsServiced;
		avgPktInterArrivalTime /= packetsInSystem;
		avgPktServiceTime /= packetsServiced;
		avgTimeInSystem /= packetsServiced;
		avgPktsAtQ1 /= emuEndTime;
		avgPktsAtQ2 /= emuEndTime;
		avgPktsAtServer /= emuEndTime;
	}
	if(packetsInSystem != 0)
	{
		packetDropProbability = (double)droppedPackets / (double)packetsInSystem;
	}
	if(totalTokens != 0)
	{
		tokenDropProbability = (double)droppedTokens / (double)totalTokens;
	}
	eXPower2 = (avgTimeInSystem)*(avgTimeInSystem);
	standardDeviation = ePowerX2 - eXPower2;
	standardDeviation = sqrt(standardDeviation);
	DisplayStatistics(avgPktInterArrivalTime, avgPktServiceTime, avgTimeInSystem, avgPktsAtQ1, avgPktsAtQ2, avgPktsAtServer, standardDeviation, tokenDropProbability, packetDropProbability);
}

void InitializePacketData(PacketData *data)
{
	data->packetNumber = 0;
	data->packetInterArrivalTime = 0;
	data->tokensRequired = 0;
	data->packetServiceTime = 0;
	data->packetDropped = false;
	data->packetServiced = false;
	data->interArrivalTime = 0;
	data->serviceTime = 0;
	data->timeInSystem = 0;
	data->timeInQ1 = 0;
	data->timeInQ2 = 0;
}

double GetEmulationStartTime(struct timeval starttime)
{
	double retVal;
	emulationStartTime = (starttime.tv_sec * 1000000) + starttime.tv_usec;
	retVal = 0;
	return retVal;
}

double GetTimeStampInUSeconds(struct timeval currenttime)
{
	double retVal;
	retVal = (currenttime.tv_sec * 1000000) + currenttime.tv_usec;
	return retVal;
}

double GetTimeDifference(double endTime, double startTime)
{
	double retVal;
	retVal = endTime - startTime;
	retVal = retVal / 1000;
	return retVal;
}

char* GetText(char *startPointer, char *tabPointer)
{
	char *text;
	int textLength = 0;
	text = startPointer;
	while(startPointer != tabPointer)
	{
		textLength++;
		startPointer++;
	}
	text[textLength] = '\0';
	return text;
}

int GetNanoSecond(char *decimalPart)
{
	int retVal, len, i, j, tenpowerval;
	tenpowerval = 1;
	len = strlen(decimalPart);
	retVal = atoi(decimalPart);
	i = 9 - len;
	for(j=0;j<i;j++)
		tenpowerval = tenpowerval*10;
	retVal*=tenpowerval;
	return retVal;
}

struct timespec GetNSleepTime(double sleepTimeValue)
{
	char *fullText, *dotPointer, *wholeText, buffer[15];
	struct timespec retVal;
	fullText = buffer;
	sprintf(fullText, "%lf", sleepTimeValue);
	dotPointer = strchr(fullText, '.');
	if(dotPointer == '\0')
	{
		retVal.tv_sec = atoi(fullText);
		retVal.tv_nsec = 0;
	}
	else
	{
		wholeText = GetText(fullText, dotPointer);
		retVal.tv_sec = atoi(wholeText);
		dotPointer++;
		retVal.tv_nsec = GetNanoSecond(dotPointer);
	}
	return retVal;
}

double GetInterArrivalSleepTime(struct timeval previousTime, double interArrivalTime)
{
	double retVal, prevTimeValue, currentTimeValue;
	struct timeval currentTime;
	gettimeofday(&currentTime,NULL);
	currentTimeValue = GetTimeStampInUSeconds(currentTime);
	prevTimeValue = GetTimeStampInUSeconds(previousTime);
	prevTimeValue = GetTimeDifference(currentTimeValue, prevTimeValue);
	prevTimeValue /= 1000;
	retVal = interArrivalTime - prevTimeValue;
	return retVal;
}

double GetStructTimeDiff(struct timeval currentTime, struct timeval prevTime)
{
	double retVal, currentTimeValue, prevTimeValue;
	currentTimeValue = GetTimeStampInUSeconds(currentTime);
	prevTimeValue = GetTimeStampInUSeconds(prevTime);
	retVal = GetTimeDifference(currentTimeValue, prevTimeValue);
	return retVal;
}
