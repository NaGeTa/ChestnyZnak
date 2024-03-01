package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CrptApi {
    TimeUnit timeUnit;
    int requestLimit;
    AtomicInteger requestCount = new AtomicInteger(0);

    public CrptApi(TimeUnit timeUnit, int requestLimit) {
        this.timeUnit = timeUnit;
        this.requestLimit = requestLimit;
    }

    public void send(Document document, String key) throws Exception {

//  Вызов метода для контроля количества запросов
        requestsCounter();

//  Создание запроса
        URL url = new URL("https://ismp.crpt.ru/api/v3/lk/documents/create");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

//  Преобразование документа в JSON и передача его в теле запроса
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
        wr.writeBytes(objectMapper.writeValueAsString(document));
        wr.flush();
        wr.close();

//  Выполнение запроса и результат
        int responseCode = connection.getResponseCode();
        System.out.println("Response Code : " + responseCode);

//  Вывод ответа от сервер по желанию  -------------------------------------------------------------------

//        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//        String inputLine;
//        StringBuffer response = new StringBuffer();
//
//        while ((inputLine = in.readLine()) != null) {
//            response.append(inputLine);
//        }
//        in.close();
//
//        System.out.println(response.toString());

    }

    public void requestsCounter() {

        requestCount.addAndGet(1);

        if (requestCount.get() > requestLimit) {
            throw new RuntimeException("Requests count overrun");
        }


        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                requestCount.getAndSet(0);
            }
        };

//  Таймер запускается в отдельном потоке-демоне
        Thread timerThread = new Thread(() -> {
            Timer timer = new Timer();
            timer.schedule(timerTask, timeUnit.toMillis(1), timeUnit.toMillis(1));
        });

        timerThread.setDaemon(true);
        timerThread.start();

    }
}
