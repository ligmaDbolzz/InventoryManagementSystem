CREATE SCHEMA `inventory_user` ;
USE inventory_user;

DROP TABLE IF EXISTS user;

CREATE TABLE IF NOT EXISTS user (
	username varchar(50),
    password varchar(5000),
    level int,
    name varchar(50),
    dateOfBirth varchar(50),
    gender varchar(50),
    imageDIR varchar(5000),
    primary key (username)
);

INSERT INTO user VALUES('Admin','a1920e3f3ea027f7248b0dc5a9bb8b7525269c4e1b3ce5acddddddb68d23c95e', 999999999, 'Cực Kì Đẹp Trai','99/99/9999','Croissant','');