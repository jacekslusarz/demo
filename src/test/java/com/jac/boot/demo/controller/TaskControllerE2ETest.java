package com.jac.boot.demo.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.jac.boot.demo.model.Task;
import com.jac.boot.demo.model.TaskRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskControllerE2ETest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  TaskRepository repo;

  @Test
  void httpGet_returnsAllTasks() {
    // given
    int initial = repo.findAll().size();
    repo.save(new Task("foo", LocalDateTime.now()));
    repo.save(new Task("bar", LocalDateTime.now()));

    // when
    Task[] result = restTemplate.getForObject("http://localhost:" + port + "/tasks", Task[].class);

    // then
    assertThat(result).hasSize(initial + 2);
  }
}
