package com.miguel.mybudgetplanner.user;

import static org.junit.jupiter.api.Assertions.*;

import com.miguel.mybudgetplanner.user.User;
import com.miguel.mybudgetplanner.user.UserRepository;
import com.miguel.mybudgetplanner.user.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveUserSuccessfully() {
        // Given
        User user = new User(
                1,
                "John",
                "Doe",
                "john@mail.pt",
                "password123",
                Role.USER,
                null
        );

        // When
        User savedUser = userRepository.save(user);

        // Then
        assertNotNull(savedUser.getId());
        assertEquals("john@mail.pt", savedUser.getEmail());
        assertEquals(Role.USER, savedUser.getRole());
    }

    @Test
    void shouldFindUserByEmail() {
        // Given
        User user = User.builder()
                .firstname("Jane")
                .lastname("Doe")
                .email("jane.doe@example.com")
                .password("password123")
                .role(Role.ADMIN)
                .build();
        userRepository.save(user);

        // When
        Optional<User> foundUser = userRepository.findByEmail("jane.doe@example.com");

        // Then
        assertTrue(foundUser.isPresent());
        assertEquals("Jane", foundUser.get().getFirstname());
        assertEquals(Role.ADMIN, foundUser.get().getRole());
    }

    @Test
    void shouldReturnEmptyWhenUserNotFoundByEmail() {
        // When
        Optional<User> foundUser = userRepository.findByEmail("nonexistent@example.com");

        // Then
        assertFalse(foundUser.isPresent());
    }

    @Test
    void shouldNotSaveUserWithDuplicateEmail() {
        // Given
        User user1 = User.builder()
                .firstname("Alice")
                .lastname("Smith")
                .email("alice@example.com")
                .password("password123")
                .role(Role.USER)
                .build();

        User user2 = User.builder()
                .firstname("Bob")
                .lastname("Smith")
                .email("alice@example.com") // Duplicate email
                .password("password456")
                .role(Role.ADMIN)
                .build();

        userRepository.save(user1);

        // When & Then
        assertThrows(DataIntegrityViolationException.class, () -> userRepository.saveAndFlush(user2));
    }

    @Test
    void shouldDeleteUserById() {
        // Given
        User user = User.builder()
                .firstname("Charlie")
                .lastname("Brown")
                .email("charlie.brown@example.com")
                .password("password123")
                .role(Role.USER)
                .build();
        User savedUser = userRepository.save(user);

        // When
        userRepository.deleteById(savedUser.getId());

        // Then
        Optional<User> deletedUser = userRepository.findById(savedUser.getId());
        assertFalse(deletedUser.isPresent());
    }
}
