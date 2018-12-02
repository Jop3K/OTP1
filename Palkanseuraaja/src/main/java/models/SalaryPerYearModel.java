package models;

import java.time.Year;

public class SalaryPerYearModel implements ISalaryModel {
	/**
	 * Data class used in Calculation and IncomesByYearsDataStrategy
	 * @author Joonas
	 */
	private Year year;
	private double totalSalary;
	
	public SalaryPerYearModel (Year year, double salary) {
		this.year = year;
		this.totalSalary = salary;
	}

	public Year getYear() {
		return year;
	}

	public void setYear(Year year) {
		this.year = year;
	}

	public double getTotalSalary() {
		return totalSalary;
	}

	public void setTotalSalary(double totalSalary) {
		this.totalSalary = totalSalary;
	}
	public void addSalary(double salary){
		totalSalary += salary;
	}
}
