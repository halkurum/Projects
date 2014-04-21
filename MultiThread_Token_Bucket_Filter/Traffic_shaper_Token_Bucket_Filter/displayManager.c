#include <stdio.h>
#include <stdbool.h>
#include <sys/time.h>

#include "structDefinitions.h"
#include "generalHelper.h"
#include "my402list.h"

bool traceFilePresent;
double tokenArrivalRate_r, packetArrivalRate_lambda, packetServiceRate_mu, emulationStartTime;
int bucketSize_B, tokensPerPacket_P, totalPackets_n, tokensInBucket, totalTokens, packetsInSystem;
char *traceFileName_t;
My402List *PacketDetailsList;


void DisplayStatistics(double avgPktInterArrivalTime, double avgPktServiceTime, double avgTimeInSystem, double avgPktsAtQ1, double avgPktsAtQ2, double avgPktsAtServer, double standardDeviation, double tokenDropProbability, double packetDropProbability)
{
	char *notApplicable = "(Not applicable because zero packets have arrived into the system)";
	fprintf(stdout, "\n\nStatistics: \n");
	if(packetsInSystem == 0)
	{
		fprintf(stdout, "\n\taverage packet inter-arrival time = %s", notApplicable);
		fprintf(stdout, "\n\taverage packet service time = %s", notApplicable);
		fprintf(stdout, "\n\n\taverage number of packets in Q1 = %s", notApplicable);
		fprintf(stdout, "\n\taverage number of packets in Q2 = %s", notApplicable);
		fprintf(stdout, "\n\taverage number of packets in S = %s", notApplicable);
		fprintf(stdout, "\n\n\taverage time a packet spent in system = %s", notApplicable);
		fprintf(stdout, "\n\tstandard deviation for time spent in system = %s", notApplicable);
	}
	if(totalTokens == 0)
		fprintf(stdout, "\n\n\ttoken drop probability = %s", notApplicable);
	else if(packetsInSystem == 0)
	{
		fprintf(stdout, "\n\n\ttoken drop probability = %.6g", tokenDropProbability);
		fprintf(stdout, "\n\tpacket drop probability = %s\n\n", notApplicable);
	}
	else
	{
		fprintf(stdout, "\n\taverage packet inter-arrival time = %.6g seconds", avgPktInterArrivalTime);
		fprintf(stdout, "\n\taverage packet service time = %.6g seconds", avgPktServiceTime);
		fprintf(stdout, "\n\n\taverage number of packets in Q1 = %.6g", avgPktsAtQ1);
		fprintf(stdout, "\n\taverage number of packets in Q2 = %.6g", avgPktsAtQ2);
		fprintf(stdout, "\n\taverage number of packets in S = %.6g", avgPktsAtServer);
		fprintf(stdout, "\n\n\taverage time a packet spent in system = %.6g seconds", avgTimeInSystem);
		fprintf(stdout, "\n\tstandard deviation for time spent in system = %.6g", standardDeviation);
		fprintf(stdout, "\n\n\ttoken drop probability = %.6g", tokenDropProbability);
		fprintf(stdout, "\n\tpacket drop probability = %.6g\n\n", packetDropProbability);
	}
}

void DisplayEmulationParameters()
{
	fprintf(stdout, "\nEmulation Parameters\n");
	fprintf(stdout, "\tr = %f\n", tokenArrivalRate_r);
	fprintf(stdout, "\tB = %d\n", bucketSize_B);
	if(!traceFilePresent)
	{
		fprintf(stdout, "\tlambda = %f\n", packetArrivalRate_lambda);
		fprintf(stdout, "\tmu = %f\n", packetServiceRate_mu);
		fprintf(stdout, "\tP = %d\n", tokensPerPacket_P);
		fprintf(stdout, "\tnumber to arrive = %d\n", totalPackets_n);
	}
	else
	{
		fprintf(stdout, "\ttsfile = %s\n", traceFileName_t);
	}
}

void DisplayEmulationStartTime(struct timeval currentTime)
{
	double timeValue;
	timeValue = GetEmulationStartTime(currentTime);
	fprintf(stdout, "\n%012.3lfms: emulation begins", timeValue);
}

void DisplayTokenArrives(struct timeval currentTime)
{
	double timeValue;
	timeValue = GetTimeStampInUSeconds(currentTime);
	timeValue = GetTimeDifference(timeValue, emulationStartTime);
	fprintf(stdout, "\n%012.3lfms: token t%d arrives, token bucket now has %d token", timeValue, totalTokens, tokensInBucket);
	if(tokensInBucket != 1)
		fprintf(stdout, "s"); 
}

void DisplayTokenDropped(struct timeval currentTime)
{
	double timeValue;
	timeValue = GetTimeStampInUSeconds(currentTime);
	timeValue = GetTimeDifference(timeValue, emulationStartTime);
	fprintf(stdout, "\n%012.3lfms: token t%d arrives, dropped", timeValue, totalTokens);
}
void DisplayPacketArrives(struct timeval currentTime, double interArrivalTime)
{
	double timeValue;
	timeValue = GetTimeStampInUSeconds(currentTime);
	timeValue = GetTimeDifference(timeValue, emulationStartTime);
	fprintf(stdout, "\n%012.3lfms: p%d arrives, needs %d token", timeValue, packetsInSystem, tokensPerPacket_P);
	if(tokensPerPacket_P != 1)
		fprintf(stdout, "s");
	fprintf(stdout, ", inter-arrival time = %.3lfms", interArrivalTime);
}

void DisplayPacketDropped(struct timeval currentTime)
{
	double timeValue;
	timeValue = GetTimeStampInUSeconds(currentTime);
	timeValue = GetTimeDifference(timeValue, emulationStartTime);
	fprintf(stdout, "\n%012.3lfms: p%d arrives, needs %d tokens, dropped", timeValue, packetsInSystem, tokensPerPacket_P);
}

void DisplayPacketEntersQueue(struct timeval currentTime, int packetNumber, char *queue)
{
	double timeValue;
	timeValue = GetTimeStampInUSeconds(currentTime);
	timeValue = GetTimeDifference(timeValue, emulationStartTime);
	fprintf(stdout, "\n%012.3lfms: p%d enters %s", timeValue, packetNumber, queue);
}

void DisplayPacketLeavesQueue1(struct timeval currentTime, int packetNumber, double timeInQ1)
{
	double timeValue;
	timeValue = GetTimeStampInUSeconds(currentTime);
	timeValue = GetTimeDifference(timeValue, emulationStartTime);
	fprintf(stdout, "\n%012.3lfms: p%d leaves Q1, time in Q1 = %.3lfms, token bucket now has %d token", timeValue, packetNumber, timeInQ1, tokensInBucket);
	if(tokensInBucket != 1)
		fprintf(stdout, "s");
}

void DisplayPacketStartsService(struct timeval currentTime, int packetNumber, double timeInQ2)
{
	double timeValue;
	timeValue = GetTimeStampInUSeconds(currentTime);
	timeValue = GetTimeDifference(timeValue, emulationStartTime);
	fprintf(stdout, "\n%012.3lfms: p%d begins service at S, time in Q2 = %.3lfms", timeValue, packetNumber, timeInQ2);
}

void DisplayPacketEndsService(struct timeval currentTime, int packetNumber, double serviceTime, double timeInSystem)
{
	
	double timeValue;
	timeValue = GetTimeStampInUSeconds(currentTime);
	timeValue = GetTimeDifference(timeValue, emulationStartTime);
	fprintf(stdout, "\n%012.3lfms: p%d departs from S, service time = %.3lfms, time in system = %.3lfms", timeValue, packetNumber, serviceTime, timeInSystem);
}
