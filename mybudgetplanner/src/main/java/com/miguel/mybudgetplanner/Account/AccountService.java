package com.miguel.mybudgetplanner.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account getAccountById(Integer id) {
        return accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public Account updateAccount(Account account, Integer id) {
        Account accountToUpdate = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
        accountToUpdate.setAccountName(account.getAccountName());
        accountToUpdate.setBalance(account.getBalance());
        return accountRepository.save(accountToUpdate);
    }

    public void deleteAccount(Integer id) {
        accountRepository.deleteById(id);
    }



    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
}
