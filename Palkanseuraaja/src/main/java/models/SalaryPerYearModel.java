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


	public double getTotalSalary() {
		return totalSalary;
	}

}
