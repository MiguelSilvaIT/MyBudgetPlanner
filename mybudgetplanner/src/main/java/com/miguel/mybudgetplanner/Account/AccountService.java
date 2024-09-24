package com.miguel.mybudgetplanner.Account;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepositorie accountRepositorie;

    public AccountService(AccountRepositorie accountRepositorie) {
        this.accountRepositorie = accountRepositorie;
    }

    public Account createAccount(Account account) {
        return accountRepositorie.save(account);
    }

    public Account getAccountById(Integer id) {
        return accountRepositorie.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public Account updateAccount(Account account, Integer id) {
        Account accountToUpdate = accountRepositorie.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
        accountToUpdate.setAccountName(account.getAccountName());
        accountToUpdate.setBalance(account.getBalance());
        return accountRepositorie.save(accountToUpdate);
    }

    public void deleteAccount(Integer id) {
        accountRepositorie.deleteById(id);
    }



    public List<Account> getAllAccounts() {
        return accountRepositorie.findAll();
    }
}
