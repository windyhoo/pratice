# coding=utf-8
import urllib
from BeautifulSoup import BeautifulSoup
import re
f=urllib.urlopen("http://tieba.baidu.com/f?ie=utf-8&kw=%E6%98%9F%E6%88%98%E9%A3%8E%E6%9A%B4")
str=f.read()
soup = BeautifulSoup(str)
titlelist = soup.findAll("a",attrs={"class": "j_th_tit "})
for i in range(len(titlelist)):
	titlelistitem = titlelist[i].string
	pattern = u"第.*卷(.*)第.*章(.*)"
	m=re.match(pattern,titlelistitem)
	if(m):
		print titlelistitem
