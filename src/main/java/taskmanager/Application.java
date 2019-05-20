package taskmanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import taskmanager.alarm.SendNotificationService;
import taskmanager.alarm.TaskStatusService;

@SpringBootApplication(scanBasePackages = "taskmanager.model")
@EnableJpaRepositories(basePackages = "taskmanager.model.repository")
@ComponentScan(basePackages = "taskmanager")
@EnableAsync
public class Application implements CommandLineRunner {
    @Autowired
    TaskStatusService statusService;

    @Autowired
    SendNotificationService notificationService;

    //Сервисы для уведомлений запускаются в отдельных потоках
    @Override
    public void run(String... args) throws Exception {
        statusService.updateStatus();
        notificationService.run();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);

    }
}