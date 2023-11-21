package com.jac.boot.demo.logic;

import com.jac.boot.demo.model.Project;
import com.jac.boot.demo.model.TaskGroup;
import com.jac.boot.demo.model.TaskGroupRepository;
import com.jac.boot.demo.model.TaskRepository;
import com.jac.boot.demo.model.projection.GroupReadModel;
import com.jac.boot.demo.model.projection.GroupWriteModel;
import java.util.List;
import java.util.stream.Collectors;

public class TaskGroupService {

  private TaskGroupRepository repository;
  private TaskRepository taskRepository;

  TaskGroupService(final TaskGroupRepository repository, final TaskRepository taskRepository) {
    this.repository = repository;
    this.taskRepository = taskRepository;
  }

  public GroupReadModel createGroup(final GroupWriteModel source) {
    return createGroup(source, null);
  }

  GroupReadModel createGroup(final GroupWriteModel source, final Project project) {
    TaskGroup result = repository.save(source.toGroup(project));
    return new GroupReadModel(result);
  }

  public List<GroupReadModel> readAll() {
    return repository.findAll().stream()
        .map(GroupReadModel::new)
        .collect(Collectors.toList());
  }

  public void toggleGroup(int groupId) {
    if (taskRepository.existsByDoneIsFalseAndGroup_Id(groupId)) {
      throw new IllegalStateException("Group has undone tasks. Done all the tasks first");
    }
    TaskGroup result = repository.findById(groupId)
        .orElseThrow(() -> new IllegalArgumentException("TaskGroup with given id not found"));
    result.setDone(!result.isDone());
    repository.save(result);
  }
}
