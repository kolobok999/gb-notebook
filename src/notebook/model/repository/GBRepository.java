package notebook.model.repository;

import notebook.model.User;

import java.util.List;
import java.util.Optional;

public interface GBRepository {
    List<User> findAll();
    List<String> readAll();
    void saveAll(List<String> data);

    User create(User user);

    Optional<User> findById(List<User> users, Long id);

    Optional<User> update(Long userId, User update);

    boolean delete(Long id);
}
