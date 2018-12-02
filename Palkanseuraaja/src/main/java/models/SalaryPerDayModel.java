package models;

public class SalaryPerDayModel implements ISalaryModel {

	private String day;
	private double salary;
	
	public SalaryPerDayModel(int day, double salary){
		this.day = Integer.toString(day);
		this.salary = salary;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}
}
