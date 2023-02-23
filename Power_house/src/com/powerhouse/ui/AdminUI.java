package com.powerhouse.ui;
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
		System.out.println("   Please fill consumer details for registration");
		System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
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
			list.forEach(System.out::print);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void deleteConsumer() {
		try {
			System.out.println("Please enter Consumer id to delete");
			int conId = scanner.nextInt();
			
			consumerDAO.deleteConsumer(conId);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void generateBillByConsumerId() throws NoRecordFoundException, SomethingWentWrongException {
		System.out.println("Please enter consumer ID to generate bill for this month");
		int conId = scanner.nextInt();
		billsDAO.generateBillByConsumerId(conId);
	}
	
	public void getBillByConsumerId() throws NoRecordFoundException, SomethingWentWrongException {
		System.out.println("Please enter consumer ID to see bill");
		int conId = scanner.nextInt();
		Bills bills = billsDAO.getBillByConsumerId(conId);
		System.out.println(bills);
	}
	
	
	public void viewAllBills() throws NoRecordFoundException, SomethingWentWrongException {
		List <Bills> list = billsDAO.viewAllBills();
		list.forEach(System.out::println);
	}
}
