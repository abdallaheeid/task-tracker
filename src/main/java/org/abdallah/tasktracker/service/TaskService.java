package org.abdallah.tasktracker.service;

import org.abdallah.tasktracker.model.Status;
import org.abdallah.tasktracker.model.Task;
import org.abdallah.tasktracker.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task addTask(String description) {
        List<Task> tasks = taskRepository.loadAll();
        Long indexId = tasks.isEmpty()
                ? 1
                : tasks.stream().mapToLong(Task::getId).max().getAsLong() + 1;

        Task task = new Task();
        task.setId(indexId);
        task.setDescription(description);
        task.setStatus(Status.todo);
        task.setCreatedAt(LocalDateTime.parse(LocalDateTime.now().toString()));
        task.setUpdatedAt(LocalDateTime.parse(LocalDateTime.now().toString()));

        tasks.add(task);
        taskRepository.saveAll(tasks);
        return task;
    }
    public Task updateTask(int id, String newDescription) {
        return null;
    }
    public boolean deleteTask(int id) {
        return false;
    }
    public Task setStatus(Status status) {
        return null;
    }
    public List<Task> listAllTasks() {
        return List.of();
    }
    public void listDoneTasks() {}
    public void listInProgressTasks() {}
    public void listTodoTasks() {}

    public List<Task> listByStatus(String status) {
        return null;
    }

    protected Task findTaskById(int id) {
        return null;
    }

}
