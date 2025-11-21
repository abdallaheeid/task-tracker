package org.abdallah.tasktracker.repository;

import org.abdallah.tasktracker.model.Task;
import org.springframework.stereotype.Repository;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TaskRepository {

    private final Path filePath =  Paths.get("tasks.json");
    private final ObjectMapper mapper = new ObjectMapper();

    public TaskRepository() {
        initStorage();
    }

    public List<Task> loadAll(){
        try {
            String json = Files.readString(filePath);
            if (json.isBlank()) return new ArrayList<>();

            return mapper.readValue(json, new TypeReference<List<Task>>() {});
        } catch (Exception e) {
            return new ArrayList<>();
        }
    };

    public void saveAll(List<Task> tasks) {
        try {
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(tasks);

            Path tmp = Paths.get("tasks.json.tmp");
            Files.writeString(tmp, json, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            Files.move(tmp, filePath, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);

        } catch (Exception e) {
            throw new RuntimeException("Failed to save tasks", e);
        }
    }

    public void initStorage(){
        try {
            if(!Files.exists(filePath)){
                Files.writeString(filePath, "[]");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected String readFileContent(){
        return "";
    };

    protected void writeFileContent(String jsonContent){}

    protected void backupCorruptedFile(){};


}
