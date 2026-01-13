package com.testConnection;

import java.sql.Connection;
import java.sql.Statement;

public class CreateSchema_table {
	public static void main(String[] args) throws Exception {
		Connection con = ConnectionPool.supply();

		// =========== database creation ===========
//		try {
//			String sql = "create database employee_db";
//			Statement st = con.createStatement();
//			st.execute(sql);
//
//			ConnectionPool.accept(con);
//			System.out.println("Database created");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		// table creation
		try {
			String sql = "CREATE TABLE IF NOT EXISTS employees(" + "id int PRIMARY KEY AUTO_INCREMENT,"
					+ "name varchar(100) NOT NULL," + "department varchar(50) NOT NULL,"
					+ "salary DECIMAL(10,2) NOT NULL," + "hire_date DATE NOT NULL" + ");";

			Statement st = con.createStatement();
			st.execute(sql);

			ConnectionPool.accept(con);
			System.out.println("Table created");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
