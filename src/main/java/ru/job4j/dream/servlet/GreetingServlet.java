package ru.job4j.dream.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.dream.model.Email;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * -@WebServlet(urlPatterns = "/greet")
 * 4. Ajax Jquery [#58534 #210961]
 * Уровень : 3. Мидл Категория : 3.2. Servlet JSPТопик : 3.2.8. JS, JQuery, Ajax
 * механизмом Ajax. Этот механизм позволяет с помощью JS
 * отправить запрос на сервер и получить ответ.
 * Давайте теперь создадим сервлет,
 * который будет отрабатывать запросы ajax.html, его нужно прописать в web.xml
 * <servlet>
 * <servlet-name>GreetingServlet</servlet-name>
 * <servlet-class>ru.job4j.dream.servlet.GreetingServlet</servlet-class>
 * </servlet>
 * <servlet-mapping>
 * <servlet-name>GreetingServlet</servlet-name>
 * <url-pattern>/greet</url-pattern>
 * </servlet-mapping>
 * Если запустить Tomcat и перейти по адресу
 * http://localhost:8080/dreamjob/greet?name=Petr
 * получим рабочую форму обработки запроса, где
 * greet - имя сервлета в web.xml
 * ?name=Petr - параметры запроса.
 * ATTENTION! - удален файл web.xml,
 * произведена замена во всех классах на аннотацию @WebServlet(urlPattern = " маппинг имя")
 * 4.1. JSON формат. [#238554 #210967]
 * Уровень : 3. МидлКатегория : 3.2. Servlet JSPТопик : 3.2.8. JS, JQuery, Ajax
 * Обновим GreetingServlet следующим образом:
 *
 * @author SlartiBartFast-art
 * @since 25.10.21
 */
public class GreetingServlet extends HttpServlet {
    private static final Gson GSON = new GsonBuilder().create();
    private final List<Email> emails = new CopyOnWriteArrayList<>();

    /**
     * При обработке GET запроса происходит сериализация списка добавленных почтовых адресов.
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
    /**
     * было
     *
     *      * 4. Ajax Jquery [#58534 #210961]
     *      * Теперь можно проверить работу сервлета. Для этого в браузере нужно набрать адрес.
     *      * http://localhost:8080/dreamjob/greet?name=Petr
     *      * dreamjob - это имя нашего приложения.
     *      * greet - имя сервлета в web.xml
     *      * ?name=Petr - параметры запроса.
     *      * String name = req.getParameter("name");
     *      *
     *      * @param req
     *      * @param resp
     *      * @throws ServletException
     *      * @throws IOException
     *
     *  @Override
     *protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
     *resp.setContentType("text/plain");
     *resp.setCharacterEncoding("UTF-8");
     *String name = req.getParameter("name");
     *PrintWriter writer = new PrintWriter(resp.getOutputStream());
     *writer.println("Nice to meet you, " + name);
     *writer.flush();
     *}
     */

}
