package org.example.BuilderPattern;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import com.fasterxml.jackson.databind.*;

class StudentUtils {
    public static void getMarks(ResultSet rs, HashMap<String, Object> studentMap) throws SQLException {
        studentMap.put("NAME", rs.getString("NAME"));
        studentMap.put("ADMISSION_NUMBER", rs.getString("ADMISSION_NUMBER"));
        studentMap.put("Percentage", String.format("%.2f", calculatePercentage(rs)));

        int physics = rs.getInt("MARKS_PHYSICS");
        int chemistry = rs.getInt("MARKS_CHEMISTRY");
        int maths = rs.getInt("MARKS_MATHS");

        studentMap.put("MARKS_PHYSICS", physics);
        studentMap.put("GRADE_PHYSICS", gradereturn(physics));
        studentMap.put("GRADEPOINT_PHYSICS", gradepoint(physics));

        studentMap.put("MARKS_CHEMISTRY", chemistry);
        studentMap.put("GRADE_CHEMISTRY", gradereturn(chemistry));
        studentMap.put("GRADEPOINT_CHEMISTRY", gradepoint(chemistry));

        studentMap.put("MARKS_MATHS", maths);
        studentMap.put("GRADE_MATHS", gradereturn(maths));
        studentMap.put("GRADEPOINT_MATHS", gradepoint(maths));

        printPrettyJson(studentMap);
    }

    public static double calculatePercentage(ResultSet rs) throws SQLException {
        int physics = rs.getInt("MARKS_PHYSICS");
        int chemistry = rs.getInt("MARKS_CHEMISTRY");
        int maths = rs.getInt("MARKS_MATHS");
        return (double) (physics + chemistry + maths) / 3;
    }

    private static void printPrettyJson(HashMap<String, Object> studentMap) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            String prettyJson = mapper.writeValueAsString(studentMap);
            System.out.println(prettyJson);
        } catch (Exception e) {
            System.err.println("Error converting to JSON: " + e.getMessage());
        }
    }

    public static String gradereturn(Integer marks) {
        if (marks >= 91) return "A1";
        if (marks >= 81) return "A2";
        if (marks >= 71) return "B1";
        if (marks >= 61) return "B2";
        if (marks >= 51) return "C1";
        if (marks >= 41) return "C2";
        if (marks >= 33) return "D";
        if (marks >= 21) return "E1";
        return "E2";
    }

    public static String gradepoint(Integer marks) {
        if (marks >= 91) return "10.0";
        if (marks >= 81) return "9.0";
        if (marks >= 71) return "8.0";
        if (marks >= 61) return "7.0";
        if (marks >= 51) return "6.0";
        if (marks >= 41) return "5.0";
        if (marks >= 33) return "4.0";
        return "C";
    }
}