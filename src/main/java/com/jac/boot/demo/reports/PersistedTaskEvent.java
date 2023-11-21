package com.jac.boot.demo.reports;

import com.jac.boot.demo.model.event.TaskEvent;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "task_events")
class PersistedTaskEvent {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  int id;
  int taskId;
  String name;
  LocalDateTime occurrence;

  public PersistedTaskEvent() {
  }

  PersistedTaskEvent(TaskEvent source) {
    taskId = source.getTaskId();
    name = source.getClass().getSimpleName();
    occurrence = LocalDateTime.ofInstant(source.getOccurrence(), ZoneId.systemDefault());
  }
}
