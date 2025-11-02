package edu.kh.jdbc.homework.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static edu.kh.jdbc.homework.common.JDBCTemplate.*;
import edu.kh.jdbc.homework.model.dto.Student;

public class StudentDAO {

	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	/** 학생 등록 DAO
	 * @param conn : DB 연결 정보가 담긴 Connection 객체
	 * @param std : 등록을 위해 입력 받은 학생 정보가 담긴 객체
	 * @return result : 등록된 결과 행의 개수
	 * @throws Exception
	 */
	public int insertStudent(Connection conn, Student std) throws Exception{
		
		int result = 0;
		
		try {
		
			String sql = """
					INSERT INTO KH_STUDENT
					VALUES(SEQ_STD_NO.NEXTVAL, ?, ?, ?, DEFAULT, ?)
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, std.getName());
			pstmt.setString(2, std.getPNo());
			pstmt.setString(3, std.getMajor());
			pstmt.setString(4, std.getStatus());
			
			result = pstmt.executeUpdate(); 
			
		} finally {
			
			close(pstmt);

		}
				
		return result;
		
	}

	/** 전체 학생 조회 DAO
	 * @param conn : DB 연결 정보가 담긴 Connection 객체
	 * @return stdList : 조회된 학생들이 담긴 List
	 * @throws Exception
	 */
	public List<Student> selectAll(Connection conn) throws Exception{
		
		List<Student> stdList = new ArrayList<Student>();
		
		try {
			
			String sql = """
					SELECT STD_NO, STD_NAME, PERSONAL_NO, MAJOR,
					TO_CHAR(ENT_DATE, 'YYYY"년" MM"월" DD"일"') ENT_DATE, STATUS
					FROM KH_STUDENT
					ORDER BY STD_NO
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				int stdNo = rs.getInt("STD_NO");
				String name = rs.getString("STD_NAME");
				String pNo = rs.getString("PERSONAL_NO");
				String major = rs.getString("MAJOR");
				String entDate = rs.getString("ENT_DATE");
				String status = rs.getString("STATUS");
				
				Student std = new Student(stdNo, name, pNo, major, entDate, status);
				stdList.add(std);
				
			}
			
		} finally {
			
			close(rs);
			close(pstmt);
			
		}
		
		return stdList;
	}

	/** 검색어를 포함하는 이름의 학생 조회 DAO
	 * @param conn : DB 연결 정보가 담긴 Connection 객체
	 * @param input : 입력 받은 검색어
	 * @return stdList : 조회 결과가 담긴 List
	 * @throws Exception
	 */
	public List<Student> selectName(Connection conn, String input) throws Exception{
		
		List<Student> stdList = new ArrayList<Student>();
		
		try {
			
			String sql = """
					SELECT STD_NO, STD_NAME, PERSONAL_NO, MAJOR,
					TO_CHAR(ENT_DATE, 'YYYY"년" MM"월" DD"일"') ENT_DATE, STATUS
					FROM KH_STUDENT
					WHERE STD_NAME LIKE '%'||?||'%'
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, input);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				int stdNo = rs.getInt("STD_NO");
				String name = rs.getString("STD_NAME");
				String pNo = rs.getString("PERSONAL_NO");
				String major = rs.getString("MAJOR");
				String entDate = rs.getString("ENT_DATE");
				String status = rs.getString("STATUS");
				
				Student std = new Student(stdNo, name, pNo, major, entDate, status);
				stdList.add(std);
				
			}
			
			
		} finally {
			
			close(rs);
			close(pstmt);
			
		}
		
		return stdList;
	}

	/** 학번으로 학생 조회 DAO
	 * @param conn : DB 연결 정보가 담긴 Connection 객체
	 * @param input : 입력 받은 학번
	 * @return std : 조회 결과가 담긴 객체
	 * @throws Exception
	 */
	public Student selectStdNo(Connection conn, int input) throws Exception{

		Student std = null;
		
		try {
			
			String sql = """
					SELECT STD_NO, STD_NAME, PERSONAL_NO, MAJOR,
					TO_CHAR(ENT_DATE, 'YYYY"년" MM"월" DD"일"') ENT_DATE, STATUS
					FROM KH_STUDENT
					WHERE STD_NO = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, input);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				
				int stdNo = rs.getInt("STD_NO");
				String name = rs.getString("STD_NAME");
				String pNo = rs.getString("PERSONAL_NO");
				String major = rs.getString("MAJOR");
				String entDate = rs.getString("ENT_DATE");
				String status = rs.getString("STATUS");
				
				std = new Student(stdNo, name, pNo, major, entDate, status);
				
			}
			
		} finally {
			
			close(rs);
			close(pstmt);
			
		}
		
		return std;
	}

	/** 전공별 학생 조회 DAO
	 * @param conn : DB 연결 정보가 담긴 Connection 객체
	 * @param input : 입력 받은 전공
	 * @return stdList : 조회 결과가 담긴 List
	 * @throws Exception
	 */
	public List<Student> selectSortByMajor(Connection conn, String input) throws Exception{
		
		List<Student> stdList = new ArrayList<Student>();
		
		try {
			
			String sql = """
					SELECT STD_NO, STD_NAME, PERSONAL_NO, MAJOR,
					TO_CHAR(ENT_DATE, 'YYYY"년" MM"월" DD"일"') ENT_DATE, STATUS
					FROM KH_STUDENT
					WHERE MAJOR LIKE '%'||?||'%'
					ORDER BY STD_NO
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, input);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				int stdNo = rs.getInt("STD_NO");
				String name = rs.getString("STD_NAME");
				String pNo = rs.getString("PERSONAL_NO");
				String major = rs.getString("MAJOR");
				String entDate = rs.getString("ENT_DATE");
				String status = rs.getString("STATUS");
				
				Student std = new Student(stdNo, name, pNo, major, entDate, status);
				stdList.add(std);
				
			}
			
		} finally {
			
			close(rs);
			close(pstmt);
		}
		
		return stdList;
	}

	/** 이름과 주민번호로 학생의 학번 조회 DAO
	 * @param conn : DB 연결 정보가 담긴 Connection 객체
	 * @param inputName : 입력 받은 이름
	 * @param inputPno : 입력 받은 주민번호
	 * @return stdNo : 조회 결과 얻은 학생의 학번
	 * @throws Exception
	 */
	public int selectStdNo(Connection conn, String inputName, String inputPno) throws Exception{
		
		int stdNo = 0;
		
		try {
			
			String sql = """
					SELECT STD_NO
					FROM KH_STUDENT
					WHERE STD_NAME = ? AND PERSONAL_NO = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, inputName);
			pstmt.setString(2, inputPno);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				stdNo = rs.getInt("STD_NO");
			}
			
		} finally {
			
			close(rs);
			close(pstmt);
			
		}
		
		return stdNo;
	}
	
	/** 학생 이름 수정 DAO
	 * @param conn : DB 연결 정보가 담긴 Connection 객체
	 * @param stdNo : selectStdNo 메서드를 통해 얻어온 학번
	 * @param reName : 수정을 위해 입력 받은 새로운 이름
	 * @return result : 수정된 결과 행의 개수
	 * @throws Exception
	 */
	public int updateName(Connection conn, int stdNo, String reName) throws Exception{
		
		int result = 0;
		
		try {
			
			String sql = """
					UPDATE KH_STUDENT SET STD_NAME = ?
							WHERE STD_NO = ?
									""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, reName);
			pstmt.setInt(2, stdNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			
			close(pstmt);
		}
		
		return result;
	}

	/** 학생 전공 수정 DAO
	 * @param conn : DB 연결 정보가 담긴 Connection 객체
	 * @param stdNo : selectStdNo 메서드를 통해 얻어온 학번
	 * @param inputMajor : 수정을 위해 입력 받은 새로운 전공
	 * @return result : 수정된 결과 행의 개수
	 * @throws Exception
	 */
	public int updateMajor(Connection conn, int stdNo, String inputMajor) throws Exception{
		
		int result = 0;
		
		try {
			
			String sql = """
					UPDATE KH_STUDENT SET MAJOR = ?
					WHERE STD_NO = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, inputMajor);
			pstmt.setInt(2, stdNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			
			close(pstmt);
			
		}
		
		return result;
	}


	/** 학생 재적 상태 수정 DAO
	 * @param conn : DB 연결 정보가 담긴 Connection 객체
	 * @param stdNo : selectStdNo 메서드를 통해 얻어온 학번
	 * @param inputStatus : 수정을 위해 입력 받은 새로운 재적 상태
	 * @return result : 수정된 결과 행의 개수
	 * @throws Exception
	 */
	public int updateStatus(Connection conn, int stdNo, String inputStatus) throws Exception{
		
		int result = 0;
		
		try {
			
			String sql = """
					UPDATE KH_STUDENT SET STATUS = ?
					WHERE STD_NO = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, inputStatus);
			pstmt.setInt(2, stdNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			
			close(pstmt);
		}
		
		return result;
	}

	/** 학생 정보 삭제 DAO
	 * @param conn : DB 연결 정보가 담긴 Connection 객체
	 * @param inputName : 입력 받은 이름
	 * @param inputPno : 입력 받은 주민번호
	 * @return result : 삭제된 결과 행의 개수
	 * @throws Exception
	 */
	public int deleteStudent(Connection conn, String inputName, String inputPno) throws Exception{
		
		int result = 0;
		
		try {
			
			String sql = """
					DELETE FROM KH_STUDENT
					WHERE STD_NAME = ? AND PERSONAL_NO = ?
					""";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, inputName);
			pstmt.setString(2, inputPno);
			
			result = pstmt.executeUpdate();
			
		} finally {
			
			close(pstmt);
		}
		
		return result;
	}

}
