# Electricity corporation management system

![ele](https://user-images.githubusercontent.com/112763866/221608431-85e05ee8-5c58-40e9-b527-391115b478af.png)

# Description
The Electricity Corporation Management System is a software application designed to manage the operations of an electricity corporation. The system is comprised of two modules: the user module and the admin module. It is very helpful to manage users.

# ADMIN

The admin module is primarily designed for the management of the system. It allows the admin to register new consumers by capturing their details such as name, address, and contact information. The admin can also delete existing consumers from the system. The admin module also allows the admin to generate bills for each consumer, view all bills that have been generated and keep a record of all transactions made by consumers.
- Admin username : admin
- Admin password : admin

## Functionalities
- Register a new consumer
- View all consumers
- Delete existing consumer (makes existing consumer status Inactive)
- Generate bill
- Get bill by consumer ID
- View all bills

# USER / CONSUMER

The user module is designed for the consumers to interact with the system. Users can log in to the system using their registered details to access their personal information and transaction records. Users can also pay their bills 

## Functionalities
- Pay bill
- View all transactions

## ER Diagram
![eletricity ER Diagram](https://user-images.githubusercontent.com/112763866/221605736-c252d6a1-4e73-426f-9723-5c0c1fc1d5bd.png)

## Tech stack used
- Java
- MYSQL
- JDBC

## Contact
- [Linkedin](https://www.linkedin.com/in/akash-chauhan-03b105243/)

# Steps to run this project in your System

## Step 1
- Clone my repository into your System.

## Step 2
- Open this repository with STS (Spring Tool Suit).

## Step 3
- Go into <b>dbdetails.properties</b> file inside the <b>src</b> folder and change the username and password according to your MySql username and password.

## Step 4
- Open your MySql Command Line Client.


## Step 5
- Copy and paste the following sql queries into your MySql Command Line Client.

```mysql
CREATE DATABASE Power_house_Electricity;

CREATE TABLE Consumers(
Consumer_id INT PRIMARY KEY AUTO_INCREMENT,
Name VARCHAR(20) NOT NULL,
User_name VARCHAR(50) UNIQUE,
Password VARCHAR(8) NOT NULL,
Mobile_no VARCHAR(10) UNIQUE,
Registration_date date,
Status VARCHAR(10) DEFAULT 'Active'
);


CREATE TABLE Bills(
Bill_id INT PRIMARY KEY AUTO_INCREMENT,
Consumer_id INT,
Units_consumption INT,
Bill_amount double(10,2),
Bill_status VARCHAR(8) DEFAULT 'Pending',
Date_of_bill datetime,
Payment_date datetime,
CONSTRAINT cons_bill
FOREIGN KEY (Consumer_id) REFERENCES Consumers (Consumer_id)
);
```

- Now program is ready to run
