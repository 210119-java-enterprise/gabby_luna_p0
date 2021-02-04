/*
 Script to initialize Basic ATM tables. 
 
 Users:
 	UserId
 	FirstName
 	LastName
 	Username
 	Password
 	
  Accounts:
  	AccountId
  	Type
  	UserId
  	Bal
  	
  	
  Transactions:
  	AccountId
  	Amount
  	TransactionDate
  	
*/

--reset Database
drop table if exists Transactions;
drop table if exists Accounts;
drop table if exists Users;


--Table Creation
create table Users ( 
	UserId 				serial constraint Users_pk primary key, 
	FirstName			varchar(25),
	LastName 			varchar(25),
	Username 			varchar(25), 
	User_password		varchar(25)	
	
);

create table Accounts (
	AccountId			serial constraint Accounts_pk primary key,
	Account_Type		varchar(25) not null,
	UserId				int ,
	Balance				numeric(11, 2) not null,
	
	foreign key (UserId) references Users (UserId)
);

ALTER SEQUENCE Accounts_AccountId_seq RESTART WITH 1001 INCREMENT BY 7;

create table Transactions (
	TransactionId		serial constraint Transactions_pk primary key,
	Transaction_Type	varchar(25) not null,
	AccountId			int ,
	Amount				numeric(11, 2) not null,
	transaction_date	date not null default current_timestamp,
	
	foreign key (AccountId) references Accounts (AccountId)
);



--Test Values
insert into Users (firstname, lastname, username, user_password)
values
	('Gabby', 'Luna', 'Gab', 'Moon');

insert into accounts 
values
	(1001, 'CHK', 1, 0.00),
	(1008, 'SVG', 1, 0.00);

insert into transactions (transaction_type, accountid, amount)
values
	('CREDIT', 1001, 250.00);

--Test Queries
select Amount, accountid 
	from transactions t 
	group by  transactionid;

select sum(Amount)
from (
	select Amount, accountid 
	from transactions t 
	group by  transactionid) as b
where B.accountid = 1001;
	
