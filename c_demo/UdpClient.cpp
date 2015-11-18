/*************************************************************************
 > File Name: client.c
 > Author: SongLee
 ************************************************************************/
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
using namespace std;

#define SERVER_PORT 8000
#define BUFFER_SIZE 1024
#define FILE_NAME_MAX_SIZE 512

int main(int argc,char** argv)
{


	int client_socket_fd = socket(AF_INET, SOCK_DGRAM, 0);
	if(client_socket_fd < 0)
	{
		perror("Create Socket Failed:");
		exit(1);
	}

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
		printf("Please Input Message:\t");
		scanf("%s", file_name);
		printf("Message:%s\n",file_name);

		char buffer[BUFFER_SIZE];
		bzero(buffer, BUFFER_SIZE);
		printf("1\n");
		strncpy(buffer, file_name, strlen(file_name)>BUFFER_SIZE?BUFFER_SIZE:strlen(file_name));

		printf("2\n");
		if(sendto(client_socket_fd, buffer, BUFFER_SIZE,0,(struct sockaddr*)&server_addr,sizeof(server_addr)) < 0)
		{
			perror("Send File Name Failed:");
			exit(1);
		}
	}

	close(client_socket_fd);
	return 0;
}
