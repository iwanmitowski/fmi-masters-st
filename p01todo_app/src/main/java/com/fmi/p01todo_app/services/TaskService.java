package com.fmi.p01todo_app.services;

import com.fmi.p01todo_app.models.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// By default its Singleton
@Service
public class TaskService {

    private final SequenceService sequenceService;
    private ArrayList<Task> tasks = new ArrayList<>();

    public TaskService(SequenceService sequenceService) {
        this.sequenceService = sequenceService;
    }

    public Task getTask(int id)  {
        var result = tasks.stream()
            .filter(task -> task.getId() == id)
            .findFirst()
            .orElse(null);

        return  result;
    }

    public ArrayList<Task> getAllTasks () {
        return tasks;
    }

    public List<Task> getAllTasksByStatus (String status) {
        return tasks.stream()
                .filter(task ->
                    task.getStatus().equals(status))
                .collect(Collectors.toList());
    }

    public Task addTask(Task task) {
        task.setId(sequenceService.GetNextId());
        tasks.add(task);

        return task;
    }

    public void updateTask(Task task) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == task.getId()) {
                tasks.set(i, task);
                return;
            }
        }
    }

    public void deleteTask(int id) {
        tasks.removeIf(task -> task.getId() == id);
    }

    

}
