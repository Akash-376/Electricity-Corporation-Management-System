package com.powerhouse.ui;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	
	// method to register a new consumer
	public void registerNewConsumer() {
			
		
		try {
			
			System.out.println("\n   Please fill consumer details for registration");
			System.out.println("⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍\n");
			
			
			// name validation that will accept only English letters in 3 to 20 char limit with space(optionally)
			
			System.out.println("Name");
			scanner.nextLine(); // to take scanner at the starting of the next line
			String name = scanner.nextLine();
			
			
			Pattern nameRegex = Pattern.compile("^[A-Za-z]{3,}(?:\\s+[A-Za-z]+)*(?:\\s+[A-Za-z]+)?$");
			Matcher nameMatcher = nameRegex.matcher(name);
			// this loop will run until it will not get a valid name
	        while (!nameMatcher.matches()) {
	        	System.out.println(name);
	            System.out.println("\033[31mmin 3 and max 20 characters required without any Integer and special character. only space is accepted\033[0m");  // red color code => \033[31m
	            System.out.println("Please try again");
	            name = scanner.next();
	            nameMatcher = nameRegex.matcher(name);
	        }
			
			
			// email validation
			System.out.println("Enter email id");
			String userName = scanner.next();
			Pattern emailRegex = Pattern.compile("^(.+)@(.+)$");
			Matcher emailMatcher = emailRegex.matcher(userName);
			// this loop will run until it will not get a valid format of email id
	        while (!emailMatcher.matches()) {
	            System.out.println("\033[31mInvalid email\033[0m, please try again");
	            userName = scanner.next();
	            emailMatcher = emailRegex.matcher(userName);
	        }
			
			// password validation code
			System.out.println("Set password (minimum 6 digits requited)");
			String pass = scanner.next();
			Pattern passwordRegex = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$");
			Matcher passwordMatcher = passwordRegex.matcher(pass);
			// this loop will run until it will not get a Strong password
	        while (!passwordMatcher.matches()) {
	            System.out.println("\033[31mweak password\033[0m, please try again");
	            pass = scanner.next();
	            passwordMatcher = passwordRegex.matcher(pass);
	        }
			
			
	     // mobile number validation code
			System.out.println("Mobile no.");
			String mobile = scanner.next();			
			Pattern mobileRegex = Pattern.compile("^[6-9]\\d{9}$");
	        Matcher mobileMatcher = mobileRegex.matcher(mobile);
	        
	        // this loop will run until it will not get a valid mobile number
	        while (!mobileMatcher.matches()) {
	            System.out.println("\033[31mInvalid mobile number format\033[0m. \nPlease enter a valid 10-digit Indian mobile number.");
	            mobile = scanner.next();
	            mobileMatcher = mobileRegex.matcher(mobile);
	        }

	        
			Consumer consumer = new ConsumerImpl(name, userName, pass, mobile);
			
			consumerDAO.registerNewConsumer(consumer);
		} catch (SomethingWentWrongException e) {
			System.out.println(e);
		}catch (InputMismatchException e) {
			System.out.println("\033[31mInvalid input\033[0m, please try again");
		}
	}
	
	
	// method to view all registered consumers 
	public void viewAllConsumers() {
		try {
			// getting consumer list from DAO layer
			List<Consumer> list = consumerDAO.viewAllConsumers();
			System.out.println("\n  Date: "+LocalDate.now()+"                                 All Consumers list");
			System.out.println("☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵\n");
			
			// printing the consumer list one by one
			list.forEach(System.out::println);
			System.out.println("\n☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	// method to delete consumer
	public void deleteConsumer() {
		try {
			System.out.println("\n Please enter Consumer id to delete");
			int conId = scanner.nextInt();
			
			consumerDAO.deleteConsumer(conId);
		} catch (InputMismatchException e) {
			System.out.println("\033[31mInvalid input\033[0m, please try again");
			scanner.nextLine(); // clear the input buffer
		}catch (SomethingWentWrongException e) {
			System.out.println("\033[31mSomething went wrong\033[0m");
		}
	}
	
	
	// method to generate consumer bill
	public void generateBillByConsumerId() throws NoRecordFoundException, SomethingWentWrongException {
		try {
			System.out.println("\n Please enter consumer ID to generate bill for this month");
			int conId = scanner.nextInt();
			billsDAO.generateBillByConsumerId(conId);
		} catch (InputMismatchException e) {
			System.out.println("\033[31mInvalid input\033[0m, please try again");
			scanner.nextLine(); // clear the input buffer
		}catch (Exception e) {
			System.out.println(e);
		}
	}
	
	// method to get bill by consumer Id
	public void getBillByConsumerId() throws NoRecordFoundException, SomethingWentWrongException {

		System.out.println("\n Please enter consumer ID to see bill");

		try {
			int conId = scanner.nextInt();
			
			// if consumer is active
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
				// if consumer is not active
			if(consumerDAO.consumerStatus(conId).equals("Inactive")){
				System.out.println("\n⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍");
				System.out.println("          This Consumer is not Active");
				System.out.println("⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍");
			}else {
				// if not getting any status then customer is invalid
				System.out.println("\n⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍");
				System.out.println("          \033[31mInvalid consumer id\033[0m");
				System.out.println("⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍");
			}
		} catch (InputMismatchException e) {
			System.out.println("\033[31mInvalid input\033[0m, please try again");
			scanner.nextLine(); // clear the input buffer
		}
		
	}
	
	// method to view all the bills
	public void viewAllBills() throws NoRecordFoundException, SomethingWentWrongException {
		// getting bill list from DAO layer
		List <Bills> list = billsDAO.viewAllBills();
		System.out.println(" \n                                                                    Power house (Uttar Pradesh)");
		System.out.println("\n Date: "+LocalDate.now()+"                                                          Bill list");
		System.out.println("☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵");
		
		// payment date will only be print if bill has already been paid,
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
