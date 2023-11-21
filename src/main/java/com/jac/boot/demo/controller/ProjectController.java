package com.jac.boot.demo.controller;

import com.jac.boot.demo.logic.ProjectService;
import com.jac.boot.demo.model.Project;
import com.jac.boot.demo.model.ProjectStep;
import com.jac.boot.demo.model.projection.ProjectWriteModel;
import io.micrometer.core.annotation.Timed;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/projects")
class ProjectController {

  private final ProjectService service;

  ProjectController(final ProjectService service) {
    this.service = service;
  }

  @GetMapping
  String showProjects(Model model, Authentication auth) {
//        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
    model.addAttribute("project", new ProjectWriteModel());
    return "projects";
//        }
//        return "index";
  }

  @PostMapping
  String addProject(
      @ModelAttribute("project") @Valid ProjectWriteModel current,
      BindingResult bindingResult,
      Model model
  ) {
    if (bindingResult.hasErrors()) {
      return "projects";
    }
    service.save(current);
    model.addAttribute("project", new ProjectWriteModel());
    model.addAttribute("projects", getProjects());
    model.addAttribute("message", "Dodano projekt!");
    return "projects";
  }

  @IllegalProcessingException
  @PostMapping(params = "addStep")
  String addProjectStep(@ModelAttribute("project") ProjectWriteModel current) {
    current.getSteps().add(new ProjectStep());
    return "projects";
  }

  @Timed(value = "project.create.group", histogram = true, percentiles = {0.5, 0.95, 0.99})
  @PostMapping("/{id}")
  String createGroup(
      @ModelAttribute("project") ProjectWriteModel current,
      Model model,
      @PathVariable int id,
      @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime deadline
  ) {
    try {
      service.createGroup(deadline, id);
      model.addAttribute("message", "Dodano grupę!");
    } catch (IllegalStateException | IllegalArgumentException e) {
      model.addAttribute("message", "Błąd podczas tworzenia grupy!");
    }
    return "projects";
  }

  @ModelAttribute("projects")
  List<Project> getProjects() {
    return service.readAll();
  }
}
