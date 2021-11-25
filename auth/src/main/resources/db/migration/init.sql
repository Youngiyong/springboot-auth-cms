create table users
(
    id         INT  not null
        primary key AUTO_INCREMENT,
    username       varchar(100) not null unique ,
    email      varchar(50) not null unique,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT NULL,
    deleted_at TIMESTAMP DEFAULT NULL
) ENGINE = InnoDb;

create table roles
(
    id         INT  not null
        primary key AUTO_INCREMENT,
    name       varchar(100) not null unique
) ENGINE = InnoDb;

create table user_roles
(
    user_id int not null,
    role_id int not null
) ENGINE = InnoDb;

create table user_tokens
(
    id          bigint       not null
        primary key,
    expiry_date timestamp  not null,
    token       varchar(255) unique not null,
    user_id     int       not null,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
) ENGINE = InnoDb;


CREATE TABLE customers
(
    id         BIGINT(50) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    firstname  VARCHAR(50)                         NOT NULL,
    lastname   VARCHAR(255)                        NOT NULL,
    email      VARCHAR(255)                        NOT NULL UNIQUE,
    added_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
) ENGINE = InnoDb;

create table tags(
                     id         int auto_increment     primary key,
                     name       varchar(255)                        not null,
                     created_at timestamp default CURRENT_TIMESTAMP not null,
                     updated_at timestamp                           null
) ENGINE = InnoDb;

create table franchisees
(
    id         int auto_increment     primary key,
    name       varchar(255)                        not null,
    phone      varchar(30)                        not null,
    description   varchar(2000)                     null,
    thumbnail   varchar(200)                        not null,
    status      TINYINT(1) default 1,
    created_at timestamp default CURRENT_TIMESTAMP not null,
    updated_at timestamp                           null,
    deleted_at timestamp                           null
) ENGINE = InnoDb;

create table franchisee_tags
(
    id  int auto_increment     primary key,
    franchisee_id int not null,
    tag_id int not null,
    created_at timestamp default CURRENT_TIMESTAMP not null,
    updated_at timestamp                           null
) ENGINE = InnoDb;

INSERT INTO customers (id, firstname, lastname, email, added_date)
VALUES (1, 'Uchiha', 'Sasuke', 'uchiha_sasuke@konohagakure.com', '2020-03-29 07:52:34'),
       (2, 'Uzumaki', 'Naruto', 'uzumaki_naruto2@konohagakure.com', '2020-03-29 08:18:59'),
       (3, 'Uzumaki', 'Naruto', 'uzumaki_naruto3@konohagakure.com', '2020-03-29 08:18:59'),
       (4, 'Uzumaki', 'Naruto', 'uzumaki_naruto4@konohagakure.com', '2020-03-29 08:18:59'),
       (5, 'Uzumaki', 'Naruto', 'uzumaki_naruto5@konohagakure.com', '2020-03-29 08:18:59'),
       (6, 'Uzumaki', 'Naruto', 'uzumaki_naruto6@konohagakure.com', '2020-03-29 08:18:59'),
       (7, 'Uzumaki', 'Naruto', 'uzumaki_naruto7@konohagakure.com', '2020-03-29 08:18:59'),
       (8, 'Uzumaki', 'Naruto', 'uzumaki_naruto8@konohagakure.com', '2020-03-29 08:18:59'),
       (9, 'Uzumaki', 'Naruto', 'uzumaki_naruto9@konohagakure.com', '2020-03-29 08:18:59'),
       (10, 'Uzumaki', 'Naruto', 'uzumaki_naruto10@konohagakure.com', '2020-03-29 08:18:59'),
       (11, 'Uzumaki', 'Naruto', 'uzumaki_naruto11@konohagakure.com', '2020-03-29 08:18:59'),
       (12, 'Uzumaki', 'Naruto', 'uzumaki_naruto12@konohagakure.com', '2020-03-29 08:18:59'),
       (13, 'Uzumaki', 'Naruto', 'uzumaki_naruto13@konohagakure.com', '2020-03-29 08:18:59'),
       (14, 'Uzumaki', 'Naruto', 'uzumaki_naruto14@konohagakure.com', '2020-03-29 08:18:59'),
       (15, 'Uzumaki', 'Naruto', 'uzumaki_naruto15@konohagakure.com', '2020-03-29 08:18:59'),
       (16, 'Uzumaki', 'Naruto', 'uzumaki_naruto16@konohagakure.com', '2020-03-29 08:18:59'),
       (17, 'Uzumaki', 'Naruto', 'uzumaki_naruto17@konohagakure.com', '2020-03-29 08:18:59'),
       (18, 'Uzumaki', 'Naruto', 'uzumaki_naruto18@konohagakure.com', '2020-03-29 08:18:59'),
       (19, 'Uzumaki', 'Naruto', 'uzumaki_naruto19@konohagakure.com', '2020-03-29 08:18:59'),
       (20, 'Uzumaki', 'Naruto', 'uzumaki_naruto20@konohagakure.com', '2020-03-29 08:18:59');


INSERT INTO roles(id, name) VALUES (1, 'USER');
INSERT INTO roles(id, name) VALUES (2, 'MANAGER');
INSERT INTO roles(id, name) VALUES (3, 'ADMIN');


INSERT INTO users(id, email, username, password)
VALUES (1, 'youn9354@naver.com', 'youn9354', '$2a$10$3MUolsky4nfLXHOgP3EHS.zRN4OM/hbgvnihP.VJdnwpdBqpbv3mC');

INSERT INTO users(id, email, username, password)
VALUES (2, 'admin@example.com', 'giyong0503', '$2a$10$6Hn2bAmJ2BXJUsq8JkThV.3qqArTMig9DFDjEQUGIcYUuEoYQWnKq');

INSERT INTO user_roles(user_id, role_id) VALUES (1, 1);
INSERT INTO user_roles(user_id, role_id) VALUES (1, 2);
INSERT INTO user_roles(user_id, role_id) VALUES (2, 1);
INSERT INTO user_roles(user_id, role_id) VALUES (2, 3);

