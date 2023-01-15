package com.worksearch.website.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * - @WebServlet(urlPatterns = "/download")
 * Загрузка и скачивание файла.
 * класс DownloadServlet. Это servlet будет отдавать файл, который лежит на сервере.
 * ознакомеление с возможностью servlet загружать файлы на сервер и отдавать их клиенту.
 * Http протокол позволяет передавать файлы между клиентом и сервером.
 * Задача этого сервлета отдать файл, который лежит на сервере.
 * +
 * ATTENTION! -
 * удален файл web.xml, произведена замена во всех классах на аннотацию @WebServlet(urlPattern = " маппинг имя")
 *
 * @since 13.10.21
 */
public class DownloadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        File downloadFile = null;
        for (File file : new File("c:\\images\\").listFiles()) {
            var f = file.getName().split("\\.");
            if (name.equals(f[0])) {
                downloadFile = file;
                break;
            }
        }
        resp.setContentType("application/octet-stream");
        resp.setHeader("Content-Disposition", "attachment; filename=\"" + downloadFile.getName() + "\"");
        try (FileInputStream stream = new FileInputStream(downloadFile)) {
            resp.getOutputStream().write(stream.readAllBytes());
        }
    }
}
