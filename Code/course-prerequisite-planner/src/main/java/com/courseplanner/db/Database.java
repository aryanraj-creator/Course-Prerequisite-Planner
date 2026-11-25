package com.courseplanner.db;

import java.sql.*;

public final class Database {
    private static final String URL = "jdbc:sqlite:course_planner.db";
    private Database() {}
    public static Connection getConnection() throws SQLException {
        Connection c = DriverManager.getConnection(URL);
        try (Statement s = c.createStatement()) { s.execute("PRAGMA foreign_keys = ON"); }
        return c;
    }
    public static void initialize() throws SQLException {
        try (Connection c = getConnection(); Statement s = c.createStatement()) {
            s.executeUpdate("CREATE TABLE IF NOT EXISTS Students(student_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL)");
            s.executeUpdate("CREATE TABLE IF NOT EXISTS Courses(course_id TEXT PRIMARY KEY, name TEXT NOT NULL, credits INTEGER NOT NULL CHECK(credits BETWEEN 1 AND 10), description TEXT)");
            s.executeUpdate("CREATE TABLE IF NOT EXISTS Prerequisites(course_id TEXT NOT NULL, prerequisite_id TEXT NOT NULL, PRIMARY KEY(course_id, prerequisite_id), FOREIGN KEY(course_id) REFERENCES Courses(course_id) ON DELETE CASCADE, FOREIGN KEY(prerequisite_id) REFERENCES Courses(course_id) ON DELETE CASCADE, CHECK(course_id <> prerequisite_id))");
            s.executeUpdate("CREATE TABLE IF NOT EXISTS Student_Progress(student_id INTEGER NOT NULL, course_id TEXT NOT NULL, status TEXT NOT NULL CHECK(status IN ('COMPLETED','ENROLLED','PENDING')), PRIMARY KEY(student_id, course_id), FOREIGN KEY(student_id) REFERENCES Students(student_id) ON DELETE CASCADE, FOREIGN KEY(course_id) REFERENCES Courses(course_id) ON DELETE CASCADE)");
            s.executeUpdate("CREATE TABLE IF NOT EXISTS Semester_Plans(plan_id INTEGER PRIMARY KEY AUTOINCREMENT, student_id INTEGER NOT NULL, course_id TEXT NOT NULL, semester INTEGER NOT NULL, created_at TEXT DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY(student_id) REFERENCES Students(student_id) ON DELETE CASCADE, FOREIGN KEY(course_id) REFERENCES Courses(course_id) ON DELETE CASCADE)");
            s.executeUpdate("INSERT OR IGNORE INTO Students(student_id,name) VALUES(1,'Demo Student')");
        }
    }
}
