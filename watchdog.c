#include <stdio.h>
#include <stdbool.h>
#include <pthread.h>
#include <stdlib.h>
#include <unistd.h>
#define PROGRAM "python /home/nvidia/Desktop/Jetson_Code/movement_sender.py"

void * program_thread(void *param){
	int status;
	status = system(PROGRAM);
	return NULL;
}

int main(int argc, char *argv[]){
	printf("Starting watchdog\n");
	pthread_t tid = (pthread_t) malloc(sizeof(pthread_t));
	pthread_create(&tid, NULL, program_thread, NULL);

	while(true){
		pthread_join(tid, NULL);
		printf("RESTARTING!!!\n");
		pthread_create(&tid, NULL, program_thread, NULL);
		sleep(1);
	}

	return 0;
}
