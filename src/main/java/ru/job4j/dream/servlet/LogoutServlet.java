package ru.job4j.dream.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;

/**
 * 1. HttpSession [#6869 #209565]
 * Уровень : 3. Мидл Категория : 3.2. Servlet JSP Топик : 3.2.6. Filter, Security
 * ВАЖНО! установлена аннотация @WebServlet вместо web.xml
 * Добавим сервлет LogoutServlet,
 * который будет обрабатывать этот запрос
 * <c:if test="${user != null}">
 *     <li class="nav-item">
 *         <a class="nav-link" href="<%=request.getContextPath()%>/logout.do">Выйти</a>
 *     </li>
 * </c:if>
 * следующим образом:
 * @author SlartiBartFast-art
 * @since 20.10.21
 */
@WebServlet(urlPatterns = "/logout.do")
public class LogoutServlet extends HttpServlet {
    /**
     * Код сервлета достаточно прост, мы получаем сессию в следующей строке:
     * HttpSession session = req.getSession();
     * а потом у этой сессии вызываем метод invalidate() который очищает
     * сессию и удаляет все добавленные ранее в нее атрибуты.
     * После этого мы будем перенаправлены на страницу авторизации следующей строкой:
     * req.getRequestDispatcher("login.jsp").forward(req, resp);
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.invalidate();
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }
}
