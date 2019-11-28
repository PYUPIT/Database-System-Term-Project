package term_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class Pay {
	
	String user_id;
	
	Scanner input = new Scanner(System.in);
	
	public Pay () {}
	
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
	
	public void PayingTicket() {
		
		if(user_id == null) {
			System.out.println("구독권을 결제하기 위해서는 로그인을 해주셔야 합니다.");
			return ;
		}
		
		PreparedStatement pstmt = null;
		
		System.out.println("Registering Ticket ....");
		
		System.out.println("구독권 구매 요금은 월 9,900원 입니다.");
		
		System.out.print("[1] 카드  \t [2] 상품권  \t [3] 쿠폰 \t [4] 계좌이체 \t [5] 휴대폰결제  \n");
		
		System.out.print("결제 수단을 선택해주세요 ( Ex. 카드  )>> ");
		
		String method_menu = input.next();
		
		try
		{ 	
			String sql = "INSERT INTO paying(userid, payingmethod, payingcharge, payingdate, expirationdate) VALUES(?,?,?,?,?)";
			
			pstmt = conn.prepareStatement(sql);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			Calendar now = Calendar.getInstance();
			
			String payingdate = sdf.format(now.getTime());
			
			now.add(Calendar.DATE, 30);
			
			String expirationdate = sdf.format(now.getTime());
			
			pstmt.setString(1, user_id);
			pstmt.setString(2, method_menu);			
			pstmt.setString(3, "9900");			
			pstmt.setString(4, payingdate);				
			pstmt.setString(5, expirationdate);			
			
			pstmt.executeUpdate();
			
			conn.close(); 
		} catch(Exception e){ System.out.println(e);} 
	}
}
