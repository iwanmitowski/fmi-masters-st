package com.fmi.p01todo_app.controllers;

import com.fmi.p01todo_app.models.Task;
import com.fmi.p01todo_app.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TaskController {
    @Value("${example_user}")
    private String userName;
    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> getTask(@PathVariable int id) {
        var result = this.taskService.getTask(id);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Task>(result, HttpStatus.OK);
    }

    @PostMapping("/tasks")
    public ResponseEntity<Task> addTask(@RequestBody Task task){
        return new ResponseEntity<Task>(taskService.addTask(task), HttpStatus.CREATED);
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getTasks(
            @RequestParam(required = false) String status)
    {
        List result;
        if (!status.isEmpty() && !status.isBlank()) {
            result = this.taskService.getAllTasksByStatus(status);
        }
        else {
            result = this.taskService.getAllTasks();
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable int id,
            @RequestBody Task task) {
        var result = this.taskService.getTask(id);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        result.setDescription(task.getDescription());
        result.setDueDate(task.getDueDate());
        result.setStatus(task.getStatus());
        result.setTitle(task.getTitle());
        this.taskService.updateTask(result);

        return new ResponseEntity<Task>(result, HttpStatus.OK);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Task> deleteTask(
            @PathVariable int id) {
        var result = this.taskService.getTask(id);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.taskService.deleteTask(id);

        return new ResponseEntity<Task>(result, HttpStatus.OK);
    }
}
