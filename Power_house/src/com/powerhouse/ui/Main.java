package com.powerhouse.ui;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		
		
		System.out.println("\n       (We can turn night into Day)       ");
		System.out.println("⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍");
		System.out.println("  Welcome to Power house (Uttar Pradesh)");
		System.out.println("⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍");
		
		System.out.println("Press\n1. For user login\n2. For admin login");
		int userChoice = -1;
		
		// This do while loop will run until it will not get proper value from user
		do {
			try {
				userChoice = sc.nextInt();
				
				if(userChoice <1 || userChoice>2) {
					System.out.println("Invalid choice, please try again");
				}
				
			}catch (InputMismatchException e) {
				System.out.println("\033[31mInvalid Input\033[0m, Please try again");
				sc.nextLine(); // clear the input buffer
			}
			
		}while (userChoice <1 || userChoice>2);
		

		if(userChoice == 1) {
			System.out.println("\n⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍");
			
			// Email
			System.out.println("  Please Enter username(email)");
			String userName = sc.next();
			
			// email validation
			Pattern emailRegex = Pattern.compile("^(.+)@(.+)$");
			Matcher emailMatcher = emailRegex.matcher(userName);
			// this loop will run until it will not get a valid format of email id
	        while (!emailMatcher.matches()) {
	            System.out.println("\033[31mInvalid email\033[0m, please try again");
	            userName = sc.next();
	            emailMatcher = emailRegex.matcher(userName);
	        }
			
	        System.out.println(consumerDAO.isCustomerAlreadyRegistered(userName));
	        
	        
	    	// password
	        // here I am not adding password validation, Because strong password is already set in DB
	        // now it is required to just match given password with the password stored in DB
	        System.out.println("  Enter password");
			String password = sc.next();
			
			// calling consumerLogin method of consumerDAO class to match userName and password with DB
			if(consumerDAO.consumerLogin(userName, password)) {
				System.out.println("\n  \033[32mLogin successful\033[0m");
				System.out.println("\n⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍\n");
				int choice = 0;
				do {
					
					try {
						
						System.out.println(" Press\n1. To pay Bill\n2. To see your all transaction\n0. for exit");
						choice = sc.nextInt();
						
						while(choice <0 || choice >2) {
							System.out.println("Invalid choice, Please try again");
							choice = sc.nextInt();
						}
						
						switch(choice) {
						case 1:
							// I am providing user name here, so it will not ask userName at the time of bill payment
							// It will allow to pay bill of logged In user only
							consumerUI.payBill(userName);
							break;
						case 2:
							consumerUI.viewAllTransactions(userName); // logged In user can view only his own transaction history
							break;
						case 0:
							System.out.println("Bye Bye...");
							System.exit(1);
	//						default: System.out.println("Invalid choice");
							
						}
						
					} catch (InputMismatchException e) {
						System.out.println("\033[31mInvalid Option\033[0m, Please try again");
						sc.nextLine(); // 
						choice = -1; // assigning -1 to run do while loop
					}
						
					
				}while(choice != 0);
				
			}else System.out.println("\033[31mWrong credentials. Please try again later");
				
	        

		}else {
			
			System.out.println("Please enter username    \033[32mhint\033[0m: your designation");
			String userName = sc.next();
			System.out.println("Enter password    \033[32mhint\033[0m: your designation");
			String password = sc.next();
			
			if(userName.equals("admin") && password.equals("admin")) {
				System.out.println("\n\033[32mLogin Successful\033[0m\n");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("        ⚠ Power house (Uttar Pradesh)");
				System.out.println("\n⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍");
				System.out.println("                Welcome Chief          ");
				System.out.println("⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍");
				int choice = -1;
				do {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	
					System.out.println("\nPress\n");
					System.out.println("1. To register a new consumer");
					System.out.println("2. To view all Consumers list");
					System.out.println("3. To \033[31mdelete\033[0m Consumer"); // red color code
					System.out.println("4. To generate bill by Consumer ID");
					System.out.println("5. To see the bill of a particular Consumer");
					System.out.println("6. To view all bills");
					System.out.println("0. exit");
					System.out.println("\n⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍");
					
					try {
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
						default: System.out.println("\033[31mInvalid choice\033[0m, please try again");
						}
						
						
					} catch (InputMismatchException e) {
						System.out.println("\033[31mInvalid Option\033[0m, Please try again");
						sc.nextLine();
					}
	
				}while (choice !=0);
				
				
			}else {
				System.out.println("\n⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍");
				System.out.println("         \033[31mwrong credentials\033[0m, Please try again later");
				System.out.println("⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍");
			}
				
				
		}
		
		
		
		
	}
}
