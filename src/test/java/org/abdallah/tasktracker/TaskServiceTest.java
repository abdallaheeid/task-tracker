package org.abdallah.tasktracker;

import org.abdallah.tasktracker.model.Status;
import org.abdallah.tasktracker.model.Task;
import org.abdallah.tasktracker.repository.TaskRepository;
import org.abdallah.tasktracker.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void addTest_shouldCreateTaskWithIncrementedId(){
        when(taskRepository.loadAll()).thenReturn(new ArrayList<>());

        Task task = taskService.addTask("Buy Groceries");

        assertThat(task).isNotNull()
                        .extracting(Task::getDescription)
                        .isEqualTo("Buy Groceries");
        assertThat(task.getId()).isEqualTo(1L);

        verify(taskRepository).saveAll(anyList());
    }

    @Test
    void updateTask_shouldModifyDescription(){
        Task existing = new Task();
        existing.setId(1L);
        existing.setDescription("Old Description");

        when(taskRepository.loadAll()).thenReturn(new ArrayList<>(List.of(existing)));

        Task updated = taskService.updateTask(1, "New Description");

        assertThat(updated.getDescription()).isEqualTo("New Description");

        verify(taskRepository).saveAll(anyList());
    }

    @Test
    void deleteTask_shouldDeleteTask(){
        Task existing = new Task();
        existing.setId(1L);
        when(taskRepository.loadAll()).thenReturn(new ArrayList<>(List.of(existing)));

        taskService.deleteTask(1);

        assertThat(taskRepository.loadAll()).isEmpty();

        verify(taskRepository).saveAll(anyList());
    }

    @Test
    void markDone_shouldSetStatusToDone() {
        Task t = new Task();
        t.setId(5L);
        t.setStatus(Status.todo);

        List<Task> tasks = new ArrayList<>(List.of(t));
        when(taskRepository.loadAll()).thenReturn(tasks);

        taskService.markDone(5);

        assertThat(t.getStatus()).isEqualTo(Status.done);
        verify(taskRepository).saveAll(tasks);
    }

    @Test
    void listTodoTasks_shouldReturnOnlyTodoTasks() {
        Task t1 = new Task(); t1.setStatus(Status.todo);
        Task t2 = new Task(); t2.setStatus(Status.done);

        when(taskRepository.loadAll()).thenReturn(List.of(t1, t2));

        List<Task> result = taskService.listTodoTasks();

        assertThat(result)
                .hasSize(1)
                .allMatch(t -> t.getStatus() == Status.todo);
    }

}
