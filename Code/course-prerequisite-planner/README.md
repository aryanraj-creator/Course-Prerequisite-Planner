# Course Prerequisite Planner

A desktop course-planning application built with **Java 17, Swing, JDBC, SQLite and graph algorithms**. It models prerequisite relationships as a directed graph and creates a valid semester-wise plan.

## Features
- Course create, update and delete (CRUD)
- Directed prerequisite links with validation and cycle prevention
- Interactive graph canvas: nodes, arrows, status colors, zoom, pan and node selection
- BFS, DFS, Kahn topological sorting, DFS topological sorting and cycle detection
- Automatic semester plan that respects prerequisite order and a configurable course limit
- Student progress tracking: `COMPLETED`, `ENROLLED`, `PENDING`
- SQLite persistence and CSV/text export
- Dashboard, course management, graph, planner, progress and reports views

## Technology
- Java 17
- Java Swing UI
- SQLite database through Xerial SQLite JDBC
- Maven
- DSA: adjacency list, `HashMap`, `ArrayList`, queue, BFS, DFS, Kahn's algorithm

## Run
1. Install JDK 17 or newer and Maven.
2. Open this directory in IntelliJ IDEA, Eclipse or VS Code as a Maven project.
3. Run:
   ```bash
   mvn clean test
   mvn exec:java
   ```
4. The app creates `course_planner.db` in the working directory and seeds demo courses on first launch.

You can also run `com.courseplanner.app.Main` from your IDE after Maven has downloaded dependencies.

## Sample prerequisite data
- C Programming → Data Structures
- Data Structures → Algorithms
- Mathematics I → Machine Learning
- Python Programming → Machine Learning
- Algorithms → Artificial Intelligence
- Machine Learning → Deep Learning
- Artificial Intelligence → Deep Learning
- Discrete Mathematics → Algorithms
- C Programming → Database Management Systems

Arrows point from prerequisite to the course that depends on it.

## Project structure
```text
course-prerequisite-planner/
├── pom.xml
├── README.md
├── docs/
│   └── diagrams.md
└── src/
    ├── main/java/com/courseplanner/
    │   ├── app/Main.java
    │   ├── db/Database.java
    │   ├── model/ (Course, ProgressStatus, SemesterPlan)
    │   ├── repository/CourseRepository.java
    │   ├── service/ (CourseGraph, CourseService, SemesterPlanner)
    │   ├── ui/ (MainFrame, GraphPanel, Theme)
    │   └── util/SampleData.java
    └── test/java/com/courseplanner/service/CourseGraphTest.java
```

## Database schema
- `Students(student_id, name)`
- `Courses(course_id, name, credits, description)`
- `Prerequisites(course_id, prerequisite_id)`
- `Student_Progress(student_id, course_id, status)`
- `Semester_Plans(plan_id, student_id, course_id, semester, created_at)`

Foreign keys and checks protect referential integrity. Course deletion cascades to its prerequisite and progress records.

## Algorithms
### Graph representation
The repository stores each course's prerequisite IDs. `CourseGraph` converts these into an adjacency list where an edge `A -> B` means A must be completed before B.

### Kahn topological sort
1. Calculate each node's incoming-edge count (indegree).
2. Add all zero-indegree nodes to a queue.
3. Remove a node, append it to the result and reduce the indegree of its neighbors.
4. Add neighbors whose indegree becomes zero.
5. If fewer than V nodes are output, the graph contains a cycle.

Time: **O(V + E)**; space: **O(V + E)**.

### DFS cycle detection and topological sort
DFS uses three states: unvisited, visiting and visited. An edge to a visiting node is a back edge and proves a cycle. DFS topological sorting adds each node after visiting its descendants, then reverses the result.

Time: **O(V + E)**.

### BFS and shortest path
BFS explores the graph level by level. With an unweighted graph, the first discovered route to a target has the fewest edges. The graph API exposes `shortestPath(start, target)`.

### Semester planning
Completed courses are treated as already satisfied. Each remaining course is assigned no earlier than one semester after its latest non-completed prerequisite. A configurable per-semester course limit is then applied. The planner refuses to create a plan if a cycle exists.

## Diagrams
See [`docs/diagrams.md`](docs/diagrams.md) for UML, use-case, architecture, DFD and ER diagrams in Mermaid syntax. GitHub renders Mermaid diagrams in Markdown.

## Test cases
| Test | Expected result |
|---|---|
| Add valid course | Course saved |
| Add duplicate course ID | Existing course updated |
| Add self prerequisite | Rejected |
| Add unknown prerequisite | Rejected |
| Add edge that creates a cycle | Rejected and rolled back |
| Topological sort on DAG | All courses in valid order |
| Topological sort on cyclic graph | Cycle reported |
| Generate plan | Every prerequisite is completed earlier or scheduled in an earlier semester |
| Update progress | Status persists after restart |
| Export | CSV/text file is written to chosen location |

## Notes / scope
- The initial app uses a demo student with ID `1`; the schema is ready for multiple students, but account registration and authentication are intentionally outside this version.
- Semester plans are persisted in SQLite. The current UI exports the generated plan as text and the course catalogue as CSV.
- The graph uses a custom Swing canvas and lightweight circular layout. It supports zooming and panning without an external graph library.

## License
For educational and portfolio use. Add a license before redistributing the project publicly.
