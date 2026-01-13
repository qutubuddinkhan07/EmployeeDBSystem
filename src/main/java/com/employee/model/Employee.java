package com.employee.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Employee {
	private int id;
	private String name;
	private String department;
	private double salary;
	private LocalDate date;

	public Employee(String name, String department, double salary, LocalDate date) {
		super();
		this.name = name;
		this.department = department;
		this.salary = salary;
		this.date = date;
	}
}
