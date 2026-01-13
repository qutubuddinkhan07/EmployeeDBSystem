package com.employee.service;

import java.util.List;

import com.employee.model.Employee;

public class DisplayUtil {
	public static void displayEmployeeTable(List<Employee> employees) {
		if (employees.isEmpty()) {
			System.out.println("No employees Found!!");
			return;
		}

		System.out.println("\n=== EMPLOYEE LIST ===");
		System.out.println("+-----+----------------------+---------------+-------------+--------------+");
		System.out.println("| ID  | Name                 | Department    | Salary      | Hire Date    |");
		System.out.println("+-----+----------------------+---------------+-------------+--------------+");

		for (Employee emp : employees) {
			System.out.printf("| %-3d | %-20s | %-13s | $%-10.2f | %-12s |\n", emp.getId(), emp.getName(),
					emp.getDepartment(), emp.getSalary(), emp.getDate());
		}
		System.out.println("+-----+----------------------+---------------+-------------+--------------+");
		System.out.println("Total Employees: " + employees.size());
	}

	public static void displayEmployeeDetails(Employee employee) {
		if (employee == null) {
			System.out.println("\nEmployee not found!");
			return;
		}

		System.out.println("\n=== EMPLOYEE DETAILS ===");
		System.out.println("ID: " + employee.getId());
		System.out.println("Name: " + employee.getName());
		System.out.println("Department: " + employee.getDepartment());
		System.out.println("Salary: $" + employee.getSalary());
		System.out.println("Hire Date: " + employee.getDate());
	}
}
