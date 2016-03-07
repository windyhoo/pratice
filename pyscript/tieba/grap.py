 # coding=utf-8
import urllib2;
import re;
from BeautifulSoup import BeautifulSoup

def grap():
	m=urllib2.urlopen('http://tieba.baidu.com/f?ie=utf-8&kw=%E6%98%9F%E6%88%98%E9%A3%8E%E6%9A%B4');
	page=m.read();
	soup = BeautifulSoup(page);
	itemList = soup.findAll("div",attrs={"class":re.compile("threadlist_title pull_left j_th_tit ")})
	for item in itemList:
		print item.a.text.encode('utf8')
		print item.a.get('href')

if __name__ == '__main__':
	grap()
