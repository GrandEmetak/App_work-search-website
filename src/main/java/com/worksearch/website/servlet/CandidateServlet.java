package com.worksearch.website.servlet;

import com.worksearch.website.model.Candidate;
import com.worksearch.website.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * -@WebServlet(urlPatterns = "/candidates.do")
 * Отвечает за Кандидата -  CandidateServlet.
 * В методу doGet мы загружаем в request список вакансий.
 * req.setAttribute("posts", Store.instOf().findAllPosts());
  * ATTENTION! -
 * удален файл web.xml,
 * произведена замена во всех классах на аннотацию @WebServlet(urlPattern = " маппинг имя")
 *
 * @since 21.09.21
 */
public class CandidateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("candidates", PsqlStore.instOf().findAllCandidates());
        req.getRequestDispatcher("candidates.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        PsqlStore.instOf().saveCandidate(
                new Candidate(
                        Integer.parseInt(req.getParameter("id")),
                        req.getParameter("name"),
                        req.getParameter("position"),
                        req.getParameter("city_id")
                ));
        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }
}
