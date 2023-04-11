package com.powerhouse.dto;

import java.time.LocalDate;

public class ConsumerImpl implements Consumer {
	int ConsumerId;
	String name;
	String email;
	String password;
	String mobile;
	LocalDate registrationDate;
	String status;
	
	public ConsumerImpl() {}
	
	public ConsumerImpl(String name, String email, String password, String mobile) {
		super();
		this.name = name;
		this.email = email;
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
	public String getEmail() {
		return email;
	}

	@Override
	public void setEmail(String email) {
		this.email = email;
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
		return "ConsumerId: " + ConsumerId + ", name: " + name + ", User Name: " + email + ", Mobile: " + mobile + ", Registration Date: " + registrationDate + ", Status: " + status
				+ "\n";
	}

	
}
