package edu.kh.jdbc.homework.model.service;

import static edu.kh.jdbc.homework.common.JDBCTemplate.getConnection;

import java.sql.Connection;

import edu.kh.jdbc.homework.model.dao.StudentDAO;
import edu.kh.jdbc.homework.model.dto.Student;

public class StudentService {

	private StudentDAO dao = new StudentDAO();
	
	public int insertStudent(Student std) throws Exception{
		
		Connection conn = getConnection();
		
		int result = dao.insertStudent(conn, std);
		
		
		return result;
	}

}
