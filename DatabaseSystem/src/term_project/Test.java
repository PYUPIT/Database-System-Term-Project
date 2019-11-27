package term_project;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class Test 
{
	String user_name;
	
	public static void main(String args[])
	{ 
		Scanner input = new Scanner(System.in);
		
		Magazine mg = new Magazine();
		
		boolean loop_a = true;
		int menu = 0;
		
		while(loop_a) {
			System.out.println(" ");
			System.out.println("------------------------------------------------------------");
			System.out.println(" Database System Term Project ");
			System.out.println(" 월정액 잡지 대여 서비스 ");
			System.out.println(" at Chungbuk National University Made By Jin Jun-ho");
			System.out.println("------------------------------------------------------------");
			System.out.println(" 1. connection		2. ********* ");
			System.out.println(" 3. ********		4. ******** ");
			System.out.println(" 5. ********	6. ******** ");
			System.out.println(" 7. ********	8. ******** ");
			System.out.println(" 99. quit");
			System.out.println("------------------------------------------------------------");
			System.out.print("Enter an integer: ");
			
			menu = input.nextInt();
			
			switch(menu) {
			case 1:
				Connecting();
				break;
			case 2:
				RegisterUser();
				break;
			case 4:
				DeleteUser();
				break;
			case 5:
				PrintUsers();
				break;
			case 6:
				PayingTicket();
				break;
			case 11:
				PrintPublishers();
				break;
			case 7:
				mg.PrintMagazines();
				break;
			case 8:
				mg.PrintMagazinesByTheme();
				break;
			case 99:
				System.out.println("Bye~");
				loop_a = false;
				break;
			}
		}
		
		input.close();
	}
	
	public static void Connecting() {
		
		System.out.println("Connecting MySQL ....");
		
		try { 
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection conn	= DriverManager.getConnection("jdbc:mysql://192.168.56.101:3308/dbproject","dbuser", "1234");

			conn.close(); 
		} catch(Exception e){ System.out.println(e);} 
	}
	
	public static void RegisterUser() {
		
		Scanner input = new Scanner(System.in);
		
		System.out.println("Registering User ....");
		System.out.print("이름을 입력해주세요: ");
		
		String user_name = input.next();
		
		try
		{ 
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection conn	= DriverManager.getConnection("jdbc:mysql://192.168.56.101:3308/dbproject","dbuser", "1234");
			
			Statement stmt = conn.createStatement();
			
			PreparedStatement pstmt = null;
			
			String sql = "INSERT INTO user(username, password, userbirthday) VALUES(?,?,?)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, "유재석");
			pstmt.setString(2, "123456");
			pstmt.setString(3, "1972-10-03");
			
			pstmt.executeUpdate();
			
			conn.close(); 
		} catch(Exception e){ System.out.println(e);} 
	}
	
	public static void DeleteUser() {
		
		try
		{ 
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection conn	= DriverManager.getConnection("jdbc:mysql://192.168.56.101:3308/dbproject","dbuser", "1234");
			
			PreparedStatement pstmt = null;
			
			String sql = "DELETE FROM user WHERE userid = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, "2");
			
			pstmt.executeUpdate();
			
			conn.close(); 
		} catch(Exception e){ System.out.println(e);} 
	}
	
	public static void PrintUsers() {
		
		System.out.println("Printing User ....");
		
		try
		{ 
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection conn	= DriverManager.getConnection("jdbc:mysql://192.168.56.101:3308/dbproject","dbuser", "1234");
			
			Statement stmt = conn.createStatement();
			
			ResultSet rs	= stmt.executeQuery("SELECT * FROM user");
			
			System.out.println("============================================================================");
			System.out.printf("%7s | %10s | %15s | %15s \n", "User ID", "회원 이름", "생년월일", "PASSWORD");
			
			while(rs.next())
			{
				System.out.printf("%7s | %10s | %15s | %15s \n", rs.getInt(1), rs.getString(2), rs.getString(3), "***********");
			}

			System.out.println("============================================================================");
			
			conn.close(); 
		}
		catch(Exception e){ System.out.println(e);} 
	}
	
	public static void PayingTicket() {
		
		Scanner input = new Scanner(System.in);
		
		System.out.println("Registering Ticket ....");
		
		System.out.println("구독권 구매 요금은 월 9,900원 입니다.");
		System.out.print("[1] 카드  \t [2] 상품권  \t [3] 쿠폰 \t [4] 계좌이체 \t [5] 휴대폰결제  \n");
		System.out.print("결제 수단을 선택해주세요 ( Ex. 카드  )>> ");
		
		String method_menu = input.next();
		
		try
		{ 
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection conn	= DriverManager.getConnection("jdbc:mysql://192.168.56.101:3308/dbproject","dbuser", "1234");
			
			Statement stmt = conn.createStatement();
			
			PreparedStatement pstmt = null;
			
			String sql = "INSERT INTO paying(userid, payingmethod, payingcharge, payingdate, expirationdate) VALUES(?,?,?,?,?)";
			
			pstmt = conn.prepareStatement(sql);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			Calendar now = Calendar.getInstance();
			
			String payingdate = sdf.format(now.getTime());
			
			now.add(Calendar.DATE, 30);
			
			String expirationdate = sdf.format(now.getTime());
			
			pstmt.setString(1, "1");
			pstmt.setString(2, method_menu);			
			pstmt.setString(3, "9900");			
			pstmt.setString(4, payingdate);				
			pstmt.setString(5, expirationdate);			
			
			pstmt.executeUpdate();
			
			conn.close(); 
		} catch(Exception e){ System.out.println(e);} 
	}
	
	public static void PrintPublishers() {
		
		System.out.println("Printing Pulishers ....");
		
		try
		{ 
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection conn	= DriverManager.getConnection("jdbc:mysql://192.168.56.101:3308/dbproject","dbuser", "1234");
			
			Statement stmt = conn.createStatement();
			
			ResultSet rs	= stmt.executeQuery("SELECT * FROM publisher");
			
			System.out.println("============================================================================");
			System.out.printf("%7s | %30s \t| %15s \n", "출판사 ID", "출판사 이름", "전화번호");
			
			while(rs.next())
			{
				System.out.printf("%7d | %30s \t| %15d \n", rs.getInt(1), rs.getString(2), rs.getInt(3));
			}

			System.out.println("============================================================================");
			
			conn.close(); 
		}
		catch(Exception e){ System.out.println(e);} 
	}
}