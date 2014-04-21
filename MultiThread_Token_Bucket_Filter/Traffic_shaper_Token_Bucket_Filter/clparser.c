#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <getopt.h>
#include <stdbool.h>
#include <ctype.h>

#include "cs402.h"
#include "my402list.h"
#include "structDefinitions.h"
#include "generalHelper.h"
#include "clparser.h"

int totalPackets_n, bucketSize_B, tokensPerPacket_P;
double tokenArrivalRate_r, packetArrivalRate_lambda, packetServiceRate_mu, tokenInterArrivalTime, packetInterArrivalTime, packetServiceTime;
char *traceFileName_t;
bool traceFilePresent;

void GetCommandLineParameters(int argc, char *argv[])
{
	int optionvar, option_index;
	option_index = 0;
	static struct option long_options[] = 
	{
		{"mu", required_argument, 0, 'm'},
		{"lambda", required_argument, 0, 'l'},
		{0, 0, 0, 0}
	};

	while((optionvar = getopt_long(argc, argv, "l:m:n:B:P:r:t:", long_options, &option_index)) != -1)
	{
		switch(optionvar)
		{
			case 'n': totalPackets_n = atoi(optarg);
				CheckValueInt(totalPackets_n);
				break;
			case 'B': bucketSize_B = atoi(optarg);
				CheckValueInt(bucketSize_B);
				break;
			case 'P': tokensPerPacket_P = atoi(optarg);
				CheckValueInt(tokensPerPacket_P);
				break;
			case 'r': tokenArrivalRate_r = atof(optarg);
				CheckValueDouble(tokenArrivalRate_r);
				tokenInterArrivalTime = 1/tokenArrivalRate_r;
				break;
			case 'm': packetServiceRate_mu = atof(argv[optind]);
				CheckValueDouble(packetServiceRate_mu);
				packetServiceTime = 1/packetServiceRate_mu;
				break;
			case 'l': packetArrivalRate_lambda = atof(argv[optind]);
				CheckValueDouble(packetArrivalRate_lambda);
				packetInterArrivalTime = 1/packetArrivalRate_lambda;
				break;
			case 't': traceFilePresent = true;
				traceFileName_t = (char*)malloc(strlen(optarg)*sizeof(optarg));
				strncpy(traceFileName_t, optarg, strlen(optarg));
				break;
			case '?': Usage();
				break;
			default: Usage();
				break;
		}
	}
}

void CheckValueInt(int value)
{
	if(value <= 0)
	{
		fprintf(stderr, "\n [-n] [-B] and [-P] all 3 should be positive integers. Exiting the program \n");
		exit(-1);
	}
}

void CheckValueDouble(double value)
{
	if(value <= 0)
	{
		fprintf(stderr, "\n [-lambda] [-mu] and [-r] all 3 should be poistive real numbers. Exiting the program \n");
		exit(-1);
	}
}

int ValidateAndGetInt(char *buf)
{
	char ch;
	int i;
	int retVal;
	for(i = 0; i<strlen(buf); i++)
	{
		ch = buf[i];
		if(!isdigit(ch))
		{
			fprintf(stderr, "\n Error in processing trace file. Exiting the program \n");
			exit(-1);
		}
	}
	retVal = atoi(buf);
	if(retVal == 0)
	{
		fprintf(stderr, "\n All values in tFile should be positive integers. Exiting the program \n");
		exit(-1);
	}
	return retVal;
}

FILE* ReadFirstLineInTraceFile(FILE *fp)
{
	char buf[100], numVal[5];
	char *bufptr;
	int i,j;
	i = j = 0;
	fgets(buf,sizeof(buf), fp);

	bufptr = &buf[0];
	while((bufptr[0] != ' ') && (bufptr[0] !='\t') && (bufptr[0] != '\n') && (i < strlen(buf)))
	{
		numVal[j] = bufptr[0];
		bufptr++;
		i++;
		j++;
	}
	if(j == 0)
	{
		fprintf(stderr, " Error in processing first line of trace(n). Exiting the program \n");
		exit(-1);
	}
	numVal[j] = '\0';
	
	totalPackets_n = ValidateAndGetInt(numVal);
	ResetOtherParameters();
	return fp;
}

void ReadLineInTraceFile(FILE *fp)
{
	char buf[100], arrivalTime[10], tokensPerPacket[5], serviceTime[10];
	char *bufptr;
	int i, j;
	i = j = 0;

	if(fgets(buf,sizeof(buf),fp) == NULL)
	{
		fprintf(stdout, "\n Error in Reading trace file. Exiting the program \n");
		exit(-1);
	}
	else
	{
		bufptr = &buf[0];
		while((bufptr[0] != ' ') && (bufptr[0] !='\t') && (i < strlen(buf)))
		{
			arrivalTime[j] = bufptr[0];
			bufptr++;
			i++;
			j++;
		}
		arrivalTime[j] = '\0';
		packetInterArrivalTime = ValidateAndGetInt(arrivalTime);
		packetInterArrivalTime /= 1000;

		while(bufptr[0] == ' ' || bufptr[0] == '\t')
			bufptr++;
		
		j=0;
		while((bufptr[0] != ' ') && (bufptr[0] !='\t') && (i < strlen(buf)))
		{
			tokensPerPacket[j] = bufptr[0];
			bufptr++;
			i++;
			j++;
		}
		tokensPerPacket[j] = '\0';
		tokensPerPacket_P = ValidateAndGetInt(tokensPerPacket);

		while(bufptr[0] == ' ' || bufptr[0] == '\t')
			bufptr++;

		j=0;
		while((bufptr[0] != ' ') && (bufptr[0] !='\t') && (bufptr[0] != '\n') && (i < strlen(buf)))
		{
			serviceTime[j] = bufptr[0];
			bufptr++;
			i++;
			j++;
		}
		serviceTime[j] = '\0';
		packetServiceTime = ValidateAndGetInt(serviceTime);
		packetServiceTime /= 1000;
	}
}

void ResetOtherParameters()
{
	packetServiceRate_mu = 0;
	packetArrivalRate_lambda = 0;
	tokensPerPacket_P = 0;
	packetInterArrivalTime = 0;
	packetServiceTime = 0;
}

void CheckPacketPerToken()
{
	/*if(tokensPerPacket_P > bucketSize_B)
	{
		fprintf(stderr, "\n Deterministic Mode: TokensPerPacket (P) > BucketSize (B).");
		fprintf(stderr, "\n Invalid condition. Emulation of Token Bucket not possible. Exiting the program \n");
		exit(-1);
	}*/
}
