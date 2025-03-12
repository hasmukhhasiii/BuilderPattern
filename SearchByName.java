package org.example.BuilderPattern;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedHashMap;

class SearchByName implements StudentSearch {
    @Override
    public void search(String name, String url, String user, String password) {
        String query = "SELECT * FROM student WHERE name = ?";
        HashMap<String, Object> studentMap = new LinkedHashMap<>();

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                StudentUtils.getMarks(rs, studentMap);
            } else {
                System.out.println("Student with name " + name + " not found.");
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
    }
}
