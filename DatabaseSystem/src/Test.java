import java.sql.*; 

import com.mysql.cj.jdbc.exceptions.PacketTooBigException;

public class Test 
{
	public static void main(String args[])
	{ 
		try
		{ 
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection con=DriverManager.getConnection("jdbc:mysql://192.168.199.130:4567/madang","juno", "Jjun09516@");
			
			Statement stmt=con.createStatement();
			
			ResultSet rs=stmt.executeQuery("SELECT * FROM Book");
			
			while(rs.next()) 
				System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3));
			
			con.close(); 
		}
		catch(Exception e){ System.out.println(e);} 
	} 
}