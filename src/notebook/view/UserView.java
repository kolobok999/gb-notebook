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
                if (com == Commands.EXIT) {
                    userController.save();
                    return;
                }
                switch (com) {
                    case CREATE -> {
                        userController.createUser(prompt("Имя: "), prompt("Фамилия: "), prompt("Номер телефона: "));
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
                        userController.updateUser(userId, prompt("Имя: "), prompt("Фамилия: "), prompt("Номер телефона: "));
                    }
                    case DELETE -> {
                        String id = prompt("Enter user id: ");
                        userController.deleteUser(id);
                    }
                    case SAVE -> userController.save();
                }
            }
        }
    }

    private String prompt(String message) {
        Scanner in = new Scanner(System.in);
        System.out.print(message);
        return in.nextLine();
    }
}
