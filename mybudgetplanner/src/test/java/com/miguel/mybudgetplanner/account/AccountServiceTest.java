package com.miguel.mybudgetplanner.account;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import com.miguel.mybudgetplanner.Account.Account;
import com.miguel.mybudgetplanner.Account.AccountRepository;
import com.miguel.mybudgetplanner.Account.AccountService;
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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateAccountSuccessfully() {
        // Given
        Account account = new Account();
        account.setAccountName("Savings Account");
        account.setBalance(BigDecimal.valueOf(1000));



        Account savedAccount = new Account();
        savedAccount.setId(1);
        savedAccount.setAccountName("Savings Account");
        savedAccount.setBalance(BigDecimal.valueOf(1000));

        when(accountRepository.save(account)).thenReturn(savedAccount);

        // When
        Account result = accountService.createAccount(account);

        // Then
        assertNotNull(result);
        assertEquals("Savings Account", result.getAccountName());
        assertEquals(BigDecimal.valueOf(1000), result.getBalance());
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void shouldReturnAccountWhenIdExists() {
        // Given
        Integer accountId = 1;
        Account account = new Account( "Main Account", BigDecimal.valueOf(2000));

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        // When
        Account result = accountService.getAccountById(accountId);

        // Then
        assertNotNull(result);
        assertEquals("Main Account", result.getAccountName());
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
        Account existingAccount = new Account(accountId, "Old Account", BigDecimal.valueOf(500), null);
        Account updatedAccount = new Account(accountId, "Updated Account", BigDecimal.valueOf(1500), null);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(existingAccount));
        when(accountRepository.save(existingAccount)).thenReturn(updatedAccount);

        // When
        Account result = accountService.updateAccount(existingAccount, accountId);

        // Then
        assertNotNull(result);
        assertEquals(updatedAccount.getAccountName(), result.getAccountName());
        assertEquals(BigDecimal.valueOf(1500), result.getBalance());
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
    void shouldReturnAllAccounts() {
        // Given
        List<Account> accounts = Arrays.asList(
                new Account(1, "Savings Account", BigDecimal.valueOf(1000), null),
                new Account(2, "Investment Account", BigDecimal.valueOf(2000), null)
        );

        when(accountRepository.findAll()).thenReturn(accounts);

        // When
        List<Account> result = accountService.getAllAccounts();

        // Then
        assertNotNull(result);
        assertEquals(accounts.size(), result.size());
        verify(accountRepository, times(1)).findAll();
    }
}
