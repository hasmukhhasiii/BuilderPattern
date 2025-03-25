package org.example.BuilderPattern;

import org.junit.jupiter.api.*;
import java.sql.*;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)  // Ensures JUnit 5 does not ignore test instance
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)  // Ensures tests run in order if needed
public class StudentUtilsTest {

    private Connection connection;

    @BeforeAll
    void setupDatabase() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "");
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("""
                CREATE TABLE STUDENTS (
                    NAME VARCHAR(50),
                    ADMISSION_NUMBER VARCHAR(20),
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
            stmt.execute("""
                INSERT INTO STUDENTS (NAME, ADMISSION_NUMBER, MARKS_PHYSICS, MARKS_CHEMISTRY, MARKS_MATHS)
                VALUES ('Low Score Student', '1001', 20, 25, 30);
            """);
        }
    }

    @AfterEach
    void clearTestData() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM STUDENTS");
        }
    }

    @AfterAll
    void closeDatabase() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    @Test
    @Order(1)
    void testCalculatePercentageForLowestMarks() throws SQLException {
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM STUDENTS WHERE ADMISSION_NUMBER = '1001'")) {

            assertTrue(rs.next());
            double percentage = StudentUtils.calculatePercentage(rs);
            assertEquals(25.0, percentage, 0.01); // (20+25+30) / 3 = 25.0
        }
    }

    @Test
    @Order(2)
    void testGetMarksForLowestMarks() throws SQLException {
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM STUDENTS WHERE ADMISSION_NUMBER = '1001'")) {

            assertTrue(rs.next());
            HashMap<String, Object> studentMap = new HashMap<>();
            StudentUtils.getMarks(rs, studentMap);

            assertEquals("Low Score Student", studentMap.get("NAME"));
            assertEquals("1001", studentMap.get("ADMISSION_NUMBER"));
            assertEquals("25.00", studentMap.get("Percentage"));
            assertEquals(20, studentMap.get("MARKS_PHYSICS"));
            assertEquals("E2", studentMap.get("GRADE_PHYSICS"));
            assertEquals("C", studentMap.get("GRADEPOINT_PHYSICS"));
        }
    }

    // 🔹 Additional tests to increase branch coverage
    @Test
    @Order(3)
    void testGradereturnForAllCases() {
        assertEquals("A1", StudentUtils.gradereturn(95));
        assertEquals("A2", StudentUtils.gradereturn(85));
        assertEquals("B1", StudentUtils.gradereturn(75));
        assertEquals("B2", StudentUtils.gradereturn(65));
        assertEquals("C1", StudentUtils.gradereturn(55));
        assertEquals("C2", StudentUtils.gradereturn(45));
        assertEquals("D", StudentUtils.gradereturn(35));
        assertEquals("E1", StudentUtils.gradereturn(25));
        assertEquals("E2", StudentUtils.gradereturn(10));
    }

    @Test
    @Order(4)
    void testGradepointForAllCases() {
        assertEquals("10.0", StudentUtils.gradepoint(95));
        assertEquals("9.0", StudentUtils.gradepoint(85));
        assertEquals("8.0", StudentUtils.gradepoint(75));
        assertEquals("7.0", StudentUtils.gradepoint(65));
        assertEquals("6.0", StudentUtils.gradepoint(55));
        assertEquals("5.0", StudentUtils.gradepoint(45));
        assertEquals("4.0", StudentUtils.gradepoint(35));
        assertEquals("C", StudentUtils.gradepoint(25));
        assertEquals("C", StudentUtils.gradepoint(10));
    }
}