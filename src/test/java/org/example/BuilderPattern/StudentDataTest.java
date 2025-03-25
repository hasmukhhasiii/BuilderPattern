package org.example.BuilderPattern;

import org.junit.jupiter.api.*;
import java.io.*;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StudentDataTest {
    private Connection connection;
    private final String testDbUrl = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
    private final String testUser = "sa";
    private final String testPassword = "";

    @BeforeAll
    void setupDatabase() throws SQLException {
        connection = DriverManager.getConnection(testDbUrl, testUser, testPassword);
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("""
                CREATE TABLE STUDENTS (
                    NAME VARCHAR(50),
                    ADMISSION_NUMBER VARCHAR(20) PRIMARY KEY,
                    MARKS_PHYSICS INT,
                    MARKS_CHEMISTRY INT,
                    MARKS_MATHS INT
                );
            """);
        }
    }

    @BeforeEach
    void insertTestData() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM STUDENTS");
            stmt.execute("""
                INSERT INTO STUDENTS (NAME, ADMISSION_NUMBER, MARKS_PHYSICS, MARKS_CHEMISTRY, MARKS_MATHS)
                VALUES ('ADAM', '1001', 80, 85, 78);
            """);
        }
    }

    @AfterAll
    void closeDatabase() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    private String runStudentDataTest(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        System.setIn(testIn);
        System.setOut(new PrintStream(testOut));

        try {
            StudentData.main(new String[]{});
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }

        return testOut.toString();
    }

    @Test
    @DisplayName("Test search by admission number (Valid)")
    void testSearchByAdmissionNumber_Valid() {
        String input = "1\n1001\n3\n"; // Search for Alice, then exit
        String output = runStudentDataTest(input);
        assertTrue(output.contains("ADAM"), "Expected Alice in output but got:\n" + output);
    }

    @Test
    @DisplayName("Test search by name (Valid)")
    void testSearchByName_Valid() {
        String input = "2\nADAM\n3\n"; // Search by name, then exit
        String output = runStudentDataTest(input);
        assertTrue(output.contains("ADAM"), "Expected admission number A001 but got:\n" + output);
    }

    @Test
    @DisplayName("Test search with invalid admission number")
    void testSearchByAdmissionNumber_Invalid() {
        String input = "1\nB999\n3\n"; // Search invalid admission number, then exit
        String output = runStudentDataTest(input);
        assertTrue(output.contains("not found"), "Expected 'not found' message but got:\n" + output);
    }

    @Test
    @DisplayName("Test invalid menu option")
    void testInvalidMenuOption() {
        String input = "5\n3\n"; // Invalid option, then exit
        String output = runStudentDataTest(input);
        assertTrue(output.contains("Invalid choice"), "Expected invalid choice message but got:\n" + output);
    }
}