package com.miguel.mybudgetplanner.Account;

import com.miguel.mybudgetplanner.response.ApiResponse;
import com.miguel.mybudgetplanner.response.ResponseUtil;
import com.miguel.mybudgetplanner.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(Authentication authentication, @RequestBody Account account) {
        // Recuperar o ID do utilizador autenticado a partir do token
        Integer userId = ((User) authentication.getPrincipal()).getId();
        Account createdAccount = accountService.createAccountForUser(userId, account);
        return ResponseEntity.ok(createdAccount);
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<List<Account>>> getAccountsForAuthenticatedUser(Authentication authentication) {
        List<Account> accounts = accountService.getAccountsByAuthenticatedUser(authentication);

        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Accounts retrieved successfully",
                accounts,
                null,
                0,
                System.currentTimeMillis(),
                "/api/accounts/me"
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Account>> getAccountById(@PathVariable Integer id, HttpServletRequest request) {
        Account account = accountService.getAccountById(id);
        ApiResponse<Account> response = ResponseUtil.success(account, "Account retrieved successfully", request.getRequestURI());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Account>> updateAccount(@PathVariable Integer id, @Valid @RequestBody Account account, HttpServletRequest request) {
        Account updatedAccount = accountService.updateAccount(account, id);
        ApiResponse<Account> response = ResponseUtil.success(updatedAccount, "Account updated successfully", request.getRequestURI());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAccount(@PathVariable("id") Integer id, HttpServletRequest request) {
        accountService.deleteAccount(id);
        ApiResponse<Void> response = ResponseUtil.success(null, "Account with ID " + id + " was successfully deleted.", request.getRequestURI());
        return ResponseEntity.ok(response);
    }

    // Endpoint para obter todas as contas
    @GetMapping
    public ResponseEntity<ApiResponse<List<Account>>> getAllAccounts(HttpServletRequest request) {
        List<Account> accounts = accountService.getAllAccounts();
        ApiResponse<List<Account>> response = ResponseUtil.success(accounts, "Accounts retrieved successfully", request.getRequestURI());
        return ResponseEntity.ok(response);
    }

    // Endpoint para obter todas as contas de um utilizador
    @GetMapping("/user")
    public ResponseEntity<ApiResponse<List<Account>>> getAccountsByUserId(Authentication authentication, HttpServletRequest request) {
        Integer userId = ((User) authentication.getPrincipal()).getId();
        List<Account> accounts = accountService.getAccountsByUserId(userId);
        ApiResponse<List<Account>> response = ResponseUtil.success(accounts, "Accounts retrieved successfully", request.getRequestURI());
        return ResponseEntity.ok(response);
    }
}
