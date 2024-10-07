package com.miguel.mybudgetplanner.account;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.miguel.mybudgetplanner.Account.Account;
import com.miguel.mybudgetplanner.Account.AccountRepository;
import com.miguel.mybudgetplanner.Account.AccountService;
import com.miguel.mybudgetplanner.user.User;
import com.miguel.mybudgetplanner.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User(); // Criar um utilizador de exemplo para associar as contas
        user.setId(1);
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setEmail("john.doe@example.com");
    }

    @Test
    void shouldCreateAccountSuccessfully() {
        // Given
        Account account = new Account();
        account.setAccountName("Savings Account");
        account.setBalance(BigDecimal.valueOf(1000));
        account.setUser(user); // Associar a conta ao utilizador

        Account savedAccount = new Account();
        savedAccount.setId(1);
        savedAccount.setAccountName("Savings Account");
        savedAccount.setBalance(BigDecimal.valueOf(1000));
        savedAccount.setUser(user);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        when(accountRepository.save(account)).thenReturn(savedAccount);

        // When
        Account result = accountService.createAccountForUser(user.getId(), account);

        // Then
        assertNotNull(result);
        assertEquals("Savings Account", result.getAccountName());
        assertEquals(BigDecimal.valueOf(1000), result.getBalance());
        assertEquals(user, result.getUser());
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void shouldReturnAccountWhenIdExists() {
        // Given
        Integer accountId = 1;
        Account account = new Account();
        account.setId(accountId);
        account.setAccountName("Main Account");
        account.setBalance(BigDecimal.valueOf(2000));
        account.setUser(user);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        // When
        Account result = accountService.getAccountById(accountId);

        // Then
        assertNotNull(result);
        assertEquals("Main Account", result.getAccountName());
        assertEquals(user, result.getUser());
        verify(accountRepository, times(1)).findById(accountId);
    }

    @Test
    void shouldThrowExceptionWhenAccountNotFound() {
        // Given
        Integer accountId = 999;

        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> accountService.getAccountById(accountId));
        verify(accountRepository, times(1)).findById(accountId);
    }

    @Test
    void shouldUpdateAccountSuccessfully() {
        // Given
        Integer accountId = 1;
        Account existingAccount = new Account(accountId, "Old Account", BigDecimal.valueOf(500), null,user);
        Account updatedAccount = new Account(accountId, "Updated Account", BigDecimal.valueOf(1500), null ,user);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(existingAccount));
        when(accountRepository.save(existingAccount)).thenReturn(updatedAccount);

        // When
        Account result = accountService.updateAccount(existingAccount, accountId);

        // Then
        assertNotNull(result);
        assertEquals("Updated Account", result.getAccountName());
        assertEquals(BigDecimal.valueOf(1500), result.getBalance());
        assertEquals(user, result.getUser());
        verify(accountRepository, times(1)).findById(accountId);
        verify(accountRepository, times(1)).save(existingAccount);
    }

    @Test
    void shouldDeleteAccountSuccessfully() {
        // Given
        Integer accountId = 1;

        // Mock repository behavior
        doNothing().when(accountRepository).deleteById(accountId);

        // When
        accountService.deleteAccount(accountId);

        // Then
        verify(accountRepository, times(1)).deleteById(accountId);
    }

    @Test
    void shouldReturnAllAccountsForUser() {
        // Given
        List<Account> accounts = Arrays.asList(
                new Account(1, "Savings Account", BigDecimal.valueOf(1000), null ,user),
                new Account(2, "Investment Account", BigDecimal.valueOf(2000), null ,user)
        );

        when(accountRepository.findAllByUserId(user.getId())).thenReturn(accounts);

        // When
        List<Account> result = accountService.getAccountsByUserId(user.getId());

        // Then
        assertNotNull(result);
        assertEquals(accounts.size(), result.size());
        assertEquals(user, result.get(0).getUser());
        verify(accountRepository, times(1)).findAllByUserId(user.getId());
    }
}
