package taskmanager.model.service;

import taskmanager.model.entity.Task;

import java.util.Date;

public interface TaskService {
    Iterable<Task> findByTitle(String title);

    Iterable<Task> findByDate(Date date);

    Iterable<Task> findAll();

    void save(Task task);

    void remove(Task task);

    void deleteById(int id);

}
