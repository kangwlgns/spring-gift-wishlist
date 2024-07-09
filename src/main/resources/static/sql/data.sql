-- 더미 데이터를 삽입할 sql파일

insert into products (id, name, price, image, md) values (1, 'dummy1', 0, 'dummy1.png', false);
insert into products (id, name, price, image, md) values (2, 'dummy2', 0, 'dummy2.png', false);
insert into products (id, name, price, image, md) values (3, 'dummy3', 0, 'dummy3.png', false);

insert into users (email, password) values ('luckyrkd@naver.com', 'aaaaa11111');

insert into wish_products (email, id, name, price, image, quantity) values ('luckyrkd@naver.com', 1, 'dummy1', 0, 'dummy1.png', 5)