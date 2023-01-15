package com.worksearch.website.servlet;

import com.worksearch.website.model.Post;
import com.worksearch.website.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * -@WebServlet(urlPatterns = "/posts.do")
 * @since 22.09.21
 */
public class PostServlet extends HttpServlet {

    /**
     * мы перенаправляем запрос в posts.jsp.     *
     * req.getRequestDispatcher("posts.jsp").forward(req, resp);
     * В методу doGet мы загружаем в request список вакансий.
     * req.setAttribute("posts", Store.instOf().findAllPosts());
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("posts", PsqlStore.instOf().findAllPosts());
        req.setAttribute("user", req.getSession().getAttribute("user"));
        req.getRequestDispatcher("posts.jsp").forward(req, resp);
    }
    /*или
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String edit = req.getParameter("edit");
        String path = edit != null ? "/post/edit.jsp" : "/post/posts.jsp";
        req.setAttribute("user", req.getSession().getAttribute("user"));
        if (edit == null) {
            req.setAttribute("posts", store.findAll());
        }
        req.getRequestDispatcher(path).forward(req, resp);
     */

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        PsqlStore.instOf().save(
                new Post(
                        Integer.parseInt(req.getParameter("id")),
                        req.getParameter("name"),
                        req.getParameter("description"),
                        req.getParameter("city")
                ));
        resp.sendRedirect(req.getContextPath() + "/posts.do");
    }
}
