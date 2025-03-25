package org.example.BuilderPattern;


import org.example.BuilderPattern.SearchByAdmissionNumber;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

class SearchByAdmissionNumberTest {

    @Test
    void testSearchSuccess() {
        // Simulate a successful search without throwing exceptions
        SearchByAdmissionNumber search = new SearchByAdmissionNumber();
        assertDoesNotThrow(() -> search.search("12345", "jdbc:mysql://localhost:3306/marklist", "root", "123456"));
    }

    @Test
    void testSearchWithInvalidDatabaseConnection() {
        // Capture system output to check error message
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.err;
        System.setErr(new PrintStream(outputStream)); // Redirect error output

        try {
            SearchByAdmissionNumber search = new SearchByAdmissionNumber();
            search.search("12345", "jdbc:invalid_url", "wrong_user", "wrong_password");
        } finally {
            System.setErr(originalOut); // Restore original error output
        }

        // Check if the expected error message is printed
        String output = outputStream.toString();
        assertTrue(output.contains("SQL Error"), "Expected SQL error message in output: " + output);
    }
}