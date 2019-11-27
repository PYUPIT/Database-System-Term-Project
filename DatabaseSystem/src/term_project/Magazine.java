package term_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Magazine {
	
	String magazine_id;
	String publisher_name;
	String magazine_name;
	int magazine_page;
	boolean adult_stat;
	String magazine_theme;
	Date magazine_releasedate;
	
	public Magazine() {
		
	}
	
	public void PrintMagazines() {
		
		System.out.println("Printing Magazines ....");
		
		try
		{ 
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection conn	= DriverManager.getConnection("jdbc:mysql://192.168.56.101:3308/dbproject","dbuser", "1234");
			
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(
					"SELECT magazinename, publishername, magazinepage, magazinereleasedate, magazinetheme FROM magazine, publisher "
					+ "WHERE magazine.publisherid = publisher.publisherid;");
			
			System.out.println("============================================================================");
			System.out.printf("%20s \t| %20s \t| %15s | %20s \t| %20s \n", "잡지명", "출판사명", "쪽수", "출시일", "주제");
			
			while(rs.next())
			{
				System.out.printf("%20s \t| %20s \t| %15d | %20s \t| %20s \n", rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5));
			}

			System.out.println("============================================================================");
			
			conn.close(); 
		}
		catch(Exception e){ System.out.println(e);} 
	}
	
	public void PrintMagazinesByTheme() {
		
		Scanner input = new Scanner(System.in);
		
		System.out.println("Printing Magazines By Theme ....");
		
		try
		{ 
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection conn	= DriverManager.getConnection("jdbc:mysql://192.168.56.101:3308/dbproject","dbuser", "1234");
			
			System.out.println("[1] 운동 \t [2] 음식 \t [3] 과학 \t [4] 자동차\t [5] 컴퓨터 \t [6] 문화 \t [7] 경제 \t [8] 19 \t");
			
			System.out.print("장르를 선택해주세요 ( Ex. 운동 ) > ");
			
			String theme_menu = input.next();
			
			PreparedStatement pstmt = null;
			
			String sql = "SELECT magazinename, publishername, magazinepage, magazinereleasedate, magazinetheme FROM magazine, publisher "
					+ "WHERE magazine.publisherid = publisher.publisherid && magazinetheme = ?;";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, theme_menu);
			
			ResultSet rs = pstmt.executeQuery();
			
			System.out.println("============================================================================");
			System.out.printf("%30s \t| %20s \t| %15s | %20s \t| %20s \n", "잡지명", "출판사명", "쪽수", "출시일", "주제");
			
			
			while(rs.next())
			{
				System.out.printf("%30s \t| %20s \t| %15d | %20s \t| %20s \n", rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5));
			}

			System.out.println("============================================================================");
	
			conn.close(); 
		} catch(Exception e){ System.out.println(e); } 
	}
}