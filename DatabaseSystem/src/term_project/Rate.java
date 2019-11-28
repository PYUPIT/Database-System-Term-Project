package term_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Rate {
	
	String user_id = "1";
	Scanner input = new Scanner(System.in);
	
	public Rate() {
	}
	
	public void RateMagazine() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		
			Connection conn	= DriverManager.getConnection("jdbc:mysql://192.168.56.101:3308/dbproject","dbuser", "1234");
			
			PreparedStatement pstmt = null;
			
			String sql_magazine1 = "SELECT lend.magazineid, magazinename FROM lend, magazine WHERE lend.magazineid = magazine.magazineid && userid = ?";
			
			pstmt = conn.prepareStatement(sql_magazine1);
			
			pstmt.setString(1, user_id);
			
			ResultSet rs1 = pstmt.executeQuery();
			
			while(rs1.next()) {
				System.out.printf("[%s] %s \n", rs1.getString("magazineid"), rs1.getString("magazinename"));
			}
			System.out.print("평가할 잡지를  선택해주세요 : ");
			
			String magazine_id = input.next();
			
			String sql_magazine2 = "SELECT lendid FROM lend WHERE userid = ? && magazineid = ?";
			
			pstmt = conn.prepareStatement(sql_magazine2);
			
			pstmt.setString(1, user_id);
			pstmt.setString(2, magazine_id);
			
			ResultSet rs2 = pstmt.executeQuery();
			
			if(rs2.next() == false) {
				System.out.println("대여했던 도서만 평가가 가능합니다.");
			}
			else {
				System.out.print("부여하실 점수를 입력해 주세요 (최대 10점 까지 ): ");
				
				String ratingscore = input.next();
				
				String sql_rating = "INSERT INTO rating(userid, magazineid, ratingscore) VALUES(?,?,?)";
				
				pstmt = conn.prepareStatement(sql_rating);
				
				pstmt.setString(1, user_id);
				pstmt.setString(2, magazine_id);
				pstmt.setString(3, ratingscore);
				
				pstmt.executeUpdate();
			}
		} catch(Exception e) {}
	}
	
	public void CheckRatingScore() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		
			Connection conn	= DriverManager.getConnection("jdbc:mysql://192.168.56.101:3308/dbproject","dbuser", "1234");
			
			PreparedStatement pstmt = null;
			
			String sql_magazine = "SELECT magazinename, AVG(ratingscore) AS Score FROM rating, magazine WHERE rating.magazineid = magazine.magazineid && rating.magazineid = ? ";
			
			pstmt = conn.prepareStatement(sql_magazine);
			
			System.out.print("어떤 잡지의 평점을 확인하시겠습니까? : ");
			
			String magazine_id = input.next();

			pstmt.setString(1, magazine_id);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next() == false) {
				System.out.println("해당 잡지에 대한 평점이 존재하지 않습니다.");
			}
			else {
				String score1 = rs.getString(2);
				float score2 = Float.parseFloat(score1);
				System.out.printf("%s %.2f", rs.getString("magazinename"),score2);
			}
		} catch(Exception e) {}
	}
}
