package org.abdallah.tasktracker.service;

import org.abdallah.tasktracker.model.Status;
import org.abdallah.tasktracker.model.Task;
import org.abdallah.tasktracker.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public void deleteTask(int id) {
        List<Task> tasks = taskRepository.loadAll();

        Task task = findTaskById(tasks, id);
        if (task == null) {
            return;
        }

        tasks.remove(task);
        taskRepository.saveAll(tasks);
    }

    public void markInProgress(int id) {
        List<Task> tasks = taskRepository.loadAll();
        Task task = findTaskById(tasks, id);
        if (task == null) {
            return;
        }
        task.setStatus(Status.inProgress);
        taskRepository.saveAll(tasks);
    }

    public void markDone(int id) {
        List<Task> tasks = taskRepository.loadAll();
        Task task = findTaskById(tasks, id);
        if (task == null) {
            return;
        }
        task.setStatus(Status.done);
        taskRepository.saveAll(tasks);
    }

    public List<Task> listAllTasks() {
        return taskRepository.loadAll();
    }

    public List<Task> listDoneTasks() {
        List<Task> tasks = taskRepository.loadAll();
        List<Task> doneTasks = new ArrayList<>();

        for (Task task : tasks) {
            if (task.getStatus().equals(Status.done)){
                doneTasks.add(task);
            }
        }
        return doneTasks;
    }
    public List<Task> listInProgressTasks() {
        List<Task> tasks = taskRepository.loadAll();
        List<Task> doneTasks = new ArrayList<>();

        for (Task task : tasks) {
            if (task.getStatus().equals(Status.inProgress)){
                doneTasks.add(task);
            }
        }
        return doneTasks;
    }
    public List<Task> listTodoTasks() {
        List<Task> tasks = taskRepository.loadAll();
        List<Task> doneTasks = new ArrayList<>();

        for (Task task : tasks) {
            if (task.getStatus().equals(Status.todo)){
                doneTasks.add(task);
            }
        }
        return doneTasks;
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
