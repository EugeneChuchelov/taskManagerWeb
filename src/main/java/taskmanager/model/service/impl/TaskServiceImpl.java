package taskmanager.model.service.impl;

import taskmanager.alarm.TaskStatus;
import taskmanager.model.entity.Task;
import taskmanager.model.repository.TaskRepository;
import taskmanager.model.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Iterable<Task> findByTitle(String title) {
        return taskRepository.findByTitle(title);
    }

    @Override
    public Iterable<Task> findByDate(Date date) {
        return taskRepository.findByDate(date);
    }

    @Override
    public Iterable<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public void save(Task task) {
        taskRepository.save(task);
    }

    @Override
    public void remove(Task task) {
        taskRepository.delete(task);
    }

    @Override
    public void deleteById(int id) {
        taskRepository.deleteById(id);
    }

    @Override
    public void setStatus(TaskStatus status, int id) {
        taskRepository.setStatus(status, id);
    }

    @Override
    public Iterable<Task> findActiveTasks() {
        return taskRepository.findActiveTasks();
    }

    @Override
    public Iterable<Task> findNotifiedTasks() {
        return taskRepository.findNotifiedTasks();
    }
}
