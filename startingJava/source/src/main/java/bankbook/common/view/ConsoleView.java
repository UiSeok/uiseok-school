package main.java.bankbook.common.view;

import java.util.List;
import main.java.bankbook.account.model.Account;
import main.java.bankbook.member.model.Member;

public class ConsoleView {

    /**
     * 통장관리메뉴 초기화면
     */
    public static void startLoginMenu() {
        System.out.println(getTitle("통장관리 시스템"));

        System.out.println("1. 로그인");
        System.out.println("2. 회원가입");
        System.out.println("종료하려면 -1을 입력해주세요");

        System.out.println();
        System.out.println();
    }

    public static void loginMenu() {
        System.out.println(getTitle("로그인"));

        System.out.println("아이디와 비밀번호를 한칸 띄워 구분해 입력해주세요.");

        System.out.println();
        System.out.println();
    }

    public static void registerMenu() {
        System.out.println(getTitle("회원가입"));

        System.out.println("아이디와 비밀번호와 이름를 한칸 띄워 구분해 입력해주세요.");

        System.out.println();
        System.out.println();
    }

    /**
     * 로그인 후 통장관리 메뉴
     */
    public static void bankBookStartMenu(String loginUserName) {
        System.out.println(getTitle(" 반갑습니다 " + loginUserName + "님 "));

        System.out.println("1. 통장 개설");
        System.out.println("2. 통장 조회");
        System.out.println("3. 입금");
        System.out.println("4. 출금");
        System.out.println("99. 통장 해지");
        System.out.println("종료하려면 -1을 입력해주세요");

        System.out.println();
        System.out.println();
    }

    /**
     * 통장계좌 조회 화면
     */
    public static void bankBookSearchResult(Member loginMember, List<Account> accountList) {
        System.out.println(getTitle(" " + loginMember.getName() + "의 통장계좌 목록입니다. "));
        System.out.println("계좌번호                잔액");
        for (Account account : accountList) {
            System.out
                .println(account.getAccountNo() + "                      " + account.getBalance());
        }
    }

    public static void bankBookSearchResult(List<Account> accountList) {
        System.out.println("계좌번호                잔액");
        for (Account account : accountList) {
            System.out.println(account.getAccountNo() + "                      " + account.getBalance());
        }
    }

    public static void createNewBankBookApprovalMenu() {
        System.out.println("신규 계좌를 개설하시겠습니까?");
        System.out.println("1. YES");
        System.out.println("2. NO");
    }

    public static void removeBankBookApprovalMenu() {
        System.out.println("정말로 계좌를 해지하시겠습니까?");
        System.out.println("1. YES");
        System.out.println("2. NO");
    }

    public static void showAccountManagementResult(Account account) {
        System.out.println("처리가 완료되었습니다.");
        System.out.println("계좌번호                잔액");
        System.out
            .println(account.getAccountNo() + "                      " + account.getBalance());
    }

    private static String getTitle(String title) {
        return "=============" + title + "=============";
    }

}
