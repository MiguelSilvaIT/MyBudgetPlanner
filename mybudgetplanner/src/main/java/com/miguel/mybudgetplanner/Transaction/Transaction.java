package com.miguel.mybudgetplanner.Transaction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.miguel.mybudgetplanner.Account.Account;
import com.miguel.mybudgetplanner.Transaction.enums.Frequency;
import com.miguel.mybudgetplanner.Transaction.enums.TransactionType;
import com.miguel.mybudgetplanner.category.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor


public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Data de criação da transação
    @NotNull(message = "Created date is mandatory")
    private LocalDate createdDate;

    // Frequência da transação (ex: mensal, anual, etc.)
    @NotNull(message = "Frequency is mandatory")
    @Enumerated(EnumType.STRING)
    private Frequency frequency;

    @NotNull(message = "Transaction type is mandatory") // Garante que o tipo de transação é especificado
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @NotBlank(message = "Description is mandatory") // Garante que a descrição não é nula ou vazia
    private String description;

    @NotNull(message = "Value is mandatory") // Garante que o valor não é nulo
    @Positive(message = "Value must be greater than zero") // Assegura que o valor é positivo
    private BigDecimal value;
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    @NotNull(message = "Account is mandatory")
    @JsonBackReference
    private Account account;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


}

