CREATE DATABASE fastfood_cms;
USE fastfood_cms;

CREATE TABLE user_info
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    username   VARCHAR(50) UNIQUE NOT NULL,
    password   VARCHAR(64)        NOT NULL,
    first_name VARCHAR(255) DEFAULT ('Employee'),
    last_name  VARCHAR(255),
    role       VARCHAR(100)        NOT NULL,
    supervisor INT
);