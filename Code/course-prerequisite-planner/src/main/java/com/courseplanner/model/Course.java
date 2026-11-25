package com.courseplanner.model;

public class Course {
    private final String id;
    private String name;
    private int credits;
    private String description;

    public Course(String id, String name, int credits, String description) {
        if (id == null || id.isBlank() || name == null || name.isBlank()) throw new IllegalArgumentException("Course ID and name are required.");
        if (credits < 1 || credits > 10) throw new IllegalArgumentException("Credits must be between 1 and 10.");
        this.id = id.trim().toUpperCase(); this.name = name.trim(); this.credits = credits;
        this.description = description == null ? "" : description.trim();
    }
    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { if (name == null || name.isBlank()) throw new IllegalArgumentException("Name is required."); this.name = name.trim(); }
    public int getCredits() { return credits; }
    public void setCredits(int credits) { if (credits < 1 || credits > 10) throw new IllegalArgumentException("Credits must be between 1 and 10."); this.credits = credits; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description == null ? "" : description.trim(); }
    @Override public String toString() { return id + " - " + name; }
}
