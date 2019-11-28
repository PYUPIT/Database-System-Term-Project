package term_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Publisher {
	
	Scanner input = new Scanner(System.in);
	
	public Publisher() {}
	
	Connection conn;
	
	public void SetConnection(String url, String usr, String pwd) {
		/*
		 * 데이터베이스와 연동을 위한 메소드
		 * MENU 1번이 사전에 실행되어야만  데이터베이스에 연동이 됩니다.
		 */
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
		
			conn = DriverManager.getConnection(url, usr, pwd);
		} catch (Exception e) {}
		
	}
	
	public void PrintPublishers() {
		
		try
		{ 
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
