package com.miguel.mybudgetplanner.Transaction;

import com.miguel.mybudgetplanner.response.ApiResponse;
import com.miguel.mybudgetplanner.response.ResponseUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Transaction>>> findAll(HttpServletRequest request) {
        List<Transaction> transactions = transactionService.findAll();
        ApiResponse<List<Transaction>> response = ResponseUtil.success(transactions, "Transactions retrieved successfully", request.getRequestURI());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Transaction>> findById(@PathVariable Integer id, HttpServletRequest request) {
        Transaction transaction = transactionService.findById(id);
        ApiResponse<Transaction> response = ResponseUtil.success(transaction, "Transaction retrieved successfully", request.getRequestURI());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Transaction>> create(@Valid @RequestBody Transaction transaction, HttpServletRequest request) {
        Transaction createdTransaction = transactionService.create(transaction);
        ApiResponse<Transaction> response = ResponseUtil.success(createdTransaction, "Transaction created successfully", request.getRequestURI());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id, HttpServletRequest request) {
        transactionService.delete(id);
        ApiResponse<Void> response = ResponseUtil.success(null, "Transaction with ID " + id + " was successfully deleted.", request.getRequestURI());
        return ResponseEntity.ok(response);
    }
}
