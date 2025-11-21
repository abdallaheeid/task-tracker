package org.abdallah.tasktracker.cli;

import org.abdallah.tasktracker.model.Task;
import org.abdallah.tasktracker.service.TaskService;
import org.springframework.stereotype.Component;
import java.util.List;

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
            case "update"  -> handleUpdate(args);
            case "delete" -> handleDelete(args);
            case "mark-in-progress" -> handleMarkInProgress(args);
            case "mark-done" -> handleMarkDone(args);
            case "list" -> handleList(args);
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

    private void handleUpdate(String[] args){
        if(args.length < 2){
            System.out.println("Usage: task-cli update <id> <description>");
        }

        int id = Integer.parseInt(args[1]);
        String description = String.join(" ", java.util.Arrays.copyOfRange(args, 2, args.length));

        var task = taskService.updateTask(id, description);
        System.out.println("Task updated successfully (ID: " + task.getId() + ")");
    }

    private void handleDelete(String[] args) {
        if(args.length < 2){
            System.out.println("Usage: task-cli delete <id>");
        }

        int id = Integer.parseInt(args[1]);
        taskService.deleteTask(id);
        System.out.println("Task deleted successfully (ID: " + id + ")");
    }

    private void handleList(String[] args) {
        if (args.length == 1) {
            List<Task> tasks = taskService.listAllTasks();
            System.out.println(tasks);
            return;
        }

        // Check second argument
        String filter = args[1];

        switch (filter) {
            case "done" -> {
                List<Task> tasks = taskService.listDoneTasks();
                System.out.println(tasks);
            }
            case "todo" -> {
                List<Task> tasks = taskService.listTodoTasks();
                System.out.println(tasks);
            }
            case "in-progress" -> {
                List<Task> tasks = taskService.listInProgressTasks();
                System.out.println(tasks);
            }
            default -> System.out.println("Unknown list filter: " + filter);
        }
    }

    private void handleMarkDone(String[] args) {
        if(args.length < 2){
            System.out.println("Usage: task-cli mark-done <id>");
        }

        int id = Integer.parseInt(args[1]);
        taskService.markDone(id);
        System.out.println("Task Marked Done successfully (ID: " + id + ")");
    }

    private void handleMarkInProgress(String[] args) {
        if(args.length < 2){
            System.out.println("Usage: task-cli mark-in-progress <id>");
        }

        int id = Integer.parseInt(args[1]);
        taskService.markInProgress(id);
        System.out.println("Task Marked inProgress successfully (ID: " + id + ")");
    }



}
