package com.courseplanner.service;

import java.util.*;

/** Directed graph: prerequisite -> course, so topological order is learning order. */
public class CourseGraph {
    private final Map<String,Set<String>> edges=new LinkedHashMap<>();
    public CourseGraph(Collection<String> courseIds, Map<String,Set<String>> prerequisites) {
        for(String id:courseIds) edges.put(id,new LinkedHashSet<>());
        prerequisites.forEach((course,reqs)->{ edges.computeIfAbsent(course,k->new LinkedHashSet<>()); for(String req:reqs){edges.computeIfAbsent(req,k->new LinkedHashSet<>()).add(course);} });
    }
    public Map<String,Set<String>> adjacencyList(){Map<String,Set<String>> copy=new LinkedHashMap<>();edges.forEach((k,v)->copy.put(k,Collections.unmodifiableSet(v)));return Collections.unmodifiableMap(copy);}
    public List<String> bfs(String start){check(start);List<String> order=new ArrayList<>();Set<String> seen=new HashSet<>();Queue<String> q=new ArrayDeque<>();q.add(start);seen.add(start);while(!q.isEmpty()){String u=q.remove();order.add(u);for(String v:edges.getOrDefault(u,Set.of()))if(seen.add(v))q.add(v);}return order;}
    public List<String> dfs(String start){check(start);List<String> out=new ArrayList<>();Set<String> seen=new HashSet<>();dfsVisit(start,seen,out);return out;}
    private void dfsVisit(String u,Set<String>s,List<String>o){s.add(u);o.add(u);for(String v:edges.getOrDefault(u,Set.of()))if(!s.contains(v))dfsVisit(v,s,o);}
    private void check(String id){if(!edges.containsKey(id))throw new IllegalArgumentException("Unknown course: "+id);}
    public boolean hasCycle(){return topologicalSort().size()!=edges.size();}
    /** Returns nodes that participate in at least one directed cycle. */
    public Set<String> cycleNodes(){Set<String> cyclic=new LinkedHashSet<>();Map<String,Integer> state=new HashMap<>();List<String> stack=new ArrayList<>();Map<String,Integer> position=new HashMap<>();for(String node:edges.keySet())if(state.getOrDefault(node,0)==0)cycleVisit(node,state,stack,position,cyclic);return cyclic;}
    private void cycleVisit(String u,Map<String,Integer> state,List<String> stack,Map<String,Integer> position,Set<String> cyclic){state.put(u,1);position.put(u,stack.size());stack.add(u);for(String v:edges.getOrDefault(u,Set.of())){int st=state.getOrDefault(v,0);if(st==0)cycleVisit(v,state,stack,position,cyclic);else if(st==1){Integer start=position.get(v);if(start!=null)cyclic.addAll(stack.subList(start,stack.size()));}}stack.remove(stack.size()-1);position.remove(u);state.put(u,2);}
    public List<String> topologicalSort(){Map<String,Integer> degree=indegrees();Queue<String> q=new PriorityQueue<>();degree.forEach((k,v)->{if(v==0)q.add(k);});List<String> out=new ArrayList<>();while(!q.isEmpty()){String u=q.remove();out.add(u);for(String v:edges.get(u)){int d=degree.merge(v,-1,Integer::sum);if(d==0)q.add(v);}}return out;}
    public List<String> topologicalSortDfs(){Map<String,Integer> state=new HashMap<>();List<String> out=new ArrayList<>();for(String u:edges.keySet())if(state.getOrDefault(u,0)==0)dfsTopo(u,state,out);Collections.reverse(out);return out;}
    private void dfsTopo(String u,Map<String,Integer>s,List<String>o){s.put(u,1);for(String v:edges.get(u)){int st=s.getOrDefault(v,0);if(st==1)throw new IllegalStateException("Circular prerequisite detected near "+v);if(st==0)dfsTopo(v,s,o);}s.put(u,2);o.add(u);}
    private Map<String,Integer> indegrees(){Map<String,Integer>d=new LinkedHashMap<>();edges.keySet().forEach(k->d.put(k,0));edges.values().forEach(v->v.forEach(x->d.merge(x,1,Integer::sum)));return d;}
    public List<String> shortestPath(String from,String to){check(from);check(to);Map<String,String> parent=new HashMap<>();Queue<String>q=new ArrayDeque<>();Set<String>seen=new HashSet<>();q.add(from);seen.add(from);while(!q.isEmpty()){String u=q.remove();if(u.equals(to))break;for(String v:edges.get(u))if(seen.add(v)){parent.put(v,u);q.add(v);}}if(!seen.contains(to))return List.of();LinkedList<String>path=new LinkedList<>();for(String x=to;x!=null;x=parent.get(x))path.addFirst(x);return path;}
}
