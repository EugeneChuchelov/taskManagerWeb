package taskmanager.model.repository;


import taskmanager.model.entity.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;

public interface TaskRepository extends CrudRepository<Task, Integer> {
    Iterable<Task> findByTitle(String title);

    Iterable<Task> findByDate(Date date);
}
