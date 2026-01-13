# Employee Database System - JDBC Project

## 📋 Overview
A console-based Employee Management System using Java JDBC and MySQL.

## 🚀 Features
- Add, view, update, and delete employee records
- Search employees by department
- Calculate total salary by department
- Filter employees by salary range
- Console-based menu interface

## 🛠️ Technologies Used
- Java 8+
- JDBC (Java Database Connectivity)
- MySQL Database
- Eclipse IDE

## 📁 Project Structure
```
EmployeeDBSystem/
├── src/
│ ├── com/employee/
│ │ ├── model/ # Employee POJO class
│ │ ├── dao/ # Data Access Object
│ │ ├── testconnection/ # Database connection
│ │ ├── service/ # Display utilities
│ │ └── main/ # Main application
├── lib/ # JDBC driver (not included)
└── README.md
```

## 🏗️ Setup Instructions

### 1. Database Setup
1. Install MySQL
2. Run the SQL script from `sql/setup.sql`

### 2. Project Setup
1. Clone this repository
2. Import project in Eclipse
3. Add MySQL JDBC driver to build path
4. Update database credentials in `DatabaseConnection.java`
5. Run `EmployeeManagementApp.java`

## 📊 Database Schema
```sql
CREATE TABLE employees (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    department VARCHAR(50) NOT NULL,
    salary DECIMAL(10, 2) NOT NULL,
    hire_date DATE NOT NULL
);
```

##📝 Usage
1. Run the application

2. Use the menu to navigate features

3. Follow on-screen instructions

##🤝 Contributing
Feel free to fork and improve this project!

## 📧 Contact
Your Name - Qutubuddin khan 
Project Link: https://github.com/yourusername/EmployeeDBSystem


## **Step 2: Create a SQL Folder**
Create a folder called `sql` in project root and add:

### **2.1 sql/setup.sql**
```sql
-- Employee Database System Setup
-- Created: [Date]

-- Create database
CREATE DATABASE IF NOT EXISTS employee_db;
USE employee_db;

-- Drop table if exists (for clean setup)
DROP TABLE IF EXISTS employees;

-- Create employees table
CREATE TABLE employees (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    department VARCHAR(50) NOT NULL,
    salary DECIMAL(10, 2) NOT NULL,
    hire_date DATE NOT NULL
);

-- Insert sample data
INSERT INTO employees (name, department, salary, hire_date) VALUES
('John Doe', 'IT', 75000.00, '2023-01-15'),
('Jane Smith', 'HR', 65000.00, '2022-03-20'),
('Bob Johnson', 'Finance', 82000.00, '2021-07-10'),
('Alice Brown', 'IT', 78000.00, '2023-05-22'),
('Charlie Wilson', 'Marketing', 60000.00, '2024-02-14');


-- Verify data
SELECT * FROM employees;
SELECT COUNT(*) as total_employees FROM employees;
```
