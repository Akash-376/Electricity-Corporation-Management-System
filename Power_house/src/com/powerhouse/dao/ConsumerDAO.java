package com.powerhouse.dao;

import java.util.List;

import com.powerhouse.dto.Consumer;
import com.powerhouse.exception.NoRecordFoundException;
import com.powerhouse.exception.SomethingWentWrongException;

public interface ConsumerDAO {
	public void registerNewConsumer(Consumer consumer) throws SomethingWentWrongException;
	
	public List<Consumer> viewAllConsumers() throws SomethingWentWrongException, NoRecordFoundException;
	
	public void deleteConsumer(int con_id) throws SomethingWentWrongException;
	
	public boolean consumerLogin(String userName, String password) throws NoRecordFoundException;
}
