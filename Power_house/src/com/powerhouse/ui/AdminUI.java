package com.powerhouse.ui;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import com.powerhouse.dao.BillsDAO;
import com.powerhouse.dao.BillsDAOImpl;
import com.powerhouse.dao.ConsumerDAO;
import com.powerhouse.dao.ConsumerDAOImpl;
import com.powerhouse.dto.Bills;
import com.powerhouse.dto.Consumer;
import com.powerhouse.dto.ConsumerImpl;
import com.powerhouse.exception.NoRecordFoundException;
import com.powerhouse.exception.SomethingWentWrongException;

public class AdminUI {
	ConsumerDAO consumerDAO;
	BillsDAO billsDAO;
	Scanner scanner;
	
	public AdminUI(Scanner scanner) {
		consumerDAO = new ConsumerDAOImpl();
		billsDAO = new BillsDAOImpl();
		this.scanner = scanner;
	}
	
	public void registerNewConsumer() {
		System.out.println("\n   Please fill consumer details for registration");
		System.out.println("⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍\n");
		System.out.println("Name");
		String name = scanner.next();
		System.out.println("Create User name");
		String userName = scanner.next();
		System.out.println("Set password (8 digit Max)");
		String pass = scanner.next();
		System.out.println("Mobile no.");
		String mobile = scanner.next();
		
		Consumer consumer = new ConsumerImpl(name, userName, pass, mobile);
		
		try {
			consumerDAO.registerNewConsumer(consumer);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void viewAllConsumers() {
		try {
			List<Consumer> list = consumerDAO.viewAllConsumers();
			System.out.println("\n  Date: "+LocalDate.now()+"                                 All Consumers list");
			System.out.println("☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵\n");
			list.forEach(System.out::println);
			System.out.println("\n☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void deleteConsumer() {
		try {
//			System.out.println("\n⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍");
			System.out.println("\n Please enter Consumer id to delete");
//			System.out.println("⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍\n");
			int conId = scanner.nextInt();
			
			consumerDAO.deleteConsumer(conId);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void generateBillByConsumerId() throws NoRecordFoundException, SomethingWentWrongException {
//		System.out.println("\n⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍");
		System.out.println("\n Please enter consumer ID to generate bill for this month");
//		System.out.println("⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍");
		int conId = scanner.nextInt();
		billsDAO.generateBillByConsumerId(conId);
	}
	
	public void getBillByConsumerId() throws NoRecordFoundException, SomethingWentWrongException {
//		System.out.println("\n⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍");
		System.out.println("\n Please enter consumer ID to see bill");
//		System.out.println("\n⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍");
		int conId = scanner.nextInt();
		
		if(consumerDAO.consumerStatus(conId).equals("Active")) {
			Bills bills = billsDAO.getBillByConsumerId(conId);
			if(bills != null) {
				
				System.out.println("\n           Consumer name: "+bills.getConsumer().getName()+"");
				System.out.println("⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍");
				System.out.println(bills);
				System.out.println("⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍");
			}else {
				System.out.println("\n⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍");
				System.out.println("          No any pending bill");
				System.out.println("⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍");
			}
		}else
		if(consumerDAO.consumerStatus(conId).equals("Inactive")){
			System.out.println("\n⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍");
			System.out.println("          This Consumer is not Active");
			System.out.println("⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍");
		}else {
			System.out.println("\n⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍");
			System.out.println("          Invalid consumer id");
			System.out.println("⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍");
		}
		
		
		
			
	}
	
	
	public void viewAllBills() throws NoRecordFoundException, SomethingWentWrongException {
		List <Bills> list = billsDAO.viewAllBills();
		System.out.println(" \n                                                                    Power house (Uttar Pradesh)");
		System.out.println("\n Date: "+LocalDate.now()+"                                                          Bill list");
		System.out.println("☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵");
		
		for(Bills bill : list) {
			System.out.println("Bill Id: "+ bill.getBill_id()
					+ ", Consumer Id: " + bill.getCon_id()
					+ ", Name: " + bill.getConsumer().getName()
					+ ", ConsUnits: " + bill.getMeterReading()
					+ ", Gross bill: " + bill.getMeterReading()*10
					+ ", Tax: " + (bill.getMeterReading()*10)/100
					+ ", Payable Amt: " + bill.getPayableAmt() 
					+ ", Bill Status: " + bill.getBillStatus()
					+ ", Bill date: " + bill.getBillDate()
					+(bill.getPaymentDate() != null ? (", Payment Date: " + bill.getPaymentDate()): "")
					+ "\n"
					);
		}
		
		System.out.println("\n☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵\n");
		
	}
	
	
}
