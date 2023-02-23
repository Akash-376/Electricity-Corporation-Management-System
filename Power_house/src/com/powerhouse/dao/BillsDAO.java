package com.powerhouse.dao;

import java.util.List;

import com.powerhouse.dto.Bills;
import com.powerhouse.exception.NoRecordFoundException;
import com.powerhouse.exception.SomethingWentWrongException;

public interface BillsDAO {
	public void generateBillByConsumerId(int con_id) throws NoRecordFoundException, SomethingWentWrongException;
	public Bills getBillByConsumerId(int con_id) throws NoRecordFoundException, SomethingWentWrongException;
	public List<Bills> viewAllBills() throws NoRecordFoundException, SomethingWentWrongException;
	public void payBill(int con_id) throws SomethingWentWrongException, NoRecordFoundException;
}