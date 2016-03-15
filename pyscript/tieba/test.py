#!/usr/bin/python
# coding=utf-8
import urllib
import re
import sqlite3
import getopt
import sys
import time

def loopcheck():
	novellist = ["1111","222","3333"]
	for index,item in enumerate(novellist):
		print index;
		print item;

def timeloop():
	i=0;
	while True:
		i=i+1;
		print i;
		time.sleep(3);

if __name__ == '__main__':
	timeloop();