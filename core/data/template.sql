-- This has to run first, or configured on the mysql workbench/navicat or any, in order to run this program
CREATE DATABASE IF NOT EXISTS ipt;
USE ipt;

CREATE TABLE IF NOT EXISTS students (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    idno VARCHAR(100) UNIQUE,
    fname VARCHAR(100),
    lname VARCHAR(100)
);
