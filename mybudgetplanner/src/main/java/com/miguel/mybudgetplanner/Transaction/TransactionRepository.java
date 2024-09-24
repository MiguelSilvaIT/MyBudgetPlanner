package com.miguel.mybudgetplanner.Transaction;

import com.miguel.mybudgetplanner.Transaction.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TransactionRepository extends JpaRepository<Transaction, Integer> {




}
