#ifndef _CLPARSER_H_
#define _CLPARSER_H_

#include <stdio.h>
#include "cs402.h"

extern void GetCommandLineParameters(int, char**);
extern FILE* ReadFirstLineInTraceFile(FILE*);
extern void ReadLineInTraceFile(FILE*);

extern int ValidateAndGetInt(char*);
extern void CheckValueInt(int);
extern void CheckValueDouble(double);

extern void ResetOtherParameters();
extern void CheckPacketPerToken();

#endif /*_CLPARSER_H_*/
