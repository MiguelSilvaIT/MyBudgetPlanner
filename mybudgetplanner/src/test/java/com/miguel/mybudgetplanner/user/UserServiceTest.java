package com.miguel.mybudgetplanner.user;

import com.miguel.mybudgetplanner.Account.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnTotalBalanceForUser() {
        // Given
        Integer userId = 1;
        BigDecimal expectedBalance = BigDecimal.valueOf(4500);

        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
        when(userRepository.getTotalBalanceById(userId)).thenReturn(expectedBalance);

        // When
        BigDecimal totalBalance = userService.getUserTotalBalance(userId);

        // Then
        assertEquals(expectedBalance, totalBalance);
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).getTotalBalanceById(userId);
    }
}
