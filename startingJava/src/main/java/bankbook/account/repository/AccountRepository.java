package main.java.bankbook.account.repository;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import main.java.bankbook.account.model.Account;
import main.java.bankbook.common.util.ScannerService;

public class AccountRepository {

    private static final String ACCOUNT_FILE_PATH = "/Users/ashe/Desktop/dev/startingJava/src/main/resource/account.txt";
    private static final String SEPARATOR = " ";

    Scanner scanner;

    BufferedWriter bufferedWriter;

    public AccountRepository() {
    }

    public void openFileWriter(boolean appendMode) {
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(new File(ACCOUNT_FILE_PATH), appendMode));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Account> selectAccount(String memberId) {

        List<Account> selectedAccount = new ArrayList<>();

        List<Account> accountList = getAllAccounts();

        for (Account account : accountList) {
            if (account.getMemberId().equals(memberId)) {
                selectedAccount.add(account);
            }
        }

        return selectedAccount;
    }

    public Account selectAccount(long accountNo) {
        List<Account> accountList = getAllAccounts();

        for (Account account : accountList) {
            if (account.getAccountNo() == accountNo) {
                return account;
            }
        }

        return new Account();
    }

    public Account insertAccount(Account account) {

        openFileWriter(true);

        try {
            bufferedWriter.write(account.convert2TextData());
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return account;
    }

    /**
     * 계좌정보 업데이트(입/출금에 사용)
     */
    public Account updateAccount(Account account) {

        List<Account> accountList = getAllAccounts();
        List<Account> newAccountList = new ArrayList<>();

        for (Account acc : accountList) {
            // 해당하는 계좌정보를 새로들어온 정보로 변경
            if (acc.equals(account)) {
                newAccountList.add(account);
            } else { // 기존계좌정보는 유지
                newAccountList.add(acc);
            }
        }

        writeAccountInfo2FileNotAppend(newAccountList);

        return account;
    }

    /**
     * 계좌정보 삭제
     */
    public Account deleteAccount(Account account) {

        List<Account> accountList = getAllAccounts();
        List<Account> newAccountList = new ArrayList<>();

        for (Account acc : accountList) {
            if (!acc.equals(account)) {
                newAccountList.add(acc);
            }
        }

        writeAccountInfo2FileNotAppend(newAccountList);

        return account;
    }

    /**
     * 덮어쓰기 모드로 파일에 계좌정보 저장
     */
    private void writeAccountInfo2FileNotAppend(List<Account> newAccountList) {

        openFileWriter(false);

        String inputData = "";

        for (Account newAccount : newAccountList) {
            inputData += newAccount.convert2TextData();
            inputData += "\n";
        }

        try {
            bufferedWriter.write(inputData);
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 마지막 개설계좌 번호 추출
     */
    public long selectLastAccountNo() {
        List<Account> accountList = getAllAccounts();

        long maxAccountNo = 0;

        for (Account account : accountList) {
            if (account.getAccountNo() > maxAccountNo) {
                maxAccountNo = account.getAccountNo();
            }
        }

        return maxAccountNo;

//        return accountList.stream()
//            .mapToLong(Account::getAccountNo)
//            .max()
//            .getAsLong();
    }

    /**
     * TEXT파일에 있는 모든 계좌정보 읽어옴
     */
    public List<Account> getAllAccounts() {

        List<Account> accountList = new ArrayList<>();

        scanner = ScannerService.getFileScanner(ACCOUNT_FILE_PATH);

        while (scanner.hasNext()) {
            String readLine = scanner.nextLine();
            String[] accountInfoStringArray = readLine.split(SEPARATOR);

            if (accountInfoStringArray.length != 4) {
                // DB에 이상한 값 존재
                continue;
            }

            Account account = new Account(Long.parseLong(accountInfoStringArray[0]),
                accountInfoStringArray[1],
                Long.parseLong(accountInfoStringArray[2]),
                LocalDateTime.parse(accountInfoStringArray[3]));

            accountList.add(account);
        }

        return accountList;
    }
}
