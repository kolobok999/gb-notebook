package notebook.view;

import notebook.controller.UserController;
import notebook.model.User;
import notebook.util.Commands;

import java.util.Scanner;

public class UserView {
    private final UserController userController;

    public UserView(UserController userController) {
        this.userController = userController;
    }

    public void run(){
        Commands com;

        while (true) {
            String command = prompt("Введите команду: ");

            boolean isCommand = true;
            try {
                com = Commands.valueOf(command.toUpperCase()); // Добавил ввод любыми
            }catch (IllegalArgumentException ex) {
                isCommand = false;
                System.out.println("Такой команды не существует");
                com = null;
            }
            if (isCommand) {
                if (com == Commands.EXIT) return;
                switch (com) {
                    case CREATE -> {
                        User u = createUser();
                        userController.saveUser(u);
                    }
                    case READ -> {
                        String id = prompt("Идентификатор пользователя: ");
                        try {
                            User user = userController.readUser(Long.parseLong(id));
                            System.out.println(user);
                            System.out.println();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                    case SHOW_LIST -> System.out.println(userController.readAll());
                    case UPDATE -> {
                        String userId = prompt("Enter user id: ");
                        userController.updateUser(userId, createUser());
                    }
                    case DELETE -> {
                        String id = prompt("Enter user id: ");
                        userController.deleteUser(id);
                    }
                }
            }
        }
    }

    private String prompt(String message) {
        Scanner in = new Scanner(System.in);
        System.out.print(message);
        return in.nextLine();
    }

    private User createUser() {
        String firstName = prompt("Имя: ");
        String lastName = prompt("Фамилия: ");
        String phone = prompt("Номер телефона: ");
        return new User(firstName, lastName, phone);
    }
}
