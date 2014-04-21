#ifndef _STRUCTDEFINITIONS_H_
#define _STRUCTDEFINITIONS_H_

#include <stdio.h>
#include <sys/time.h>
#include <time.h>
#include <stdbool.h>

#include "cs402.h"

#define Queue1 "Q1"
#define Queue2 "Q2"

typedef struct PacketDetails
{
	int packetNumber;
	double packetInterArrivalTime;
	int tokensRequired;
	double packetServiceTime;
	bool packetDropped;
	bool packetServiced;
	struct timeval SystemArrivalTimeStamp;
	struct timeval Q1ArrivalTimeStamp;
	struct timeval Q1DepartTimeStamp;
	struct timeval Q2ArrivalTimeStamp;
	struct timeval ServiceStartTimeStamp;
	struct timeval ServiceEndTimeStamp;
	double timeInQ1;
	double timeInQ2;
	double interArrivalTime;
	double serviceTime;
	double timeInSystem;
}PacketData;

#endif /*_STRUCTDEFINITIONS_H_*/
