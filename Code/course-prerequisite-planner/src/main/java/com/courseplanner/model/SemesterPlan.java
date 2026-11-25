package com.courseplanner.model;
import java.util.*;
public class SemesterPlan {
    private final int semester;
    private final List<Course> courses;
    public SemesterPlan(int semester, List<Course> courses) { this.semester = semester; this.courses = List.copyOf(courses); }
    public int getSemester() { return semester; }
    public List<Course> getCourses() { return courses; }
}
