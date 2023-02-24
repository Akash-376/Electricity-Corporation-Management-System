package com.powerhouse.dto;

import java.time.LocalDate;

public class BillsImpl implements Bills {
	int bill_id;
	int con_id;
	int meterReading;
	double payableAmt;
	String BillStatus;
	LocalDate billDate;
	LocalDate paymentDate;
	
	
	Consumer consumer;
	
	public BillsImpl() {}
	
	public BillsImpl(int con_id) {
		super();
		this.con_id = con_id;
		int reading = (int) (90 +( 150-90) * Math.random());
		this.meterReading = reading;
		int amt = 120 + (reading*10); // 120 is fixed charge
		double amtWithTax = (amt*102.5)/100;  // tax is 2.5%
		payableAmt = amtWithTax;
		billDate = LocalDate.now();
		
	}
	
	

	public int getBill_id() {
		return bill_id;
	}

	public void setBill_id(int bill_id) {
		this.bill_id = bill_id;
	}

	public int getCon_id() {
		return con_id;
	}

	public void setCon_id(int con_id) {
		this.con_id = con_id;
	}

	public int getMeterReading() {
		return meterReading;
	}

	public void setMeterReading(int meterReading) {
		this.meterReading = meterReading;
	}

	
	public double getPayableAmt() {
		return payableAmt;
	}

	public void setPayableAmt(double payableAmt) {
		this.payableAmt = payableAmt;
	}
	
	

	public String getBillStatus() {
		return BillStatus;
	}

	public void setBillStatus(String BillStatus) {
		this.BillStatus = BillStatus;
	}

	public LocalDate getBillDate() {
		return billDate;
	}

	public void setBillDate(LocalDate billDate) {
		this.billDate = billDate;
	}
	
	

	public Consumer getConsumer() {
		return consumer;
	}

	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
	}
	
	

	public LocalDate getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}

	@Override
	public String toString() {
		
		return (bill_id!=0? ("\nBill Id: "+ bill_id) : "")
				+ "\nConsumer Id: " + con_id 
				+ "\nName: " + consumer.getName()
				+ "\nConsUnits: " + meterReading
				+ "\nGross bill: " + meterReading*10
				+ "\nTax: " + (meterReading*10*2.5)/100
				+ "\nPayable Amt: " + payableAmt 
				+ "\nBill Status: " + BillStatus
				+(paymentDate != null ? ("\nPayment Date: " + paymentDate): "")+ "\n";
				
	}

	
	
	
//	public static void main(String[] args) {
//		int ran = (int) (90 +( 150-90) * Math.random());
//		System.out.println(ran);
//		
//	}
	
}
