-- 스키마 구조를 미리 설정하는 sql파일

create table products (
     id bigint,
     name varchar(255),
     price int,
     image varchar(255),
     md boolean,
     primary key (id)
);

create table products (
      email varchar(255),
      name varchar(255),
      price int,
      image varchar(255),
      quantity int,
      primary key (email)
);

create table users (
      email varchar(255),
      password varchar(255),
      primary key (email)
);