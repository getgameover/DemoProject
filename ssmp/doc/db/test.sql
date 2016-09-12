create schema test;
create table test.a(
    id serial primary key not null,
    name varchar(64) not null
);
create table test.b(
    id serial primary key not null,
    name varchar(64) not null
);
insert into test.a(name)values('张三');
insert into test.a(name)values('李四');
insert into test.a(name)values('王五');

insert into test.b(name)values('张三');

select a.* as a,b.* from test.a as a  join test.b as b on a.name=b.name;
drop table test.a;
create table test.a(
    id serial primary key not null,
    power decimal(64,0),
    pp integer ,
    name varchar(64)
);
insert into test.a(power,pp,name)values(111111111111111111111111,1111,"张三");
insert into test.a(power,pp,name)values(111111111111111111111111,1111,"历史");
insert into test.a(power,pp,name)values(111111111111111111111111,1111,"王五");
insert into test.a(power,pp,name)values(222222222222222222222222,2222,"lao shi");
insert into test.a(power,pp,name)values(333333333333333333333333,3333,"li shi");
select pp&2=2 from test.a;
select power >> 2 from test.a;
select 1<<20,1<<52;
select string_agg(power,",")from test.a group by pp;

select * from general.tb_proc_inst;