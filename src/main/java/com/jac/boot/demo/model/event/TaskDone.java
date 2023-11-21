package com.jac.boot.demo.model.event;

import com.jac.boot.demo.model.Task;
import java.time.Clock;

public class TaskDone extends TaskEvent {

  TaskDone(final Task source) {
    super(source.getId(), Clock.systemDefaultZone());
  }
}
