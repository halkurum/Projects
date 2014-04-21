#ifndef _DISPLAYMANAGER_H_
#define _DISPLAYMANAGER_H_

#include <stdio.h>
#include <sys/time.h>

extern void DisplayEmulationParameters();
extern void DisplayEmulationStartTime(struct timeval);

extern void DisplayTokenArrives(struct timeval);
extern void DisplayTokenDropped(struct timeval);

extern void DisplayPacketArrives(struct timeval, double);
extern void DisplayPacketDropped(struct timeval);

extern void DisplayPacketEntersQueue(struct timeval, int, char*);
extern void DisplayPacketLeavesQueue1(struct timeval, int, double);

extern void DisplayPacketStartsService(struct timeval, int, double);
extern void DisplayPacketEndsService(struct timeval, int, double, double);

extern void DisplayStatistics(double, double, double, double, double, double, double, double, double);

#endif /*_DISPLAYMANAGER_H_*/
