#include <stdio.h>        
#include <sys/types.h>  
#include <ifaddrs.h>  
#include <netinet/in.h>   
#include <string.h>   
#include <arpa/inet.h>  
#include <unistd.h>  
#include <sys/types.h>  
#include <string.h>  
#include <stdlib.h>  
#include <stdlib.h>  
#include <time.h>  
//#include <sys/vfs.h>  
#include <stdio.h>  
#include <signal.h>  
#include <sys/stat.h>  
#include <sys/socket.h>  
#include <netinet/in.h>  
#include <arpa/inet.h>  
#include <netdb.h>
#include <string>

using namespace std;

int main (int argc, const char * argv[])   
{  
  
    struct ifaddrs * ifAddrStruct=NULL;  
    void * tmpAddrPtr=NULL;  
  
    getifaddrs(&ifAddrStruct);

    string addressInfo;

    while (ifAddrStruct!=NULL)   
    {  
        if (ifAddrStruct->ifa_addr->sa_family==AF_INET)  
        {   // check it is IP4  
            // is a valid IP4 Address  
            tmpAddrPtr = &((struct sockaddr_in *)ifAddrStruct->ifa_addr)->sin_addr;  
            char addressBuffer[INET_ADDRSTRLEN];
            char addressItem[INET_ADDRSTRLEN+64];
            inet_ntop(AF_INET, tmpAddrPtr, addressBuffer, INET_ADDRSTRLEN);
            sprintf(addressItem,"%s IPV4 Address %s\n", ifAddrStruct->ifa_name, addressBuffer);
            addressInfo.append(addressItem);
        }  
        // else if (ifAddrStruct->ifa_addr->sa_family==AF_INET6)  
        // {   // check it is IP6  
        //     // is a valid IP6 Address  
        //     tmpAddrPtr=&((struct sockaddr_in *)ifAddrStruct->ifa_addr)->sin_addr;  
        //     char addressBuffer[INET6_ADDRSTRLEN];  
        //     inet_ntop(AF_INET6, tmpAddrPtr, addressBuffer, INET6_ADDRSTRLEN);  
        //     printf("%s IPV6 Address %s\n", ifAddrStruct->ifa_name, addressBuffer);   
        // }   
        ifAddrStruct = ifAddrStruct->ifa_next;  
    }

    int server_socket_fd = socket(AF_INET, SOCK_DGRAM, 0);
    if(server_socket_fd == -1)
    {
        perror("Create Socket Failed:");
        exit(1);
    }

    const char* serverIp="119.29.163.52";
    unsigned short serverPort = 9999;
    struct sockaddr_in server_addr;
    bzero(&server_addr, sizeof(server_addr));
    server_addr.sin_family = AF_INET;
    server_addr.sin_addr.s_addr = inet_addr(serverIp);
    server_addr.sin_port = htons(serverPort);

    if(sendto(server_socket_fd, addressInfo.c_str(), addressInfo.size(),0,(struct sockaddr*)&server_addr,sizeof(server_addr)) < 0)
    {
        perror("Send File Name Failed:");
        exit(1);
    }

    return 0;  
}  

