package com.courseplanner.util;
import com.courseplanner.db.Database;
import com.courseplanner.model.Course;
import com.courseplanner.repository.CourseRepository;
import java.sql.*;
public final class SampleData {
 private SampleData(){}
 public static void seed() throws Exception {
  CourseRepository r=new CourseRepository();
  Course[] data={new Course("CS101","C Programming",4,"Programming fundamentals"),new Course("MA101","Mathematics I",4,"Foundational mathematics"),new Course("PY101","Python Programming",3,"Python fundamentals"),new Course("CS201","Data Structures",4,"Linear and non-linear data structures"),new Course("MA201","Discrete Mathematics",3,"Logic, sets and combinatorics"),new Course("CS301","Algorithms",4,"Algorithm design and analysis"),new Course("DB301","Database Management Systems",3,"Relational databases and SQL"),new Course("AI401","Machine Learning",4,"Supervised and unsupervised learning"),new Course("AI402","Artificial Intelligence",3,"Search and intelligent agents"),new Course("AI501","Deep Learning",4,"Neural networks and deep learning")};
  for(Course c:data)r.save(c);
  try(Connection c=Database.getConnection();Statement s=c.createStatement()){
   s.executeUpdate("INSERT OR IGNORE INTO Prerequisites VALUES('CS201','CS101')");s.executeUpdate("INSERT OR IGNORE INTO Prerequisites VALUES('CS301','CS201')");s.executeUpdate("INSERT OR IGNORE INTO Prerequisites VALUES('AI401','MA101')");s.executeUpdate("INSERT OR IGNORE INTO Prerequisites VALUES('AI401','PY101')");s.executeUpdate("INSERT OR IGNORE INTO Prerequisites VALUES('AI402','CS301')");s.executeUpdate("INSERT OR IGNORE INTO Prerequisites VALUES('AI501','AI401')");s.executeUpdate("INSERT OR IGNORE INTO Prerequisites VALUES('AI501','AI402')");s.executeUpdate("INSERT OR IGNORE INTO Prerequisites VALUES('CS301','MA201')");s.executeUpdate("INSERT OR IGNORE INTO Prerequisites VALUES('DB301','CS101')");
  }
 }
}
