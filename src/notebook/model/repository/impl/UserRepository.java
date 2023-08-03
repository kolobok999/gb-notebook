package notebook.model.repository.impl;

import notebook.model.User;
import notebook.model.repository.GBRepository;
import notebook.util.mapper.impl.UserMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository implements GBRepository {
    private final UserMapper mapper;
    private final String fileName;
    private final List<User> users;

    public UserRepository(String fileName) {
        this.mapper = new UserMapper();
        this.fileName = fileName;
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        this.users = findAll();
    }

    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public List<User> findAll() {
        List<String> lines = this.readAll();
        List<User> users = new ArrayList<>();
        for (String line : lines) {
            users.add(mapper.toOutput(line));
        }
        return users;
    }

    @Override
    public User create(User user) {
        long max = 0L;
        for (User u : users) {
            long id = u.getId();
            if (max < id) {
                max = id;
            }
        }
        long next = max + 1;
        user.setId(next);
        users.add(user);
        return user;
    }

    @Override
    public Optional<User> findById(Long userId) {
        return users.stream()
                .filter(u -> u.getId()
                        .equals(userId))
                .findFirst();
    }

    @Override
    public Optional<User> update(Long userId, User update) {

//        На семинаре ничего не менялось, т. к. при вынесении поиска юзера, мы уже не работали с users в этом методе.
//        Поэтому я передаю список юзеров в метод findById
        User editUser = findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        editUser.setFirstName(update.getFirstName().equals("") ? editUser.getFirstName() : update.getFirstName());
        editUser.setLastName(update.getLastName().equals("") ? editUser.getLastName() : update.getLastName());
        editUser.setPhone(update.getPhone().equals("") ? editUser.getPhone() : update.getPhone());
        return Optional.of(update);
    }

    @Override
    public boolean delete(Long id) {
        users.remove(findById(id).orElseThrow());
        return true;
    }

    @Override
    public void save() {
        List<String> lines = new ArrayList<>();
        for (User u : users) {
            lines.add(mapper.toInput(u));
        }
        this.saveAll(lines);
    }


    @Override
    public List<String> readAll() {
        List<String> lines = new ArrayList<>();
        try {
            File file = new File(fileName);
            //создаем объект FileReader для объекта File
            FileReader fr = new FileReader(file);
            //создаем BufferedReader с существующего FileReader для построчного считывания
            BufferedReader reader = new BufferedReader(fr);
            // считаем сначала первую строку
            String line = reader.readLine();
            if (line != null) {
                lines.add(line);
            }
            while (line != null) {
                // считываем остальные строки в цикле
                line = reader.readLine();
                if (line != null) {
                    lines.add(line);
                }
            }
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    private void saveAll(List<String> data) {
        try (FileWriter writer = new FileWriter(fileName, false)) {
            for (String line : data) {
                // запись всей строки
                writer.write(line);
                // запись по символам
                writer.append('\n');
            }
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
