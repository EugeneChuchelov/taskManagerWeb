import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import taskmanager.Application;
import taskmanager.model.entity.Task;
import taskmanager.model.repository.TaskRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class TaskRepositoryTest {
    @Autowired
    TaskRepository taskRepository;

    private Task task;

    private Date date;

    private int id;

    @Before
    public void setup() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        date = simpleDateFormat.parse("201402132000");
        task = new Task("testTask", "asdf", date, "1234");

        taskRepository.save(task);

        List<Task> tasks = (List<Task>) taskRepository.findByTitle("testTask");
        id = tasks.get(0).getId();
    }

    @Test
    public void findByTitle(){
        List<Task> tasks = (List<Task>) taskRepository.findByTitle("testTask");
        Assert.assertTrue(!tasks.isEmpty());
        id = tasks.get(0).getId();
    }

    @Test
    public void findById(){
        Task foundTask = taskRepository.findById(id).get();

        Assert.assertTrue(task.equals(foundTask));
    }

    @Test
    public void findByDate(){
        List<Task> tasks = (List<Task>) taskRepository.findByDate(date);

        Assert.assertTrue(!tasks.isEmpty());
    }

    public void findAll(){
        List<Task> tasks = (List<Task>) taskRepository.findAll();

        Assert.assertTrue(!tasks.isEmpty());
    }

    @Test
    public void deleteById(){
        taskRepository.deleteById(id);

        Assert.assertFalse(taskRepository.findById(id).isPresent());
    }
}
