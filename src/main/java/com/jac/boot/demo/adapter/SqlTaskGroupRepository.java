package com.jac.boot.demo.adapter;

import com.jac.boot.demo.model.TaskGroup;
import com.jac.boot.demo.model.TaskGroupRepository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
interface SqlTaskGroupRepository extends TaskGroupRepository, JpaRepository<TaskGroup, Integer> {

  @Override
  @Query("select distinct g from TaskGroup g join fetch g.tasks")
  List<TaskGroup> findAll();

  @Override
  boolean existsByDoneIsFalseAndProject_Id(Integer projectId);
}
