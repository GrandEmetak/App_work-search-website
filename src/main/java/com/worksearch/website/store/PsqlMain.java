package com.worksearch.website.store;

import com.worksearch.website.model.Post;
import com.worksearch.website.Store;

/**
 * Замена MemStore на PsqlStore
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
