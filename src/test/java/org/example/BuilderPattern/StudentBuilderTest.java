

package org.example.BuilderPattern;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StudentBuilderTest {
    @Test
    void testBuildAdmissionSearch() {
        StudentSearch search = new StudentBuilder().setSearchType("admission").build();
        assertTrue(search instanceof SearchByAdmissionNumber);
    }

    @Test
    void testBuildNameSearch() {
        StudentSearch search = new StudentBuilder().setSearchType("name").build();
        assertTrue(search instanceof SearchByName);
    }

    @Test
    void testInvalidSearchType() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new StudentBuilder().setSearchType("invalid").build();
        });
        assertEquals("Invalid search type", exception.getMessage());
    }
}
