package org.abdallah.tasktracker;

import org.abdallah.tasktracker.model.Status;
import org.abdallah.tasktracker.model.Task;
import org.abdallah.tasktracker.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TaskRepositoryTest {

    @TempDir
    Path tempDir;

    @Test
    void saveAll_and_loadAll_shouldPersistTasks() throws Exception {
        // Arrange
        Path json = tempDir.resolve("tasks.json");

        TaskRepository repo = new TaskRepository(json.toString());

        Task t = new Task();
        t.setId(1L);
        t.setDescription("Test");
        t.setStatus(Status.todo);

        // Act
        repo.saveAll(List.of(t));
        List<Task> loaded = repo.loadAll();

        // Assert
        assertThat(loaded)
                .hasSize(1)
                .first()
                .extracting(Task::getDescription)
                .isEqualTo("Test");
    }
}
