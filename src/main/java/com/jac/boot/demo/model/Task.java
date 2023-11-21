package com.jac.boot.demo.model;

import com.jac.boot.demo.model.event.TaskEvent;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @NotBlank(message = "Task's description must not be empty")
  private String description;
  private boolean done;
  private LocalDateTime deadline;
  @Embedded
  private Audit audit = new Audit();
  @ManyToOne
  @JoinColumn(name = "task_group_id")
  private TaskGroup group;

  Task() {
  }

  public Task(String description, LocalDateTime deadline) {
    this(description, deadline, null);
  }

  public Task(String description, LocalDateTime deadline, TaskGroup group) {
    this.description = description;
    this.deadline = deadline;
    if (group != null) {
      this.group = group;
    }
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

  void setDescription(final String description) {
    this.description = description;
  }

  public boolean isDone() {
    return done;
  }

  public TaskEvent toggle() {
    this.done = !this.done;
    return TaskEvent.changed(this);
  }

  public LocalDateTime getDeadline() {
    return deadline;
  }

  void setDeadline(final LocalDateTime deadline) {
    this.deadline = deadline;
  }

  TaskGroup getGroup() {
    return group;
  }

  void setGroup(final TaskGroup group) {
    this.group = group;
  }

  public void updateFrom(final Task source) {
    description = source.description;
    done = source.done;
    deadline = source.deadline;
    group = source.group;
  }
}
