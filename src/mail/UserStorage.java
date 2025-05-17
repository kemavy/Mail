package mail;

import java.util.ArrayList;

public class UserStorage {
    private final ArrayList<User> users;

    public UserStorage() {
        users = new ArrayList<>();
    }

    public String addUser(User user) {
        users.add(user);
        return "Пользователь '" + user.getName() + "' добавлен";
    }

    public User getUser(String name) {
        for (User user: users) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }

    public boolean isExist(String name) {
        boolean exist = false;
        for (User user: users) {
            if (user.getName().equals(name)) {
                exist = true;
                break;
            }
        }
        return exist;
    }

    public boolean isEmpty() {
        return users.isEmpty();
    }

    public String getList() {
        StringBuilder list = new StringBuilder();
        for (User user: users) {
            list.append(user.getName());
            list.append(", ");
        }
        return list.substring(0, list.length() - 2);
    }

}
