package com.powerhouse.dao;

import java.util.List;

import com.powerhouse.dto.Bills;
import com.powerhouse.exception.NoRecordFoundException;
import com.powerhouse.exception.SomethingWentWrongException;

public interface BillsDAO {
	/**
	 * This method will accept consumer Id (con_id) as a parameter and generate the bill.
	 * bill is calculated on the bases of meter reading which is generated by Math.random function.
	 * @param con_id
	 * @throws NoRecordFoundException
	 * @throws SomethingWentWrongException
	 */
	public void generateBillByConsumerId(int con_id) throws NoRecordFoundException, SomethingWentWrongException;
	
	/**
	 * This method will accept consumer Id (con_id) as a parameter and return the bill.
	 * @param con_id
	 * @return Bill
	 * @throws NoRecordFoundException
	 * @throws SomethingWentWrongException
	 */
	public Bills getBillByConsumerId(int con_id) throws NoRecordFoundException, SomethingWentWrongException;
	
	
	/**
	 * This method does not accept ant parameter
	 * @return List of bills of all the consumers
	 * @throws NoRecordFoundException
	 * @throws SomethingWentWrongException
	 */
	public List<Bills> viewAllBills() throws NoRecordFoundException, SomethingWentWrongException;
	
	/**
	 * It will accept userName (email) as a parameter
	 * This method is going to use by consumers to pay their bills.
	 * @param User_name
	 * @throws SomethingWentWrongException
	 * @throws NoRecordFoundException
	 */
	public void payBill(String User_name) throws SomethingWentWrongException, NoRecordFoundException;
	
	/**
	 * It will accept userName (email) as a parameter
	 * @param User_name
	 * @return List of bills of the given consumer
	 * @throws NoRecordFoundException
	 * @throws SomethingWentWrongException
	 */
	public List<Bills> viewAllTransactions(String User_name) throws NoRecordFoundException, SomethingWentWrongException;
}




