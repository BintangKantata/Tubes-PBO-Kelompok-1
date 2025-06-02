package com.pboToDoList.ToDoList.task;

import com.pboToDoList.ToDoList.user.RegularUser;

import java.util.List;
import java.util.Optional;

public interface EveryTask {
    public abstract void addTask(Task task);
    public abstract void updateTask(Task updatedTask);
    public abstract void deleteTask(int taskId);
    public abstract List<Task> getAllTasks();
    List<Task> getTasksForUser(RegularUser user);
    Optional<Task> findById(int taskId);
    public List<Task> searchAndFilterTasks(String title, Integer categoryId, TaskPriority priority, Boolean completed, RegularUser user);
}
