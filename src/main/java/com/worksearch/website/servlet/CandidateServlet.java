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
 * 2. CandidateSerlvet. Создание кандидата. [#2513]02
 * Уровень : 3. МидлКатегория : 3.2. Servlet JSPТопик : 3.2.3. Servlet
 * Создайте CandidateServlet.
 * Добавьте методы save(Candidate) в хранилище(Store).
 * Оживите страницу добавления кандитата.(web.xml)
 * 0. MVC в Servlet. [#6868]
 * Уровень : 3. Мидл Категория : 3.2. Servlet JSP Топик : 3.2.4. Шаблон MVC
 * Мы уже сделали CandidateServlet. Доработаем его. Добавим метод doGet.
 * В методу doGet мы загружаем в request список вакансий.
 * req.setAttribute("posts", Store.instOf().findAllPosts());
 * Обратите внимание в методе doPost тоже изменен адрес.
 * resp.sendRedirect(req.getContextPath() + "/candidates.do"); -- was candidates.jsp
 * 3. Редактирование вакансии. [#277566 #207261]02
 * Уровень : 3. Мидл Категория : 3.2. Servlet JSP Топик : 3.2.3. Servlet
 * В этом уроке мы добавим возможность редактировать вакансию.
 * Последний элемент - это загрузка id в сервлет.
 * Integer.valueOf(req.getParameter("id")),
 * ATTENTION! -
 * удален файл web.xml, произведена замена во всех классах на аннотацию @WebServlet(urlPattern = " маппинг имя")
 * @author SlartiBartFast-art
 * @version 04
 * @since 21.09.21
 */
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
