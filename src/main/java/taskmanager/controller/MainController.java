package taskmanager.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import taskmanager.model.entity.Task;
import taskmanager.model.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Api
@RestController
public class MainController {
    @Autowired
    private TaskService taskService;

    @PostMapping(path = "/add")
    @ApiOperation(value = "Add new task")
    public @ResponseBody
    void addNewTask(@RequestBody Task task) {
        taskService.save(task);
    }

    @DeleteMapping(path = "/delete")
    @ApiOperation(value = "Remove existing task")
    void deleteTask(@RequestParam int id){
        taskService.deleteById(id);
    }

    @GetMapping(path = "/find/all")
    @ApiOperation(value = "Get all tasks")
    public @ResponseBody
    Iterable<Task> getAllTasks() {
        return taskService.findAll();
    }

    @GetMapping(path = "/find/title")
    @ApiOperation(value = "Find task by title")
    public @ResponseBody
    Iterable<Task> getByName(@RequestParam String title){
        return taskService.findByTitle(title);
    }

    @GetMapping(path = "/find/date")
    @ApiOperation(value = "Find task by date")
    public @ResponseBody
    Iterable<Task> getByDate(@RequestParam String date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-DD HH:mm");
        Date parsedDate = null;
        try {
            parsedDate = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            System.err.println("Wrong date format");
            parsedDate = new Date();
        }
        return taskService.findByDate(parsedDate);
    }
}