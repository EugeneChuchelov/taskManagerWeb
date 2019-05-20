package taskmanager.model.repository;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import taskmanager.alarm.TaskStatus;
import taskmanager.model.entity.Task;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Date;

public interface TaskRepository extends CrudRepository<Task, Integer> {
    Iterable<Task> findByTitle(String title);

    Iterable<Task> findByDate(Date date);

    @Transactional
    @Modifying
    @Query("UPDATE Task t SET t.status = :status WHERE t.id = :id")
    void setStatus(@Param("status") TaskStatus status, @Param("id") int id);

    @Query("SELECT t FROM Task t WHERE t.status < 3")
    Iterable<Task> findActiveTasks();

    @Query("SELECT t FROM Task t WHERE t.status = 3 OR t.status = 1")
    Iterable<Task> findNotifiedTasks();
}
