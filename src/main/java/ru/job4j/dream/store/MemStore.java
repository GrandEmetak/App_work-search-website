package ru.job4j.dream.store;

import ru.job4j.dream.Store;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemStore implements Store {
    private static final MemStore INST = new MemStore();
    private static final AtomicInteger POST_ID = new AtomicInteger(4);
    private static final AtomicInteger CANDIDATE_ID = new AtomicInteger(4);
    private static final AtomicInteger USER_ID = new AtomicInteger();
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private final Map<Integer, User> users = new ConcurrentHashMap<>();

    private MemStore() {
        posts.put(1, new Post(1, "Junior Java Job"));
        posts.put(2, new Post(2, "Middle Java Job"));
        posts.put(3, new Post(3, "Senior Java Job"));
        candidates.put(1, new Candidate(1, "Junior Java"));
        candidates.put(2, new Candidate(2, "Middle Java"));
        candidates.put(3, new Candidate(3, "Senior Java"));
    }

    public static MemStore instOf() {
        return INST;
    }

    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }

    /**
     * Если id вакансии равен 0, то нужно сгенерировать новую id.
     *
     * @param post Object
     */
    public void save(Post post) {
        if (post.getId() == 0) {
            post.setId(POST_ID.incrementAndGet());
        }
        posts.put(post.getId(), post);
    }

    /**
     * Если id candidate равен 0, то нужно сгенерировать новую id.
     * @param candidate Object
     */
    @Override
    public void saveCandidate(Candidate candidate) {
        if (candidate.getId() == 0) {
            candidate.setId(CANDIDATE_ID.incrementAndGet());
        }
        candidates.put(candidate.getId(), candidate);
    }

    @Override
    public Candidate deleteCandidateId(int id) {
      return candidates.remove(id);
    }

    @Override
    public void save(User user) {
        if (user.getId() == 0) {
            user.setId(USER_ID.incrementAndGet());
        }
        users.put(user.getId(), user);
    }

    @Override
    public User findByEmail(String email) {
        User user = null;
        for (User us : users.values()) {
            if (us.getEmail().equals(email)) {
                user = us;
                break;
            }
        }
        return user;
    }

    /**
     * Метод для поиска вакансии по id.
     *
     * @param id Object Post
     * @return Object Post or null if there was no mapping for key
     */
    public Post findById(int id) {
        return posts.get(id);
    }

    /**
     * Метод для поиска вакансии по id.
     *
     * @param id Object Post
     * @return Object Post or null if there was no mapping for key
     */
    public Candidate findByIdCandidate(int id) {
        return candidates.get(id);
    }

    public static void main(String[] args) {

        var store1 = PsqlStore.instOf();
        store1.saveCandidate(new Candidate(1, "Petr Arsentev"));
        store1.saveCandidate(new Candidate(2, "Petr Arsentev"));
        store1.saveCandidate(new Candidate(3, "Petr Arsentev"));
        store1.saveCandidate(new Candidate(1, "Иван Иванов"));
        for (Candidate candidate : store1.findAllCandidates()) {
            System.out.println(candidate.toString());
        }
    }
}
