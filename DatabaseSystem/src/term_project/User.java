package term_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class User {
	
	private String user_id;
	
	Scanner input = new Scanner(System.in);
	
	public String GetUserId() {
		return this.user_id;
	}
	
	public String Login() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		
			Connection conn	= DriverManager.getConnection("jdbc:mysql://192.168.56.101:3308/dbproject","dbuser", "1234");
		
			System.out.println("Login ....");
			System.out.println("--------------------------------------");
			System.out.print("|	Name	| ");
			String input_name = input.next();
			System.out.print("|	PW		| ");
			String input_pw = input.next();
			System.out.println("--------------------------------------");
			
			PreparedStatement pstmt = null;
			
			String sql = "SELECT username, password, userid FROM user WHERE username = ? && password = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, input_name);
			pstmt.setString(2, input_pw);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next() == false) {
				System.out.println("회원등록이 안되어 있습니다. 가입을 해주세요.");
				String temp = rs.getString("userid");
				return temp;
			}
			else {
				System.out.println("로그인 되었습니다.");
				return null;
			}
		} catch(Exception e) {}
		
		return null;
	}
}
