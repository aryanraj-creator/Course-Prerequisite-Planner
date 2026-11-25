package com.courseplanner.service;

import com.courseplanner.model.*;
import com.courseplanner.repository.CourseRepository;
import java.sql.SQLException;
import java.util.*;

public class CourseService {
    private final CourseRepository repo;
    public CourseService(CourseRepository repo){this.repo=repo;}
    public List<Course> all() throws SQLException{return repo.findAll();}
    public void save(Course c)throws SQLException{repo.save(c);}
    public void delete(String id)throws SQLException{repo.delete(id);}
    public Map<String,Set<String>> prerequisites()throws SQLException{return repo.prerequisites();}
    public void addPrerequisite(String course,String prerequisite)throws SQLException{
        if(repo.findById(course).isEmpty()||repo.findById(prerequisite).isEmpty())throw new IllegalArgumentException("Both courses must exist before linking prerequisites.");
        repo.addPrerequisite(course,prerequisite);
        try{CourseGraph g=new CourseGraph(repo.prerequisites().keySet(),repo.prerequisites());if(g.hasCycle()){repo.removePrerequisite(course,prerequisite);throw new IllegalArgumentException("This relationship creates a circular prerequisite. It was not saved.");}}
        catch(SQLException|RuntimeException ex){if(ex instanceof IllegalArgumentException)throw ex;repo.removePrerequisite(course,prerequisite);throw ex;}
    }
    public void setProgress(int student,String id,ProgressStatus status)throws SQLException{repo.setProgress(student,id,status);}
    public Map<String,ProgressStatus> progress(int student)throws SQLException{return repo.getProgress(student);}
    public List<SemesterPlan> plan(int student,int maxCourses)throws SQLException{
        Map<String,ProgressStatus> p=progress(student);Set<String> done=new HashSet<>();p.forEach((id,status)->{if(status==ProgressStatus.COMPLETED)done.add(id);});
        List<SemesterPlan> plans=new SemesterPlanner().generate(all(),prerequisites(),done,maxCourses);repo.savePlan(student,plans);return plans;
    }
}
