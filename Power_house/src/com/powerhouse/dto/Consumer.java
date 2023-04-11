package com.powerhouse.dto;

import java.time.LocalDate;

public interface Consumer {
	public int getConsumerId();
	
	public void setConsumerId(int ConsumerId);
	
	public String getName();

	public void setName(String name);
	
	public String getEmail();

	public void setEmail(String userName);

	public String getPassword();

	public void setPassword(String password);
	
	public String getMobile();

	public void setMobile(String mobile);

	public LocalDate getRegistrationDate();

	public void setRegistrationDate(LocalDate registrationDate);
	
	public String getStatus();
	
	public void setStatus(String status);
}
