package org.abdallah.tasktracker;

import lombok.extern.slf4j.Slf4j;
import org.abdallah.tasktracker.cli.CommandProcessor;
import org.abdallah.tasktracker.repository.TaskRepository;
import org.abdallah.tasktracker.service.TaskService;
import org.jspecify.annotations.NullMarked;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@Slf4j
public class TaskTrackerApplication implements CommandLineRunner {

    private final CommandProcessor commandProcessor;
    private final TaskRepository taskRepository;
    private final TaskService taskService;

    public TaskTrackerApplication(CommandProcessor commandProcessor, TaskRepository taskRepository, TaskService taskService) {
        this.commandProcessor = commandProcessor;
        this.taskRepository = taskRepository;
        this.taskService = taskService;
    }

    public static void main(String[] args) {
        SpringApplication.run(TaskTrackerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        commandProcessor.process(args);
    }
}
