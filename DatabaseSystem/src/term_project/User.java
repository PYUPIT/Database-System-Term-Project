package term_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class User {
	
	private String user_id;
	
	Scanner input = new Scanner(System.in);
	
	public void SetUserId(String user_id) {
		this.user_id = user_id;
		System.out.println(this.user_id);
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
				return null;
			}
			else {
				System.out.println("로그인 되었습니다.");
				String temp = rs.getString("userid");
				user_id = temp;
				return temp;
			}
		} catch(Exception e) {}
		
		return null;
	}
	
	public void RegisterUser() {
		
		System.out.println("Registering User ....\n");
		System.out.println("--------------------------------------------------------------------\n");
		System.out.print("이름을 입력해주세요: ");
		String register_user_name = input.next();
		System.out.print("생년월일을 입력해주세요: ");
		String register_user_birthday = input.next();
		System.out.print("비밀번호를 입력해주세요: ");
		String register_password = input.next();
		System.out.println("--------------------------------------------------------------------\n");
		
		try
		{ 
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection conn	= DriverManager.getConnection("jdbc:mysql://192.168.56.101:3308/dbproject","dbuser", "1234");
			
			PreparedStatement pstmt = null;
			
			String sql = "INSERT INTO user(username, password, userbirthday) VALUES(?,?,?)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, register_user_name);
			pstmt.setString(2, register_password);
			pstmt.setString(3, register_user_birthday);
			
			int tag = pstmt.executeUpdate();
			
			if (tag != 0) {
				System.out.printf("%s 님의 회원 등록이 정상적으로 처리되었습니다.", register_user_name);
			}
			
			conn.close();
		} catch(Exception e){ System.out.println(e);} 
	}
	
	public void UpdateUserinfo() {
		/*
		 * 본 메소드는 로그인 메소드를 수행한 후 user_id에 값이 배정될 때 실행
		 */
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection conn	= DriverManager.getConnection("jdbc:mysql://192.168.56.101:3308/dbproject","dbuser", "1234");
			
			if(user_id == null) {	
				return ;
			}
			
			System.out.println("[1] 이름 \t [2] 생년월일 \t [3] 비밀번호");
			System.out.print("어떠한 정보를 바꾸시겠습니까? : ");
			int menu = input.nextInt();
			
			PreparedStatement pstmt = null;
			
			switch(menu) {
			case 1:		// 회원의 이름을 변경한다.
				System.out.print("변경할 이름을 입력해주세요. : ");
				String updated_user_name = input.next();
				String sql_username = "UPDATE user SET username = ? WHERE userid = ?";			// 현재 로그인된 회원의 이름을 변경합니다.
				
				pstmt = conn.prepareStatement(sql_username);
				
				pstmt.setString(1, updated_user_name);
				pstmt.setString(2, user_id);
				
				pstmt.executeUpdate();
				break;
				
			case 2:		// 회원의 생년월일을 변경한다.
				System.out.print("변경할 생년월일을 입력해주세요. : ");
				String updated_user_birthday = input.next();
				String sql_birthday = "UPDATE user SET userbirthday = ? WHERE userid = ?";		// 현재 로그인된 회원의 생년월일을 변경합니다.
				
				pstmt = conn.prepareStatement(sql_birthday);
				
				pstmt.setString(1, updated_user_birthday);
				pstmt.setString(2, user_id);
				
				pstmt.executeUpdate();
				break;
				
			case 3:		// 회원의 비밀번호를 변경한다.
				System.out.print("변경할 비밀번호 입력해주세요. : ");
				String updated_user_password = input.next();
				String sql_password = "UPDATE user SET password = ? WHERE userid = ?";		// 현재 로그인된 회원의 비밀번호를 변경합니다.
				
				pstmt = conn.prepareStatement(sql_password);
				
				pstmt.setString(1, updated_user_password);
				pstmt.setString(2, user_id);
				
				pstmt.executeUpdate();
				break;
				
			default:
				break;
			}
			
			System.out.println("변경이 완료되었습니다.");
			
		} 	catch(Exception e) {}
	}
	
	public void DeleteUser() {
		
		try
		{ 
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection conn	= DriverManager.getConnection("jdbc:mysql://192.168.56.101:3308/dbproject","dbuser", "1234");
			
			PreparedStatement pstmt = null;
			
			String sql = "DELETE FROM user WHERE userid = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, user_id);
			
			pstmt.executeUpdate();
			
			conn.close();
			
		} catch(Exception e){ System.out.println(e);} 
	}
	
	public void PrintUsers() {
		
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection conn	= DriverManager.getConnection("jdbc:mysql://192.168.56.101:3308/dbproject","dbuser", "1234");
			
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM user");
			
			System.out.println("============================================================================");
			System.out.printf("%7s | %10s | %15s | %15s \n", "User ID", "회원 이름", "생년월일", "PASSWORD");
			
			while(rs.next())
			{
				System.out.printf("%7s | %10s | %15s | %15s \n", rs.getInt(1), rs.getString(2), rs.getString(3), "***********");
			}

			System.out.println("============================================================================");
			
			conn.close(); 
		}	catch(Exception e){ System.out.println(e);} 
	}
}
