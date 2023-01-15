package com.worksearch.website.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * -@WebServlet(urlPatterns = "/logout.do")
 * @since 20.10.21
 */
public class LogoutServlet extends HttpServlet {

    /**
     * мы получаем сессию в следующей строке:
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
