package com.miguel.mybudgetplanner.user;

import com.miguel.mybudgetplanner.Account.UserDashboardDTO;
import com.miguel.mybudgetplanner.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Endpoint para obter o saldo total de um usuário

    @GetMapping("balance")
    public ResponseEntity<ApiResponse<BigDecimal>> getUserTotalBalance(Authentication auth) {
        BigDecimal totalBalance = userService.getAuthUserTotalBalance(auth);



        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "User total balance retrieved successfully",
                totalBalance,
                null,
                0,
                System.currentTimeMillis(),
                "/api/users/balance"
        ));
    }

    // get data from user
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserDashboardDTO>> getUserDashboardData(Authentication authentication) {
        UserDashboardDTO userDashboardDTO = userService.getUserDashboardData(authentication);

        ApiResponse<UserDashboardDTO> response = new ApiResponse<>(
                true,
                "User retrieved successfully",
                userDashboardDTO,
                null,
                0,
                System.currentTimeMillis(),
                "/api/users/me"
        );

        return ResponseEntity.ok(response);
    }







}
