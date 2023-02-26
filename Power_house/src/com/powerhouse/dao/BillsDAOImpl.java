package com.powerhouse.dao;
import java.util.Scanner;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.powerhouse.dto.Bills;
import com.powerhouse.dto.BillsImpl;
import com.powerhouse.dto.Consumer;
import com.powerhouse.dto.ConsumerImpl;
import com.powerhouse.exception.NoRecordFoundException;
import com.powerhouse.exception.SomethingWentWrongException;

public class BillsDAOImpl implements BillsDAO{
	
	Scanner scanner = new Scanner(System.in);
	

	@Override
	public void generateBillByConsumerId(int con_id){
		Bills bill = new BillsImpl(con_id);
		
		Connection connection = null;
		
		try {
			connection = DBUtils.connectToDatabase();
			
			String SELECT_QUERY = "SELECT Status FROM Consumers WHERE Consumer_id = ?";
			
			PreparedStatement selectPrepStmt = connection.prepareStatement(SELECT_QUERY);
			
			selectPrepStmt.setInt(1, con_id);
			ResultSet r = selectPrepStmt.executeQuery();
			
			boolean isNext = r.next();
			if(isNext && r.getString("Status").equals("Active")) {
				// prepare query
				String INSERT_QUERY = "INSERT INTO Bills (Consumer_id,Units_consumption, Bill_amount, Date_of_bill) VALUES (?, ?, ?, ?)";
				
				PreparedStatement ps = connection.prepareStatement(INSERT_QUERY);
				
				// stuff data in the query
				ps.setInt(1, bill.getCon_id());
				ps.setInt(2, bill.getMeterReading());
				ps.setDouble(3, bill.getPayableAmt());
				ps.setDate(4, Date.valueOf(bill.getBillDate()));
				
				if(ps.executeUpdate() > 0) {
					System.out.println("\n*********************************\n");
					System.out.println("    Bill generated successfully");
					System.out.println("\n*********************************\n");
				}
			}else
			if(isNext && r.getString("Status").equals("Inactive")) {
				System.out.println("");
				System.out.println(" Can't generate bill, Because Consumer is Inactivated");
			}else {
				System.out.println(" No any Consumer registered for this ID");
			}
				
			
		} catch (SQLException e) {
			
		}finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	// check whether ReasultSet is empty or not
	private boolean isResultSetEmpty(ResultSet rs) throws SQLException {
		return (!rs.isBeforeFirst() && rs.getRow() == 0)? true : false;
	}
	
	// get Bill list
	private List<Bills> getBillListFromResultSet(ResultSet rs) throws SQLException{
		List<Bills> list = new ArrayList<>();
		
		while(rs.next()) {
			Bills bill = new BillsImpl();
			Consumer consumer = new ConsumerImpl();
			
			bill.setBill_id(rs.getInt("Bill_id"));
			bill.setCon_id(rs.getInt("Consumer_id"));
			bill.setMeterReading(rs.getInt("Units_consumption"));
			bill.setPayableAmt(rs.getDouble("Bill_amount"));
			bill.setBillStatus(rs.getString("Bill_status"));
			bill.setBillDate(rs.getDate("Date_of_bill").toLocalDate());
//				bill.setPaymentDate(rs.getDate("Payment_date").toLocalDate());
			
//				if(rs.getDate("Payment_date").toLocalDate() != null) {
//					bill.setPaymentDate(rs.getDate("Payment_date").toLocalDate());
//				}
			
			java.sql.Date paymentDate = rs.getDate("Payment_date");
			if(paymentDate != null) {
			    bill.setPaymentDate(paymentDate.toLocalDate());
			}
			
			consumer.setName(rs.getString("Name"));
			bill.setConsumer(consumer);
			
			list.add(bill);
		}
		return list;
	}
	
	// get Bill of a particular Customer
		private Bills getAParticularCustomerBillFromResultSet(ResultSet rs) throws SQLException{
			Bills bill = new BillsImpl();
			while(rs.next()) {
				Consumer consumer = new ConsumerImpl();
				
				bill.setCon_id(rs.getInt("Consumer_id"));//
				consumer.setName(rs.getString("Name"));//
				bill.setMeterReading(rs.getInt("Units"));
				bill.setPayableAmt(rs.getDouble("Payable"));
				bill.setBillStatus(rs.getString("Bill_status"));
				
				bill.setConsumer(consumer);
				
				
			}
			return bill;
		}
	
	
	// get bill amount from resultSet
	private double getBillAmountFromResultSet(ResultSet rs) throws SQLException{
		
		if(rs.next())
		return rs.getDouble("Payable");
		return 0;
		
	}
		

	@Override
	public Bills getBillByConsumerId(int con_id) throws NoRecordFoundException, SomethingWentWrongException {
		Connection connection = null;
		try {
			connection = DBUtils.connectToDatabase();
			
			//prepare query
			String SELECT_QUERY = "SELECT C.Consumer_id, Name, SUM(Units_consumption) Units, SUM(Bill_amount) Payable, Bill_status FROM Consumers C INNER JOIN Bills B ON C.Consumer_id = B.Consumer_id WHERE C.Consumer_id = ? AND Bill_status = 'Pending' GROUP BY C.Consumer_id, Name, Bill_status";
			
			PreparedStatement ps = connection.prepareStatement(SELECT_QUERY);
			
			ps.setInt(1, con_id);
			
			ResultSet rs = ps.executeQuery();
			
			if(isResultSetEmpty(rs)) {
				return null;
			}
			
			return getAParticularCustomerBillFromResultSet(rs);
			
		} catch (SQLException e) {
			throw new SomethingWentWrongException();
		}finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public List<Bills> viewAllBills() throws NoRecordFoundException, SomethingWentWrongException {
		Connection connection = null;
		try {
			connection = DBUtils.connectToDatabase();
			
			//prepare query
			String SELECT_QUERY = "SELECT Bill_id, C.Consumer_id, Name, Units_consumption, Bill_amount, Date_of_bill, Bill_status, Payment_date FROM Consumers C INNER JOIN Bills B ON C.Consumer_id = B.Consumer_id";
			PreparedStatement ps = connection.prepareStatement(SELECT_QUERY);
			
			ResultSet rs = ps.executeQuery();
			
			if(isResultSetEmpty(rs)) throw new NoRecordFoundException("No consumer found");
			
			return getBillListFromResultSet(rs);
			
		} catch (SQLException e) {
			throw new SomethingWentWrongException();
		}finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void payBill(int con_id) throws SomethingWentWrongException, NoRecordFoundException {
		Connection connection = null;
		try {
			connection = DBUtils.connectToDatabase();
			
			// prepare query
			String SELECT_QUERY = "SELECT Consumer_id, SUM(Bill_amount) Payable FROM Bills WHERE Consumer_id = ? AND Bill_status = 'Pending' GROUP BY Consumer_id";
			
			PreparedStatement prepStatement = connection.prepareStatement(SELECT_QUERY);
			prepStatement.setInt(1, con_id);
			
			ResultSet rs = prepStatement.executeQuery();
			
			if(isResultSetEmpty(rs)) {
				System.out.println("No any bill found");
				return;
			}
			double payable = getBillAmountFromResultSet(rs);
			System.out.println("\n⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍");
			
			System.out.println("Payable amount = " + payable + " (incl. of all Taxes)");
			
			System.out.println("⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍");
			
			double receivedAmt;

			//prepare another query			
			String UPDATE_QUERY = "UPDATE Bills SET Bill_status = 'Paid', Payment_date = ? WHERE Consumer_id = ?";
			
//			System.out.println("⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍");
			System.out.println("\n Enter bill amount to pay");
//			System.out.println("⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍");
			
			do {
				// receiving bill amount from consumer
				receivedAmt = scanner.nextDouble();
				if(receivedAmt == payable) {
					PreparedStatement ps = connection.prepareStatement(UPDATE_QUERY);
					
					LocalDate payDate = LocalDate.now();
					ps.setDate(1, Date.valueOf(payDate));
					ps.setInt(2, con_id);
					
					if(ps.executeUpdate() > 0) {
						System.out.println("\n⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍");
						System.out.println("   Payment Successful");
						System.out.println("⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍");
					}
					
				}else {
					System.out.println("\n⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍");
					System.out.println("Please enter valid amount");
					System.out.println("⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍");
				}
			}while(receivedAmt != payable);	
			
			
		} catch (SQLException e) {
			throw new SomethingWentWrongException();
		}finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	
	
	public List<Bills> viewAllTransactions(int con_Id) throws NoRecordFoundException, SomethingWentWrongException {
		Connection connection = null;
		try {
			connection = DBUtils.connectToDatabase();
			
			//prepare query
			String SELECT_QUERY = "SELECT Bill_id, C.Consumer_id, Name, Units_consumption, Bill_amount, Date_of_bill, Bill_status, Payment_date FROM Consumers C INNER JOIN Bills B ON C.Consumer_id = B.Consumer_id WHERE C.Consumer_id = ? AND Bill_status = 'Paid'";
			PreparedStatement ps = connection.prepareStatement(SELECT_QUERY);
			
			ps.setInt(1, con_Id);
			
			ResultSet rs = ps.executeQuery();
			
			if(isResultSetEmpty(rs)) {
				return null;
			}
			
			return getBillListFromResultSet(rs);
			
		} catch (SQLException e) {
			throw new SomethingWentWrongException();
		}finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	

	
}
