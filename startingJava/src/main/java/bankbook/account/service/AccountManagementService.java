package main.java.bankbook.account.service;

import main.java.bankbook.account.model.Account;
import main.java.bankbook.account.repository.AccountRepository;

public class AccountManagementService {
    private AccountRepository accountRepository;

    public AccountManagementService() {
        accountRepository = new AccountRepository();
    }

    /**
     * 입금
     *
     * @param account
     * @param money
     * @return
     */
    public Account deposit(Account account, long money) {
        Account updatedAccount = new Account(account);
        updatedAccount.setBalance(account.getBalance() + money);

        return accountRepository.updateAccount(updatedAccount);
    }


    /**
     * 출금
     *
     * @param account
     * @param money
     * @return
     */
    public Account withdraw(Account account, long money) {
        Account updatedAccount = new Account(account);
        updatedAccount.setBalance(account.getBalance() - money);

        return accountRepository.updateAccount(updatedAccount);
    }
}
