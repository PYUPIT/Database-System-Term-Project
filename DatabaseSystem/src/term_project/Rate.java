package term_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Rate {
	
	private String user_id;
	Scanner input = new Scanner(System.in);
	
	public Rate() {}
	
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
	
	void SetUserID(String user_id) {
		
		this.user_id = user_id;
	}
	
	public void RateMagazine() {
		/*
		 * 해당 메소드를 수행하기 위해사는 사용자가 로그인을 해야합니다.
		 */
		if(user_id == null) {
			System.out.println("잡지에 대한 평점을 매기기 위해서는 로그인을 해주셔야 합니다.");
			return ;
		}
		
		try {
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
			
			/*
			 * 회원은 대여했던 잡지에 관해서만 평점을 부여할 수 있습니다. ( lend테이블에 회원이 평가하고자 하는 잡지에 대한 대여정보가 있어야한다. )
			 * 해당 userid와  magazineid를 가지는 lend테이블에 튜플이 있는가를 확인한다.
			 * 있으면 평점을 메길 수 있는 과정으로 넘어가고 없다면 "대여했던 도서만 평가가 가능합니다"라는 문구를 출력하고 메소드를 종료한다.
			 */
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
			PreparedStatement pstmt = null;
			
			/*
			 * 평점을 확인할 잡지의 잡지ID를 입력합니다. 형식은 잡지의 잡지ID를 숫자로 입력해주시면 됩니다.
			 * 해당 잡지 ID를 통해 rating 테이블에 저장된 잡지에 대한 평점과 magazine테이블에서 해당 잡지명을 불러와 출력합니다.
			 */
			
			String sql_magazine = "SELECT magazinename, AVG(ratingscore) AS Score FROM rating, magazine WHERE rating.magazineid = magazine.magazineid && rating.magazineid = ? ";
			
			pstmt = conn.prepareStatement(sql_magazine);
			
			System.out.print("어떤 잡지의 평점을 확인하시겠습니까? : ");
			
			String magazine_id = input.next();

			pstmt.setString(1, magazine_id);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next() == false) {
				/*
				 * 선택한 잡지에 대한 평점이 존재하지 않는 경우 (= rating테이블에 값이 존재하지 않음 ) 아래와 같은 문구를 출력하고 종료한다. 
				 */
				System.out.println("해당 잡지에 대한 평점이 존재하지 않습니다.");
			}
			else {
				/*
				 * else문 선택한 잡지에 대해 평점이 존재하는 경우 해당 잡지에 대한 잡지명과, 평점을 출력한다.
				 * 평점은 소숫점 2자리까지 표시해준다.
				 */
				String score1 = rs.getString(2);
				float score2 = Float.parseFloat(score1);
				System.out.println("-------------------------------------------------------------"	);
				System.out.printf("[잡지명] %-30s | [평점] %.2f \n", rs.getString("magazinename"),score2);
				System.out.println("-------------------------------------------------------------"	);
			}
		} catch(Exception e) {}
	}
}
