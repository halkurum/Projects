warmup2: warmup2main.o threadManager.o displayManager.o clparser.o generalHelper.o my402list.o
	gcc -o warmup2 -g warmup2main.o threadManager.o displayManager.o clparser.o generalHelper.o my402list.o -lm -lrt -lnsl -lpthread

warmup2main.o: warmup2main.c warmup2main.h
	gcc -g -c -Wall warmup2main.c

threadManager.o: threadManager.c threadManager.h
	gcc -g -c -Wall threadManager.c

displayManager.o: displayManager.c displayManager.h
	gcc -g -c -Wall displayManager.c

clparser.o: clparser.c clparser.h
	gcc -g -c -Wall clparser.c

generalHelper.o: generalHelper.c generalHelper.h
	gcc -g -c -Wall generalHelper.c

my402list.o: my402list.c my402list.h
	gcc -g -c -Wall my402list.c
clean:
	rm -f *.o warmup2
