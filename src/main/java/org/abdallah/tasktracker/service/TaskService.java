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
        List<Task> tasks = taskRepository.loadAll();
        Task task = this.findTaskById(tasks, id);
        if (task == null) {
            return null;
        }
        task.setDescription(newDescription);
        task.setUpdatedAt(LocalDateTime.parse(LocalDateTime.now().toString()));
        taskRepository.saveAll(tasks);
        return task;
    }

    // TODO
    public boolean deleteTask(int id) {
        List<Task> tasks = taskRepository.loadAll();

        Task task = findTaskById(tasks, id);
        if (task == null) {
            return false;
        }

        tasks.remove(task);
        taskRepository.saveAll(tasks);
        return true;
    }
    // TODO
    public Task markInProgress(int id) {
        return null;
    }
    // TODO
    public Task markDone(int id) {
        return null;
    }
    // TODO
    public Task markTodo(int id) {
        return null;
    }
    // TODO
    public List<Task> listAllTasks() {
        return List.of();
    }
    // TODO
    public void listDoneTasks() {}
    public void listInProgressTasks() {}
    public void listTodoTasks() {}

    public List<Task> listByStatus(String status) {
        return null;
    }

    protected Task findTaskById(List<Task> tasks, int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }

}
