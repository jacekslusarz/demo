package com.jac.boot.demo.adapter;

import com.jac.boot.demo.model.Task;
import com.jac.boot.demo.model.TaskRepository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
interface SqlTaskRepository extends TaskRepository, JpaRepository<Task, Integer> {

  @Override
  @Query(nativeQuery = true, value = "select count(*) > 0 from tasks where id=:id")
  boolean existsById(@Param("id") Integer id);

  @Override
  boolean existsByDoneIsFalseAndGroup_Id(Integer groupId);

  @Override
  List<Task> findAllByGroup_Id(Integer groupId);
}
