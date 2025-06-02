package com.pboToDoList.ToDoList.repository;

import com.pboToDoList.ToDoList.task.Task;
import com.pboToDoList.ToDoList.task.TaskPriority;
import com.pboToDoList.ToDoList.user.RegularUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findByRuser(RegularUser ruser);
    @Query("SELECT t FROM Task t WHERE " +
            "t.ruser = :ruser AND " +
            "(:title IS NULL OR LOWER(t.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
            "(:categoryId IS NULL OR t.category.id = :categoryId) AND " +
            "(:priority IS NULL OR t.priority = :priority) AND " +
            "(:completed IS NULL OR t.completed = :completed)")
    List<Task> findByFilters(@Param("title") String title,
                             @Param("categoryId") Integer categoryId,
                             @Param("priority") TaskPriority priority,
                             @Param("completed") Boolean completed,
                             @Param("ruser") RegularUser ruser);
    List<Task> findByDeadline(LocalDate deadline);
}
