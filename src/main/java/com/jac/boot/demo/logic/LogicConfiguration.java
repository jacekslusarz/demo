package com.jac.boot.demo.logic;

import com.jac.boot.demo.TaskConfigurationProperties;
import com.jac.boot.demo.model.ProjectRepository;
import com.jac.boot.demo.model.TaskGroupRepository;
import com.jac.boot.demo.model.TaskRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LogicConfiguration {

  @Bean
  ProjectService projectService(
      final ProjectRepository repository,
      final TaskGroupRepository taskGroupRepository,
      final TaskGroupService taskGroupService,
      final TaskConfigurationProperties config
  ) {
    return new ProjectService(repository, taskGroupRepository, taskGroupService, config);
  }

  @Bean
  TaskGroupService taskGroupService(
      final TaskGroupRepository taskGroupRepository,
      final TaskRepository taskRepository
  ) {
    return new TaskGroupService(taskGroupRepository, taskRepository);
  }
}
