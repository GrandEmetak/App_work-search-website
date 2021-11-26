package ru.job4j.dream.servlet;

import ru.job4j.dream.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * -@WebServlet(urlPatterns = "/delete.do")
 * Серлет проводит действия по удалению кандидата
 */
public class DeleteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("candidates", PsqlStore.instOf().findAllCandidates());
        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }

    /**
     * удаление кандидата из БД
     * int id = PsqlStore.instOf().deleteCandidateId(Integer.parseInt(req.getParameter("id"))).getId();
     * удаление пути к рисунку
     * new File("c\\images\\" + name.getName()).delete();
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        var s = (req.getParameter("id"));
        PsqlStore.instOf().deleteCandidateId(Integer.parseInt(s));
        for (File name : new File("c:\\images\\").listFiles()) {
            System.out.println("path : " + name);
            if (name.getName().startsWith(s)) {
                new File("c\\images\\" + name.getName()).delete();
            }
        }
        doGet(req, resp);
    }
}

