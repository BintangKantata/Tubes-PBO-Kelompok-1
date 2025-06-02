package com.pboToDoList.ToDoList.controller;

import com.pboToDoList.ToDoList.category.Category;
import com.pboToDoList.ToDoList.category.CategoryService;
import com.pboToDoList.ToDoList.repository.RuserRepository;
import com.pboToDoList.ToDoList.task.EveryTask;
import com.pboToDoList.ToDoList.task.Task;
import com.pboToDoList.ToDoList.task.TaskPriority;
import com.pboToDoList.ToDoList.user.RegularUser;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/task")
public class TaskController {

    private EveryTask etask;
    private RuserRepository ruserRepository;
    private CategoryService categoryService;

    public TaskController(EveryTask etask, RuserRepository ruserRepository, CategoryService categoryService) {
        this.etask = etask;
        this.ruserRepository = ruserRepository;
        this.categoryService = categoryService;
    }

    @GetMapping("/my-tasks")
    public String showUserTasks(Model model, Authentication authentication) {
        String email = authentication.getName();
        RegularUser user = ruserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        model.addAttribute("user", user);
        model.addAttribute("tasks", etask.getTasksForUser(user));
        model.addAttribute("priorities", TaskPriority.values());
        model.addAttribute("categories", categoryService.getCategoriesByUser(user));

        return "my-tasks";
    }

    @GetMapping("/add")
    public String showAddForm(Model model, Authentication authentication) {
        String email = authentication.getName();
        RegularUser user = ruserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        model.addAttribute("task", new Task());
        model.addAttribute("priorities", TaskPriority.values());
        model.addAttribute("categories", categoryService.getCategoriesByUser(user));
        return "add-task";
    }


    @PostMapping("/add")
    public String addTask(@ModelAttribute Task task,
                          @RequestParam(value = "categoryId", required = false) Integer categoryId,
                          Authentication authentication) {

        String email = authentication.getName();
        RegularUser user = ruserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        task.setUser(user);

        if (categoryId != null) {
            Category category = categoryService.findById(categoryId)
                    .orElse(null);
            task.setCategory(category);
        } else {
            task.setCategory(null);
        }

        etask.addTask(task);

        return "redirect:/task/my-tasks";
    }

    @PostMapping("/delete")
    public String deleteTask(@RequestParam("id") int id) {
        etask.deleteTask(id);
        return "redirect:/task/my-tasks";
    }

    @GetMapping("/edit")
    public String showEditForm(@RequestParam int id, Model model, Authentication authentication) {
        String email = authentication.getName();
        RegularUser user = ruserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Optional<Task> task = etask.findById(id);
        if (task.isEmpty()) {
            throw new RuntimeException("Task not found");
        }

        Task taskObj = task.get();
        model.addAttribute("task", taskObj);
        model.addAttribute("priorities", TaskPriority.values());
        model.addAttribute("categories", categoryService.getCategoriesByUser(user));
        return "edit-task";
    }

    @PostMapping("/edit")
    public String updateTask(@ModelAttribute Task task,
                             @RequestParam(value = "categoryId", required = false) Integer categoryId) {

        if (categoryId != null) {
            Category category = categoryService.findById(categoryId)
                    .orElse(null);
            task.setCategory(category);
        } else {
            task.setCategory(null);
        }

        etask.updateTask(task);

        return "redirect:/task/my-tasks";
    }
    @GetMapping("/search")
    public String searchAndFilterTasks(@RequestParam(required = false) String title,
                                       @RequestParam(required = false) Integer categoryId,
                                       @RequestParam(required = false) TaskPriority priority,
                                       @RequestParam(required = false) Boolean completed,
                                       Model model,
                                       Authentication authentication) {

        String email = authentication.getName();
        RegularUser user = ruserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Task> tasks = etask.searchAndFilterTasks(title, categoryId, priority, completed, user);

        model.addAttribute("tasks", tasks);
        model.addAttribute("categories", categoryService.getCategoriesByUser(user));
        model.addAttribute("priorities", TaskPriority.values());
        model.addAttribute("user", user);
        return "my-tasks";
    }
}
