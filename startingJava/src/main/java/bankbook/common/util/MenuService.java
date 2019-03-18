package main.java.bankbook.common.util;

import java.util.List;
import main.java.bankbook.account.model.Account;
import main.java.bankbook.account.service.AccountManagementService;
import main.java.bankbook.account.service.AccountService;
import main.java.bankbook.member.model.Member;
import main.java.bankbook.member.service.MemberService;
import main.java.bankbook.common.view.ConsoleView;

public class MenuService {

    private static final String SEPARATOR = " ";

    private MemberService memberService = new MemberService();
    private AccountService accountService = new AccountService();
    private AccountManagementService accountManagementService = new AccountManagementService();

    /**
     * step1. 시작메뉴/로그인
     *
     * @return
     */
    public Member startLoginMenu() {

        int selectedMenu;

        Member loginMember = new Member();

        do {
            // 시작메뉴
            ConsoleView.startLoginMenu();

            selectedMenu = ScannerService.getScanner().nextInt();

            if (selectedMenu == -1) { // 종료
                System.out.println("bye bye");
                return new Member();
            }

            if (selectedMenu == 1) { // 로그인

                ConsoleView.loginMenu();

                String loginInfo = ScannerService.getScanner().nextLine();

                String[] loginInfoArray = loginInfo.split(SEPARATOR);

                if (loginInfoArray.length < 2) {
                    System.out.println("잘못된 입력값입니다. 다시 시도해 주세요.");
                    continue;
                }

                loginMember = memberService.getMember(loginInfoArray[0], loginInfoArray[1]);

                if (!loginMember.existMember()) {
                    System.out.println("정보가 정상적으로 조회되지 않았습니다. 다시 시도해 주세요\n\n");
                    continue;
                }
            }

            if (selectedMenu == 2) { // 회원가입

                ConsoleView.registerMenu();

                String registerInfo = ScannerService.getScanner().nextLine();

                String[] registerInfoArray = registerInfo.split(SEPARATOR);

                if (registerInfoArray.length < 3) {
                    System.out.println("잘못된 입력값입니다. 처음부터 다시 진행해 주세요.");
                    continue;
                }

                String id = registerInfoArray[0];
                String password = registerInfoArray[1];
                String name = registerInfoArray[2];

                if (memberService.existId(id)) {
                    System.out.println("이미 존재하는 아이디입니다. 처음부터 다시 진행해 주세요.");
                    continue;
                }

                loginMember = memberService.doRegister(id, password, name);
            }

        } while (selectedMenu > 0 && !loginMember.existMember());

        return loginMember;
    }

    public void startBankBookManagementMenu(Member loginMember) {

        int selectedMenu;

        do {
            ConsoleView.bankBookStartMenu(loginMember.getName());

            selectedMenu = ScannerService.getScanner().nextInt();

            if (selectedMenu == 1) { // 통장 개설
               createNewBankBookMenu(loginMember);
            }

            if (selectedMenu == 2) { // 통장 조회
                bankBookSearchMenu(loginMember);
            }

            if (selectedMenu == 3) { // 입금
                depositMenu(loginMember);
            }

            if (selectedMenu == 4) { // 출금
                withdrawMenu(loginMember);
            }

            if (selectedMenu == 99) { // 통장 해지
                removeBankBookMenu(loginMember);
            }

        } while(selectedMenu > 0);

    }

    public void createNewBankBookMenu(Member loginMember) {
        ConsoleView.createNewBankBookApprovalMenu();
        int yesOrNo = ScannerService.getScanner().nextInt();

        if (yesOrNo == 1) {
            accountService.createNewAccount(loginMember);
        }
    }

    public void bankBookSearchMenu(Member loginMember) {
        List<Account> accountList = accountService.getAccountList(loginMember);
        ConsoleView.bankBookSearchResult(loginMember, accountList);
    }

    public void depositMenu(Member loginMember) {
        List<Account> accountList = accountService.getAccountList(loginMember);
        ConsoleView.bankBookSearchResult(accountList);

        System.out.println("입금하고자하는 계좌번호를 입력해주세요");
        long accountNo = ScannerService.getScanner().nextLong();
        System.out.println("입금할 금액을 입력하세요");
        long depositAmount = ScannerService.getScanner().nextLong();

        Account depositResult = accountManagementService.deposit(accountService.getAccount(accountNo), depositAmount);
        ConsoleView.showAccountManagementResult(depositResult);
    }

    public void withdrawMenu(Member loginMember) {
        List<Account> accountList = accountService.getAccountList(loginMember);
        ConsoleView.bankBookSearchResult(accountList);

        System.out.println("출금하고자하는 계좌번호를 입력해주세요");
        long accountNo = ScannerService.getScanner().nextLong();
        System.out.println("출금할 금액을 입력하세요");
        long withdrawAmount = ScannerService.getScanner().nextLong();

        Account depositResult = accountManagementService.withdraw(accountService.getAccount(accountNo), withdrawAmount);
        ConsoleView.showAccountManagementResult(depositResult);
    }

    public void removeBankBookMenu(Member loginMember) {
        List<Account> accountList = accountService.getAccountList(loginMember);
        ConsoleView.bankBookSearchResult(accountList);

        System.out.println("해지하고자하는 계좌번호를 입력해주세요");
        long accountNo = ScannerService.getScanner().nextLong();

        ConsoleView.removeBankBookApprovalMenu();
        int yesOrNo = ScannerService.getScanner().nextInt();

        if (yesOrNo == 1) {
            accountService.removeAccount(loginMember, accountNo);
        }
    }


}
