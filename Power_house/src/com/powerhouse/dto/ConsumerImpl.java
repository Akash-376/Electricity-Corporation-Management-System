package com.powerhouse.dto;

import java.time.LocalDate;

public class ConsumerImpl implements Consumer {
	int ConsumerId;
	String name;
	String userName;
	String password;
	String mobile;
	LocalDate registrationDate;
	String status;
	
	public ConsumerImpl() {}
	
	public ConsumerImpl(String name, String userName, String password, String mobile) {
		super();
		this.name = name;
		this.userName = userName;
		this.password = password;
		this.mobile = mobile;
		this.registrationDate = LocalDate.now();
	}
	
	@Override
	public int getConsumerId() {
		return this.ConsumerId;
	}
	
	@Override
	public void setConsumerId(int ConsumerId) {
		this.ConsumerId = ConsumerId;
	}
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getUserName() {
		return userName;
	}

	@Override
	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String getMobile() {
		return mobile;
	}

	@Override
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Override
	public LocalDate getRegistrationDate() {
		return registrationDate;
	}

	@Override
	public void setRegistrationDate(LocalDate registrationDate) {
		this.registrationDate = registrationDate;
	}
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ConsumerId: " + ConsumerId + " name: " + name + " User Name: " + userName + " Mobile: " + mobile + " Registration Date: " + registrationDate + " Status: " + status
				+ "\n";
	}

	
}
