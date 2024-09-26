package com.miguel.mybudgetplanner.transaction;

import com.miguel.mybudgetplanner.Account.Account;
import com.miguel.mybudgetplanner.Account.AccountRepository;
import com.miguel.mybudgetplanner.Transaction.Transaction;
import com.miguel.mybudgetplanner.Transaction.TransactionRepository;
import com.miguel.mybudgetplanner.Transaction.TransactionService;
import com.miguel.mybudgetplanner.Transaction.enums.Frequency;
import com.miguel.mybudgetplanner.Transaction.enums.TransactionType;
import com.miguel.mybudgetplanner.category.Category;
import com.miguel.mybudgetplanner.category.CategoryRepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TransactionServiceTest {
    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void should_successfully_save_a_transaction() {

        Account existingAccount = new Account(1, "Main Account", BigDecimal.valueOf(1000), null);
        // Given
        Transaction transaction = new Transaction();
        transaction.setCreatedDate(LocalDate.now());
        transaction.setFrequency(Frequency.MONTHLY);
        transaction.setType(TransactionType.EXPENSE);
        transaction.setDescription("Groceries");
        transaction.setValue(BigDecimal.valueOf(150.00));
        transaction.setCategory(new Category(1, "Food"));
        transaction.setAccount(existingAccount);


        Transaction savedTransaction = new Transaction();
        savedTransaction.setId(1);
        savedTransaction.setCreatedDate(LocalDate.now());
        savedTransaction.setFrequency(Frequency.MONTHLY);
        savedTransaction.setType(TransactionType.EXPENSE);
        savedTransaction.setDescription("Groceries");
        savedTransaction.setValue(BigDecimal.valueOf(150.00));
        savedTransaction.setCategory(new Category(1, "Food"));
        savedTransaction.setAccount(existingAccount);

        // Mock
        when(accountRepository.findById(existingAccount.getId())).thenReturn(Optional.of(existingAccount)); // Adiciona este mock
        when(transactionRepository.save(transaction)).thenReturn(savedTransaction);

        // When
        Transaction result = transactionService.create(transaction);

        // Then
        assertEquals("Groceries", result.getDescription());
        assertEquals(BigDecimal.valueOf(150.00), result.getValue());
        assertEquals("Food", result.getCategory().getName());

        verify(transactionRepository, times(1)).save(transaction);
    }

    @Test
    public void should_successfully_find_all_transactions() {
        // Given
        List<Transaction> transactions = List.of(
                new Transaction(1, LocalDate.now(), Frequency.MONTHLY, TransactionType.EXPENSE, "Groceries", BigDecimal.valueOf(150.00),  new Account("Main Account", BigDecimal.valueOf(1000)), new Category(1, "Food")),
                new Transaction(2, LocalDate.now(), Frequency.MONTHLY, TransactionType.INCOME, "Salary", BigDecimal.valueOf(2000.00), new Account("Main Account", BigDecimal.valueOf(3000)), new Category(2, "Salary"))
        );

        // Mock
        when(transactionRepository.findAll()).thenReturn(transactions);

        // When
        List<Transaction> result = transactionService.findAll();

        // Then
        assertEquals(transactions.size(), result.size());
        verify(transactionRepository, times(1)).findAll();
    }

    @Test
    public void should_find_transaction_by_id() {
        // Given
        Integer transactionId = 1;
        Transaction transaction = new Transaction(
                transactionId,
                LocalDate.now(),
                Frequency.MONTHLY,
                TransactionType.EXPENSE,
                "Groceries",
                BigDecimal.valueOf(150.00),
                new Account("Main Account", BigDecimal.valueOf(1000)),
                new Category(1, "Food"));


        // Mock
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transaction));

        // When
        Transaction result = transactionService.findById(transactionId);

        // Then
        assertEquals(transactionId, result.getId());
        assertEquals(transaction.getDescription(), result.getDescription());
        assertEquals(transaction.getValue(), result.getValue());
        assertEquals(transaction.getCategory().getName(), result.getCategory().getName());
        assertEquals(transaction.getAccount().getAccountName(), result.getAccount().getAccountName());
        assertEquals(transaction.getFrequency() , result.getFrequency());
        assertEquals(transaction.getType(), result.getType());


        verify(transactionRepository, times(1)).findById(transactionId);
    }

    @Test
    public void should_throw_exception_when_transaction_not_found() {
        // Given
        Integer transactionId = 999;

        // Mock
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> transactionService.findById(transactionId));
        verify(transactionRepository, times(1)).findById(transactionId);
    }

    @Test
    public void should_delete_transaction() {

        Account existingAccount = new Account(1, "Main Account", BigDecimal.valueOf(1000), null);


        // Given
        Transaction transaction = new Transaction();
        transaction.setCreatedDate(LocalDate.now());
        transaction.setFrequency(Frequency.MONTHLY);
        transaction.setType(TransactionType.EXPENSE);
        transaction.setDescription("Groceries");
        transaction.setValue(BigDecimal.valueOf(150.00));
        transaction.setCategory(new Category(1, "Food"));
        transaction.setAccount(existingAccount);
        transaction.setId(1);


        // Mock
        when(transactionRepository.findById(transaction.getId())).thenReturn(Optional.of(transaction));
        when(accountRepository.findById(existingAccount.getId())).thenReturn(Optional.of(existingAccount));
        doNothing().when(transactionRepository).deleteById(transaction.getId());

        // When
        transactionService.delete(transaction.getId());

        // Then
        verify(transactionRepository, times(1)).deleteById(transaction.getId());
    }


}

