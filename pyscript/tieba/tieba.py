# coding=utf-8
import urllib
from BeautifulSoup import BeautifulSoup
import re
from BeautifulSoup import Tag
import sqlite3

DB_PATH = '/Users/windyhoo/workspace/pyscript/tieba/novel.db'

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
	sql = "select id,name,url from novel_list";
	if(novelId != None) :
		sql = sql + " where id="+str(novelId);

	connection = sqlite3.connect(DB_PATH);
	cursor = connection.cursor();
	result = cursor.execute(sql);
	resultList = result.fetchall();
	connection.close();
	return resultList;


if __name__ == '__main__':
	url="http://tieba.baidu.com/f?ie=utf-8&kw=%E6%98%9F%E6%88%98%E9%A3%8E%E6%9A%B4"
	# getUrlBytitle(url,1)
	print(getNovelList(None));


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