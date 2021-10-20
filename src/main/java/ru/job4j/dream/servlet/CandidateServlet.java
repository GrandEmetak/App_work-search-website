package ru.job4j.dream.servlet;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.store.MemStore;
import ru.job4j.dream.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@WebServlet(urlPatterns = "/candidates.do")
public class CandidateServlet extends HttpServlet {
    /**
     * мы перенаправляем запрос в index.jsp.
     * req.getRequestDispatcher("index.jsp").forward(req, resp);
     * В методу doGet мы загружаем в request список вакансий.
     * req.setAttribute("posts", Store.instOf().findAllPosts());
     * открытый интерфейс ServletRequest
     * Определяет объект для предоставления сервлету информации о запросе клиента.
     * Контейнер сервлета создает ServletRequest объект и передает его в качестве
     * аргумента service методу сервлета .
     * Интерфейс RequestDispatcher предоставляет два метода. Они есть:
     * <p>
     * public void forward (запрос ServletRequest, ответ ServletResponse) выдает исключение ServletException,
     * java.io.IOException: перенаправляет запрос от сервлета к другому ресурсу
     * (сервлету, файлу JSP или файлу HTML) на сервере.
     *Если требуемый ресурс находится в том же контексте, что и сервлет, который его вызывает,
     *  то для получения ресурса необходимо использовать метод
     *  public RequestDispatcher getRequestDispatcher(String path);
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("candidates", PsqlStore.instOf().findAllCandidates());
        req.getRequestDispatcher("candidates.jsp").forward(req, resp);
    }

  /*  @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
      /*  Store.instOf().saveCandidate(
                new Candidate(Integer.parseInt(req.getParameter("id")),
                        req.getParameter("name")));*/
     /*   System.out.println("ID : " + req.getParameter("id"));
        if (req.getParameter("method") != null && req.getParameter("method").equals("delete")) {
            int id = PsqlStore.instOf().deleteCandidateId(Integer.parseInt(req.getParameter("id"))).getId();
            new File("c\\images\\" + id + ".png").delete();
        } else {
            System.out.println("CandidateServlet : " + req.getParameter("id"));
            PsqlStore.instOf().saveCandidate(
                    new Candidate(
                            Integer.parseInt(req.getParameter("id")),
                            req.getParameter("name")));
        }
        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }*/
}
