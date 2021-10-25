package ru.job4j.dream.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
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
 *
 * @author SlartiBartFast-art
 * @since 25.10.21
 */
@WebServlet(urlPatterns = "/greet")
public class GreetingServlet extends HttpServlet {
    /**
     * Теперь можно проверить работу сервлета. Для этого в браузере нужно набрать адрес.     *
     * http://localhost:8080/dreamjob/greet?name=Petr
     * dreamjob - это имя нашего приложения.
     * greet - имя сервлета в web.xml
     * ?name=Petr - параметры запроса.
     *  String name = req.getParameter("name");
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.println("Nice to meet you, " + name);
        writer.flush();
    }
}
