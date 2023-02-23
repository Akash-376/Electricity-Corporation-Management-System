package com.powerhouse.dto;

import java.time.LocalDate;

public interface Bills {
	
	public int getBill_id();

	public void setBill_id(int bill_id);
	
	public int getCon_id();

	public void setCon_id(int con_id);

	public int getMeterReading();

	public void setMeterReading(int meterReading);
	
	public double getPayableAmt();

	public void setPayableAmt(double payableAmt);
	
	public String getBillStatus();

	public void setBillStatus(String status);

	public LocalDate getBillDate();

	public void setBillDate(LocalDate billDate);
	
	public Consumer getConsumer();

	public void setConsumer(Consumer consumer);
	
	public LocalDate getPaymentDate();

	public void setPaymentDate(LocalDate paymentDate);
}