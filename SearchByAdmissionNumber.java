package org.example.BuilderPattern;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedHashMap;

class SearchByAdmissionNumber implements StudentSearch {
    @Override
    public void search(String admissionNumber, String url, String user, String password) {
        String query = "SELECT * FROM student WHERE admission_number = ?";
        HashMap<String, Object> studentMap = new LinkedHashMap<>();

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, admissionNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                StudentUtils.getMarks(rs, studentMap);
            } else {
                System.out.println("Student with Admission Number " + admissionNumber + " not found.");
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
    }
}