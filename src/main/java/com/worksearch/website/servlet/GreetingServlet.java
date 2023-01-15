package com.worksearch.website.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.worksearch.website.model.Email;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * -@WebServlet(urlPatterns = "/greet")
 * механизмом Ajax. Этот механизм позволяет с помощью JS
 * отправить запрос на сервер и получить ответ.
 * сервлет, который будет отрабатывать запросы ajax.html, его нужно прописать в web.xml
 *
 * @since 25.10.21
 */
public class GreetingServlet extends HttpServlet {
    private static final Gson GSON = new GsonBuilder().create();
    private final List<Email> emails = new CopyOnWriteArrayList<>();

    /**
     * При обработке GET запроса происходит сериализация списка добавленных почтовых адресов.
     *
     * @param req
     * @param resp
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json; charset=utf-8");
        OutputStream output = resp.getOutputStream();
        String json = GSON.toJson(emails);
        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }

    /**
     * При обработке POST запроса производится десериализация модели.
     * Далее объект сохраняется в список.
     *
     * @param req
     * @param resp
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Email email = GSON.fromJson(req.getReader(), Email.class);
        emails.add(email);

        resp.setContentType("application/json; charset=utf-8");
        OutputStream output = resp.getOutputStream();
        String json = GSON.toJson(email);
        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }
}
