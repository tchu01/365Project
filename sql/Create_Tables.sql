DROP TABLE IF EXISTS RequiredBook;
DROP TABLE IF EXISTS Transaction;
DROP TABLE IF EXISTS Course;
DROP TABLE IF EXISTS Professor;
DROP TABLE IF EXISTS VendorArchive;
DROP TABLE IF EXISTS Textbook;
DROP TABLE IF EXISTS Vendor;
DROP TABLE IF EXISTS Customer;


CREATE TABLE Customer (
	Customer_ID INTEGER AUTO_INCREMENT PRIMARY KEY,
	Customer_Name VARCHAR(45) NOT NULL,
	Phone_Number VARCHAR(10),
	Street VARCHAR(45),
	City VARCHAR(45),
	State VARCHAR(2),
	Zip_Code VARCHAR(7)
);

CREATE TABLE Vendor (
	Vendor_ID INTEGER AUTO_INCREMENT PRIMARY KEY,
	Vender_Name VARCHAR(45) NOT NULL,
	Phone_Number VARCHAR(45),
	Street VARCHAR(45),
	City VARCHAR(45),
	State VARCHAR(2),
	Zip_Code VARCHAR(7)
);

CREATE TABLE Textbook (
	ISBN VARCHAR(13) PRIMARY KEY,
	Title VARCHAR(45) NOT NULL,
	Subject VARCHAR(45),
	Author VARCHAR(45),
	Edition INTEGER,
);

CREATE TABLE VendorArchive(
	Vendor_ID INTEGER,
	ISBN VARCHAR(13),
	Price REAL
	PRIMARY KEY(Vendor_ID, ISBN),
	FOREIGN KEY (Vendor_ID) REFERENCES Vendor(Vendor_ID),
	FOREIGN KEY (ISBN) REFERENCES Textbook(ISBN)
);

CREATE TABLE Professor (
	Professor_ID INTEGER AUTO_INCREMENT PRIMARY KEY,
	Professor_Name VARCHAR(45),
	Email VARCHAR(45),
	Department VARCHAR(45)
);

CREATE TABLE Course (
	Department VARCHAR(45),
	Course_Number INTEGER,
	Professor_ID INTEGER,
	PRIMARY KEY (Department, Course_Number, Professor_ID),
	FOREIGN KEY (Professor_ID) REFERENCES Professor(Professor_ID)
);

CREATE TABLE Transaction (
	Transaction_ID INTEGER AUTO_INCREMENT PRIMARY KEY,
	Vendor_ID INTEGER NOT NULL,
	Customer_ID INTEGER NOT NULL,
	ISBN VARCHAR(13) NOT NULL,
	Purchase_Date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY (Vendor_ID) REFERENCES Vendor(Vendor_ID),
	FOREIGN KEY (Customer_ID) REFERENCES Customer(Customer_ID),
	FOREIGN KEY (ISBN) REFERENCES Textbook(ISBN)
);

CREATE TABLE RequiredBook (
	Department VARCHAR(45),
	Course_Number INTEGER,
	Professor_ID INTEGER,
	Textbook_Required VARCHAR(13),
	PRIMARY KEY (Department, Course_Number, Professor_ID, Textbook_Required),
	FOREIGN KEY (Department, Course_Number, Professor_ID) REFERENCES Course(Department, Course_Number, Professor_ID),
	FOREIGN KEY (Textbook_Required) REFERENCES Textbook(ISBN)
);
