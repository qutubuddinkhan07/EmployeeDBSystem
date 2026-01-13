package com.testConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.LinkedList;
import java.util.List;

public class ConnectionPool {
	private static final int poolSize = 10;
	private static final List<Connection> pool = new LinkedList<>();

	private static final String url = "jdbc:mysql://localhost:3306/employee_db";
	private static final String user = "root";
	private static final String password = "root";

	static {
		for (int i = 1; i <= poolSize; i++) {
			try {
				Connection temp = DriverManager.getConnection(url, user, password);
				pool.add(temp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void destroy() throws Exception {
		for (Connection con : pool) {
			con.close();
		}
		pool.clear();
	}

	public static Connection supply() {
		return pool.remove(0);
	}

	public static void accept(Connection con) {
		pool.add(con);
	}
}
