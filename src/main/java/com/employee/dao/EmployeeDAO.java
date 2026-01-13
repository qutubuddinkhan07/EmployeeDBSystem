package com.employee.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.employee.model.Employee;
import com.testConnection.ConnectionPool;

public class EmployeeDAO {
	// 1. adding employee
	public boolean addEmployee(Employee employee) {
		String sql = "insert into employees(name, department, salary, hire_date) values(?,?,?,?)";
		Connection con = ConnectionPool.supply();
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, employee.getName());
			ps.setString(2, employee.getDepartment());
			ps.setDouble(3, employee.getSalary());
			ps.setDate(4, Date.valueOf(employee.getDate()));

			int rowsAffected = ps.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			System.err.println("Error adding employee: " + e.getMessage());
			return false;
		}
	}

	// 2. Get all employee
	public List<Employee> getAllEmployees() {
		List<Employee> employees = new ArrayList<>();
		String sql = "SELECT * FROM  employees ORDER BY id";

		Connection con = ConnectionPool.supply();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);

			while (rs.next()) {
				Employee emp = extractEmployeeFromResultSet(rs);
				employees.add(emp);
			}
		} catch (SQLException e) {
			System.err.println("Error fetching employees: " + e.getMessage());
		} finally {
			ConnectionPool.accept(con);
		}
		return employees;
	}

	// 3. Get employee by id
	public Employee getEmployeeById(int id) {
		String sql = "SELECT * FROM employees WHERE id = ?";
		Connection con = ConnectionPool.supply();

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return extractEmployeeFromResultSet(rs);
			}
		} catch (SQLException e) {
			System.err.println("Error fetching employee: " + e.getMessage());
		} finally {
			ConnectionPool.accept(con);
		}

		return null;
	}

	// 4. Update employee
	public boolean updateEmployee(Employee employee) {
		String sql = "UPDATE employees SET name = ?, department = ?, salary = ?, hire_date = ? WHERE id = ?";
		Connection con = ConnectionPool.supply();

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, employee.getName());
			ps.setString(2, employee.getDepartment());
			ps.setDouble(3, employee.getSalary());
			ps.setDate(4, Date.valueOf(employee.getDate()));
			ps.setInt(5, employee.getId());

			int rowsAffected = ps.executeUpdate();

			return rowsAffected > 0;
		} catch (SQLException e) {
			System.err.println("Error updating employee: " + e.getMessage());
			return false;
		} finally {
			ConnectionPool.accept(con);
		}
	}

	// 5. Delete employee
	public boolean deleteEmployee(int id) {
		String sql = "DELETE FROM employees WHERE id = ?";
		Connection con = ConnectionPool.supply();
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			int rowsAffected = ps.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			System.err.println("Error deleting employee: " + e.getMessage());
			return false;
		} finally {
			ConnectionPool.accept(con);
		}
	}

	// 6. Search employee by department (FEATURE 1)
	public List<Employee> getEmployeesByDepartment(String department) {
		List<Employee> employees = new ArrayList<>();
		String sql = "SELECT * FROM employees WHERE department = ? ORDER BY name";
		Connection con = ConnectionPool.supply();

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, department);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Employee emp = extractEmployeeFromResultSet(rs);
				employees.add(emp);
			}
		} catch (SQLException e) {
			System.err.println("Error while searching department: " + e.getMessage());
		} finally {
			ConnectionPool.accept(con);
		}

		return employees;
	}

	// 7. Calculate total salary by department
	public void displayTotalSalaryByDepartment() {
		String sql = "SELECT department, SUM(salary) as total_salary FROM employees GROUP BY department";
		Connection con = ConnectionPool.supply();

		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);

			System.out.println("\n=== TOTAL SALARY BY DEPARTMENT ===");
			System.out.println("+---------------+-----------------+");
			System.out.println("| Department    | Total Salary    |");
			System.out.println("+---------------+-----------------+");

			double grandTotal = 0;
			while (rs.next()) {
				String dept = rs.getString("department");
				double total = rs.getDouble("total_salary");

				grandTotal += total;
				System.out.printf("| %-13s | $%-14.2f |\n", dept, total);

				System.out.println("+---------------+-----------------+");
				System.out.printf("| Grand Total   | $%-14.2f |\n", grandTotal);
				System.out.println("+---------------+-----------------+\n");
			}
		} catch (Exception e) {
			System.err.println("Error calculating salary: " + e.getMessage());
		} finally {
			ConnectionPool.accept(con);
		}
	}

	// 8. Filter employees by salary
	public List<Employee> getEmployeesBySalaryRange(double minSalary, double maxSalary) {
		List<Employee> employees = new ArrayList<>();
		String sql = "SELECT * FROM employees WHERE salary BETWEEN ? AND ? ORDER BY salary DESC";
		Connection con = ConnectionPool.supply();

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setDouble(1, minSalary);
			ps.setDouble(2, maxSalary);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Employee emp = extractEmployeeFromResultSet(rs);
				employees.add(emp);
			}
		} catch (Exception e) {
			System.err.println();
		} finally {
			ConnectionPool.accept(con);
		}

		return employees;
	}

	// 9. Helper method to extract employee from result set
	private Employee extractEmployeeFromResultSet(ResultSet rs) throws SQLException {
		int id = rs.getInt("id");
		String name = rs.getString("name");
		String department = rs.getString("department");
		Double salary = rs.getDouble("salary");
		LocalDate hireDate = rs.getDate("hire_date").toLocalDate();

		return new Employee(id, name, department, salary, hireDate);
	}
}
