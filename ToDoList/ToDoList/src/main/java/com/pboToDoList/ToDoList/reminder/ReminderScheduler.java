package com.pboToDoList.ToDoList.reminder;

import com.pboToDoList.ToDoList.repository.TaskRepository;
import com.pboToDoList.ToDoList.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReminderScheduler {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private EmailService emailService;

    @Scheduled(cron = "0 28 23 * * ?")
    public void sendTaskReminders() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        List<Task> tasksDueTomorrow = taskRepository.findByDeadline(tomorrow);

        for (Task task : tasksDueTomorrow) {
            String email = task.getUser().getEmail();
            String subject = "Task Reminder: \"" + task.getTitle() + "\" is due tomorrow.";
            String body = "Hello " + task.getUser().getUsername() + ",\n\n" +
                    "Your task \"" + task.getTitle() + "\" is due on " + task.getDeadline() + ".\n\n" +
                    "Description: " + task.getDescription() + "\n\n" +
                    "Regards,\nTo-Do-List";
            emailService.sendReminderEmail(email, subject, body);
        }
    }
}

