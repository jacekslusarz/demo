package com.jac.boot.demo.logic;

import com.jac.boot.demo.TaskConfigurationProperties;
import com.jac.boot.demo.model.Project;
import com.jac.boot.demo.model.ProjectRepository;
import com.jac.boot.demo.model.TaskGroupRepository;
import com.jac.boot.demo.model.projection.GroupReadModel;
import com.jac.boot.demo.model.projection.GroupTaskWriteModel;
import com.jac.boot.demo.model.projection.GroupWriteModel;
import com.jac.boot.demo.model.projection.ProjectWriteModel;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectService {

  private ProjectRepository repository;
  private TaskGroupRepository taskGroupRepository;
  private TaskGroupService taskGroupService;
  private TaskConfigurationProperties config;

  public ProjectService(final ProjectRepository repository,
      final TaskGroupRepository taskGroupRepository, final TaskGroupService taskGroupService,
      final TaskConfigurationProperties config) {
    this.repository = repository;
    this.taskGroupRepository = taskGroupRepository;
    this.taskGroupService = taskGroupService;
    this.config = config;
  }

  public List<Project> readAll() {
    return repository.findAll();
  }

  public Project save(final ProjectWriteModel toSave) {
    return repository.save(toSave.toProject());
  }

  public GroupReadModel createGroup(LocalDateTime deadline, int projectId) {
    if (!config.getTemplate().isAllowMultipleTasks()
        && taskGroupRepository.existsByDoneIsFalseAndProject_Id(projectId)) {
      throw new IllegalStateException("Only one undone group from project is allowed");
    }
    return repository.findById(projectId)
        .map(project -> {
          var targetGroup = new GroupWriteModel();
          targetGroup.setDescription(project.getDescription());
          targetGroup.setTasks(
              project.getSteps().stream()
                  .map(projectStep -> {
                        var task = new GroupTaskWriteModel();
                        task.setDescription(projectStep.getDescription());
                        task.setDeadline(deadline.plusDays(projectStep.getDaysToDeadline()));
                        return task;
                      }
                  ).collect(Collectors.toList())
          );
          return taskGroupService.createGroup(targetGroup, project);
        }).orElseThrow(() -> new IllegalArgumentException("Project with given id not found"));
  }
}
