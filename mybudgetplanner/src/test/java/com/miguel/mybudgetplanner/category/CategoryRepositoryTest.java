package com.miguel.mybudgetplanner.category;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void shouldSaveCategorySuccessfully() {
        // Given
        Category category = new Category();
        category.setName("Food");

        // When
        Category savedCategory = categoryRepository.save(category);

        // Then
        assertNotNull(savedCategory.getId());
        assertEquals("Food", savedCategory.getName());
    }

    @Test
    public void shouldFindCategoryById() {
        // Given
        Category category = new Category();
        category.setName("Utilities");
        Category savedCategory = categoryRepository.save(category);

        // When
        Optional<Category> foundCategory = categoryRepository.findById(savedCategory.getId());

        // Then
        assertTrue(foundCategory.isPresent());
        assertEquals("Utilities", foundCategory.get().getName());
    }

    @Test
    public void shouldReturnEmptyWhenCategoryNotFound() {
        // When
        Optional<Category> foundCategory = categoryRepository.findById(999);

        // Then
        assertFalse(foundCategory.isPresent());
    }

    @Test
    public void shouldFindAllCategories() {
        // Given
        Category category1 = new Category();
        category1.setName("Food");

        Category category2 = new Category();
        category2.setName("Transport");

        categoryRepository.save(category1);
        categoryRepository.save(category2);

        // When
        List<Category> categories = categoryRepository.findAll();

        // Then
        assertEquals(2, categories.size());
    }

    @Test
    public void shouldDeleteCategoryById() {
        // Given
        Category category = new Category();
        category.setName("Entertainment");
        Category savedCategory = categoryRepository.save(category);

        // When
        categoryRepository.deleteById(savedCategory.getId());

        // Then
        Optional<Category> foundCategory = categoryRepository.findById(savedCategory.getId());
        assertFalse(foundCategory.isPresent());
    }
}
