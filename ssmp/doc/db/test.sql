drop schema test;
create schema test;
drop table test.a;
create table test.a(
    id serial primary key not null,
    name varchar(64) not null,
    power bit(50) not null
);

insert into test.a(name,power)values('张三','00000000000000000000000000000020000000011111111111');

select pp&2=2 from test.a;
select power >> 2 from test.a;
select 1<<20,1<<52;
select string_agg(power,",")from test.a group by pp;

select * from general.tb_proc_inst;