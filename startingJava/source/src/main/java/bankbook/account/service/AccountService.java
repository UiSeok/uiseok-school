package main.java.bankbook.account.service;

import java.util.List;
import main.java.bankbook.account.model.Account;
import main.java.bankbook.account.repository.AccountRepository;
import main.java.bankbook.common.util.ScannerService;
import main.java.bankbook.member.model.Member;
import main.java.bankbook.member.repository.MemberRepository;

public class AccountService {

    private static final int MAX_ACCOUNT_PER_MEMBER = 3;
    private static final int AUTO_INCREAMENT_SIZE = 4;

    private AccountRepository accountRepository;
    private MemberRepository memberRepository;

    public AccountService() {
        accountRepository = new AccountRepository();
        memberRepository = new MemberRepository();
    }

    /**
     * 신규계좌 개설
     *
     * @param member
     * @return
     */
    public Account createNewAccount(Member member) {

        List<Account> accountList = accountRepository.selectAccount(member.getId());

        /** 최대 개설가능 계좌 갯수 초과 시 계좌개설 제한  **/
        if (accountList.size() > MAX_ACCOUNT_PER_MEMBER) {
            return new Account();
        }

        long lastAccountNo = accountRepository.selectLastAccountNo();

        Account newAccount = new Account(lastAccountNo + AUTO_INCREAMENT_SIZE, member.getId());

        return accountRepository.insertAccount(newAccount);
    }

    /**
     * 회원 전체계좌 조회
     *
     * @param member
     * @return
     */
    public List<Account> getAccountList(Member member) {
        return accountRepository.selectAccount(member.getId());
    }

    /**
     * 계좌정보 조회 (단건)
     *
     * @param accountNo
     * @return
     */
    public Account getAccount(long accountNo) {
        return accountRepository.selectAccount(accountNo);
    }

    /**
     * 계좌정보 삭제
     * 제약1. 비밀번호 재확인
     * 제약2. 잔고가 0이 아닌경우 삭제 불가
     *
     * @param member
     * @param accountNo
     * @return
     */
    public Account removeAccount(Member member, long accountNo) {

        Account account = accountRepository.selectAccount(accountNo);

        if (account.getBalance() > 0) {
            System.out.println("잔고가 존재합니다. 전체 금액을 출금한 후 계좌 삭제를 진행해 주세요.");
            return new Account();
        }

        System.out.println("계좌정보 삭제를 위해 비밀번호를 다시 입력해 주세요");
        String password = ScannerService.getScanner().nextLine();

        Member provedMember = memberRepository.selectMember(member.getId(), password);

        /** 비밀번호 재확인 실패 시 삭제 불가 **/
        if (!provedMember.existMember()) {
            System.out.println("비밀번호 입력정보가 잘못되었습니다. 다시 시도해 주세요.");
            return new Account();
        }

        return accountRepository.deleteAccount(account);

    }
}
