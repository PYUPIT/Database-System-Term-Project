package term_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class Lend {
	
	String user_id = "1";
	
	Scanner input = new Scanner(System.in);
	
	public Lend(String user_id) {
		this.user_id = user_id;
	}
	
	public void LendMagazine() {
		
		System.out.println("Lending megazine ....\n");
		
		try
		{ 
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection conn	= DriverManager.getConnection("jdbc:mysql://192.168.56.101:3308/dbproject","dbuser", "1234");
			
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
