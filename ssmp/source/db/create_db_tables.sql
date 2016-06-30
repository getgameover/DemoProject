--drop table base.lu_user;
--drop table base.lu_user_detail;
--drop table base.lu_img;

--创建Scheam
create schema base;
create table base.lu_user(
    id serial primary key not null,
    add_time timestamp not null,
    status integer not null default 1,
    username varchar(64) not null unique,
    password varchar(64) not null,
    pwd_key varchar(64) not null,
    role integer not null default 0,
    user_right bigint not null
);
create index id_user_username on base.lu_user(username);

create table base.lu_user_detail(
    user_id integer primary key not null,
    name varchar(20),
    sex integer default 0,
    phone varchar(20),
    tel varchar(20),
    qq varchar(20),
    idcard varchar(20),
    address varchar(120),
    head_img_id integer
);

create table base.lu_img(
    id serial primary key not null,
    add_time timestamp not null,
    status integer not null default 1,
    img_type integer not null default 1,
    add_user_id integer not null,
    name varchar(60),
    filename varchar(60),
    ext varchar(30),
    path varchar(256),
    url varchar(256),
    size bigint not null default 0
);

insert into base.lu_user(id,add_time,status,username,password,pwd_key,role,user_right) values(
    1,now(),1,'admin','b712f10d9913c57c017e49f43fbb365a8b422d9d','123456A',999,9223372036854775807
);
