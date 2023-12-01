package com.powerhouse.dao;

import java.util.List;

import com.powerhouse.dto.Consumer;
import com.powerhouse.exception.NoRecordFoundException;
import com.powerhouse.exception.SomethingWentWrongException;

public interface ConsumerDAO {
	
	/**
	 * It will accept Consumer object as a parameter and register it in database
	 * @param consumer
	 * @throws SomethingWentWrongException
	 */
	public void registerNewConsumer(Consumer consumer) throws SomethingWentWrongException;
	
	/**
	 * It will not accept any parameter
	 * @return List of all registered consumers (Active and Inactive both) 
	 * @throws SomethingWentWrongException
	 * @throws NoRecordFoundException
	 */
	public List<Consumer> viewAllConsumers() throws SomethingWentWrongException, NoRecordFoundException;
	
	/**
	 * This method will accept consumer Id (con_id) as a parameter and delete (Inactivate) it
	 * It will not delete the details of consumer from database, but it will change the status of the consumer like Active to Inactive
	 * @param con_id
	 * @throws SomethingWentWrongException
	 */
	public void deleteConsumer(int con_id) throws SomethingWentWrongException;
	
	/**
	 * This method will accept the userName and password as parameter and provide the login functionality.
	 * @param userName
	 * @param password
	 * @return boolean value by verifying userName and password with database
	 * @throws NoRecordFoundException
	 */
	public boolean consumerLogin(String userName, String password) throws NoRecordFoundException;
	
	/**
	 * This method will accept consumer Id (con_id) as a parameter
	 * @param con_id
	 * @return status of the consumer
	 */
	public String consumerStatus(int con_id);
	
	/**
	 * This method will accept userName as a parameter
	 * @param UserName
	 * @return boolean (true/false) by checking whether userName is already registered or not
	 */
	public boolean isCustomerAlreadyRegistered(String userName);
}
