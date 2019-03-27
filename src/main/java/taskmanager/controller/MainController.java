package taskmanager.controller;


import taskmanager.model.entity.Task;
import taskmanager.model.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Controller
@RequestMapping(path = "/test")
public class MainController {
    @Autowired
    private TaskService taskService;

    @GetMapping(path = "/add")
    public @ResponseBody
    String addNewTask(@RequestParam String title, @RequestParam String description,
                      @RequestParam String date, @RequestParam String contacts) {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Task task = null;
        try {
            task = new Task(title, description, ft.parse(date), contacts);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        taskService.save(task);
        return "Saved";
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Task> getAllTasks() {
        return taskService.findAll();
    }

    @GetMapping(path = "/find")
    public @ResponseBody
    Iterable<Task> getByName(@RequestParam String title){
        return taskService.findByTitle(title);
    }
}