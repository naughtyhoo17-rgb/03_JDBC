package edu.kh.jdbc.homework.view;

import java.util.InputMismatchException;
import java.util.Scanner;

import edu.kh.jdbc.homework.model.dto.Student;
import edu.kh.jdbc.homework.model.service.StudentService;


public class StudentView {
	
	private StudentService service = new StudentService();
	private Scanner sc = new Scanner(System.in);

	
	public void displayMenu() {
		
		int input = 0; // 메뉴 선택용 변수
		
		do {
			
			try {
				
				System.out.println("\n===학생 정보 관리 프로그램===\n");
				System.out.println("1. 학생 등록");
				System.out.println("2. 전체 학생 조회");
				System.out.println("3. 이름으로 학생 조회");
				System.out.println("4. 학번으로 학생 조회");
				System.out.println("5. 전공별 학생 조회");
				System.out.println("6. 학생 정보 수정");
				System.out.println("7. 학생 삭제");
				System.out.println("0. 프로그램 종료");
				
				System.out.print("메뉴 선택 : ");
				input = sc.nextInt();
				sc.nextLine();
				
				switch (input) {
				case 1: insertStudent(); break;
				case 2: selectAll(); break;
				case 3: selectName(); break;
				case 4: selectStdNo(); break;
				case 5: selectSortByMajor(); break;
				case 6: updateStudent(); break;
				case 7: deleteStudent(); break;
				case 0: System.out.println("\n[프로그램 종료]\n"); break;
				default: System.out.println("\n[있는 번호만 입력하세요]\n");
				}
				System.out.println("\n-------------------------------------\n");
				
			} catch (InputMismatchException e) {
				
				System.out.println("\n!!!입력 오류!!!\n");
				input = -1;
				sc.nextLine();
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
		} while (input != 0);
		
	}


	/** 학생 등록 메서드
	 * 
	 */
	private void insertStudent() throws Exception{
		
		System.out.println("\n====1. 학생 등록====\n");
		
		System.out.print("학생 이름 : ");
		String name = sc.next();
		
		System.out.print("주민등록번호 : ");
		String pNo = sc.next();
		
		System.out.print("전공 : ");
		String major = sc.next();
		
		System.out.print("재적 상태 : ");
		String status = sc.next();
		
		Student std = new Student();
		
		std.setName(name);
		std.setPNo(pNo);
		std.setMajor(major);
		std.setStatus(status);
		
		int result = service.insertStudent(std);
	}


	/** 전체 학생 조회
	 * 
	 */
	private void selectAll() {
		
	}


	/** 이름으로 학생 조회
	 * 
	 */
	private void selectName() {
		
		
	}


	/** 학번으로 학생 조회
	 * 
	 */
	private void selectStdNo() {
		
		
	}


	/** 전공별 학생 조회
	 * 
	 */
	private void selectSortByMajor() {
		
	}


	/** 학생 정보 수정
	 * 
	 */
	private void updateStudent() {
		
	}


	/** 학생 정보 삭제
	 * 
	 */
	private void deleteStudent() {
		
	}
}
