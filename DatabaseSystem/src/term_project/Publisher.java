package term_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Publisher {
	
	Scanner input = new Scanner(System.in);
	
	public Publisher() {}
	
	public void PrintPublishers() {
		
		try
		{ 
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection conn	= DriverManager.getConnection("jdbc:mysql://192.168.56.101:3308/dbproject","dbuser", "1234");
			
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM publisher");
			
			System.out.println("");
			System.out.println("============================================================================");
			System.out.printf("%15s | %30s \t| %15s \n", "출판사 ID", "출판사 이름", "전화번호");
			
			while(rs.next())
			{
				System.out.printf("\t%15d | %30s \t| %15d \n", rs.getInt(1), rs.getString(2), rs.getInt(3));
			}

			System.out.println("============================================================================");
			
			conn.close(); 
		}
		catch(Exception e){ System.out.println(e);} 
	}
}
