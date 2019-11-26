import java.sql.*;
import java.util.Scanner;

public class Test 
{
	public static void main(String args[])
	{ 
		Scanner input = new Scanner(System.in);
		
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
		
		try
		{ 
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection conn	= DriverManager.getConnection("jdbc:mysql://192.168.56.101:3308/madang","dbuser", "1234");
			
			Statement stmt = conn.createStatement();
			
			ResultSet rs	= stmt.executeQuery("SELECT * FROM Book");
			
			while(rs.next()) 
				System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3));
			
			conn.close(); 
		}
		catch(Exception e){ System.out.println(e);} 
	}
}