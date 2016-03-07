#coding=utf-8
import sqlite3

DB_PATH = '/Users/windyhoo/workspace/pyscript/tieba/novel.db'

def test():
	# cx = sqlite3.connect(DB_PATH)

	# cu=cx.cursor()
	# # cu.execute("create table pic_log (id integer primary key autoincrement,url varchar(1000),name varchar(100),tags varchar(100))")
	# # row = ['www.baidu.com',u'百度','web']
	# # cx.execute("insert into pic_log(url,name,tags) values (?,?,?)", row)
	# name = u'baidu'
	# cu.execute("select * from novel_list")
	# print cu.fetchall()
	# cx.commit()

	connection = sqlite3.connect(DB_PATH)
	cursor=connection.cursor();
	# cursor.execute("insert into novel_list_dtl(dtl_id,id,title_name,content,state_date) values(?,?,?,?,datetime('now'))",(1,1,'t','tt'));
	# connection.commit();

	# result = cursor.execute("select content from novel_list_dtl where dtl_id=1");
	# for row in result:
	# 	print row[0]

	result = cursor.execute("select title_name from novel_list_dtl where id=1");
	for row in result:
		print row[0]
		if(row[0]!=u"第二十五卷 神谕之战 第九十三章 烈家的挽歌（求月票！）"):
			print 'not equal'
		else:
			print 'equal'
	connection.close();

def checkExist(titleName,novelId):
	connection = sqlite3.connect(DB_PATH);
	cursor=connection.cursor();
	result = cursor.execute("select title_name from novel_list_dtl where id="+str(novelId));
	exist = False;
	for row in result:
		print row[0]
		if(row[0]==titleName):
			exist = True;
			break;
	connection.close();
	return exist;

if __name__ == '__main__':
	checkExist('gg',1);
