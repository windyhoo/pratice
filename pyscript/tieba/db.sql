create table novel_list(id INTEGER PRIMARY KEY,name text,url text);

insert into novel_list(name,url) values('星战风暴','http://tieba.baidu.com/f?ie=utf-8&kw=%E6%98%9F%E6%88%98%E9%A3%8E%E6%9A%B4');


create table novel_list_dtl(dtl_id INTEGER PRIMARY KEY,id int,title_name text,content text,state_date timestamp);

select (datetime('now'))

insert into novel_list_dtl(dtl_id,id,title_name,content,state_date) values(1,1,'第二十五卷 神谕之战 第九十四章 王铮苏醒！','现在银盟的基本底线正在一点点消失，到时候谁都想分一杯羹的时候，我们可能就真的危险了。”木真说道，从银盟回来，木真跟一般巨人已经有了巨大的',datetime('now'));
insert into novel_list_dtl(dtl_id,id,title_name,content,state_date) values(1,1,'tt','ttt',datetime('now');