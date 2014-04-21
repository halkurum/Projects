#ifndef _GENERALHELPER_H_
#define _GENERALHELPER_H_

#include <stdio.h>
#include <stdbool.h>
#include <sys/time.h>
#include <time.h>
#include <pthread.h>

#include "my402list.h"
#include "structDefinitions.h"
#include "cs402.h"

extern int totalPackets_n, bucketSize_B, tokensPerPacket_P, tokensInBucket, totalTokens, droppedTokens, packetsInSystem, droppedPackets;
extern double tokenArrivalRate_r, packetArrivalRate_lambda, packetServiceRate_mu, tokenInterArrivalTime, packetInterArrivalTime, packetServiceTime;
extern char *traceFileName_t;
extern bool traceFilePresent;
extern My402List *Q1, *Q2, *PacketDetailsList;
extern struct timeval emulationStartTimeVal;
extern double emulationStartTime;

extern void Usage();
extern void InitializeDefaultParameters();
extern void CheckAllTimes();
extern void InitializePacketData(PacketData*);
extern void CalculateAndDisplayStatistics(double);
extern char* GetText(char*, char*);
extern int GetNanoSecond(char*);
extern struct timespec GetNSleepTime(double); 

extern double GetInterArrivalSleepTime(struct timeval, double);
extern double GetTimeStampInUSeconds(struct timeval);
extern double GetTimeDifference(double, double);
extern double GetStructTimeDiff(struct timeval, struct timeval);
extern double GetEmulationStartTime(struct timeval);

extern void RemoveAllMemoryAllocated();
#endif /*_GENERALHELPER_H_*/
