package com.testConnection;

import java.sql.Connection;
import java.sql.Statement;

public class InsertSampleData {
	public static void main(String[] args) {
		Connection con = ConnectionPool.supply();
		try {

			String sql = "INSERT INTO employees (name, department, salary, hire_date) VALUES\r\n"
					+ "('John Doe', 'IT', 75000.00, '2023-01-15'),\r\n"
					+ "('Jane Smith', 'HR', 65000.00, '2022-03-20'),\r\n"
					+ "('Bob Johnson', 'Finance', 82000.00, '2021-07-10'),\r\n"
					+ "('Alice Brown', 'IT', 78000.00, '2023-05-22'),\r\n"
					+ "('Charlie Wilson', 'Marketing', 60000.00, '2024-02-14');";

			Statement st = con.createStatement();
			int row = st.executeUpdate(sql);
			System.out.println(row + " row inserted");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.accept(con);
		}
	}
}
