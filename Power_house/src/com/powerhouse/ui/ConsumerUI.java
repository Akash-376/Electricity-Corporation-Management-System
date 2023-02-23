package com.powerhouse.ui;
import java.util.Scanner;

import com.powerhouse.dao.BillsDAO;
import com.powerhouse.dao.BillsDAOImpl;
import com.powerhouse.dao.ConsumerDAO;
import com.powerhouse.dao.ConsumerDAOImpl;
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
	
	public void payBill() throws SomethingWentWrongException, NoRecordFoundException {
		System.out.println("Please enter your Consumer ID");
		int conID = scanner.nextInt();
		billsDAO.payBill(conID);
	}
	
	
}
