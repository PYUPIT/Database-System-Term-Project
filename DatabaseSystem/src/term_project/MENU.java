package term_project;

import java.sql.*;
import java.util.Scanner;

public class MENU 
{
	public static Connection conn;
	
	static String user_id;
	
	static String url;
	static String usr;
	static String pwd;
	
	public static void main(String args[])
	{ 
		Scanner input = new Scanner(System.in);
		
		User us = new User();
		Magazine mg = new Magazine();
		Lend ld = new Lend();
		Rate rt = new Rate();
		Publisher pb = new Publisher();
		Publish ps = new Publish();
		Pay pay = new Pay();
		
		boolean loop_a = true;
		
		while(loop_a) {
			System.out.println(" ");
			System.out.println("------------------------------------------------------------------------");
			System.out.println(" 						Database System Term Project	 				");
			System.out.println(" 월정액 잡지 대여 서비스 														");
			System.out.println("			At Chungbuk National University Made By Junho Jin			");
			System.out.println("------------------------------------------------------------------------");
			System.out.println("  1. 연동하기			 2. 로그인			\t 3. 회원등록하기					");
			System.out.println("  4. 회원정보 수정하기	 5. 회원 탈퇴하기 		 6. 회원정보 출력하기					");
			System.out.println("  7. 구독권 결제하기		 8. 잡지 목록 출력하기	 9. 주제별 잡지 출력하기 					");
			System.out.println(" 10. 월별 잡지 출력하기	11. 출판사 목록 출력하기	12. 잡지 등록하기					");
			System.out.println(" 13. 대여하기			14. 평점 부여하기		15. 잡지 평점 확인하기					");
			System.out.println(" 99. 프로그램 종료															");
			System.out.println("------------------------------------------------------------------------");
			
			
			
			System.out.print("Select a Menu : ");
			int menu = input.nextInt();
			
			switch(menu) {
			case 1:
				Connecting();
				break;
			case 2:
				us.SetConnection(url, usr, pwd);
				user_id = us.Login();
				break;
			case 3:
				us.SetConnection(url, usr, pwd);
				us.RegisterUser();
				break;
			case 4:
				us.UpdateUserinfo();
				break;
			case 5:
				us.DeleteUser();
				break;
			case 6:
				us.PrintUsers();
				break;
			case 7:
				pay.SetConnection(url, usr, pwd);
				pay.SetUserID(user_id);
				pay.PayingTicket();
				break;
			case 8:
				mg.SetConnection(url, usr, pwd);
				mg.PrintMagazines();
				break;
			case 9:
				mg.SetConnection(url, usr, pwd);
				mg.PrintMagazinesByTheme();
				break;
			case 10:
				mg.SetConnection(url, usr, pwd);
				mg.PrintMagazinesByReleasedate();
				break;
			case 11:
				pb.SetConnection(url, usr, pwd);
				pb.PrintPublishers();
				break;
			case 12:
				ps.PublishMagazine();
				break;
			case 13:
				ld.SetConnection(url, usr, pwd);
				ld.SetUserID(user_id);
				ld.LendMagazine();
				break;
			case 14:
				rt.SetConnection(url, usr, pwd);
				rt.SetUserID(user_id);
				rt.RateMagazine();
				break;
			case 15:
				rt.SetConnection(url, usr, pwd);
				rt.SetUserID(user_id);
				rt.CheckRatingScore();
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
		
		System.out.println("Connecting to Database ....");
		
		url = "jdbc:mysql://192.168.56.101:3308/dbproject";
		usr = "dbuser";
		pwd = "1234";
		
		String jdbc_driver = "com.mysql.cj.jdbc.Driver";
		
		try
		{
			Class.forName(jdbc_driver);
			
			conn = DriverManager.getConnection(url,usr, pwd);
		
		} catch(Exception e){ System.out.println(e);}
		
		System.out.println("데이터베이스와 연동이 완료되었습니다.");
	}
}