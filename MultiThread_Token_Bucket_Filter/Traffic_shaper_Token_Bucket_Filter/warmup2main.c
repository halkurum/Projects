#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <pthread.h>

#include "cs402.h"
#include "my402list.h"
#include "generalHelper.h"
#include "clparser.h"
#include "threadManager.h"
#include "displayManager.h"

My402List *tFilePacketList = NULL;
bool traceFilePresent = false;
double emulationStartTime;

int main(int argc, char *argv[])
{
	FILE *fp;	
	InitializeDefaultParameters();
	GetCommandLineParameters(argc, argv);
	if(traceFilePresent)
	{
		//tFilePacketList = (My402List*)malloc(sizeof(My402List));
		if((fp = fopen(traceFileName_t, "r")) == NULL)
		{
			fprintf(stderr, "\n File %s not Present. Exiting the program. \n", traceFileName_t);
			exit(-1);
		}
		fp = ReadFirstLineInTraceFile(fp);
	}
	else
	{
		CheckPacketPerToken();
	}

	CheckAllTimes();
	DisplayEmulationParameters();
	
	CreateAndRunThreads(fp);
	pthread_exit(0);
	return 0;
}
