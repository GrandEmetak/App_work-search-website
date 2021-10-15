package ru.job4j.dream.servlet;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class CandidateServlet extends HttpServlet {
    /**
     * мы перенаправляем запрос в index.jsp.     *
     * req.getRequestDispatcher("index.jsp").forward(req, resp);
     *  В методу doGet мы загружаем в request список вакансий.
     *      *  req.setAttribute("posts", Store.instOf().findAllPosts());
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("candidates", Store.instOf().findAllCandidates());
        req.getRequestDispatcher("candidates.jsp").forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
      /*  Store.instOf().saveCandidate(
                new Candidate(Integer.parseInt(req.getParameter("id")),
                        req.getParameter("name")));*/
        if (req.getParameter("method") != null && req.getParameter("method").equals("delete")) {
            int id = Store.instOf().deleteCandidateId(Integer.parseInt(req.getParameter("id"))).getId();
            new File("c\\images\\" + id + ".png").delete();
        } else {
            Store.instOf().saveCandidate(
                    new Candidate(
                            Integer.parseInt(req.getParameter("id")),
                            req.getParameter("name")));
        }
        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }
}
