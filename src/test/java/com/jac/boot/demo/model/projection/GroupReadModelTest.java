package com.jac.boot.demo.model.projection;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.jac.boot.demo.model.Task;
import com.jac.boot.demo.model.TaskGroup;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GroupReadModelTest {

  @Test
  @DisplayName("should create null deadline for group when no task deadlines")
  void constructor_noDeadlines_createsNullDeadline() {
    // given
    var source = new TaskGroup();
    source.setDescription("foo");
    source.setTasks(Set.of(new Task("bar", null)));

    // when
    var result = new GroupReadModel(source);

    // then
    assertThat(result).hasFieldOrPropertyWithValue("deadline", null);
  }
}
