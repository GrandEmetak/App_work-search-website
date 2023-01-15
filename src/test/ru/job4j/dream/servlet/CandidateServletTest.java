package ru.job4j.dream.servlet;

import static org.junit.Assert.*;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.store.MemStore;
import ru.job4j.dream.store.PsqlStore;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.mock;

/**
 * 1. Написать тест на PostServlet. [#2518]10
 * Уровень : 3. Мидл Категория : 3.2. Servlet JSPТопик : 3.2.7. Mockito
 * Чтобы запуск теста производился за счет библиотеки PowerMock,
 * необходимо указать аннотации ниже:
 * RunWith(PowerMockRunner.class)
 * PrepareForTest(PsqlStore.class)
 *
 * @version 01
 * @since 21.10.21
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(PsqlStore.class)
public class CandidateServletTest {
    @Test
    public void whenCreatePost() throws IOException, ServletException {
        var store = MemStore.instOf();

        PowerMockito.mockStatic(PsqlStore.class);
        PowerMockito.when(PsqlStore.instOf()).thenReturn(store);

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        PowerMockito.when(req.getParameter("id")).thenReturn("1");
        PowerMockito.when(req.getParameter("name")).thenReturn("Junior Java");

        new PostServlet().doPost(req, resp);

        Post result = store.findAllPosts().iterator().next();
        Assert.assertThat(result.getName(), Is.is("Junior Java"));
    }

    @Test
    public void whenGetCandidates() throws IOException, ServletException {
        var store = MemStore.instOf();

        String path = "candidates.jsp";
        PowerMockito.mockStatic(PsqlStore.class);
        PowerMockito.when(PsqlStore.instOf()).thenReturn(store);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        PowerMockito.when(req.getRequestDispatcher(path)).thenReturn(dispatcher);
        new CandidateServlet().doGet(req, resp);
    }
}