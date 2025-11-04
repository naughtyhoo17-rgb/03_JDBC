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

	/** 6-1. ID, PW가 일치하는 회원이 있는지 조회(SELECT)
	 * @param userId
	 * @param userPw
	 * @return
	 */
	public int selectUserNo(String userId, String userPw) throws Exception{
		
		Connection conn = getConnection();
		
		int userNo = dao.selectUser(conn, userId, userPw);
		
		close(conn);
		
		return userNo;
	}

	/** 6-2. USER_NO가 일치하는 회원의 이름 수정 서비스(UPDATE)
	 * @param name
	 * @param userNo
	 * @return
	 */
	public int updateName(String name, int userNo) throws Exception{
		
		Connection conn = getConnection();
		
		int result = dao.updateName(conn, name, userNo);
		
		if(result > 0) commit(conn);
		else		   rollback(conn);
		
		close(conn);
		
		return result;
	}

	/** 7. 아이디 중복 확인 서비스
	 * @param userId
	 * @return
	 */
	public int idCheck(String userId) throws Exception {
		
		Connection conn = getConnection();
		
		int count = dao.idCheck(conn, userId);
		
		close(conn);
		
		return count;
	}

	/** userList에 있는 모든 User 객체를 INSERT 서비스
	 * @param userList
	 * @return
	 */
	public int multiInsertUser(List<User> userList) throws Exception{
		
		// 다중 INSERT 방법
		// 1) SQL 을 이용한 다중 INSERT
		// 2) Java 반복문을 이용한 다중 INSERT (이거 사용!)
		
		Connection conn = getConnection();
		
		int count = 0; // 삽입 성공한 행의 갯수 count
		
		for(User user : userList) {
			int result = dao.insertUser(conn, user);
			count += result; // 삽입 성공한 행의 갯수를 count 누적
		}
		
		//count--;
		
		// 전체 삽입 성공 시 commit / 아니면 rollback(일부 삽입, 전체 실패)
		if(count == userList.size()) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		close(conn);
		
		return count;
	}
}
