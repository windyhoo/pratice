/* 
 * broadcast.c - An IP multicast server 
 */
#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>

int port = 6789;

int main(void)
{    
	int socket_descriptor;  
	struct sockaddr_in address;  
	/*  首先建立套接口 */
	socket_descriptor = socket(AF_INET, SOCK_DGRAM, 0);  
	if (socket_descriptor == -1) 
	{      
		perror("Opening socket"); 
		exit(EXIT_FAILURE);  
	}

	/* 初始化IP多播地址 */ 
	memset(&address, 0, sizeof(address));   
	address.sin_family = AF_INET;  
	address.sin_addr.s_addr = inet_addr("224.0.0.1"); 
	address.sin_port = htons(port);  

	/* 开始进行IP多播 */  
	while(1) {
		char *msg = "test from broadcast22";
		if(sendto(socket_descriptor, msg,strlen(msg), 0,(struct sockaddr *)&address, sizeof(address)) < 0) 
		{  
			perror("sendto");  
			exit(EXIT_FAILURE); 
		}   
		sleep(2);
	}  
	exit(EXIT_SUCCESS);
}