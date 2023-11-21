package com.jac.boot.demo.model.event;

import com.jac.boot.demo.model.Task;
import java.time.Clock;

public class TaskUndone extends TaskEvent {

  TaskUndone(final Task source) {
    super(source.getId(), Clock.systemDefaultZone());
  }
}
