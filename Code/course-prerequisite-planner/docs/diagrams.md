# System diagrams

## 1. System architecture
```mermaid
flowchart TB
  UI[Java Swing UI] --> SVC[Service Layer]
  SVC --> GRAPH[CourseGraph / SemesterPlanner]
  SVC --> REPO[CourseRepository]
  REPO --> JDBC[JDBC]
  JDBC --> DB[(SQLite Database)]
```

## 2. UML class diagram
```mermaid
classDiagram
  class Course { -String id; -String name; -int credits; -String description }
  class ProgressStatus { <<enumeration>> COMPLETED; ENROLLED; PENDING }
  class SemesterPlan { -int semester; -List~Course~ courses }
  class CourseRepository { +findAll(); +save(Course); +delete(String); +prerequisites(); +setProgress() }
  class CourseService { +all(); +save(); +delete(); +addPrerequisite(); +plan() }
  class CourseGraph { -Map adjacencyList; +bfs(); +dfs(); +topologicalSort(); +hasCycle(); +shortestPath() }
  class SemesterPlanner { +generate() }
  CourseService --> CourseRepository
  CourseService --> CourseGraph
  CourseService --> SemesterPlanner
  SemesterPlan o-- Course
  Course --> ProgressStatus
```

## 3. Use-case diagram
```mermaid
flowchart LR
  Student((Student)) --> UC1[Manage courses]
  Student --> UC2[Add prerequisites]
  Student --> UC3[View dependency graph]
  Student --> UC4[Generate semester plan]
  Student --> UC5[Track course progress]
  Student --> UC6[Export report]
  UC2 --> UC7[Validate graph / detect cycle]
  UC4 --> UC8[Topological sort]
```

## 4. DFD level 0
```mermaid
flowchart LR
  Student[Student] -->|Course data, progress, planning request| System[Course Prerequisite Planner]
  System -->|Graph, eligible courses, semester plan, reports| Student
  System <--> DB[(SQLite Database)]
```

## 5. DFD level 1
```mermaid
flowchart TB
  A[Student input] --> P1[1. Manage courses]
  P1 <--> D1[(Courses store)]
  P1 --> P2[2. Manage prerequisites]
  P2 <--> D2[(Prerequisite store)]
  P2 --> P3[3. Validate graph]
  P3 --> P4[4. Generate semester plan]
  D1 --> P4
  D2 --> P4
  D3[(Student progress)] --> P4
  A --> P5[5. Update progress]
  P5 <--> D3
  P4 --> D4[(Semester plans)]
  P4 --> O[Plan and reports]
```

## 6. ER diagram
```mermaid
erDiagram
  STUDENTS ||--o{ STUDENT_PROGRESS : tracks
  COURSES ||--o{ STUDENT_PROGRESS : has
  COURSES ||--o{ SEMESTER_PLANS : scheduled
  STUDENTS ||--o{ SEMESTER_PLANS : owns
  COURSES ||--o{ PREREQUISITES : dependent_course
  COURSES ||--o{ PREREQUISITES : prerequisite_course
  STUDENTS { int student_id PK; string name }
  COURSES { string course_id PK; string name; int credits; string description }
  PREREQUISITES { string course_id FK; string prerequisite_id FK }
  STUDENT_PROGRESS { int student_id FK; string course_id FK; string status }
  SEMESTER_PLANS { int plan_id PK; int student_id FK; string course_id FK; int semester }
```

## 7. Example dependency graph
```mermaid
flowchart LR
  C[C Programming] --> DS[Data Structures]
  DS --> A[Algorithms]
  M[Mathematics I] --> ML[Machine Learning]
  P[Python Programming] --> ML
  A --> AI[Artificial Intelligence]
  ML --> DL[Deep Learning]
  AI --> DL
```
