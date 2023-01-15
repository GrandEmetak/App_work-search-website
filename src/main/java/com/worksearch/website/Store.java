package com.worksearch.website;

import com.worksearch.website.model.Candidate;
import com.worksearch.website.model.Post;
import com.worksearch.website.model.User;

import java.util.Collection;

/**
 * 1. PsqlStore. [#281226]
 * Уровень : 3. Мидл Категория : 3.2. Servlet JSPТопик : 3.2.5. База данных в Web
 * Сделаем интерфейс Store
 * 3. Расширить Store для пользователя. [#283109]
 * Уровень : 3. Мидл Категория : 3.2. Servlet JSPТопик : 3.2.6. Filter, Security
 * Расширьте интерфейс Store. Добавьте методы для работы с классом User.
 * сохранение и поиск по email
 * @author SlartiBartFast-art
 * @since 13.10.21
 */
public interface Store {
    Collection<Post> findAllPosts();

    Collection<Candidate> findAllCandidates();

    void save(Post post);

    void saveCandidate(Candidate candidate);

    Post findById(int id);

    Candidate findByIdCandidate(int id);

    Candidate deleteCandidateId(int id);

    void save(User user);

    User findByEmail(String email);

    Collection<Candidate> findByData();

    Collection<Post> findByDataPost();
}
