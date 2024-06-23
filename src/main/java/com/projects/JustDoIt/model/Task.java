package com.projects.JustDoIt.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "Tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    private String title;
    private String description;
    private Boolean finished;

    /**
     * Default Constructor
     */
    protected Task() {

    }

    /**
     * @param title is the title of the specific task
     * @param description is a short description of what the task is or extra info needed to complete the task
     * @param finished is a flag to keep track of if the task is complete or not
     */
    public Task(String title, String description, Boolean finished) {
        this.title = title;
        this.description = description;
        this.finished = finished;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return String.format(
                "Task %d: [Title: %s; Description: %s; Status: %b]",
                id, title, description, finished);
    }

}
