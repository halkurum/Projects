#ifndef _THREADMANAGER_H_
#define _THREADMANAGER_H_

#include <stdio.h>
#include <pthread.h>
#include <stdlib.h>

#include "my402list.h"
#include "cs402.h"

extern void CreateAndRunThreads(FILE*);
extern void CheckAndMovePacketToQ2();

extern void* tokenThreadFunctionality(void*);
extern void* packetThreadFuncionality(void*);
extern void* serverThreadFunctionality(void*);

extern void HandleSIGINT();
extern void PacketSIGINTHandler(int);
extern void TokenAndServerSIGINTHandler(int);
#endif /*_THREADMANAGER_H*/
