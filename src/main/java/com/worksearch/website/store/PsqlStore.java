package com.worksearch.website.store;

import com.worksearch.website.model.Post;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.worksearch.website.model.Candidate;
import com.worksearch.website.Store;
import com.worksearch.website.model.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

/**
 * Замена MemStore на PsqlStore
 * @since 13.10.21
 */
public class PsqlStore implements Store {
    final static Logger LOGGER =
            LogManager.getLogger(PsqlStore.class.getName());
    private final BasicDataSource pool = new BasicDataSource();

    private PsqlStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new FileReader("db.properties")
        )) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    private static final class Lazy {
        private static final Store INST = new PsqlStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    /**
     * Find all Post Object in table(PostgreSQL) post
     *
     * @return all present Post Object in table post view Collection<Post>
     */
    @Override
    public Collection<Post> findAllPosts() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM post join city as t1 on city_id = t1.id")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(new Post(it.getInt("id"),
                            it.getString("name"),
                            it.getString("description"),
                            it.getString(7),
                            convert(it.getTimestamp("created").toLocalDateTime()))
                    );
                }
            }
        } catch (SQLException e) {
            LOGGER.error("findAllPosts() ERROR. Unable to SQL query", e);
        }
        return posts;
    }

    /**
     * Find all Candidate Object in table(PostgreSQL) candidate
     *
     * @return all present Candidate in table candidate view Collection<Candidate>
     */
    @Override
    public Collection<Candidate> findAllCandidates() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM candidate join city as t1 on city_id = t1.id")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(
                            new Candidate(
                                    it.getInt("id"), it.getString("name"),
                                    it.getString("position"),
                                    it.getString(7),
                                    convert(it.getTimestamp("created").toLocalDateTime())
                            ));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("findAllCandidates() ERROR. Unable to SQL query", e);
        }
        return candidates;
    }

    /**
     * Saves the Post Object in the table post (PostgreSQL),
     * or updates the table post if it present
     *
     * @param post Object Post
     */
    @Override
    public void save(Post post) {
        if (post.getId() == 0) {
            create(post);
        } else {
            update(post);
        }
    }

    /**
     * Saves the Candidate Object in the table candidate (PostgreSQL),
     * or updates the table post if it present
     *
     * @param candidate Object Candidate
     */
    @Override
    public void saveCandidate(Candidate candidate) {
        if (candidate.getId() == 0) {
            createCandidate(candidate);
        } else {
            updateCandidate(candidate);
        }
    }

    @Override
    public Candidate deleteCandidateId(int id) {
        Candidate candidate = findByIdCandidate(id);
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "DELETE FROM candidate name WHERE id = (?)")
        ) {
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException e) {
            LOGGER.error("remove(int id) ERROR. Unable to SQL query", e);
        }
        return candidate;
    }

    private User createUser(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO users(name, email, password) VALUES (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("createUser(User user) ERROR. Unable to SQL query", e);
        }
        return user;
    }

    private void updateUser(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "UPDATE users SET name = (?), email = (?), password = (?) WHERE id = (?)")
        ) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getName());
            ps.setString(3, user.getName());
            ps.setInt(4, user.getId());
            ps.execute();
        } catch (SQLException e) {
            LOGGER.error("updateUser(User user) ERROR. Unable to SQL query", e);
        }
    }

    /**
     * Создание вакансии. Здесь выполняется обычный sql запрос.
     *
     * @param post Object
     * @return Post Object
     */
    private Post create(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO post(name, description, created, city_id ) VALUES (?, ?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(4, cityIdInst(post.getCityId()));
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("create(Post post) ERROR. Unable to SQL query", e);
        }
        return post;
    }

    private int cityIdInst(String string) {
        int rsl = 0;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO city(name) VALUES (?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, string);
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    rsl = id.getInt(1);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("create(Post post) ERROR. Unable to SQL query", e);
        }
        return rsl;
    }

    /**
     * Create new Object Candidate in the table candidate
     *
     * @param candidate Object
     * @return Candidate from table candidate
     */
    private Candidate createCandidate(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO candidate(name, position, created, city_id) VALUES (?, ?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getPosition());
            ps.setTimestamp(3, Timestamp.valueOf(candidate.getDateTime()));
            ps.setInt(4, cityIdInst(candidate.getCityId()));
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("createCandidate(Candidate candidate) ERROR. Unable to SQL query", e);
        }
        return candidate;
    }

    /**
     * Does update value write Post Object in the table post
     *
     * @param post Object
     */
    private void update(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "UPDATE post SET name = (?), description = (?), city_id = (?)  WHERE id = (?)")
        ) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setInt(3, cityIdInst(post.getCityId()));
            ps.setInt(4, post.getId());
            ps.execute();
        } catch (SQLException e) {
            LOGGER.error("update(Post post) ERROR. Unable to SQL query", e);
        }
    }

    /**
     * Does update value write Candidate Object in the table candidate
     *
     * @param candidate Object
     */
    private void updateCandidate(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "UPDATE candidate SET name = (?), position = (?), city_id = (?) WHERE id = (?)")
        ) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getPosition());
            ps.setInt(3, cityIdInst(candidate.getCityId()));
            ps.setInt(4, candidate.getId());
            ps.execute();
        } catch (SQLException e) {
            LOGGER.error("updateCandidate(Candidate candidate) ERROR. Unable to SQL query", e);
        }
    }

    /**
     * Implements find Object Post in table (PostgresSQL) from value id
     * Replace Post post = new Post(0, "") by Optional.of(new Post(0, ""));
     * or  Optional<Post> post = Optional.empty();
     * Replace e.printStackTrace(); by LOGGER.fatal("Unable to SQL query.", e);
     *
     * @param id value Post object
     * @return Post Object
     * SELECT  t1.name, t1.position, t2.name, t1.created"
     * + " from candidate as t1"
     * + " join city as t2 on t2.id = t1.city_id"
     * + " where t1.id = (?)")
     * "SELECT * FROM post WHERE id = (?)")
     */
    @Override
    public Post findById(int id) {
        Post post = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT  t1.name, t1.description, t2.name, t1.created"
                             + " from post as t1 join city as t2 on t2.id = t1.city_id"
                             + " where t1.id = (?)")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    post = new Post(it.getInt("id"),
                            it.getString("name"),
                            it.getString("description"),
                            convert(it.getTimestamp("created").toLocalDateTime()));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("findById(int id) ERROR. Unable to SQL query", e);
        }
        return post;
    }

    /**
     * Implements find Object Post in table (PostgresSQL) from value id
     * Replace  Candidate candidate = new Candidate(0, ""); by
     * Optional<Candidate> candidate = Optional.empty();
     * Replace e.printStackTrace(); by LOGGER
     *
     * @param id value Candidate object
     * @return Candidate Object
     */
    @Override
    public Candidate findByIdCandidate(int id) {
        Candidate candidate = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT  t1.name, t1.position, t2.name, t1.created"
                             + " from candidate as t1"
                             + " join city as t2 on t2.id = t1.city_id"
                             + " where t1.id = (?)")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidate = new Candidate(
                            id, it.getString("name"),
                            it.getString("position"),
                            it.getString(3),
                            convert(it.getTimestamp("created").toLocalDateTime())
                    );
                    System.out.println("Что возвращается из БД : " + candidate.getName() + " " + candidate.getId());
                }
            }
        } catch (SQLException e) {
            /*e.printStackTrace();*/
            LOGGER.error("findByIdCandidate(int id) ERROR. Unable to SQL query", e);
        }
        return candidate;
    }

    /**
     * Метод производит сохрение пользователя в БД через вызов метода createUser(user);
     *
     * @param user Пользователь
     */
    @Override
    public void save(User user) {
        if (user.getId() == 0) {
            createUser(user);
        } else {
            updateUser(user);
        }
    }

    @Override
    public User findByEmail(String email) {
        User user = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM users WHERE email = (?)")
        ) {
            ps.setString(1, email);
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    user = new User(
                            it.getInt("id"), it.getString("name"),
                            it.getString("email"), it.getString("password")
                    );
                }
            }
        } catch (SQLException e) {
            LOGGER.error("findByEmail(String email) ERROR. Unable to SQL query", e);
        }
        return user;
    }

    /**
     * Метод выводит кандидатов за последние сутки из БД
     *
     * @return
     */
    @Override
    public Collection<Candidate> findByData() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT t1.id, t1.name, t1.position, t2.name, t1.created"
                             + " from candidate as t1 join city as t2 on t2.id = t1.city_id"
                             + " where created between CURRENT_TIMESTAMP - interval '1 day' and CURRENT_TIMESTAMP")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(new Candidate(
                            it.getInt("id"), it.getString("name"),
                            it.getString("position"), it.getString(4),
                            convert(it.getTimestamp("created").toLocalDateTime())));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("findByEmail(String email) ERROR. Unable to SQL query", e);
        }
        return candidates;
    }

    @Override
    public Collection<Post> findByDataPost() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT t1.id, t1.name, t1.description, t2.name, t1.created"
                             + " from post as t1 join city as t2 on t2.id = t1.city_id"
                             + " where created between CURRENT_TIMESTAMP - interval '1 day' and CURRENT_TIMESTAMP")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(new Post(
                            it.getInt("id"), it.getString("name"),
                            it.getString("description"), it.getString(4),
                            convert(it.getTimestamp("created").toLocalDateTime())));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("findByEmail(String email) ERROR. Unable to SQL query", e);
        }
        return posts;
    }

    /**
     * метод проводит конвертацию времени из LocalDateTime в String
     *
     * @param localDateTime
     * @return
     */
    private String convert(LocalDateTime localDateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy.");
        return localDateTime.format(dateTimeFormatter);
    }

    public static void main(String[] args) {
      /*  var f = PsqlStore.instOf().findAllCandidates();
        for (Candidate candidate : f) {
            System.out.println("CANDIDATE : " + candidate);
            /*System.out.println("КАНДИДАТ : " + candidate.getName() + " " + candidate.getId());*/
        /*  }*/
        var c = PsqlStore.instOf().findByIdCandidate(1);
        System.out.println("ЧТо нашли в БД : " + c.getName() + c.getId() + " " + c.getCityId());
       /* var r = PsqlStore.instOf().findById(1);
        System.out.println("Что возвращается из БД : " + r.getName() + " " + r.getId());*/
       /* for (Post post : PsqlStore.instOf().findAllPosts()) {
            System.out.println("Post " + post);
        }*/
        for (Candidate candidate : PsqlStore.instOf().findByData()) {
            System.out.println("candidate : " + candidate);
        }
    }
}
