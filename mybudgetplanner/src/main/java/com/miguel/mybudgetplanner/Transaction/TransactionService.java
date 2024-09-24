package com.miguel.mybudgetplanner.Transaction;

import com.miguel.mybudgetplanner.Account.Account;
import com.miguel.mybudgetplanner.Account.AccountRepository;
import com.miguel.mybudgetplanner.Transaction.enums.TransactionType;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    private final AccountRepository accountRepository;

    public TransactionService(TransactionRepository transactionRepository , AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    /*public List<Transaction> findByType(TransactionType type) {
        return transactionRepository.findByType(type);
    }*/

    public Transaction findById(Integer id) {
        return transactionRepository.findById(id).orElse(null);
    }

    public Transaction create(Transaction transaction) {
        Account account = accountRepository.findById(transaction.getAccount().getId()).orElse(null);

        if (transaction.getType() == TransactionType.INCOME) {
            account.setBalance(account.getBalance().add(transaction.getValue()));
        } else if (transaction.getType() == TransactionType.EXPENSE) {
            account.setBalance(account.getBalance().subtract(transaction.getValue()));
        }

        // Salvamos a conta atualizada
        accountRepository.save(account);

        // Salvamos a transação
        transaction.setCreatedDate(LocalDate.now()); // Definimos a data de criação
        return transactionRepository.save(transaction);
    }

    /*public Transaction update(Integer id, Transaction transaction) {
        Transaction transactionToUpdate = transactionRepository.findById(id).orElse(null);
        if (transactionToUpdate == null) {
            return null;
        }
        transactionToUpdate.setFrequency(transaction.getFrequency());
        transactionToUpdate.setType(transaction.getType());
        transactionToUpdate.setDescription(transaction.getDescription());
        transactionToUpdate.setValue(transaction.getValue());
        transactionToUpdate.setAccount(transaction.getAccount());

        //balance update
        Account account = accountRepository.findById(transaction.getAccount().getId()).orElse(null);
        if (transactionToUpdate.getType() == TransactionType.INCOME) {
            account.setBalance(account.getBalance().subtract(transactionToUpdate.getValue()));
        } else if (transactionToUpdate.getType() == TransactionType.EXPENSE) {
            account.setBalance(account.getBalance().add(transactionToUpdate.getValue()));
        }

        return transactionRepository.save(transactionToUpdate);
    }*/

    public void delete(Integer id) {

        Transaction transaction = transactionRepository.findById(id).orElse(null);
        Account account = accountRepository.findById(transaction.getAccount().getId()).orElse(null);

        if (transaction.getType() == TransactionType.INCOME) {
            account.setBalance(account.getBalance().subtract(transaction.getValue()));
        } else if (transaction.getType() == TransactionType.EXPENSE) {
            account.setBalance(account.getBalance().add(transaction.getValue()));
        }

        accountRepository.save(account);
        transactionRepository.deleteById(id);
    }


}
