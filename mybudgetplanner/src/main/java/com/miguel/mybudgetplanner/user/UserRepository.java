package com.miguel.mybudgetplanner.user;

import com.miguel.mybudgetplanner.Account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    List<Account> findAllById(Integer userId);

    @Query("SELECT SUM(a.balance) FROM Account a WHERE a.user.id = :userId")
    BigDecimal getTotalBalanceById(@Param("userId") Integer userId);

}
