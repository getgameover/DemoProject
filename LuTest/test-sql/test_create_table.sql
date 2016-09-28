drop table  test.people;
drop table  test.people_details;
create table test.people(
	id serial primary key not null,
	name varchar(100) not null unique
);
create table test.people_details(
	id serial primary key not null,
	people_id integer not null,
	age integer default 0
);
insert into test.people(name) values('张三'),('李四'),('王五'),('赵六');
insert into test.people_details(people_id,age)values
	((select id from test.people where name='张三'),23)
	,((select id from test.people where name='李四'),24)
	,((select id from test.people where name='王五'),25)
	,((select id from test.people where name='赵六'),26);
	
	delete from test.people_details where age=24;
	select * from test.people p  right join test.people_details pd on p.id=pd.people_id;
	
	select sum(a.t) from (select count(1) as t from test.people union all select count(1) as t from test.people) a 