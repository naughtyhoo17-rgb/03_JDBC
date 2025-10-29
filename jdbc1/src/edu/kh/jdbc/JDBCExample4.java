package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample4 {

	public static void main(String[] args) {
		
		// 부서명을 입력받아 해당 부서에 근무하는 사원의
		// 사번, 이름, 부서명, 직급명을
		// 직급코드 오름차순 조회
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		Scanner sc = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String userName = "kh_psm";
			String password = "kh1234";
			
			conn = DriverManager.getConnection(url, userName, password);
			
			sc = new Scanner(System.in);
			System.out.print("부서명 입력 : ");
			String input = sc.next();
			
			String sql = "SELECT EMP_ID, EMP_NAME, DEPT_TITLE, JOB_NAME "
					+ "FROM EMPLOYEE JOIN JOB USING(JOB_CODE) "
					+ "JOIN DEPARTMENT ON (DEPT_CODE = DEPT_ID) "
					+ "WHERE DEPT_TITLE = " + "'" + input + "' ORDER BY JOB_CODE";
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);

			boolean flag = true; // 조회 결과가 있다면 false, 없으면 true
			
			while(rs.next()) {
				
				flag = false; // 조회 결과가 있음을 표시
				
				String empId = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String dTitle = rs.getString("DEPT_TITLE");
				String jName = rs.getString("JOB_NAME");
				
				System.out.printf("%s / %s / %s / %s \n", empId, empName, dTitle, jName);
			}
			
			if(flag) System.out.println("일치하는 부서가 없습니다.");
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		} finally {
			
			try {
				
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
				
				if(sc != null) sc.close();
				
			} catch (Exception e) {
				
				e.printStackTrace();
				
			}
		 }
		
		
	}

}

/* 	// 6-2) return 사용법
			if(!rs.next()) {
				System.out.println("일치하는 부서가 없습니다");
				return;
			}
			
			// 왜 do~while 문?
			// 위 if문 조건에서 이미 첫번째행 커서가 소비됨.
			// 보통 while문 사용 시 next()를 바로 만나면서 2행부터 접근하게됨.
			// do~while문 사용하여 next() 하지 않아도 1번째행 부터 접근할 수 있도록 함.
			
			do {
				String empId = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String deptTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				
				System.out.printf("%s / %s / %s / %s \n", 
						empId, empName, deptTitle, jobName);
			} while(rs.next());
*/