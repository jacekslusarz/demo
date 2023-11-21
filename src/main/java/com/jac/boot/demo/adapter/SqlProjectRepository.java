package com.jac.boot.demo.adapter;

import com.jac.boot.demo.model.Project;
import com.jac.boot.demo.model.ProjectRepository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
interface SqlProjectRepository extends ProjectRepository, JpaRepository<Project, Integer> {

  @Override
  @Query("select distinct p from Project p join fetch p.steps")
  List<Project> findAll();
}
