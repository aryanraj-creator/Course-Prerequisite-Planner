package com.courseplanner.service;
import org.junit.jupiter.api.Test;import java.util.*;import static org.junit.jupiter.api.Assertions.*;
class CourseGraphTest {
 @Test void kahnReturnsValidOrder(){CourseGraph g=new CourseGraph(List.of("A","B","C"),Map.of("B",Set.of("A"),"C",Set.of("B")));assertEquals(List.of("A","B","C"),g.topologicalSort());}
 @Test void detectsCycle(){CourseGraph g=new CourseGraph(List.of("A","B"),Map.of("A",Set.of("B"),"B",Set.of("A")));assertTrue(g.hasCycle());}
 @Test void bfsFindsShortestPath(){CourseGraph g=new CourseGraph(List.of("A","B","C","D"),Map.of("B",Set.of("A"),"C",Set.of("A"),"D",Set.of("B","C")));assertEquals(List.of("A","B","D"),g.shortestPath("A","D"));}
}
