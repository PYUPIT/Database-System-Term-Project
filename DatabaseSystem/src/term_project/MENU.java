package term_project;

import java.sql.*;
import java.util.Scanner;

public class MENU 
{
	public static Connection conn;
	
	static String user_id;
	
	public static void main(String args[])
	{ 
		Scanner input = new Scanner(System.in);
		
		User us = new User();
		Magazine mg = new Magazine();
		Lend ld = new Lend(user_id);
		Rate rt = new Rate();
		Publisher pb = new Publisher();
		Publish ps = new Publish();
		Pay pay = new Pay();
		boolean loop_a = true;
		int menu = 0;
		
		while(loop_a) {
			System.out.println(" ");
			System.out.println("------------------------------------------------------------------------");
			System.out.println(" 						Database System Term Project	 				");
			System.out.println(" 월정액 잡지 대여 서비스 														");
			System.out.println("			At Chungbuk National University Made By Junho Jin			");
			System.out.println("------------------------------------------------------------------------");
			System.out.println("  1. 연동하기			 2. 로그인			\t 3. 회원등록하기					");
			System.out.println("  4. 회원정보 수정하기	 5. 회원 탈퇴하기 		 6. 모든 회원정보 출력하기				");
			System.out.println("  7. 구독권 결제하기		 8. 잡지 목록 출력하기	 9. 주제별 잡지 출력하기 					");
			System.out.println(" 10. 월별 잡지 출력하기	11. 출판사 목록 출력하기	12. 잡지 등록하기					");
			System.out.println(" 13. 대여하기			14. 평점 부여하기		15. 잡지 평점 확인하기					");
			System.out.println(" 99. 프로그램 종료															");
			System.out.println("------------------------------------------------------------------------");
			
			System.out.print("Select a Menu : ");
			
			menu = input.nextInt();
			
			switch(menu) {
			case 1:
				Connecting();
				break;
			case 2:
				user_id = us.Login();
				break;
			case 3:
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
				pay.SetUserID(user_id);
				pay.PayingTicket();
				break;
			case 8:
				mg.PrintMagazines();
				break;
			case 9:
				mg.PrintMagazinesByTheme();
				break;
			case 10:
				mg.PrintMagazinesByReleasedate();
				break;
			case 11:
				pb.PrintPublishers();
				break;
			case 12:
				ps.PublishMagazine();
				break;
			case 13:
				ld.LendMagazine();
				break;
			case 14:
				rt.RateMagazine();
				break;
			case 15:
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
		
		System.out.println("Connecting MySQL ....");
		
		String jdbc_driver = "com.mysql.cj.jdbc.Driver";
		
		try { 
			Class.forName(jdbc_driver);
			
			conn = DriverManager.getConnection("jdbc:mysql://192.168.56.101:3308/dbproject","dbuser", "1234");
		
		} catch(Exception e){ System.out.println(e);} 
	}
}