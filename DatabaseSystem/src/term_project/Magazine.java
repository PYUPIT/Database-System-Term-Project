package term_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
	
	Scanner input = new Scanner(System.in);
	
	public Magazine() {}
	
	Connection conn;
	
	public void SetConnection(String url, String usr, String pwd) {
		
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
		
			conn = DriverManager.getConnection(url, usr, pwd);
		} catch (Exception e) {}
		
	}
	
	public void PrintMagazines() {
		
		System.out.println("Printing Magazines ....");
		
		try
		{
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
		
		System.out.println("Printing Magazines By Theme ....");
		
		try
		{ 
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
	
	public void PrintMagazinesByReleasedate() {
		
		System.out.println("Printing Magazines By ReleaseDate ....");
		
		try
		{ 
			System.out.println("[1] 01월 \t [2] 02월 \t [3] 03월 \t [ 4] 04월\t [ 5] 05월 \t [ 6] 06월 ");
			System.out.println("[7] 07월 \t [8] 08월 \t [9] 09월 \t [10] 10월\t [11] 11월 \t [12] 12월 ");
			
			System.out.print("날짜(월)를 선택해주세요 ( Ex. 01 ) > ");
			
			String releasedate_menu = input.next();
			
			PreparedStatement pstmt = null;
			
			String sql = "SELECT magazinename, publishername, magazinepage, magazinereleasedate, magazinetheme FROM magazine, publisher "
					+ "WHERE magazine.publisherid = publisher.publisherid && DATE_FORMAT(magazinereleasedate, '%m') = ?;";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, releasedate_menu);
			
			ResultSet rs = pstmt.executeQuery();
			
			System.out.println("============================================================================");
			System.out.printf("%30s \t| %25s \t| %15s | %20s \t| %20s \n", "잡지명", "출판사명", "쪽수", "출시일", "주제");
			
			
			while(rs.next())
			{
				System.out.printf("%30s \t| %25s \t| %15d | %20s \t| %20s \n", rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5));
			}

			System.out.println("============================================================================");
	
			conn.close();
		} catch(Exception e){ System.out.println(e); } 
	}
}