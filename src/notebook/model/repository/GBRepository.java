package notebook.model.repository;

import notebook.model.User;

import java.util.List;
import java.util.Optional;

public interface GBRepository {
    List<User> findAll();

    List<User> getUsers();

    List<String> readAll();

    void save();

    User create(User user);

    Optional<User> findById(Long id);

    Optional<User> update(Long userId, User user);

    boolean delete(Long id);
}
