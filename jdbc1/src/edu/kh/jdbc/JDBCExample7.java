package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class JDBCExample7 {

	public static void main(String[] args) {
		
		// EMPLOYEE	테이블에서
		// 사번, 이름, 성별, 급여, 직급명, 부서명을 조회
		// 단, 입력 받은 조건에 맞는 결과만 조회하고 정렬할 것
						
		// - 조건 1 : 성별 (M, F)
		// - 조건 2 : 급여 범위
		// - 조건 3 : 급여 오름차순/내림차순
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Scanner sc = null;
		
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String userName = "kh_psm";
			String password = "kh1234";
			
			conn = DriverManager.getConnection(url, userName, password);
			
			sc = new Scanner(System.in);
			
			System.out.print("조회할 성별(M/F) : ");
			String gender = sc.next().toUpperCase();
			System.out.print("급여 범위(최소, 최대 순으로 작성) : ");
			int min = sc.nextInt();
			int max = sc.nextInt();
			System.out.print("급여 정렬(1.오름차순, 2.내림차순) : ");
			int sort = sc.nextInt();
			
			String sql = "SELECT EMP_ID 사번, EMP_NAME 이름, DECODE(SUBSTR(EMP_NO, 8, 1), '1', 'M', '2', 'F') 성별, "
					+ "SALARY 급여, JOB_NAME 직급명, NVL(DEPT_TITLE, '없음') 부서명"
					+ " FROM EMPLOYEE JOIN JOB USING (JOB_CODE) LEFT JOIN DEPARTMENT ON (DEPT_CODE = DEPT_ID)"
					+ " WHERE DECODE(SUBSTR(EMP_NO, 8, 1), '1', 'M', '2', 'F') = ?"
					+ " AND SALARY BETWEEN ? AND ?"
					+ " ORDER BY SALARY";
			
			/* ORDER BY절에 위치홀더 사용 시 오류
			  : SQL 명령어가 올바르게 종료되지 않았습니다.
			 => 위치홀더는 데이터값(리터럴)을 대체하는 용도로만 사용 가능!
			 	thus 정렬기준인 ASC/DESC는 값이 아닌 SQL 문법의 일부로서
			 	위치홀더로 대체 불가능 */
			
			// 급여의 정렬 조건에 따라 SQL 보완
			if(sort == 1) sql += " ASC";
			else sql += " DESC";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, gender);
			pstmt.setInt(2, min);
			pstmt.setInt(3, max);
			
			rs = pstmt.executeQuery();
			
			boolean flag = true; // 조회결과 없으면 true, 있으면 false
			
			System.out.println("사번 | 이름   | 성별 | 급여    | 직급명 | 부서명");
			System.out.println("--------------------------------------------------------");
			
			while(rs.next()) {
				
				flag = false; // while문에 들어온 것은 조회결과가 있다는 의미이니 false
				
				String empId = rs.getString("사번");
				String empName = rs.getString("이름");
				String gen = rs.getString("성별");
				int salary = rs.getInt("급여");
				String jobName = rs.getString("직급명");
				String deptTitle = rs.getString("부서명");
				
				System.out.printf("%-4s | %3s | %-4s | %7d | %-3s  | %s \n",
							empId, empName, gen, salary, jobName, deptTitle);

			}
			
			if(flag) { // flag == true => while문에 안 들어간 것 => 조회결과 없는 것
				
				System.out.println("조회결과없음");
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			
				if(sc != null) sc.close();
				
			} catch (Exception e) {
				
				e.printStackTrace();
				
			}
		}


	}

}
