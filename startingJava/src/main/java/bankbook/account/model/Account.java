package main.java.bankbook.account.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Account {

    private long accountNo;
    private String memberId;
    private long balance;
    private LocalDateTime createdAt;

    public Account(Account account) {
        this.accountNo = account.getAccountNo();
        this.memberId = account.getMemberId();
        this.balance = account.getBalance();
        this.createdAt = account.getCreatedAt();
    }


    public boolean existAccount() {
        if (accountNo > 0 && memberId != null && !memberId.equals("")) {
            return true;
        }
        return false;
    }

    public String convert2TextData() {
        return accountNo + " " + memberId + " " + balance + " " + createdAt.toString();
    }

    public Account() {

    }

    public Account(long accountNo, String memberId, long balance, LocalDateTime createdAt) {
        this.accountNo = accountNo;
        this.memberId = memberId;
        this.balance = balance;
        this.createdAt = createdAt;
    }

    public Account(long accountNo, String memberId) {
        this.accountNo = accountNo;
        this.memberId = memberId;
        this.balance = 0;
        this.createdAt = LocalDateTime.now();
    }

    public long getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(long accountNo) {
        this.accountNo = accountNo;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Account)) {
            return false;
        }
        Account account = (Account) o;
        return getAccountNo() == account.getAccountNo() &&
            getMemberId().equals(account.getMemberId()) &&
            Objects.equals(getCreatedAt(), account.getCreatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccountNo(), getMemberId(), getCreatedAt());
    }
}
