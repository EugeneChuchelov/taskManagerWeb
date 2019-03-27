package taskmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "taskmanager.model")
@EnableJpaRepositories(basePackages = "taskmanager.model.repository")
@ComponentScan(basePackages = "taskmanager")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}