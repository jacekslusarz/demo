package com.jac.boot.demo.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.jac.boot.demo.model.Task;
import com.jac.boot.demo.model.TaskRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TaskController.class)
class TaskControllerLightIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TaskRepository repo;

  @Test
  void httpGet_returnsGivenTask() throws Exception {
    // given
    String description = "foo";
    when(repo.findById(anyInt()))
        .thenReturn(Optional.of(new Task(description, LocalDateTime.now())));

    // when + then
    mockMvc.perform(get("/tasks/123"))
        .andDo(print())
        .andExpect(content().string(containsString(description)));
  }
}
