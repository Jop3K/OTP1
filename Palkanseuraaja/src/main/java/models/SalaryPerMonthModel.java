package models;

import java.time.*;

public class SalaryPerMonthModel {
	private Month month;
	private double totalSalary;

	public SalaryPerMonthModel(Month month, double totalSalary) {
		
		this.month = month;
		this.totalSalary = totalSalary;

	}

	@Override
	public String toString() {
		return "SalaryPerMonthModel [month=" + month + ", totalSalary=" + totalSalary + "]";
	}

	public Month getMonth() {
		return month;
	}

	public void setMonth(Month month) {
		this.month = month;
	}

	public double getTotalSalary() {
		return totalSalary;
	}

	public void setTotalSalary(double totalSalary) {
		this.totalSalary = totalSalary;
	}

}
