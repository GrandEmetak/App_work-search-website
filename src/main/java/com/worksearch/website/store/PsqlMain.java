package com.worksearch.website.store;

import com.worksearch.website.model.Post;
import com.worksearch.website.Store;

/**
 * 2. Замена MemStore на PsqlStore [#51922 #209148]
 * Уровень : 3. Мидл Категория : 3.2. Servlet JSP Топик : 3.2.5. База данных в Web
 * @author SlartiBartFast-art
 * @since 13.10.21
 */
public class PsqlMain {
    public static void main(String[] args) {
      Store store = PsqlStore.instOf();
        store.save(new Post(0, "Java Job", "начинающий java программист"));
        for (Post post : store.findAllPosts()) {
            System.out.println(post.getId() + " " + post.getName());
        }
    }
}
