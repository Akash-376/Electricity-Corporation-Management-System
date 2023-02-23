package com.powerhouse.ui;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.powerhouse.dao.ConsumerDAO;
import com.powerhouse.dao.ConsumerDAOImpl;
import com.powerhouse.exception.NoRecordFoundException;
import com.powerhouse.exception.SomethingWentWrongException;
public class Main {
	public static void main(String[] args) throws NoRecordFoundException, SomethingWentWrongException {
		
		ConsumerDAO consumerDAO = new ConsumerDAOImpl();
		
		Scanner sc = new Scanner(System.in);
		
		AdminUI adminUI = new AdminUI(sc);
		ConsumerUI consumerUI = new ConsumerUI(sc);
		
		System.out.println("******************************************");
		System.out.println("  Welcome to Power house (Uttar Pradesh)");
		System.out.println("******************************************");
		
		System.out.println("Press\n1. For user login\n2. For admin login");
		int userChoice = -1;
		
		try {
			userChoice = sc.nextInt();
	
			
		}catch(InputMismatchException e) {
			System.out.println("Invalid Option, Please try again later...");
			return;
		}
		
		
		
		if(userChoice == 1) {
			System.out.println("Please Enter username");
			String userName = sc.next();
			System.out.println("Enter password");
			String password = sc.next();
			
			if(consumerDAO.consumerLogin(userName, password)) {
				System.out.println("Login successful");
				int choice;
				do {
					System.out.println("Press\n1. To pay Bill\n2. To see your all transaction\n0. for exit");
					choice = sc.nextInt();
					switch(choice) {
					case 1:
						consumerUI.payBill();
						break;
					case 2:
						System.out.println("work is under progress..., please try again later");
						break;
					case 0:
						System.exit(1);
					default: System.out.println("Invalid choice");
						
					}
					
				}while(choice != 0);
				
			}else System.out.println("Wrong credentials. Please try again later");
		}else {
			
			System.out.println("Please enter username");
			String userName = sc.next();
			System.out.println("Enter password");
			String password = sc.next();
			
			if(userName.equals("admin") && password.equals("admin")) {
				System.out.println("Login Successful\n");
				System.out.println("*******************************");
				System.out.println("        Welcome Chief          ");
				System.out.println("*******************************");
				int choice;
				do {
				
					System.out.println("Press\n1. To register a new consumer\n2. To view all Consumers list\n3. To delete Consumer\n4. To generate bill by Consumer ID\n5. To see the bill of a particular Consumer\n6. To view all bills\n0. exit");
					
					choice = sc.nextInt();
					
					switch(choice) {
					case 1:
						adminUI.registerNewConsumer();
						break;
					case 2:
						adminUI.viewAllConsumers();
						break;
					case 3:
						adminUI.deleteConsumer();
						break;
					case 4:
						adminUI.generateBillByConsumerId();
						break;
					case 5:
						adminUI.getBillByConsumerId();
						break;
					case 6:
						adminUI.viewAllBills();
						break;
					case 0:
						System.exit(1);
					default: System.out.println("Invalid choice");
					}
					
				}while (choice !=0);
				
				
			}else {
				System.out.println("wrong credentials, Please try again later");
			}
			
			
				
		}
		
		
		
		
	}
}
