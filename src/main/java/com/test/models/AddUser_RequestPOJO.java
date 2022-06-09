package com.test.models;

public class AddUser_RequestPOJO {

	private String accountno;
	private String departmentno;
	private String salary;
	private String pincode;
	
	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}
	
	public String getAccountno() {
		return accountno;
	}
	
	public void setDepartmentno(String departmentno) {
		this.departmentno = departmentno;
	}
	
	public String getDepartmentno() {
		return departmentno;
	}
	
	public void setSalary(String salary) {
		this.salary = salary;
	}
	
	public String getSalary() {
		return salary;
	}
	
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	
	public String getPincode() {
		return pincode;
	}
}
