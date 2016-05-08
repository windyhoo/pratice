CC      = gcc
CFLAGS  = -g -Wall -O2

OBJPATH		=	obj
TARGET		=	https_client

INC_PATH    = -I/usr/local/opt/openssl/include
LIB_PATH    = -L/usr/local/opt/openssl/lib

SOURCE 		=	https_client.c
OBJS=$(addprefix $(OBJPATH)/, $(addsuffix .o, $(basename $(SOURCE))))
EXT_LIB 	= -lssl -lcrypto 

$(OBJPATH)/%.o : %.c
	$(CC) $(LIB_PATH) $(INC_PATH) $(CFLAGS) -o $@ -c $<

$(TARGET): $(OBJS)
	$(CC) $(LIB_PATH) $(EXT_LIB) $(CFLAGS) -o $@ $^

clean:
	rm -rf $(OBJPATH)/*.d $(OBJPATH)/*.o $(TARGET) 
