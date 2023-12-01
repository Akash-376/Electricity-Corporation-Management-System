package com.powerhouse.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.powerhouse.dto.Consumer;
import com.powerhouse.dto.ConsumerImpl;
import com.powerhouse.exception.NoRecordFoundException;
import com.powerhouse.exception.SomethingWentWrongException;
import com.powerhouse.security.PasswordHashing;

public class ConsumerDAOImpl implements ConsumerDAO {

	Map<String, String> consumersCreddentialsMap = new HashMap<>();
	
	@Override
	public void registerNewConsumer(Consumer consumer) throws SomethingWentWrongException {

		Connection connection = null;
		
		try {
			connection = DBUtils.connectToDatabase();
			
			// prepare query to insert consumer details
			String INSERT_QUERY = "INSERT INTO Consumers (Name, User_name, Password, Mobile_no, Registration_date) VALUES (?, ?, ?, ?, ?)";
			
			PreparedStatement ps = connection.prepareStatement(INSERT_QUERY);
			
			// stuff data in the query
			ps.setString(1, consumer.getName());
			ps.setString(2, consumer.getEmail());
			
			ps.setString(3, PasswordHashing.doHashing(consumer.getPassword())); // password hashing is applied
			ps.setString(4, consumer.getMobile());
			ps.setDate(5, Date.valueOf(consumer.getRegistrationDate()));
			
			if(ps.executeUpdate() > 0) {
				System.out.println("⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍\n");
				System.out.println("      \033[32mConsumer registered successfully\033[0m");
				System.out.println("⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊\n");
				System.out.println("      Username = " + consumer.getEmail());
				System.out.println("      Password = " + consumer.getPassword());
				System.out.println();
				System.out.println("⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍\n");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
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
	
	// get Consumer list from the resultSet
	private List<Consumer> getConsumerListFromResultSet(ResultSet rs) throws SQLException{
		List<Consumer> list = new ArrayList<>();
		
		while(rs.next()) {
			Consumer consumer = new ConsumerImpl();
			consumer.setConsumerId(rs.getInt("Consumer_id"));
			consumer.setName(rs.getString("Name"));
			consumer.setEmail(rs.getString("User_name"));
			consumer.setMobile(rs.getString("Mobile_no"));
			consumer.setRegistrationDate(rs.getDate("Registration_date").toLocalDate());
			consumer.setStatus(rs.getString("Status"));
			
			list.add(consumer);
		}
		return list;
	}
	
	// this method will return consumers' list
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
			
			// step to check whether consumer is already activated or not
			String SELECT_QUERY = "SELECT Status FROM consumers WHERE Consumer_id = ?";
			
			PreparedStatement psToGetStatus = connection.prepareStatement(SELECT_QUERY);
			
			psToGetStatus.setInt(1, con_id);
			
			ResultSet rs = psToGetStatus.executeQuery();
			
			rs.next();
			String status = rs.getString("Status");
			
			if(status.equalsIgnoreCase("Inactive")) {
				System.out.println("\033[38;5;208mConsumer is already Inactivated\033[0m");
			}else {
				
				//prepare query
				String UPDATE_QUERY = "UPDATE consumers SET Status = 'Inactive' WHERE Consumer_id = ?";
				
				PreparedStatement ps = connection.prepareStatement(UPDATE_QUERY);
				
				ps.setInt(1, con_id);
				
				if(ps.executeUpdate() > 0) {
					System.out.println("\n⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍\n");
					System.out.println("     \033[32mConsumer inactivated\033[0m");
					System.out.println("\n⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍⚍\n");
				}else {
					System.out.println("\033[31mInvalid consumer Id\033[0m, Please try again");
				}
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("\nPlease try again...");
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
	
	// method to login Consumer by verifying hashed password
	public boolean consumerLogin(String userName, String password) {
		Connection connection = null;
		try {
			connection = DBUtils.connectToDatabase();
			
			String hashedPassword = PasswordHashing.doHashing(password);
			
			//prepare query
			String SELECT_QUERY = "SELECT Password FROM Consumers WHERE User_name = ?";
			
			PreparedStatement ps = connection.prepareStatement(SELECT_QUERY);
			ps.setNString(1, userName);
			
			ResultSet rs = ps.executeQuery();
			
			
			if(!rs.next()) throw new NoRecordFoundException("No consumer found");
			
			String dbPassword = rs.getString("Password");
			
			if(dbPassword.equals(hashedPassword)) return true;
	
			
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
	
	
	// this method will return the status of the consumer (Active / Inactive)
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

	// this method is to check whether entered user name is already registered with Electricity corporation on not. 
	@Override
	public boolean isCustomerAlreadyRegistered(String userName) {
	    Connection connection = null;
	    try {
	        connection = DBUtils.connectToDatabase();

	        // Prepare query with parameterized statement
	        String SELECT_QUERY = "SELECT User_name FROM Consumers WHERE User_name = ?";
	        try (PreparedStatement ps = connection.prepareStatement(SELECT_QUERY)) {
	            ps.setString(1, userName);

	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) {
	                    return true;
	                }
	            }
	        }

	        return false;

	    } catch (SQLException e) {
	        throw new SomethingWentWrongException();
	    } finally {
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	}


	
}
