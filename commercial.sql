create database commercial;
use commercial;
drop database commercial;
create table user(
id bigint not null auto_increment primary key,
name varchar(255),
username varchar(50),
password varchar(50),
address varchar(50),
phone varchar(50),
id_roles bigint,
foreign key (id_roles) references roles(id)
);

create table roles(
id bigint not null auto_increment primary key,
name varchar(50)
);

create table product(
id bigint not null auto_increment primary key,
name varchar(50),
price double,
quantity int check(quantity >= 0),
description varchar(50),
brand varchar(50),
id_category bigint,
image varchar(255),
foreign key (id_category) references categories(id)
);

create table categories(
id bigint not null auto_increment primary key,
name varchar(50)
);

drop table categories;
drop table product;

create table cart(
id bigint not null auto_increment primary key,
id_user bigint not null,
id_product bigint not null,
quantity int,
price double,
foreign key (id_user) references user(id),
foreign key (id_product) references product(id)
);

create table store(
id bigint not null auto_increment primary key,
name varchar(50),
phone varchar(50),
address varchar(255),
logo varchar(255),
description varchar(255)
);

create table payment(
id bigint not null auto_increment primary key,
id_cart bigint not null,
id_user bigint not null, 
id_store bigint not null,
date_create date,
totalPrice double,
foreign key (id_cart) references cart(id),
foreign key (id_user) references user(id),
foreign key (id_store) references store(id)
);