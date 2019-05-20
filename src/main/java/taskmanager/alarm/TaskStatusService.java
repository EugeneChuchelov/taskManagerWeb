package taskmanager.alarm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import taskmanager.model.entity.Task;
import taskmanager.model.service.TaskService;

import java.util.Date;

@Service
public class TaskStatusService {
    private static final long HALF_HOUR = 1800000;

    @Autowired
    private TaskService taskService;

    //Получает из БД задачи со статусом FAR, SOON или SOON_MESSAGE_SENT
    //И в случае необходимости меняет статус
    @Async
    public void updateStatus() throws InterruptedException {
        while (true) {
            Iterable<Task> tasks = taskService.findActiveTasks();

            Date now = new Date();

            Date soonMin = new Date(now.getTime() + HALF_HOUR);

            Date soonMax = new Date(now.getTime() + HALF_HOUR - 30000);
            for (Task task : tasks) {
                if (task.getDate().before(now)) {
                    taskService.setStatus(TaskStatus.ALREADY, task.getId());
                } else if (task.getDate().before(soonMin) &&
                        task.getDate().after(soonMax)) {
                    taskService.setStatus(TaskStatus.SOON, task.getId());
                }
            }
            Thread.sleep(30000);
        }
    }
}
