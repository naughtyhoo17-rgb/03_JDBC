package edu.kh.jdbc.view;

import java.util.InputMismatchException;
import java.util.Scanner;

import edu.kh.jdbc.model.service.UserService;

/* View : 사용자와 직접 상호작용하는 화면(UI)을 담당
  => 사용자로부터 입력을 받고 그 결과를 사용자에게 출력하는 역할 */

public class UserView {

	// 필드
	private UserService service = new UserService();
	private Scanner sc = new Scanner(System.in);
	
	/**
	 * User 관리 프로그램 메인 메뉴 UI (View)
	 */
	public void mainMenu() {
		
		int input = 0; // 메뉴 선택용 변수
		
		do {
			try {
				
				System.out.println("\n===== User 관리 프로그램 =====\n");
				System.out.println("1. User 등록(INSERT)");
				System.out.println("2. User 전체 조회(SELECT)");
				System.out.println("3. User 중 이름에 검색어가 포함된 회원 조회 (SELECT)");
				System.out.println("4. USER_NO를 입력 받아 일치하는 User 조회(SELECT)");
				System.out.println("5. USER_NO를 입력 받아 일치하는 User 삭제(DELETE)");
				System.out.println("6. ID, PW가 일치하는 회원이 있을 경우 이름 수정(UPDATE)");
				System.out.println("7. User 등록(아이디 중복 검사)");
				System.out.println("8. 여러 User 등록하기");
				System.out.println("0. 프로그램 종료");
				
				System.out.print("메뉴 선택 : ");
				input = sc.nextInt();
				sc.nextLine(); // 버퍼에 남은 개행문자 제거
				
				switch (input) {
				case 1: insertUser(); break;
				case 2: selectAll(); break;
				case 3: selectName(); break;
				case 4: selectUser(); break;
				case 5: deleteUser(); break;
				case 6: updateName(); break;
				case 7: insertUser2(); break;
				case 8: multiInsertUser(); break;
				case 0: System.out.println("\n[프로그램 종료]\n"); break;
				default: System.out.println("\n[메뉴 번호만 입력하세요]\n");
				}
				System.out.println("\n-------------------------------------\n");
				
			} catch (InputMismatchException e) {
				
				// 스캐너 이용한 입력 시 자료형이 잘못된 경우
				System.out.println("\n***잘못된 입력***\n");
				
				input = -1; // 초장에 잘못 입력해 while문이 멈추는 것 방지
				sc.nextLine(); // 입력버퍼에 남았을 잘못 입력된 문자 제거
				
			} catch (Exception e) {
				e.printStackTrace(); // 발생되는 예외를 모두 여기서 모아 처리				
			}
			
			
		} while(input != 0);
		
	}

	private void insertUser() {
		
	}

	private void selectAll() {
		
	}

	private void selectName() {
		
	}

	private void selectUser() {
		
	}

	private void deleteUser() {
		
	}

	private void updateName() {
		
	}

	private void insertUser2() {
		
	}

	private void multiInsertUser() {
		
	}
	
	


}
