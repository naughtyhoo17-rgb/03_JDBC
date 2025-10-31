package edu.kh.jdbc.homework.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import edu.kh.jdbc.homework.model.dto.Student;

public class StudentDAO {

	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	public int insertStudent(Connection conn, Student std) throws Exception{
		
		int result = 0;
		
		try {
		
			String sql = """
					INSERT INTO KH_STUDENT
					VALUES(SEQ_STD_NO.NEXTVAL, ?, ?, ?, DEFAULT, ?)
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, name);
			pstmt.setString(2, pNo);
			pstmt.setString(3, major);
			pstmt.setString(4, status);
			
		} finally {

		}
		
		
		return result;
	}

}
