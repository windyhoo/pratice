/*
 * UdpPackage.cpp
 *
 *  Created on: 2015年11月19日
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
#include <iostream>
#include <pthread.h>
using namespace std;

#define SERVER_PORT 8000
#define BUFFER_SIZE 1024
#define FILE_NAME_MAX_SIZE 512

void* receiveThread( void* args ) {
	int server_socket_fd = *(int*)args;

	while(1)
	{
		struct sockaddr_in client_addr;
		socklen_t client_addr_length = sizeof(client_addr);

		char buffer[BUFFER_SIZE];
		bzero(buffer, BUFFER_SIZE);
		if(recvfrom(server_socket_fd, buffer, BUFFER_SIZE,0,(struct sockaddr*)&client_addr, &client_addr_length) == -1)
		{
			perror("Receive Data Failed:");
			exit(1);
		}

		printf("port:%d\n",ntohs(client_addr.sin_port));
		printf("address:%s\n",inet_ntoa(client_addr.sin_addr));

		char file_name[FILE_NAME_MAX_SIZE+1];
		bzero(file_name,FILE_NAME_MAX_SIZE+1);
		strncpy(file_name, buffer, strlen(buffer)>FILE_NAME_MAX_SIZE?FILE_NAME_MAX_SIZE:strlen(buffer));
		printf("%s\n", file_name);
	}

}

int main(int argc,char** argv) {
	int serverPort=8000;

	if(argc!=2) {
		cout<<"argv error"<<endl;
		return -1;
	} else {
		serverPort=atoi(argv[1]);
	}

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

	pthread_t threadId;
	int ret = pthread_create( &threadId, NULL, receiveThread, &server_socket_fd );

	while(true) {
		char serverIp[FILE_NAME_MAX_SIZE+1];
		bzero(serverIp, FILE_NAME_MAX_SIZE+1);
		cout<<"Please Input server ip:";
		cin>>serverIp;

		unsigned short serverPort;
		cout<<"Please Input server port:";
		cin>>serverPort;
		cout<<serverIp<<" "<<serverPort<<endl;

		struct sockaddr_in server_addr;
		bzero(&server_addr, sizeof(server_addr));
		server_addr.sin_family = AF_INET;
		server_addr.sin_addr.s_addr = inet_addr(serverIp);
		server_addr.sin_port = htons(serverPort);

		char file_name[FILE_NAME_MAX_SIZE+1];
		bzero(file_name, FILE_NAME_MAX_SIZE+1);
		cout<<"Please Input Message:";
		cin>>file_name;

		char buffer[BUFFER_SIZE];
		bzero(buffer, BUFFER_SIZE);

		strncpy(buffer, file_name, strlen(file_name)>BUFFER_SIZE?BUFFER_SIZE:strlen(file_name));

		if(sendto(server_socket_fd, buffer, BUFFER_SIZE,0,(struct sockaddr*)&server_addr,sizeof(server_addr)) < 0)
		{
			perror("Send File Name Failed:");
			exit(1);
		}
	}

	pthread_exit( NULL );
	return 0;
}


