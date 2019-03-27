package taskmanager.model.repository;


import taskmanager.model.entity.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Integer> {
    Iterable<Task> findByTitle(String title);
}
