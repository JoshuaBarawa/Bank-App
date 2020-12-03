package userdatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBase {
	
	public Connection getConnection() throws ClassNotFoundException {
      Connection connection = null;
      Class.forName("com.mysql.jdbc.Driver");
      
      try {
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/useraccounts", "root", "connect");
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      
      return connection;
		
	}

	public boolean saveToDataBase(User user) throws SQLException, ClassNotFoundException {
         Connection connection = getConnection();
		
		String savesql = "insert into account (userName, gender, idNumber, accountNumber, pinNumber, accountBalance) values (?,?,?,?,?,?)";
		PreparedStatement savestm = connection.prepareStatement(savesql);

		String checksql = "select * from account where accountNumber=? and idNumber=?";
		PreparedStatement checkstm = connection.prepareStatement(checksql);
		
		checkstm.setInt(1, user.getAccountNumber());
		checkstm.setInt(2, user.getIdNumber());
		
		ResultSet result = checkstm.executeQuery();
		if(result.next()) {
			return false;
		}
		
		else {
			
			savestm.setString(1, user.getUserName());
			savestm.setString(2, user.getGender());
			savestm.setInt(3, user.getIdNumber());
			savestm.setInt(4, user.getAccountNumber());
			savestm.setInt(5, user.getPinNumber());
			savestm.setInt(6, user.getAccountBalance());
			
			savestm.executeUpdate();
			return true;
		}
		
	}

	public boolean login(LoginDetails login) throws SQLException, ClassNotFoundException {
      Connection connection = getConnection();
      
		String loginsql = "select accountNumber, pinNumber from account where accountNumber=? and pinNumber=?";
		PreparedStatement loginstm = connection.prepareStatement(loginsql);

		loginstm.setInt(1, login.getAccountNumber());
		loginstm.setInt(2, login.getPinNumber());

		ResultSet set = loginstm.executeQuery();
		if (set.next()) {
			return true;
		}
		loginstm.close();

		return false;

	}
	
	public User selectUser(int id) throws ClassNotFoundException, SQLException {
		Connection connection = getConnection();
		String selectsql = "select userName, gender, idNumber, accountNumber, pinNumber, accountBalance from account where accountNumber=" + id;
		PreparedStatement selectstm = connection.prepareStatement(selectsql);
		
		User user = new User();
		
	    ResultSet result = selectstm.executeQuery();
		if(result.next()) {
			
			String name = result.getString(1);
			String gender = result.getString(2);
			int idN = result.getInt(3);
			int accountN = result.getInt(4);
			int pinN = result.getInt(5);
			int accountB = result.getInt(6);
			
			user.setUserName(name);
			user.setGender(gender);
			user.setIdNumber(idN);
			user.setAccountNumber(accountN);
			user.setPinNumber(pinN);
			user.setAccountBalance(accountB);
			
			
		}
		
		return user;	
	}
	
	public void updateAccount(User user) throws ClassNotFoundException, SQLException {
		Connection connection = getConnection();
		
	String updatesql = "update account set accountBalance=? where accountNumber=" + user.getAccountNumber();	
	PreparedStatement updatestm = connection.prepareStatement(updatesql);

	  updatestm.setInt(1, user.getAccountBalance());
	  
	  updatestm.executeUpdate();
		
	}

}
