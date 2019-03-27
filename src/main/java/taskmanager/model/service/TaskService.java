package taskmanager.model.service;

import taskmanager.model.entity.Task;

public interface TaskService {
    Iterable<Task> findByTitle(String title);

    Iterable<Task> findAll();

    void save(Task task);

    void remove(Task task);

}
