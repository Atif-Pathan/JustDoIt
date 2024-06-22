package com.projects.JustDoIt.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Task {
    @Id
    @GeneratedValue
    private Integer id;
    private String title;
    private String description;
    private Boolean finished;
}
