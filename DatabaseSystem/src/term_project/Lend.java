package term_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class Lend {
	
	String user_id;
	
	Scanner input = new Scanner(System.in);
	
	public Lend() {}
	
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
		/*
		 * 해당 클래스의 메소드는 로그인 되어야만 사용이 가능하므로 해당 메소드는 ID를 불러오는 기능을 수행합니다.
		 */
		this.user_id = user_id;
	}
	
	public void LendMagazine() {
		
		if(user_id == null) {
			System.out.println("잡지를 대여하기 위해서는 로그인을 해주셔야 합니다.");
			return ;
		}
		
		System.out.println("Lending megazine ....\n");
		
		try
		{ 
			PreparedStatement pstmt = null;
			
			String sql_paying = "SELECT payingmethod FROM paying WHERE userid = ?";
			
			pstmt = conn.prepareStatement(sql_paying);
			
			pstmt.setString(1, user_id);
			
			ResultSet rs1 = pstmt.executeQuery();
			
			if(rs1.next() == false) {
				System.out.println("대여하기 전에 구독권을 결제해주세요.");
			}
			
			else {
				System.out.print("대여할 잡지 ID를 입력해주세요. ( Ex. 1 ) > ");
				
				String magazine_id = input.next();
				
				String sql_lending = "INSERT INTO lend(userid, magazineid, lenddate) VALUES(?,?,?)";
				
				pstmt = conn.prepareStatement(sql_lending);
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				
				Calendar now = Calendar.getInstance();
				
				String lending_date = sdf.format(now.getTime());
				
				pstmt.setString(1, user_id);
				pstmt.setString(2, magazine_id);
				pstmt.setString(3, lending_date);
				
				pstmt.executeUpdate();
			}
			
			conn.close(); 
		} catch(Exception e){ System.out.println(e);} 
	}
}
