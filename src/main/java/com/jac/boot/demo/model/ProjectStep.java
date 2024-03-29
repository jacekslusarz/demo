package com.jac.boot.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "project_steps")
public class ProjectStep {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @NotBlank(message = "Project step's description must not be empty")
  private String description;
  private int daysToDeadline;
  @ManyToOne
  @JoinColumn(name = "project_id")
  private Project project;

  public int getId() {
    return id;
  }

  void setId(final int id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  public int getDaysToDeadline() {
    return daysToDeadline;
  }

  public void setDaysToDeadline(final int daysToDeadline) {
    this.daysToDeadline = daysToDeadline;
  }

  Project getProject() {
    return project;
  }

  public void setProject(final Project project) {
    this.project = project;
  }
}
