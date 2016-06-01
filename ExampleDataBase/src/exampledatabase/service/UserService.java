package exampledatabase.service;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.CallableStatement;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import exampledatabase.model.User;

public class UserService {
	
	private final String host = "jdbc:mysql://localhost:3306/";
	private final String dbName = "test";
	private final String user = "root";
	private final String pass = "root";
	
	public List<User> getUsers() throws SQLException{
		List<User> users = new ArrayList<User>();
		
		try(Connection con = (Connection) DriverManager.getConnection(host + dbName, user, pass)){
			
			Statement st = (Statement) con.createStatement();
			
			ResultSet rs = st.executeQuery("select id, name, age from user");
			
			while(rs.next()){
				User user = new User(rs.getInt("id"), rs.getString("name"), rs.getInt("age"));
				users.add(user);
			}
		}
		return users;
	}
	
	public void addUser(String name, int age) throws SQLException{
		
		try(Connection con = (Connection) DriverManager.getConnection(host + dbName, user, pass)){
			
			Statement st = (Statement) con.createStatement();
			
			String commad = String.format("insert into user (name, age) value ('%s', %d)", name, age);
			
			st.executeUpdate(commad);	
		}
	}
	
	public void addWithStoredProcedure(String name, int age) throws SQLException{
		try(Connection conn = (Connection) DriverManager.getConnection(host+dbName, user, pass)){
			
			CallableStatement st = (CallableStatement) conn.prepareCall("{ call addUser(? , ?)}");
			st.setString(1, name);
			st.setInt(2, age);
			
			st.executeUpdate();
		}
	}
}
