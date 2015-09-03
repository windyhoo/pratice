# coding=utf-8
import re
str="第二十三卷 乾坤一战 第二十七章 太空迷航"
m=re.match("第.*卷(.*)第.*章(.*)",str)
if(m):
	print 'ok'
else:
	print 'failed'
print m.group(1).strip()
print m.group(2).strip()
