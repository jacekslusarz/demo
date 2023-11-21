package com.jac.boot.demo.model.projection;

import com.jac.boot.demo.model.Task;
import com.jac.boot.demo.model.TaskGroup;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

public class GroupTaskWriteModel {

  @NotBlank(message = "Task's description must not be empty")
  private String description;
  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
  private LocalDateTime deadline;

  public String getDescription() {
    return description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  public LocalDateTime getDeadline() {
    return deadline;
  }

  public void setDeadline(final LocalDateTime deadline) {
    this.deadline = deadline;
  }

  Task toTask(final TaskGroup group) {
    return new Task(description, deadline, group);
  }
}
