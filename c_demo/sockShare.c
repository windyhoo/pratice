/*
 * sockShare.c
 *
 *  Created on: 2015年11月30日
 *      Author: windyhoo
 */

#include<sys/types.h>
#include<sys/socket.h>
#include<unistd.h>
#include<netinet/in.h>
#include<arpa/inet.h>
#include<stdio.h>
#include<stdlib.h>
#include<errno.h>
#include<netdb.h>
#include<stdarg.h>
#include<string.h>
#include <pthread.h>
#include <sys/shm.h>

#define BUFFER_SIZE 1024
#define FILE_NAME_MAX_SIZE 512


int main() {
	int serverPort = 8000;
	struct sockaddr_in server_addr;
	bzero(&server_addr, sizeof(server_addr));
	server_addr.sin_family = AF_INET;
	server_addr.sin_addr.s_addr = htonl(INADDR_ANY);
	server_addr.sin_port = htons(serverPort);

	int server_socket_fd = socket(AF_INET, SOCK_DGRAM, 0);
	if(server_socket_fd == -1)
	{
		perror("Create Socket Failed:");
		exit(1);
	}

	if(-1 == (bind(server_socket_fd,(struct sockaddr*)&server_addr,sizeof(server_addr))))
	{
		perror("Server Bind Failed:");
		exit(1);
	}

	int shmid;//共享内存标识符
	//创建共享内存
	shmid = shmget((key_t)1234, sizeof(int), 0666|IPC_CREAT);
	if(shmid == -1)
	{
		fprintf(stderr, "shmget failed\n");
		exit(EXIT_FAILURE);
	}

	void *shm = NULL;//分配的共享内存的原始首地址
	//将共享内存连接到当前进程的地址空间
	shm = shmat(shmid, 0, 0);
	if(shm == (void*)-1)
	{
		fprintf(stderr, "shmat failed\n");
		exit(EXIT_FAILURE);
	}
	printf("\nMemory attached at %X\n", (int)shm);
	printf("\nMemory attached at %X\n", (int)shm);
	//设置共享内存
	shm = &server_socket_fd;
	int *shared = (int*)shm;

	pid_t fpid; //fpid表示fork函数返回的值
	fpid=fork();
	if (fpid < 0) {
		printf("error in fork!");
	} else if(fpid==0) {
		//child
		for(int i=0;i<3;++i) {
			sleep(3);
			char* sendBuffer="child hello";
			int shmSockId = *shared;

			struct sockaddr_in disAddr;
			bzero(&disAddr, sizeof(disAddr));
			disAddr.sin_family = AF_INET;
			disAddr.sin_addr.s_addr = inet_addr("127.0.0.1");
			disAddr.sin_port = htons(8001);

			if(sendto(shmSockId, sendBuffer, strlen(sendBuffer),0,(struct sockaddr*)&disAddr,sizeof(disAddr)) < 0) {
				perror("Send File Name Failed:");
			}
		}
		while(1) {
			sleep(5);
		}
	} else {
		struct sockaddr_in client_addr;
		socklen_t client_addr_length = sizeof(client_addr);

		int shmSockId = *shared;

		char buffer[BUFFER_SIZE];
		bzero(buffer, BUFFER_SIZE);
		if(recvfrom(shmSockId, buffer, BUFFER_SIZE,0,(struct sockaddr*)&client_addr, &client_addr_length) == -1)
		{
			perror("Receive Data Failed:");
			exit(1);
		}

		printf("port:%d\n",ntohs(client_addr.sin_port));
		printf("address:%s\n",inet_ntoa(client_addr.sin_addr));
		printf("family:%d\n",client_addr.sin_family);

		char file_name[FILE_NAME_MAX_SIZE+1];
		bzero(file_name,FILE_NAME_MAX_SIZE+1);
		strncpy(file_name, buffer, strlen(buffer)>FILE_NAME_MAX_SIZE?FILE_NAME_MAX_SIZE:strlen(buffer));
		printf("%s\n", file_name);
	}


//	//把共享内存从当前进程中分离
//	if(shmdt(shm) == -1)
//	{
//		fprintf(stderr, "Shamed failed\n");
//		exit(EXIT_FAILURE);
//	}
//	//删除共享内存
//	if(shmctl(shmid, IPC_RMID, 0) == -1)
//	{
//		fprintf(stderr, "shmctl(IPC_RMID) failed\n");
//		exit(EXIT_FAILURE);
//	}
//	exit(EXIT_SUCCESS);

	return 0;
}
