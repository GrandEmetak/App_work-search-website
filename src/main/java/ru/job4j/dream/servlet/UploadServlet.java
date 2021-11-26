package ru.job4j.dream.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import ru.job4j.dream.store.MemStore;
import ru.job4j.dream.store.PsqlStore;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServlet;

/**
 * -@WebServlet(urlPatterns = "/upload")
 * 1. Загрузка и скачивание файла. [#154183 #207844]
 * Уровень : 3. Мидл Категория : 3.2. Servlet JSP Топик : 3.2.5.1. Form
 * класс UploadServlet. Это servlet будет обрабатывать загрузку файла на сервер.
 * +
 * ATTENTION! -
 * удален файл web.xml, произведена замена во всех классах на аннотацию @WebServlet(urlPattern = " маппинг имя")
 *
 * @author SlartiBartFast-art
 * @since 13.10.21
 */
public class UploadServlet extends HttpServlet {

    /**
     * Передает по ключу candidates данные полученные в MemStore.instOf().findAllCandidates())
     * перенаправляет на страницу candidates.do
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setAttribute("candidates", PsqlStore.instOf().findAllCandidates());
        String name = req.getParameter("id") + ".png";
        System.out.println("urlPatterns = \"/upload\")\n"
                + "public class UploadServlet   NAME + " + name);
        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }

   /* Метод doGet отображает список доступных файлов.
   @Override
     protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> images = new ArrayList<>();
        for (File name : new File("c:\\images\\").listFiles()) {
            System.out.println("UploadServlet - images.add(name.getName())  - " + name.getName());
            images.add(name.getName());
        }
        req.setAttribute("images", images);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/candidates.do");
        dispatcher.forward(req, resp);
    }*/

    /**
     * Метод doPost загружает выбранный файл на сервер в папку c:\images\
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String s = req.getParameter("id");
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);
        ServletFileUpload upload = new ServletFileUpload(factory);
        String h = null;
        try {
            List<FileItem> items = upload.parseRequest(req);
            for (FileItem item : items) {
                System.out.println("what is come : " + item);
                var f = item.toString().split(",");
                var g = f[0].split("=");
                var d = g[1].split("\\.");
                System.out.println("d String : " + d[0] + " " + d[1]);
                h = s + "." + d[1];
                System.out.println("in the end :" + h);
            }
            File folder = new File("c:\\images\\");
            if (!folder.exists()) {
                folder.mkdir();
            }
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    File file = new File(folder + File.separator + h);
                    try (FileOutputStream out = new FileOutputStream(file)) {
                        out.write(item.getInputStream().readAllBytes());
                    }
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        doGet(req, resp);
    }
}
