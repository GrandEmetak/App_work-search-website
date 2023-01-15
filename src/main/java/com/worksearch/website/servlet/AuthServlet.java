package com.worksearch.website.servlet;

import com.worksearch.website.model.User;
import com.worksearch.website.store.PsqlStore;

import javax.servlet.http.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

/**
 * -@WebServlet(urlPatterns = "/auth.do")
 * Сервлет проверяет почту и пароль, если они совпадают, то переходит на страницу вакансий.
 * Если нет, то возвращает обратно на страницу Login.
 *
 * @since 20.10.21
 */
public class AuthServlet extends HttpServlet {

    /**
     * Если пользователь ввел верную почту и пароль,
     * то мы записываем в HttpSession детали этого пользователя.
     * Теперь эти данные можно получить на другой странице.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User user = PsqlStore.instOf().findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            HttpSession sc = req.getSession();
            User admin = new User();
            admin.setName(user.getName());
            admin.setEmail(user.getEmail());
            sc.setAttribute("user", admin);
            resp.sendRedirect(req.getContextPath() + "/posts.do");
        } else {
            req.setAttribute("error", "Не верный email или пароль");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}
