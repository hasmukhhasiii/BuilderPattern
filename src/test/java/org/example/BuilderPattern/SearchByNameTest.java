package org.example.BuilderPattern;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SearchByNameTest {

    @Test
    void testSearchSuccess() {
        SearchByName search = new SearchByName();
        assertDoesNotThrow(() -> search.search("John", "jdbc:mysql://localhost:3306/marklist", "root", "123456"));
    }

    @Test
    void testSearchInvalidDatabaseConnection() {
        SearchByName search = new SearchByName();
        Exception exception = assertThrows(RuntimeException.class, () ->
                search.search("John", "jdbc:invalid_url", "root", "123456")
        );

        assertTrue(exception.getMessage().contains("No suitable driver found"), "Expected error message not found");
    }
}