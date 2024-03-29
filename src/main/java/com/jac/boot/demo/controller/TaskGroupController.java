package com.jac.boot.demo.controller;

import com.jac.boot.demo.logic.TaskGroupService;
import com.jac.boot.demo.model.Task;
import com.jac.boot.demo.model.TaskRepository;
import com.jac.boot.demo.model.projection.GroupReadModel;
import com.jac.boot.demo.model.projection.GroupTaskWriteModel;
import com.jac.boot.demo.model.projection.GroupWriteModel;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/groups")
class TaskGroupController {

  private static final Logger logger = LoggerFactory.getLogger(TaskGroupController.class);
  private final TaskGroupService service;
  private final TaskRepository repository;

  TaskGroupController(final TaskGroupService service, final TaskRepository repository) {
    this.service = service;
    this.repository = repository;
  }

  @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
  String showGroups(Model model) {
    model.addAttribute("group", new GroupWriteModel());
    return "groups";
  }

  @PostMapping(produces = MediaType.TEXT_HTML_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  String addGroup(
      @ModelAttribute("group") @Valid GroupWriteModel current,
      BindingResult bindingResult,
      Model model
  ) {
    if (bindingResult.hasErrors()) {
      return "groups";
    }
    service.createGroup(current);
    model.addAttribute("group", new GroupWriteModel());
    model.addAttribute("groups", getGroups());
    model.addAttribute("message", "Dodano grupę!");
    return "groups";
  }

  @PostMapping(params = "addTask", produces = MediaType.TEXT_HTML_VALUE)
  String addGroupTask(@ModelAttribute("group") GroupWriteModel current) {
    current.getTasks().add(new GroupTaskWriteModel());
    return "groups";
  }

  @ResponseBody
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<GroupReadModel> createGroup(@RequestBody @Valid GroupWriteModel toCreate) {
    GroupReadModel result = service.createGroup(toCreate);
    return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
  }

  @ResponseBody
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<List<GroupReadModel>> readAllGroups() {
    return ResponseEntity.ok(service.readAll());
  }

  @ResponseBody
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<List<Task>> readAllTasksFromGroup(@PathVariable int id) {
    return ResponseEntity.ok(repository.findAllByGroup_Id(id));
  }

  @ResponseBody
  @Transactional
  @PatchMapping("/{id}")
  public ResponseEntity<?> toggleGroup(@PathVariable int id) {
    service.toggleGroup(id);
    return ResponseEntity.noContent().build();
  }

  @ExceptionHandler(IllegalArgumentException.class)
  ResponseEntity<?> handleIllegalArgument(IllegalArgumentException e) {
    return ResponseEntity.notFound().build();
  }

  @ExceptionHandler(IllegalStateException.class)
  ResponseEntity<String> handleIllegalState(IllegalStateException e) {
    return ResponseEntity.badRequest().body(e.getMessage());
  }

  @ModelAttribute("groups")
  List<GroupReadModel> getGroups() {
    return service.readAll();
  }
}
