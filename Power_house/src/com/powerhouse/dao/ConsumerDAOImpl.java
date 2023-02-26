package com.powerhouse.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.powerhouse.dto.Consumer;
import com.powerhouse.dto.ConsumerImpl;
import com.powerhouse.exception.NoRecordFoundException;
import com.powerhouse.exception.SomethingWentWrongException;

public class ConsumerDAOImpl implements ConsumerDAO {

	Map<String, String> consumersCeddentialsMap = new HashMap<>();
	
	@Override
	public void registerNewConsumer(Consumer consumer) throws SomethingWentWrongException {

		Connection connection = null;
		
		try {
			connection = DBUtils.connectToDatabase();
			
			// prepare query
			String INSERT_QUERY = "INSERT INTO Consumers (Name, User_name, Password, Mobile_no, Registration_date) VALUES (?, ?, ?, ?, ?)";
			
			PreparedStatement ps = connection.prepareStatement(INSERT_QUERY);
			
			// stuff data in the query
			ps.setString(1, consumer.getName());
			ps.setString(2, consumer.getUserName());
			ps.setString(3, consumer.getPassword());
			ps.setString(4, consumer.getMobile());
			ps.setDate(5, Date.valueOf(consumer.getRegistrationDate()));
			
			if(ps.executeUpdate() > 0) {
				System.out.println("⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍\n");
				System.out.println("      Consumer registered successfully");
				System.out.println("⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊\n");
				System.out.println("      Username = " + consumer.getUserName());
				System.out.println("      Password = " + consumer.getPassword());
				System.out.println();
				System.out.println("⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍\n");
			}
			
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
	
	
	// check whether ReasultSet is empty or not
	private boolean isResultSetEmpty(ResultSet rs) throws SQLException {
		return (!rs.isBeforeFirst() && rs.getRow() == 0)? true : false;
	}
	
	// get Consumer list
	private List<Consumer> getConsumerListFromResultSet(ResultSet rs) throws SQLException{
		List<Consumer> list = new ArrayList<>();
		
		while(rs.next()) {
			Consumer consumer = new ConsumerImpl();
			consumer.setConsumerId(rs.getInt("Consumer_id"));
			consumer.setName(rs.getString("Name"));
			consumer.setUserName(rs.getString("User_name"));
			consumer.setMobile(rs.getString("Mobile_no"));
			consumer.setRegistrationDate(rs.getDate("Registration_date").toLocalDate());
			consumer.setStatus(rs.getString("Status"));
			
			list.add(consumer);
		}
		return list;
	}

	@Override
	public List<Consumer> viewAllConsumers() throws SomethingWentWrongException, NoRecordFoundException {
		Connection connection = null;
		try {
			connection = DBUtils.connectToDatabase();
			
			//prepare query
			String SELECT_QUERY = "SELECT Consumer_id, Name, User_name, Mobile_no, Registration_date, Status FROM Consumers";
			PreparedStatement ps = connection.prepareStatement(SELECT_QUERY);
			
			ResultSet rs = ps.executeQuery();
			
			if(isResultSetEmpty(rs)) throw new NoRecordFoundException("No consumer found");
			
			return getConsumerListFromResultSet(rs);
			
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
	public void deleteConsumer(int con_id) throws SomethingWentWrongException {
		Connection connection = null;
		try {
			connection = DBUtils.connectToDatabase();
			
			//prepare query
			String UPDATE_QUERY = "UPDATE consumers SET Status = 'Inactive' WHERE Consumer_id = ?";
			
			PreparedStatement ps = connection.prepareStatement(UPDATE_QUERY);
			
			ps.setInt(1, con_id);
			
			if(ps.executeUpdate() > 0) {
				System.out.println("\n⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍\n");
				System.out.println("     Consumer inactivated");
				System.out.println("\n⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍\n");
			}
			
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
	
	public boolean consumerLogin(String userName, String password) {
		Connection connection = null;
		try {
			connection = DBUtils.connectToDatabase();
			
			//prepare query
			String SELECT_QUERY = "SELECT Password FROM Consumers WHERE User_name = ?";
			
			PreparedStatement ps = connection.prepareStatement(SELECT_QUERY);
			ps.setNString(1, userName);
			
			ResultSet rs = ps.executeQuery();
			
			
			if(!rs.next()) throw new NoRecordFoundException("No consumer found");
			
			String dbPassword = rs.getString("Password");
			
			if(dbPassword.equals(password)) return true;
	
			
		} catch (SQLException e) {
			
		} catch (NoRecordFoundException e) {
			
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
		return false;
		
	}
	
	
	
	public String consumerStatus(int con_id) {
		Connection connection = null;
		try {
			connection = DBUtils.connectToDatabase();
			
			//prepare query
			String SELECT_QUERY = "SELECT Status FROM Consumers WHERE Consumer_id = ?";
			
			PreparedStatement ps = connection.prepareStatement(SELECT_QUERY);
			ps.setInt(1, con_id);
			
			ResultSet rs = ps.executeQuery();
			
			
			if(!rs.next()) return "not found";
			
			String status = rs.getString("Status");
			
			return status;
			
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
		return "not found";
		
		
	}

	
}
