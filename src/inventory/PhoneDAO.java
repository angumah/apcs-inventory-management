package inventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.Statement;
public class PhoneDAO {
	
	private final String url;
	private final String user;
	private final String password;
	
	public PhoneDAO(String url, String user, String password) {
		this.url = url;
		this.user = user;
		this.password = password;
	}
	
	public Phone getPhone(int id) throws SQLException {
		final String sql = "SELECT * FROM phones WHERE phone_id = ?";
		
		Phone phone = null;
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1,  id);
		ResultSet rs = pstmt.executeQuery();
		
		if(rs.next()) {
			String name  = rs.getString("name");
			String manufacturer = rs.getString("manufacturer");
			int units = rs.getInt("units");
			int supply = rs.getInt("supply");
			int cost = rs.getInt("cost");
			String description = rs.getString("description");
			
			phone = new Phone(id, name, manufacturer, units, supply, cost, description);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return phone;
		
	}
	
	public Phone getPhone(String name) throws SQLException{
		final String sql = "SELECT * FROM phones WHERE name = ?";
		
		Phone phone = null;
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1,  name);
		ResultSet rs = pstmt.executeQuery();
		
		if(rs.next()) {
			int id  = rs.getInt("phone_id");
			String manufacturer = rs.getString("manufacturer");
			int units = rs.getInt("units");
			int supply = rs.getInt("supply");
			int cost = rs.getInt("cost");
			String description = rs.getString("description");
			
			phone = new Phone(id, name, manufacturer, units, supply, cost, description);
		} else {
			return null;
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return phone;
		
	}
	
	
	public List<Phone> getPhones() throws SQLException {
		final String sql = "SELECT * FROM phones ORDER BY phone_id ASC";
		
		List<Phone> phones= new ArrayList<>();
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		
		while(rs.next()) {
			int id = rs.getInt("phone_id");
			String name = rs.getString("name");
			String manufacturer = rs.getString("manufacturer");
			int units = rs.getInt("units");
			int supply = rs.getInt("supply");
			int cost = rs.getInt("cost");
			String description = rs.getString("description");
			
			phones.add(new Phone(id, name, manufacturer, units, supply, cost, description));
		}
		
		rs.close();
		stmt.close();
		conn.close();
		
		return phones;
		
	}
	
	public List<Phone> getPhones(String partial) throws SQLException {
		final String sql = "SELECT * FROM phones WHERE name LIKE '" + partial + "%' ORDER BY phone_id ASC";
		
		List<Phone> phones= new ArrayList<>();
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		
		while(rs.next()) {
			int id = rs.getInt("phone_id");
			String name = rs.getString("name");
			String manufacturer = rs.getString("manufacturer");
			int units = rs.getInt("units");
			int supply = rs.getInt("supply");
			int cost = rs.getInt("cost");
			String description = rs.getString("description");
			
			phones.add(new Phone(id, name, manufacturer, units, supply, cost, description));
		}
		
		rs.close();
		stmt.close();
		conn.close();
		
		return phones;
		
	}
	
	
	public boolean insertPhone(String name, String manufacturer, int units, int supply, int cost, String description) throws SQLException {
		final String sql = "INSERT INTO phones (name, manufacturer, units, supply, cost, description) " + 
				"VALUES (?, ?, ?, ?, ?, ?)";
		
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, name);
		pstmt.setString(2, manufacturer);
		pstmt.setInt(3, units);
		pstmt.setInt(4, supply);
		pstmt.setInt(5, cost);
		pstmt.setString(6, description);
		int affected = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return affected == 1;
	}
	
	public boolean updatePhone(Phone phone) throws SQLException {
		final String sql = "UPDATE phones SET name = ?, manufacturer = ?, units = ?, supply = ?, cost = ?, description = ? "+
							"WHERE phone_id = ?";
		
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, phone.getName());
		pstmt.setString(2, phone.getManufacturer());
		pstmt.setInt(3, phone.getUnits());
		pstmt.setInt(4, phone.getSupply());
		pstmt.setInt(5,  phone.getCost());
		pstmt.setString(6,  phone.getDescription());
		pstmt.setInt(7, phone.getId());
		int affected = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return affected == 1;
		
	}
	
	
	public boolean deletePhone(Phone phone) throws SQLException {
		final String sql = "DELETE FROM phones WHERE phone_id = ?";
		
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1,  phone.getId());
		
		int affected = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return affected == 1;
	}
	private Connection getConnection() throws SQLException{
		final String driver = "com.mysql.cj.jdbc.Driver";
		
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return DriverManager.getConnection(url, user, password);
	}
}
