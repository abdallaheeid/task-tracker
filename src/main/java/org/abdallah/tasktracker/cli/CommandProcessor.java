package org.abdallah.tasktracker.cli;

import org.abdallah.tasktracker.service.TaskService;
import org.springframework.stereotype.Component;

@Component
public class CommandProcessor {

    private final TaskService taskService;

    public CommandProcessor(TaskService taskService) {
        this.taskService = taskService;
    }

    public void process(String[] args){
        if(args.length == 0){
            System.out.println("No command provided.");
            return;
        }

        String command = args[0];

        switch (command) {
            case "add" -> handleAdd(args);
            default -> System.out.println("Unknown command." + command);
        }
    }

    private void handleAdd(String[] args){
        if(args.length < 2){
            System.out.println("Usage: task-cli add \"description\"");
        }

        String description = String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length));

        var task = taskService.addTask(description);

        System.out.println("Task added successfully (ID: " + task.getId() + ")");

    }



}
