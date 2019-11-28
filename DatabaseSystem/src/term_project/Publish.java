package term_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Publish {
	
	Scanner input = new Scanner(System.in);
	
	public Publish() {
		
	}
	
	public void PublishMagazine() {
		
		try
		{ 
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection conn	= DriverManager.getConnection("jdbc:mysql://192.168.56.101:3308/dbproject","dbuser", "1234");
			
			PreparedStatement pstmt = null;
			
			String sql = "INSERT INTO magazine(magazinename, publisherid, magazinepage, magazinereleasedate, magazinetheme) VALUES(?,?,?,?,?)";
			
			System.out.print("잡지명을 입력해주세요 : ");
			String magazine_name  = input.nextLine();
			System.out.print("출판사 ID를 입력해주세요 : ");
			String publisher_id  = input.next();
			System.out.print("페이지 수를 입력해주세요 : ");
			String magazine_page  = input.next();
			System.out.print("잡지 출판일을 입력해주세요 : ");
			String magazine_releasedate = input.next();
			System.out.print("잡지의 주제를 입력해주세요 : ");
			String magazine_theme = input.next();
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, magazine_name);
			pstmt.setString(2, publisher_id);
			pstmt.setString(3, magazine_page);
			pstmt.setString(4, magazine_releasedate);
			pstmt.setString(5, magazine_theme);
			
			pstmt.executeUpdate();
			
			conn.close(); 
		} catch(Exception e){ System.out.println(e);} 
	}
}
