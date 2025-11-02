package edu.kh.jdbc.homework.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.homework.model.dto.Student;
import edu.kh.jdbc.homework.model.service.StudentService;


public class StudentView {
	
	private StudentService service = new StudentService();
	private Scanner sc = new Scanner(System.in);

	
	public void displayMenu() {
		
		int input = 0; 
		
		do {
			
			try {
				
				System.out.println("\n====학생 정보 관리 프로그램====\n");
				System.out.println("1. 학생 등록");
				System.out.println("2. 전체 학생 조회");
				System.out.println("3. 이름으로 학생 조회");
				System.out.println("4. 학번으로 학생 조회");
				System.out.println("5. 전공별 학생 조회");
				System.out.println("6. 학생 정보 수정");
				System.out.println("7. 학생 정보 삭제");
				System.out.println("0. 프로그램 종료");
				
				System.out.print("\n메뉴 선택 : ");
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
				
				System.out.println("\n----------------------------------------------------------------\n");
				
			} catch (InputMismatchException e) {
				
				System.out.println("\n!!!입력 오류!!!\n");
				input = -1;
				sc.nextLine();
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
		} while (input != 0);
		
	}


	/** 학생 등록
	 * 
	 */
	private void insertStudent() throws Exception{
		
		System.out.println("\n====1. 학생 등록====\n");
		
		System.out.print("학생 이름 : ");
		String name = sc.next();
		
		System.out.print("주민등록번호(- 포함) : ");
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
		
		if(result > 0) {
			
			System.out.println("\n" + name + " 학생이 등록되었습니다.\n");
			
		} else {
			
			System.out.println("\n!!!등록 실패!!!\n");
		}
		
	}


	/** 전체 학생 조회
	 * 
	 */
	private void selectAll() throws Exception{
		
		System.out.println("\n====2. 전체 학생 조회====\n");
		
		List<Student> stdList = service.selectAll();
		
		if(stdList.isEmpty()) {
			
			System.out.println("\n***조회 결과가 없습니다***\n");
			return;
		}
		
		for (Student std : stdList) {
			
			System.out.println(std);
		}
			
		
	}


	/** 입력 받은 검색어를 포함하는 이름의 학생 조회
	 */
	private void selectName() throws Exception{
		
		System.out.println("\n====3. 이름으로 학생 조회====\n");
		
		System.out.print("검색어 입력 : ");
		String input = sc.next();
		
		List<Student> stdList = service.selectName(input);
		
		if(stdList.isEmpty()) {
			
			System.out.println("\n***조회 결과가 없습니다***\n");
			return;
		}
		
		for (Student std : stdList) {
			
			System.out.println(std);
		}

	}


	/** 입력 받은 학번으로 학생 조회
	 * 
	 */
	private void selectStdNo() throws Exception{
		
		System.out.println("\n====4. 학번으로 학생 조회====\n");
		
		System.out.print("검색할 학번 입력 : ");
		int input = sc.nextInt();
		
		Student std = service.selectStdNo(input);
		
		if(std != null) {
			
			System.out.println(std);
			
		} else {
			
			System.out.println("\n입력하신 학번과 일치하는 학생이 없습니다\n");
			
		}
	}


	/** 입력 받은 전공별 학생 조회
	 * 
	 */
	private void selectSortByMajor() throws Exception{
		
		System.out.println("\n====5. 전공별 학생 조회====\n");
		
		System.out.println("검색할 전공 입력 : ");
		String input = sc.next();
		
		List<Student> stdList = service.selectSortByMajor(input);
		
		if(stdList.isEmpty()) {
			
			System.out.println("\n***조회 결과가 없습니다***\n");
			return;
		}
		
		for(Student std : stdList) {
			
			System.out.println(std);
		}
		
	}


	/** 학생 정보 수정
	 * 입력 받은 이름과 주민번호가 일치하는 학생이 있으면 
	 * 해당 학생의 이름, 전공, 재적상태 수정
	 */
	private void updateStudent() throws Exception{
		
		System.out.println("\n====6. 학생 정보 수정====\n");
		
		System.out.print("이름 입력 : ");
		String inputName = sc.next();
		System.out.print("주민등록번호 입력(- 포함) : ");
		String inputPno = sc.next();
		
		int stdNo = service.selectStdNo(inputName, inputPno);
		
		if(stdNo == 0) {
			
			System.out.println("\n!!!해당하는 학생이 없습니다!!!\n");
			return;
		}
		
		System.out.println("\n====학생 정보 수정 메뉴====\n");
		System.out.println("1. 이름 수정");
		System.out.println("2. 전공 수정");
		System.out.println("3. 재적상태 수정");
		System.out.print("\n수정할 정보 선택 : ");
		int input = sc.nextInt();
		
		int result = 0;
		
		switch(input) {
		
		case 1 : System.out.print("수정할 이름 입력 : ");
				 String reName = sc.next();
				 result = service.updateName(stdNo, reName); break;
		
		case 2 : System.out.print("수정할 전공 입력 : ");
				 String inputMajor = sc.next();
				 result = service.updateMajor(stdNo, inputMajor); break;
				
		case 3 : System.out.print("수정할 재적상태 입력 : ");
				 String inputStatus = sc.next();
				 result = service.updateStatus(stdNo, inputStatus); break;
				 
		default : System.out.println("\n!!!잘못된 입력입니다!!!\n");		 
		
		}
		
		if(result > 0) System.out.println("\n수정 완료!!\n");
		else		   System.out.println("\n수정 실패..\n");
		
	}


	/** 학생 정보 삭제
	 * 입력 받은 이름과 주민번호가 일치하는 학생이 있으면 삭제
	 */
	private void deleteStudent() throws Exception{
		
		System.out.println("\n====7. 학생 정보 삭제====\n");
		
		System.out.print("이름 입력 : ");
		String inputName = sc.next();
		System.out.print("주민등록번호 입력(- 포함) : ");
		String inputPno = sc.next();
		
		int result = service.deleteStudent(inputName, inputPno);
		
		if(result > 0) System.out.println("\n삭제 완료!!\n");
		else 		   System.out.println("\n해당하는 학생이 존재하지 않습니다!\n");
	}
}
