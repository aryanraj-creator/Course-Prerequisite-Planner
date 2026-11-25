package com.courseplanner.service;

import com.courseplanner.model.*;
import java.util.*;

public class SemesterPlanner {
    /** Schedules all non-completed courses in the earliest valid semester. */
    public List<SemesterPlan> generate(List<Course> courses,Map<String,Set<String>> prerequisites,Set<String> completed,int maxCoursesPerSemester) {
        if(maxCoursesPerSemester<1)throw new IllegalArgumentException("Semester course limit must be at least 1.");
        Map<String,Course> byId=new LinkedHashMap<>();courses.forEach(c->byId.put(c.getId(),c));
        CourseGraph graph=new CourseGraph(byId.keySet(),prerequisites);
        List<String> order=graph.topologicalSort();
        if(order.size()!=courses.size())throw new IllegalStateException("Cannot create a plan: prerequisite graph contains a cycle.");
        Map<String,Integer> semesterOf=new HashMap<>();
        for(String id:completed)semesterOf.put(id,0);
        Map<Integer,List<Course>> buckets=new TreeMap<>();
        for(String id:order){if(completed.contains(id))continue;int earliest=1;for(String pre:prerequisites.getOrDefault(id,Set.of())){if(!completed.contains(pre)&&!byId.containsKey(pre))throw new IllegalStateException("Missing prerequisite course: "+pre);earliest=Math.max(earliest,semesterOf.getOrDefault(pre,0)+1);}
            int sem=earliest;while(buckets.getOrDefault(sem,List.of()).size()>=maxCoursesPerSemester)sem++;
            buckets.computeIfAbsent(sem,k->new ArrayList<>()).add(byId.get(id));semesterOf.put(id,sem);
        }
        List<SemesterPlan> result=new ArrayList<>();buckets.forEach((n,list)->result.add(new SemesterPlan(n,list)));return result;
    }
}
