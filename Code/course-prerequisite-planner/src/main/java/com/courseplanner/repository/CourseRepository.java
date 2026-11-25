package com.courseplanner.repository;

import com.courseplanner.db.Database;
import com.courseplanner.model.Course;
import com.courseplanner.model.ProgressStatus;
import java.sql.*;
import java.util.*;

public class CourseRepository {
    public List<Course> findAll() throws SQLException {
        List<Course> out = new ArrayList<>();
        try (Connection c=Database.getConnection(); PreparedStatement p=c.prepareStatement("SELECT * FROM Courses ORDER BY course_id"); ResultSet r=p.executeQuery()) {
            while(r.next()) out.add(new Course(r.getString("course_id"),r.getString("name"),r.getInt("credits"),r.getString("description")));
        } return out;
    }
    public Optional<Course> findById(String id) throws SQLException {
        try(Connection c=Database.getConnection(); PreparedStatement p=c.prepareStatement("SELECT * FROM Courses WHERE course_id=?")) {
            p.setString(1,id.toUpperCase()); try(ResultSet r=p.executeQuery()) { return r.next()?Optional.of(new Course(r.getString(1),r.getString(2),r.getInt(3),r.getString(4))):Optional.empty(); }
        }
    }
    public void save(Course x) throws SQLException {
        try(Connection c=Database.getConnection(); PreparedStatement p=c.prepareStatement("INSERT INTO Courses(course_id,name,credits,description) VALUES(?,?,?,?) ON CONFLICT(course_id) DO UPDATE SET name=excluded.name,credits=excluded.credits,description=excluded.description")) {
            p.setString(1,x.getId());p.setString(2,x.getName());p.setInt(3,x.getCredits());p.setString(4,x.getDescription());p.executeUpdate();
        }
    }
    public void delete(String id) throws SQLException { try(Connection c=Database.getConnection();PreparedStatement p=c.prepareStatement("DELETE FROM Courses WHERE course_id=?")){p.setString(1,id);p.executeUpdate();} }
    public Map<String,Set<String>> prerequisites() throws SQLException {
        Map<String,Set<String>> map=new LinkedHashMap<>();
        for(Course x:findAll()) map.put(x.getId(),new LinkedHashSet<>());
        try(Connection c=Database.getConnection();Statement s=c.createStatement();ResultSet r=s.executeQuery("SELECT course_id,prerequisite_id FROM Prerequisites")) { while(r.next()) map.computeIfAbsent(r.getString(1),k->new LinkedHashSet<>()).add(r.getString(2)); }
        return map;
    }
    public void addPrerequisite(String course,String prerequisite) throws SQLException {
        if(course.equalsIgnoreCase(prerequisite)) throw new IllegalArgumentException("A course cannot be its own prerequisite.");
        try(Connection c=Database.getConnection();PreparedStatement p=c.prepareStatement("INSERT OR IGNORE INTO Prerequisites(course_id,prerequisite_id) VALUES(?,?)")){p.setString(1,course.toUpperCase());p.setString(2,prerequisite.toUpperCase());p.executeUpdate();}
    }
    public void removePrerequisite(String course,String prerequisite) throws SQLException { try(Connection c=Database.getConnection();PreparedStatement p=c.prepareStatement("DELETE FROM Prerequisites WHERE course_id=? AND prerequisite_id=?")){p.setString(1,course);p.setString(2,prerequisite);p.executeUpdate();} }
    public Map<String,ProgressStatus> getProgress(int studentId) throws SQLException {
        Map<String,ProgressStatus> m=new HashMap<>(); try(Connection c=Database.getConnection();PreparedStatement p=c.prepareStatement("SELECT course_id,status FROM Student_Progress WHERE student_id=?")){p.setInt(1,studentId);try(ResultSet r=p.executeQuery()){while(r.next())m.put(r.getString(1),ProgressStatus.valueOf(r.getString(2)));}} return m;
    }
    public void setProgress(int studentId,String courseId,ProgressStatus status) throws SQLException {
        try(Connection c=Database.getConnection();PreparedStatement p=c.prepareStatement("INSERT INTO Student_Progress(student_id,course_id,status) VALUES(?,?,?) ON CONFLICT(student_id,course_id) DO UPDATE SET status=excluded.status")){p.setInt(1,studentId);p.setString(2,courseId);p.setString(3,status.name());p.executeUpdate();}
    }
    public void savePlan(int studentId,List<com.courseplanner.model.SemesterPlan> plans) throws SQLException {
        try(Connection c=Database.getConnection()) { c.setAutoCommit(false); try(PreparedStatement d=c.prepareStatement("DELETE FROM Semester_Plans WHERE student_id=?")){d.setInt(1,studentId);d.executeUpdate();}
            try(PreparedStatement p=c.prepareStatement("INSERT INTO Semester_Plans(student_id,course_id,semester) VALUES(?,?,?)")){for(var plan:plans)for(Course x:plan.getCourses()){p.setInt(1,studentId);p.setString(2,x.getId());p.setInt(3,plan.getSemester());p.addBatch();}p.executeBatch();} c.commit();
        }
    }
}
