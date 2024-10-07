package com.miguel.mybudgetplanner.config;

import com.miguel.mybudgetplanner.Account.Account;
import com.miguel.mybudgetplanner.Account.AccountRepository;
import com.miguel.mybudgetplanner.Transaction.Transaction;
import com.miguel.mybudgetplanner.Transaction.TransactionRepository;
import com.miguel.mybudgetplanner.Transaction.enums.Frequency;
import com.miguel.mybudgetplanner.Transaction.enums.TransactionType;
import com.miguel.mybudgetplanner.user.Role;
import com.miguel.mybudgetplanner.user.User;
import com.miguel.mybudgetplanner.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Configuration
public class DataInitializer {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final PasswordEncoder passwordEncoder;

    // Injeção via construtor para garantir que todas as dependências são injetadas corretamente
    public DataInitializer(
            UserRepository userRepository,
            AccountRepository accountRepository,
            TransactionRepository transactionRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            // Verificar se já existem dados no banco para evitar duplicação ao reiniciar a aplicação
            if (userRepository.count() == 0) {
                User user = createUser("John", "Doe", "johndoe@example.com", "password123");
                Account savingsAccount = createAccount("Savings Account", BigDecimal.valueOf(5000), user);
                Account investmentAccount = createAccount("Investment Account", BigDecimal.valueOf(10000), user);

                // Criar transações associadas à conta de poupança
                createTransaction("Grocery shopping", BigDecimal.valueOf(150), TransactionType.EXPENSE, Frequency.ONE_TIME, savingsAccount);
                createTransaction("Salary for September", BigDecimal.valueOf(3000), TransactionType.INCOME, Frequency.MONTHLY, savingsAccount);

                System.out.println("Sample data inserted successfully.");
            } else {
                System.out.println("Data already exists. Skipping initialization.");
            }
        };
    }

    // Método responsável pela criação de usuários
    private User createUser(String firstname, String lastname, String email, String password) {
        User user = new User();
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setEmail(email);
        // Codificação da senha para segurança
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.USER);
        return userRepository.save(user);
    }

    // Método responsável pela criação de contas
    private Account createAccount(String accountName, BigDecimal balance, User user) {
        Account account = new Account();
        account.setAccountName(accountName);
        account.setBalance(balance);
        account.setUser(user);
        return accountRepository.save(account);
    }

    // Método responsável pela criação de transações
    private Transaction createTransaction(String description, BigDecimal value, TransactionType type, Frequency frequency, Account account) {
        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setValue(value);
        transaction.setCreatedDate(LocalDate.now());
        transaction.setType(type);
        transaction.setFrequency(frequency);
        transaction.setAccount(account);
        return transactionRepository.save(transaction);
    }
}
