package edu.kh.jdbc.model.service;

import java.sql.Connection;
import java.util.List;

import static edu.kh.jdbc.common.JDBCTemplate.*;
import edu.kh.jdbc.model.dao.UserDAO;
import edu.kh.jdbc.model.dto.User;

/* Service (Model 중 하나) : 비즈니스 로직을 처리하는 계층
  => 데이터를 가공하고 트랜잭션(commit, rollback) 관리 수행 */

public class UserService {
	
	// 필드
	private UserDAO dao = new UserDAO();

	/** 1. User 등록 서비스
	 * @param user : 입력받은 id, pw, name 세팅된 객체
	 * @return INSERT된 결과 행의 개수
	 */
	public int insertUser(User user) throws Exception{
		
		// 1. 커넥션 생성
		Connection conn = getConnection();
		
		// 2. 데이터 가공(필요 없으면 생략 가능)
		
		// 3. DAO 메서드 호출 후 결과 반환 받아오기
		int result = dao.insertUser(conn, user);
				
		// 4. DML(INSERT) 수행 결과에 따라 트랜잭션 제어 처리 (전달, 반환 절차 다 끝난 후)
		if(result > 0) { // INSERT 성공한 경우
			
			commit(conn);
			
		} else { // INSERT 실패한 경우
			
			rollback(conn);
		}
		
		// 5. Connection 반환
		close(conn);
		
		// 6. 결과 반환
		return result;
	}

	/** 2. User 전체 조회 서비스
	 * @return 조회된 User들 담긴 List
	 */
	public List<User> selectAll() throws Exception{
		// 1. 커넥션 생성
		Connection conn = getConnection();
		
		// 2. (데이터 가공 생략 후) DAO 메서드 호출(SELECT) 후 결과 반환(List<User>) 받아오기
		List<User> userList = dao.selectAll(conn);
		
		// 3. 커넥션 반환
		close(conn);		
		
		// 4. 결과 반환
		return userList;
	}

	/** 3. User 중 이름에 검색어가 포함된 회원 조회 서비스
	 * @param input : 입력 받은 검색어
	 * @return userList : 조회된 회원 리스트
	 * @throws Exception
	 */
	public List<User> selectName(String input) throws Exception{
		
		Connection conn = getConnection();
		
		List<User> userList = dao.selectName(conn, input);
		
		close(conn);
		
		return userList;
	}

	public User selectUser(int input) throws Exception{
		
		Connection conn = getConnection();
		
		User user = dao.selectUser(conn, input);
		
		close(conn);
				
		return user;
	}

	public int deleteUser(int input) throws Exception{
		
		Connection conn = getConnection();
		
		int result = dao.deleteUser(conn, input);
		
		if(result != 0) commit(conn);
		else rollback(conn);
		
		close(conn);
		
		return result;
	}


}
