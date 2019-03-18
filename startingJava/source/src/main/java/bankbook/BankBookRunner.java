package main.java.bankbook;

import main.java.bankbook.common.util.MenuService;
import main.java.bankbook.member.model.Member;

public class BankBookRunner {

    public static void run() {

        MenuService menuService = new MenuService();

        /** step1. 로그인 메뉴 시작 **/
        Member loginMember = menuService.startLoginMenu();

        /** step2. 로그인 성공시 계좌관리시스템 시작 **/
        if (loginMember.existMember()) {
            menuService.startBankBookManagementMenu(loginMember);
        }

        System.out.println("프로그램을 종료합니다.");
    }


}
