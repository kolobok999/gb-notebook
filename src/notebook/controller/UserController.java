package notebook.controller;

import notebook.model.User;
import notebook.model.repository.GBRepository;

import java.util.List;

public class UserController {
    private final GBRepository repository;

    public UserController(GBRepository repository) {
        this.repository = repository;
    }

    public void createUser(String firstName, String lastName, String phone) {
        repository.create(new User(firstName, lastName, phone));

    }

    public User readUser(Long userId) throws Exception {
        return repository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void updateUser(String userId, String firstName, String lastName, String phone) {
        repository.update(Long.parseLong(userId), new User(firstName, lastName, phone));
    }

    public void deleteUser(String userId) {
        repository.delete(Long.parseLong(userId));
    }
    public List<User> readAll() {
        return repository.getUsers();
    }

    public void save() {
        repository.save();
    }
}
