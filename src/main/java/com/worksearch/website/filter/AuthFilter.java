package com.worksearch.website.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "*.do")
public class AuthFilter implements Filter {

    /**
     * Интерфейс Filter содержит метод doFilter.
     * Через этот метод будут проходить запросы к сервлетам.
     * Если запрос идет на сервлет авторизации, то проверка делаться не будет.
     * if (uri.endsWith("auth.do")) {
     * chain.doFilter(sreq, sresp);
     * return; }
     * Для всех остальных запросов мы проверяем текущего пользователя. Если его нет,
     * то отправляем на страницу авторизации.
     * if (req.getSession().getAttribute("user") == null) {
     * resp.sendRedirect(req.getContextPath() + "/login.jsp");
     * return;
     * }
     *
     * @param sreq
     * @param sresp
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest sreq, ServletResponse sresp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) sreq;
        HttpServletResponse resp = (HttpServletResponse) sresp;
        String uri = req.getRequestURI();
        if (uri.endsWith("auth.do") || uri.endsWith("reg.do")) {
            chain.doFilter(sreq, sresp);
            return;
        }
        if (req.getSession().getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }
        chain.doFilter(sreq, sresp);
    }
}
