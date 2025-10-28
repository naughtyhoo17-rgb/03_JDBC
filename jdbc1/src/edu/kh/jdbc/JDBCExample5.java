package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class JDBCExample5 {

	public static void main(String[] args) {

		// 아이디, 비밀번호, 이름을 입력받아
		// TB_USER 테이블에 삽입(INSERT)하기
		
		/*
		 * java.sql.PreparedStatement
		  - SQL 중간에 ?(위치홀더, placeholder)를 작성하여 그 자리에
		    java 값을 대입할 준비가 되어있는 Statement
		  
		 * 장점 1 : SQL 작성 간편
		 * 장점 2 : 위치홀더에 값 대입 시 자료형에 맞는 형태의 리터럴로 대입됨! 
		 			ex) String 대입 -> '값' (자동으로 '' 추가)
		  			ex) int 대입 -> 값
		 * 장점 3 : 성능, 속도에서 기본 Statement보다 우위
		 
		 * *** PreparedStatement는 Statement의 자식!!! ***.
		 * QUERY에 종류와 상관없이 위치홀더가 있다면 무조건 PreparedStatement 사용!
		 * 
		 */
		
		// 1. JDBC 객체 참조 변수 선언
		Connection conn = null;
		PreparedStatement pstmt = null;
			// SELECT 아니기 때문에 ResultSet 받을 rs 필요 없음!
		
		Scanner sc = null;
		
		try {
			
		// 2. DriverManager로 Connection 객체 생성
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String userName = "kh_psm";
			String password = "kh1234";
			
			conn = DriverManager.getConnection(url, userName, password);
			
		// 3. SQL 작성
			sc = new Scanner(System.in);
			
			System.out.print("아이디 입력 : ");
			String id = sc.next(); 
			
			System.out.print("비밀번호 입력 : ");
			String pw = sc.next(); 
			
			System.out.print("이름 입력 : ");
			String name = sc.next(); 
			
			String sql = """
					INSERT INTO TB_USER
					VALUES(SEQ_USER_NO.NEXTVAL, ?, ?, ?, DEFAULT)
					""";
		
		// 4. PreparedStatement 객체 생성
			// => 객체 생성과 동시에 SQL 담김 (버스 만듦과 동시에 타고있는 셈)
			// bcuz 미리 위치홀더에 값을 받을 준비를 해야함
			pstmt = conn.prepareStatement(sql);
			
		// 5. 위치홀더에 알맞은 값 대입
			// pstmt.set자료형(?순서, 대입할 값);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.setString(3, name);
		// 여기까지 수행되면 온전한 SQL 완성된 상태!
			
		// 6. SQL(INSERT) 수행 후 결과(int) 반환 받기
			// DML 수행 전 반드시 해야할 것! => Connection AutoCommit 끄기!
			// Why? 개발자가 transaction을 의도대로 제어하기 위해서
			conn.setAutoCommit(false);
			
			/* executeUpdate() : DML 수행, 결과 행 갯수(int) 반환
			 => 보통 DML 실패 시 0, 성공 시 0 초과값 반환 */
			/* pstmt에서 executeQuery(), executeUpdate()
			 매개변수 자리에 sql 들어오면 안됨 */
			int result = pstmt.executeUpdate();
					// 여기서 전달인자에 sql 넣지 않는다! (pstmt 생성 시 이미 실려있음) 
			
		// 7. result 값에 따른 결과 처리 & 트랜잭션 제어 처리
			if(result > 0) { // INSERT 성공 시
				
				conn.commit(); // COMMIT 수행 => Dbeaver와 동일
										// DB에 INSERT 데이터 영구 반영
				System.out.println(name + "님이 추가되었습니다.");
				
			} else { // INSERT 실패
				
				conn.rollback(); // 실패한 메모리 뒤집어 엎어 비운다
				System.out.println("추가 실패");
				
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			
		} finally {
		
		// 8. 사용한 JDBC 객체 자원 반환
			try {
				
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
				
				if(sc != null) sc.close();
				
			} catch (Exception e) {
				
				e.printStackTrace();
				
			}
			
		}
	}

}
