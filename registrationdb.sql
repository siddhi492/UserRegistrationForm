CREATE DATABASE registrationdb;

USE registrationdb;

CREATE TABLE users (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(100),
  email VARCHAR(100) UNIQUE,
  password VARCHAR(100),
  role VARCHAR(20),
  PRIMARY KEY (id)
);
select * from users;