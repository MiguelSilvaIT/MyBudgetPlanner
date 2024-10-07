package com.miguel.mybudgetplanner.Account;

import com.miguel.mybudgetplanner.user.User;
import com.miguel.mybudgetplanner.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private final AccountRepository accountRepository;

    @Autowired
    private final UserRepository userRepository;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public Account createAccountForUser(Integer userId, Account account) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        account.setUser(user);
        return accountRepository.save(account);
    }

    // Recuperar todas as contas associadas a um utilizador
    public List<Account> getAccountsByUserId(Integer userId) {
        return accountRepository.findAllByUserId(userId);

    }

    public List<Account> getAccountsByAuthenticatedUser(Authentication authentication) {
        // Obter o usuário autenticado a partir do token JWT
        User loggedUser = (User) authentication.getPrincipal();

        // Buscar todas as contas associadas ao usuário autenticado
        return accountRepository.findAllByUserId(loggedUser.getId());
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

    public User findByUserId(Integer id) {
        return accountRepository.findByUserId(id);
    }

    public void deleteAccount(Integer id) {
        accountRepository.deleteById(id);
    }



    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
}
