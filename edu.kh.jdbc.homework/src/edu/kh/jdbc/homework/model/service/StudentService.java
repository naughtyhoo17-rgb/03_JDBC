package edu.kh.jdbc.homework.model.service;

import static edu.kh.jdbc.homework.common.JDBCTemplate.getConnection;

import java.sql.Connection;
import java.util.List;

import static edu.kh.jdbc.homework.common.JDBCTemplate.*;
import edu.kh.jdbc.homework.model.dao.StudentDAO;
import edu.kh.jdbc.homework.model.dto.Student;

public class StudentService {

	private StudentDAO dao = new StudentDAO();
	
	/** 학생 등록 서비스
	 * @param std : 입력 받은 이름, 주민번호, 전공, 재적 상태가 세팅된 객체
	 * @return result : 등록된 결과 행의 개수
	 * @throws Exception
	 */
	public int insertStudent(Student std) throws Exception{
		
		Connection conn = getConnection();
		
		int result = dao.insertStudent(conn, std);
		
		if(result > 0) commit(conn);
		else rollback(conn);
			
		close(conn);
		
		return result;
		
	}

	/** 전체 학생 조회 서비스
	 * @return stdList : 조회된 전체 학생들이 담긴 List
	 * @throws Exception
	 */
	public List<Student> selectAll() throws Exception{

		Connection conn = getConnection();
		
		List<Student> stdList = dao.selectAll(conn);
		
		close(conn);
				
		return stdList;
	}

	/** 검색어를 포함하는 이름의 학생 조회 서비스
	 * @param input : 입력 받은 검색어
	 * @return stdList : 조회 결과가 담긴 List
	 * @throws Exception
	 */
	public List<Student> selectName(String input) throws Exception{
		
		Connection conn = getConnection();
		
		List<Student> stdList = dao.selectName(conn, input);
		
		close(conn);
		
		return stdList;
	}

	/** 학번으로 학생 조회 서비스
	 * @param input : 입력 받은 학번
	 * @return std : 입력 받은 학번과 일치하는 학번의 학생 정보가 담긴 객체
	 * @throws Exception
	 */
	public Student selectStdNo(int input) throws Exception{
		
		Connection conn = getConnection();
		
		Student std = dao.selectStdNo(conn, input);
		
		close(conn);
		
		return std;
	}

	/** 전공별 학생 조회 서비스
	 * @param input : 입력 받은 전공명
	 * @return stdList : 해당 전공을 가진 학생들이 담긴 List
	 * @throws Exception
	 */
	public List<Student> selectSortByMajor(String input) throws Exception{
		
		Connection conn = getConnection();
		
		List<Student> stdList = dao.selectSortByMajor(conn, input);
		
		close(conn);
		
		return stdList;
	}
	/** 이름과 주민번호로 학생의 학번 조회 서비스
	 * @param inputName : 입력 받은 이름
	 * @param inputPno : 입력 받은 주민번호
	 * @return stdNo : 조회 결과에 해당하는 학생의 학번
	 * @throws Exception
	 */
	public int selectStdNo(String inputName, String inputPno) throws Exception{
		
		Connection conn = getConnection();
		
		int stdNo = dao.selectStdNo(conn, inputName, inputPno);
		
		close(conn);
		
		return stdNo;
	}


	/** 학생 이름 수정 서비스
	 * @param stdNo : selectStdNo 메서드를 통해 얻어온 학번
	 * @param reName : 수정을 위해 입력 받은 새로운 이름
	 * @return result : 수정된 결과 행의 개수
	 * @throws Exception
	 */
	public int updateName(int stdNo, String reName) throws Exception{
		
		Connection conn = getConnection();
		
		int result = dao.updateName(conn, stdNo, reName);
		
		if(result > 0) commit(conn);
		else rollback(conn);
		
		close(conn);
		
		return result;
	}

	/** 학생 전공 수정 서비스
	 * @param stdNo : selectStdNo 메서드를 통해 얻어온 학번
	 * @param inputMajor : 수정을 위해 입력 받은 새로운 전공
	 * @return result : 수정된 결과 행의 개수
	 * @throws Exception
	 */
	public int updateMajor(int stdNo, String inputMajor) throws Exception{
		
		Connection conn = getConnection();
		
		int result = dao.updateMajor(conn, stdNo, inputMajor);
		
		if(result > 0) commit(conn);
		else rollback(conn);
		
		close(conn);
		
		return result;
	}
	
	/** 학생 재적 상태 수정 서비스
	 * @param stdNo : selectStdNo 메서드를 통해 얻어온 학번
	 * @param inputStatus : 수정을 위해 입력 받은 새로운 재적 상태
	 * @return result : 수정된 결과 행의 개수
	 * @throws Exception
	 */
	public int updateStatus(int stdNo, String inputStatus) throws Exception{
		
		Connection conn = getConnection();
		
		int result = dao.updateStatus(conn, stdNo, inputStatus);
		
		if(result > 0) commit(conn);
		else rollback(conn);
		
		close(conn);
		
		return result;
	}

	/** 이름과 주민번호로 학생 정보 삭제 서비스
	 * @param inputName : 입력 받은 이름
	 * @param inputPno : 입력 받은 주민번호
	 * @return result : 삭제된 결과 행의 개수
	 * @throws Exception
	 */
	public int deleteStudent(String inputName, String inputPno) throws Exception{
		
		Connection conn = getConnection();
		
		int result = dao.deleteStudent(conn, inputName, inputPno);
		
		if(result > 0) commit(conn);
		else rollback(conn);
		
		close(conn);
		
		return result;
	}

}
