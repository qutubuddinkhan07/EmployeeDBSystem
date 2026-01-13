package com.employee.main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import com.employee.dao.EmployeeDAO;
import com.employee.model.Employee;
import com.employee.service.DisplayUtil;

public class EmployeeManagementApp {
	private static EmployeeDAO employeeDAO = new EmployeeDAO();
	private static Scanner sc = new Scanner(System.in);
	private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public static void main(String[] args) {
		System.out.println("====================================");
		System.out.println("   EMPLOYEE MANAGEMENT SYSTEM");
		System.out.println("====================================");

		boolean running = true;

		while (running) {
			displayMenu();
			int choice = getIntInput("Enter your choice: ");

			switch (choice) {
			case 1:
				addEmployee();
				break;
			case 2:
				viewAllEmployees();
				break;
			case 3:
				searchEmployeeById();
				break;
			case 4:
				searchEmployeesByDepartment();
				break;
			case 5:
				updateEmployee();
				break;
			case 6:
				deleteEmployee();
				break;
			case 7:
				viewTotalSalaryByDepartment();
				break;
			case 8:
				filterEmployeesBySalaryRange();
				break;
			case 9:
				viewDepartmentStatistics();
				break;
			case 10:
				System.out.println("\nThank you for using Employee Management System!");
				running = false;
				break;
			default:
				System.out.println("\nInvalid choice! Please enter 1-10.");
			}

			if (running) {
				System.out.print("\nPress Enter to continue...");
				sc.nextLine();
			}
		}

		sc.close();
	}

	private static void displayMenu() {
		System.out.println("\n=== MAIN MENU ===");
		System.out.println("1. Add New Employee");
		System.out.println("2. View All Employees");
		System.out.println("3. Search Employee by ID");
		System.out.println("4. Search Employees by Department");
		System.out.println("5. Update Employee Details");
		System.out.println("6. Delete Employee");
		System.out.println("7. View Total Salary by Department");
		System.out.println("8. Filter Employees by Salary Range");
		System.out.println("9. View Department Statistics");
		System.out.println("10. Exit");
		System.out.println("=================");
	}

	// 1. Add New Employee
	private static void addEmployee() {
		System.out.println("\n=== ADD NEW EMPLOYEE ===");

		System.out.println("Enter Name:");
		String name = sc.nextLine();

		System.out.println("Enter Department: ");
		String department = sc.nextLine();

		double salary = getDoubleInput("Enter Salary: ");
		LocalDate hireDate = getDateInput("Enter Hire Date(YYYY-MM-DD): ");

		Employee newEmployee = new Employee(name, department, salary, hireDate);

		if (employeeDAO.addEmployee(newEmployee)) {
			System.out.println("\n✓ Employee added successfully!");
		} else {
			System.out.println("\n✗ Failed to add employee.");
		}
	}

	// 2. View All Employees
	private static void viewAllEmployees() {
		List<Employee> employees = employeeDAO.getAllEmployees();
		DisplayUtil.displayEmployeeTable(employees);
	}

	// 3. Search Employee by ID
	private static void searchEmployeeById() {
		int id = getIntInput("\nEnter Employee ID to search: ");
		Employee employee = employeeDAO.getEmployeeById(id);
		DisplayUtil.displayEmployeeDetails(employee);
	}

	// 4. Search Employees by Department (FEATURE 1)
	private static void searchEmployeesByDepartment() {
		System.out.print("\nEnter Department to search: ");
		String department = sc.nextLine();

		List<Employee> employees = employeeDAO.getEmployeesByDepartment(department);

		if (employees.isEmpty()) {
			System.out.println("\nNo employees found in " + department + " department.");
		} else {
			System.out.println("\n=== EMPLOYEES IN " + department.toUpperCase() + " DEPARTMENT ===");
			DisplayUtil.displayEmployeeTable(employees);
		}
	}

	// 5. Update Employee
	private static void updateEmployee() {
		int id = getIntInput("\nEnter Employee ID to update: ");
		Employee employee = employeeDAO.getEmployeeById(id);

		if (employee == null) {
			System.out.println("\nEmployee not found!");
			return;
		}

		System.out.println("\nCurrent Details:");
		DisplayUtil.displayEmployeeDetails(employee);

		System.out.println("\nEnter new details (press Enter to keep current value):");

		System.out.print("Name [" + employee.getName() + "]: ");
		String name = sc.nextLine();
		if (!name.isEmpty()) {
			employee.setName(name);
		}

		System.out.print("Department [" + employee.getDepartment() + "]: ");
		String department = sc.nextLine();
		if (!department.isEmpty()) {
			employee.setDepartment(department);
		}

		String salaryInput = getStringInput("Salary [" + employee.getSalary() + "]: ");
		if (!salaryInput.isEmpty()) {
			try {
				employee.setSalary(Double.parseDouble(salaryInput));
			} catch (NumberFormatException e) {
				System.out.println("Invalid salary format. Keeping current value.");
			}
		}

		String dateInput = getStringInput("Hire Date [" + employee.getDate() + "]: ");
		if (!dateInput.isEmpty()) {
			try {
				employee.setDate(LocalDate.parse(dateInput, dateFormatter));
			} catch (DateTimeParseException e) {
				System.out.println("Invalid date format. Keeping current value.");
			}
		}

		if (employeeDAO.updateEmployee(employee)) {
			System.out.println("\n✓ Employee updated successfully!");
		} else {
			System.out.println("\n✗ Failed to update employee.");
		}
	}

	// 6. Delete Employee
	private static void deleteEmployee() {
		int id = getIntInput("\nEnter Employee ID to delete: ");

		System.out.print("Are you sure you want to delete employee #" + id + "? (yes/no): ");
		String confirm = sc.nextLine();

		if (confirm.equalsIgnoreCase("yes")) {
			if (employeeDAO.deleteEmployee(id)) {
				System.out.println("\n✓ Employee deleted successfully!");
			} else {
				System.out.println("\n✗ Failed to delete employee. Employee may not exist.");
			}
		} else {
			System.out.println("\nDeletion cancelled.");
		}
	}

	// 7. View Total Salary by Department (FEATURE 2)
	private static void viewTotalSalaryByDepartment() {
		employeeDAO.displayTotalSalaryByDepartment();
	}

	// 8. Filter Employees by Salary Range (FEATURE 3)
	private static void filterEmployeesBySalaryRange() {
		System.out.println("\n=== FILTER EMPLOYEES BY SALARY RANGE ===");

		double minSalary = getDoubleInput("Enter minimum salary: ");
		double maxSalary = getDoubleInput("Enter maximum salary: ");

		if (minSalary > maxSalary) {
			System.out.println("\n✗ Minimum salary cannot be greater than maximum salary!");
			return;
		}

		List<Employee> employees = employeeDAO.getEmployeesBySalaryRange(minSalary, maxSalary);

		if (employees.isEmpty()) {
			System.out.printf("\nNo employees found with salary between $%.2f and $%.2f\n", minSalary, maxSalary);
		} else {
			System.out.printf("\n=== EMPLOYEES WITH SALARY $%.2f - $%.2f ===\n", minSalary, maxSalary);
			DisplayUtil.displayEmployeeTable(employees);
		}
	}

	// 9. Bonus: Department Statistics
	private static void viewDepartmentStatistics() {
		System.out.println("\n=== DEPARTMENT STATISTICS ===");

		List<Employee> allEmployees = employeeDAO.getAllEmployees();

		// Simple statistics
		long itCount = allEmployees.stream().filter(e -> e.getDepartment().equalsIgnoreCase("IT")).count();

		long hrCount = allEmployees.stream().filter(e -> e.getDepartment().equalsIgnoreCase("HR")).count();

		long financeCount = allEmployees.stream().filter(e -> e.getDepartment().equalsIgnoreCase("Finance")).count();

		System.out.println("\nEmployee Count by Department:");
		System.out.println("IT: " + itCount + " employees");
		System.out.println("HR: " + hrCount + " employees");
		System.out.println("Finance: " + financeCount + " employees");
		System.out.println("Marketing: " + (allEmployees.size() - (itCount + hrCount + financeCount)) + " employees");
		System.out.println("\nTotal Employees: " + allEmployees.size());
	}

	// Utility methods for input validation
	private static int getIntInput(String prompt) {
		while (true) {
			System.out.print(prompt);
			try {
				return Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Invalid input! Please enter a valid number.");
			}
		}
	}

	private static double getDoubleInput(String prompt) {
		while (true) {
			System.out.print(prompt);
			try {
				return Double.parseDouble(sc.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Invalid input! Please enter a valid number.");
			}
		}
	}

	private static String getStringInput(String prompt) {
		System.out.print(prompt);
		return sc.nextLine();
	}

	private static LocalDate getDateInput(String prompt) {
		while (true) {
			System.out.print(prompt);
			try {
				String input = sc.nextLine();
				return LocalDate.parse(input, dateFormatter);
			} catch (DateTimeParseException e) {
				System.out.println("Invalid date format! Please use YYYY-MM-DD format.");
			}
		}
	}
}
