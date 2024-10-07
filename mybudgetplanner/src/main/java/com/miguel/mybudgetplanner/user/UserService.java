package com.miguel.mybudgetplanner.user;

import com.miguel.mybudgetplanner.Account.UserDashboardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.miguel.mybudgetplanner.Account.Account;


import java.math.BigDecimal;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Novo método para obter os dados necessários do dashboard usando um record (DTO)
    public UserDashboardDTO getUserDashboardData(Authentication auth) {
        User user = getAuthUser(auth); // Reutiliza o método que já recupera o usuário autenticado

        // Calcula o saldo total somando o saldo de todas as contas associadas ao usuário
        BigDecimal totalBalance = user.getAccounts().stream()
                .map(Account::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Retorna um record UserDashboardDTO contendo o nome e saldo total
        return new UserDashboardDTO(user.getFirstname(), totalBalance);
    }

    public BigDecimal getUserTotalBalance(Integer userId) {
        // Verifica se o usuário existe
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Consulta o saldo total das contas associadas ao usuário
        return userRepository.getTotalBalanceById(userId);
    }

    public BigDecimal getAuthUserTotalBalance(Authentication auth) {

        // Recupera o ID do usuário autenticado
        Integer userId = ((User) auth.getPrincipal()).getId();

        return getUserTotalBalance(userId);
    }

    public User getAuthUser(Authentication auth) {
        // Recupera o ID do usuário autenticado
        Integer userId = ((User) auth.getPrincipal()).getId();

        // Consulta o usuário autenticado
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
