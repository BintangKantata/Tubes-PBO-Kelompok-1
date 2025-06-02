package com.pboToDoList.ToDoList.implement;
import com.pboToDoList.ToDoList.category.Category;
import com.pboToDoList.ToDoList.repository.CategoryRepository;
import com.pboToDoList.ToDoList.repository.TaskRepository;
import com.pboToDoList.ToDoList.task.Task;
import com.pboToDoList.ToDoList.task.EveryTask;
import com.pboToDoList.ToDoList.task.TaskPriority;
import com.pboToDoList.ToDoList.user.RegularUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskImplements implements EveryTask {
    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;

    public TaskImplements(TaskRepository taskRepository, CategoryRepository categoryRepository) {
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void updateTask(Task updatedTask) {
        Task existingTask = taskRepository.findById(updatedTask.getId())
                .orElseThrow(() -> new RuntimeException("Task not found"));

        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setDeadline(updatedTask.getDeadline());
        existingTask.setPriority(updatedTask.getPriority());
        existingTask.setProgress(updatedTask.getProgress());
        existingTask.setCategory(updatedTask.getCategory());

        taskRepository.save(existingTask);
    }

    @Override
    public void addTask(Task task) {
        if (task.getCategory() != null && task.getCategory().getName() != null) {
            String name = task.getCategory().getName();
            RegularUser user = task.getUser();

            Category existingCategory = categoryRepository.findByNameAndRuser(name, user)
                    .orElseThrow(() -> new RuntimeException("Category '" + name + "' not found for user"));

            task.setCategory(existingCategory);
        }

        taskRepository.save(task);
    }

    @Override
    public void deleteTask(int taskId){
        Task task = taskRepository.findById(taskId)
            .orElseThrow(() -> new RuntimeException("Task not found"));

        taskRepository.delete(task);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public List<Task> getTasksForUser(RegularUser user) {
        return taskRepository.findByRuser(user);
    }

    @Override
    public Optional<Task> findById(int taskId){
        return taskRepository.findById(taskId);
    }

    @Override
    public List<Task> searchAndFilterTasks(String title, Integer categoryId, TaskPriority priority, Boolean completed, RegularUser user) {
        if (title == null) title = "";
        return taskRepository.findByFilters(title, categoryId, priority, completed, user);
    }

}
