package com.powerhouse.ui;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import com.powerhouse.dao.BillsDAO;
import com.powerhouse.dao.BillsDAOImpl;
import com.powerhouse.dao.ConsumerDAO;
import com.powerhouse.dao.ConsumerDAOImpl;
import com.powerhouse.dto.Bills;
import com.powerhouse.exception.NoRecordFoundException;
import com.powerhouse.exception.SomethingWentWrongException;


public class ConsumerUI {
	ConsumerDAO consumerDAO;
	BillsDAO billsDAO;
	Scanner scanner;
	
	public ConsumerUI(Scanner scanner) {
		consumerDAO = new ConsumerDAOImpl();
		billsDAO = new BillsDAOImpl();
		this.scanner = scanner;
	}
	
	public void payBill(String User_name) throws SomethingWentWrongException, NoRecordFoundException {

		billsDAO.payBill(User_name); // Invoking payBill method of billsDAO layer
	}
	
	public void viewAllTransactions(String user_name) throws SomethingWentWrongException, NoRecordFoundException {
		
		List<Bills> list =  billsDAO.viewAllTransactions(user_name);
		
		if(list != null) {
			System.out.println(" \n                                                                  ⚠ Power house (Uttar Pradesh)");
			System.out.println("\n Date: "+LocalDate.now()+"                                                        All transactions");
			System.out.println("☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵\n");
			
			for(Bills bill : list) {
				System.out.println("Bill Id: "+ bill.getBill_id()
						+ ", Consumer Id: " + bill.getCon_id()
						+ ", Name: " + bill.getConsumer().getName()
						+ ", Paied Amt: " + bill.getPayableAmt() 
						+ ", Bill Status: " + bill.getBillStatus()
						+ ", Bill date: " + bill.getBillDate()
						+(bill.getPaymentDate() != null ? (", Payment Date: " + bill.getPaymentDate()): "")
						+ "\n"
						);
			}
			
			System.out.println("\n☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵☵\n");
			
		
		}else {
			System.out.println("\n⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍\n");
			System.out.println("   No record found");
			System.out.println("\n⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍\n");
		}
		
	}
	
	
}
