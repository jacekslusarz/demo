package com.jac.boot.demo.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "projects")
public class Project {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @NotBlank(message = "Project's description must not be empty")
  private String description;
  @OneToMany(mappedBy = "project")
  private Set<TaskGroup> groups;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
  private Set<ProjectStep> steps;

  public Project() {
  }

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

  Set<TaskGroup> getGroups() {
    return groups;
  }

  void setGroups(final Set<TaskGroup> groups) {
    this.groups = groups;
  }

  public Set<ProjectStep> getSteps() {
    return steps;
  }

  public void setSteps(final Set<ProjectStep> steps) {
    this.steps = steps;
  }
}
