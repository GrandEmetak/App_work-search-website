package com.worksearch.website.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * -@WebServlet(urlPatterns = "/index.do")
 * 0. MVC в Servlet. [#6868 #207783]0
 * Уровень : 3. МидлКатегория : 3.2. Servlet JSPТопик : 3.2.4. Шаблон MVC
 * Model - это классы, которые содержать данные. В нашем примере это класс Store.
 * <p>
 * Controller - этот класс собирает данные для вида. В нашем примере - это Servlet.
 * View - это отображение данных. В нашем примере это JSP.
 * Под каждую JSP создадим сервлет.
 * index.jsp - IndexServlet
 * +
 * ATTENTION! -
 * удален файл web.xml, произведена замена во всех классах на аннотацию @WebServlet(urlPattern = " маппинг имя")
 *
 * @author SlartiBartFast-art
 * @since 13.10.21
 */
public class IndexServlet extends HttpServlet {
    /**
     * мы перенаправляем запрос в index.jsp.
     * req.getRequestDispatcher("index.jsp").forward(req, resp);
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
