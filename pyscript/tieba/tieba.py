#!/usr/bin/python
# coding=utf-8
import urllib
from BeautifulSoup import BeautifulSoup
import re
from BeautifulSoup import Tag
import sqlite3
import getopt
import sys
import time
import socket

socket.setdefaulttimeout(30)

DB_PATH = '/Users/windyhoo/workspace/pyscript/tieba/novel.db'
# DB_PATH = '/root/workspace/pratice/pyscript/tieba/novel.db'

#http://tieba.baidu.com/f?ie=utf-8&kw=%E6%98%9F%E6%88%98%E9%A3%8E%E6%9A%B4
def getUrlBytitle(url,novelId):
	f=urllib.urlopen(url)
	str=f.read()
	soup = BeautifulSoup(str)
	titlelist = soup.findAll("a",attrs={"class": "j_th_tit "})
	for i in range(len(titlelist)):
		titlelistitem = titlelist[i].string
		pattern = u"第.*卷(.*)第.*章(.*)"
		m=re.match(pattern,titlelistitem)
		if(m):
			print titlelist[i].get('href')
			print titlelistitem

			if(checkExist(titlelistitem,novelId)):
				print 'exist';
			else:
				print 'begin get new content';
				getContent("http://tieba.baidu.com"+titlelist[i].get('href'),titlelistitem,novelId)
			
def getContent(contentUrl,titleName,novelId):
	f = urllib.urlopen(contentUrl)
	str=f.read()
	soup = BeautifulSoup(str)
	louzhudiv = soup.find("div",attrs={"class": re.compile("j_louzhubiaoshi")})
	louzhuurl=louzhudiv.a.get('href');
	louzhuid=louzhuurl[louzhuurl.find('#')+1:];
	louzhudivid = "post_content_"+louzhuid;
	content=soup.find("div",attrs={"id": louzhudivid})

	article=""
	for contentitem in content.contents:
		if isinstance(contentitem,basestring):
			article += contentitem;
		elif isinstance(contentitem,Tag):
			if contentitem.name=="br":
				article += "\n"
			elif contentitem.text!="":
				article += contentitem.text;

	connection = sqlite3.connect(DB_PATH)
	cursor=connection.cursor();
	cursor.execute("insert into novel_list_dtl(id,title_name,content,state_date) values(?,?,?,datetime('now'))",(novelId,titleName,article));
	connection.commit();
	connection.close();


# 疯了，这里的逻辑得改造
def checkExist(titleName,novelId):
	connection = sqlite3.connect(DB_PATH);
	cursor=connection.cursor();
	result = cursor.execute("select title_name from novel_list_dtl where id="+str(novelId));
	exist = False;
	for row in result:
		if(row[0]==titleName):
			exist = True;
			break;
	connection.close();
	return exist;

def getNovelList(novelId):
	sql = "select id,name,url,source_type from novel_list";
	if(novelId != None) :
		sql = sql + " where id="+str(novelId);

	connection = sqlite3.connect(DB_PATH);
	cursor = connection.cursor();
	result = cursor.execute(sql);
	resultList = result.fetchall();
	connection.close();
	return resultList;

def getNovelDtlList(novelId,titleCount):
	if(novelId == None):
		return;

	sql = "select dtl_id,title_name,state_date from novel_list_dtl where id ="+str(novelId) +" order by dtl_id desc";
	connection = sqlite3.connect(DB_PATH);
	cursor = connection.cursor();
	result = cursor.execute(sql);
	resultList = result.fetchall();
	connection.close();

	if(len(resultList)>titleCount):
		resultList = resultList[0:titleCount-1]
	return resultList;

def getNovelContent(novelDtlId):
	if(novelDtlId == None):
		return;

	sql = "select content from novel_list_dtl where dtl_id ="+str(novelDtlId);
	connection = sqlite3.connect(DB_PATH);
	cursor = connection.cursor();
	result = cursor.execute(sql);
	resultList = result.fetchall();
	connection.close();

	if(len(resultList)>0):
		return resultList[0][0];
	return;

def updateTiebaNovelById(url,novelId):
	f=urllib.urlopen(url)
	str=f.read()
	soup = BeautifulSoup(str)
	titlelist = soup.findAll("a",attrs={"class": "j_th_tit "})
	for i in range(len(titlelist)):
		titlelistitem = titlelist[i].string
		# pattern = u"第.*卷(.*)第.*章(.*)"
		pattern = u"第.*章(.*)"
		m=re.match(pattern,titlelistitem)
		if(m):
			# print titlelist[i].get('href')
			# print titlelistitem

			if not checkExist(titlelistitem,novelId):
				print 'new:' + titlelistitem;
				getContent("http://tieba.baidu.com"+titlelist[i].get('href'),titlelistitem,novelId)
			# else:
			# 	print 'exist:' + titlelistitem;

def updateNovel():
	novelList = getNovelList(None);
	for novelItem in novelList:
		novelUrl = novelItem[2];
		novelId = novelItem[0];
		sourceType = novelItem[3];
		if(sourceType == 1):
			updateTiebaNovelById(novelUrl,novelId);
		elif(sourceType ==2):
			updateUBNovelById(novelUrl,novelId);


# def updateTiebaNovel():
# 	novelList = getNovelList(None);
# 	for novelItem in novelList:
# 		novelUrl = novelItem[2];
# 		novelId = novelItem[0];
# 		updateTiebaNovelById(novelUrl,novelId)

def getUBContent(contentUrl,titleName,novelId):
	f = urllib.urlopen(contentUrl)
	str=f.read()
	soup = BeautifulSoup(str)
	content = soup.find("div",attrs={"id": "content"});

	article=""
	doubleBr = False;
	for contentitem in content.contents:
		if isinstance(contentitem,basestring):
			article += contentitem;
		elif isinstance(contentitem,Tag):
			article += contentitem.text;

	connection = sqlite3.connect(DB_PATH)
	cursor=connection.cursor();
	cursor.execute("insert into novel_list_dtl(id,title_name,content,state_date) values(?,?,?,datetime('now'))",(novelId,titleName,article));
	connection.commit();
	connection.close();

def updateUBNovelById(url,novelId):
	f=urllib.urlopen(url)
	str=f.read()
	soup = BeautifulSoup(str)
	titlelist = soup.findAll("dd")
	for i in range(len(titlelist)):
		titlelistitem = titlelist[i].text
		pattern = u"第.*章(.*)"
		m=re.match(pattern,titlelistitem)
		if(m):
			if not checkExist(titlelistitem,novelId):
				print 'new:' + titlelistitem;
				getUBContent(url+titlelist[i].a.get('href'),titlelistitem,novelId)


'''
novel -l
查询novel列表，返回novel名称和ID

novel -t 3 -i 1
id为1的novel最新的3章title和dtl_id

novel -c 3
列出dtl_id为3的文章内容

novel -o -i 1

ps:
novel table
列出所有novel list的最新3章的title和dtl_id

novel content ?
必须指定dtl_id
'''

'''
c) 调用getopt函数。函数返回两个列表：opts和args。opts为分析出的格式信息。
args为不属于格式信息的剩余的命令行参数。opts是一个两元组的列表。
每个元素为：(选项串,附加参数)。如果没有附加参数则为空串''。

getopt函数的第三个参数[, long_options]为可选的长选项参数，上面例子中的都为短选项(如-i -o)
长选项格式举例:
--version
--file=error.txt
让一个脚本同时支持短选项和长选项
getopt.getopt(sys.argv[1:], "hi:o:", ["version", "file="])
'''

if __name__ == '__main__':
	if(len(sys.argv)<2):
		print '请输入执行参数';
		sys.exit();

	try:
		opts,args = getopt.getopt(sys.argv[1:],"lot:i:c:s:")
	except:
		print '参数异常';
		sys.exit();

	if(len(opts)==0):
		print '请输入执行参数';
		sys.exit();

	showlist = False;
	daemonFlag = False;
	titleCount = 0;
	novelId = 0;
	novelDtlId = 0;
	novelIdList = [];

	for op,value in opts:
		novellist = "gg"
		if op == "-l":
			showlist = True;
		elif op == "-t":
			titleCount = value;
		elif op == "-i":
			novelId = value;
		elif op == "-c":
			novelDtlId = value;
		elif op == "-o":
			daemonFlag = True;

	if(showlist):
		for listitem in getNovelList(None):
			print str(listitem[0]) + " " + listitem[1] + " " +listitem[2];

	if(int(titleCount)!=0 and int(novelId)!=0):
		novelDtlList = getNovelDtlList(novelId,titleCount);
		for index,listdtlitem in enumerate(novelDtlList) :
			if(index< int(titleCount)):
				print str(listdtlitem[0]) + " " + listdtlitem[1] + " " +listdtlitem[2];

	if(int(novelDtlId)!=0):
		print(getNovelContent(novelDtlId));

	if(daemonFlag):
		while True:
			updateNovel();
			print('['+time.strftime("%Y-%m-%d %H:%M:%S",time.localtime(time.time()))+']begin sleep...');
			time.sleep(5*60);



# if __name__ == '__main__':
# 	url="http://tieba.baidu.com/f?ie=utf-8&kw=%E6%98%9F%E6%88%98%E9%A3%8E%E6%9A%B4"
# 	# getUrlBytitle(url,1)
# 	print(getNovelList(None));


# content=soup.find("div",attrs={"id": "post_content_84867355520"})

# article=""
# for contentitem in content.contents:
# 	if isinstance(contentitem,basestring):
# 		article += contentitem;
# 	elif isinstance(contentitem,Tag):
# 		if contentitem.name=="br":
# 			article += "\n"
# 		elif contentitem.text!="":
# 			article += contentitem.text;


##number和字符串互转的问题还是得重新了解下
#type()
#dir()
#isinstance
#BeautifulSoup的基础对象
#字符串连接的方式
#导入模块，导入模块的类，使用模块的类
#明细表加入url字段
#不能用 && 得用and
#for不用括号，正确用法比如 for listdtlitem in getNovelDtlList(novelId,titleCount) :
#枚举获取下标	for index,item in enumerate(novellist):
#if not操作，!操作非法
