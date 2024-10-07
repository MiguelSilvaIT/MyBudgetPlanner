package com.miguel.mybudgetplanner.Account;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.miguel.mybudgetplanner.Transaction.Transaction;
import com.miguel.mybudgetplanner.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data



public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Account name is mandatory")
    private String accountName;

    @NotNull(message = "Balance is mandatory")
    @PositiveOrZero(message = "Balance must be positive")
    private BigDecimal balance;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Transaction> transactions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference // Prevenir recursividade na serialização JSON
    private User user;

    public Account() {
    }

    public Account(String accountName, BigDecimal balance) {
        this.accountName = accountName;
        this.balance = balance;
    }

    public Account(Integer id, String accountName, BigDecimal balance, List<Transaction> transactions, User user) {
        this.id = id;
        this.accountName = accountName;
        this.balance = balance;
        this.transactions = transactions;
        this.user = user;
    }
}

