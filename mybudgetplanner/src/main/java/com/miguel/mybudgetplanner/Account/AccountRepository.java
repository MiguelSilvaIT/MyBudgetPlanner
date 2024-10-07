package com.miguel.mybudgetplanner.Account;

import com.miguel.mybudgetplanner.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    List<Account> findAllByUserId(Integer userId);

    User findByUserId(Integer id);


}
