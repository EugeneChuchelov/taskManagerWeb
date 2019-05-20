package taskmanager.alarm;

import org.apache.http.HttpHost;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import taskmanager.model.entity.Task;
import taskmanager.model.service.TaskService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;

import static org.springframework.web.util.UriUtils.encodeQuery;

@Service
public class SendNotificationService {
    @Autowired
    private TaskService taskService;

    //Токен и id чата для бота
    private String token = "806778683:AAGMkOWMmos9y9ZWXs5Lobv6Yd4Th8bnLxg";

    private String chat_id = "710848602";

    //Получает из БД задачи со статусами SOON или ALREADY и посылает уведомление
    //При успехе меняет статус на SOON_MESSAGE_SENT и ALREADY_MESSAGE_SENT сответственно
    //Если не удается отправить сообщение статус не меняется
    @Async
    public void run() throws IOException, InterruptedException {
        while(true){
            Iterable<Task> tasks = taskService.findNotifiedTasks();

            for(Task task : tasks){
                if(request(createURL(task))){
                    if(task.getStatus().equals(TaskStatus.ALREADY)){
                        taskService.setStatus(TaskStatus.ALREADY_MESSAGE_SENT, task.getId());
                    } else {
                        taskService.setStatus(TaskStatus.SOON_MESSAGE_SENT, task.getId());
                    }
                }
            }
            Thread.sleep(30000);
        }
    }

    //Выполняется GET запрос к api telegram через прокси
    private boolean request(String url) throws IOException {
        boolean isSent = true;
        HttpHost proxy = new HttpHost("209.97.165.158", 31330);

        DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setRoutePlanner(routePlanner)
                .build();
        HttpGet httpget = new HttpGet(url);
        try {
            httpclient.execute(httpget);
        } catch (NoHttpResponseException e){
            isSent = false;
        }
        finally {
            httpclient.close();
        }
        return isSent;
    }

    //Создается URL для GET запроса к api telegram
    private String createURL(Task task){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-DD HH:mm");

        StringBuilder url = new StringBuilder("https://api.telegram.org/bot");
        url.append(token).append("/sendMessage?chat_id=").append(chat_id).append("&text=");
        if(task.getStatus().equals(TaskStatus.ALREADY)){
            url.append("Time has come").append("\n");
        } else {
            url.append("30 minutes left").append("\n");
        }
        url.append("Title: ").append(task.getTitle()).append("\n")
                .append("Time: ").append(simpleDateFormat.format(task.getDate())).append("\n")
                .append("Description: ").append(task.getDescription()).append("\n")
                .append("Contacts: ").append(task.getContacts());
        String encodedURL = encodeQuery(url.toString(), StandardCharsets.UTF_8);
        return encodedURL;
    }
}
