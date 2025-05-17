package mail;

import spamFilters.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static UserStorage users = new UserStorage();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String result;
        System.out.println("Добро пожаловать в почту (для завершения работы введите пустую строку)!");
        System.out.print("Введите команду: ");
        String command = scanner.nextLine();
        while (!command.isEmpty()) {
            if (command.equals("add")) {
                System.out.print("Введите имя пользователя: ");
                String name = scanner.nextLine();
                result = add(name);
            } else if (command.equals("list")) {
                result = list();
            } else if (command.equals("send")) {
                result = send(scanner);
            } else if (command.equals("inbox")) {
                System.out.print("Введите имя пользователя: ");
                String userName = scanner.nextLine();
                result = inbox(userName);
            } else if (command.equals("outbox")) {
                System.out.print("Введите имя пользователя: ");
                String userName = scanner.nextLine();
                result = outbox(userName);
            } else if (command.equals("spam")) {
                System.out.print("Введите имя пользователя: ");
                String userName = scanner.nextLine();
                result = spam(userName);
            } else if (command.equals("setfilter")) {
                result = setFilter(scanner);
            }
            else {
                result = "Команды '" + command + "' не существует";
            }
            if (!result.isEmpty()) {
                System.out.println(result);
            }
            System.out.print("Введите команду: ");
            command = scanner.nextLine();
        }
        System.out.println("Программа завершила работу");
    }

    public static String add(String name) {
        if (users.isExist(name)) {
            return "Пользователь с именем '" + name + "' уже существует";
        }
        User user = new User(name);
        return users.addUser(user);
    }

    public static String list() {
        if (users.isEmpty()) {
            return "Ни одного пользователя не добавлено";
        }
        return users.getList();
    }

    public static String send(Scanner scanner) {
        System.out.print("Введите имя отправителя: ");
        String senderName = scanner.nextLine();
        if (!users.isExist(senderName)) {
            return "Пользователя '" + senderName + "' не существует";
        }
        User sender = users.getUser(senderName);

        System.out.print("Введите имя получателя: ");
        String receiverName = scanner.nextLine();
        if (!users.isExist(receiverName)) {
            return "Пользователя '" + receiverName + "' не существует";
        }
        User receiver = users.getUser(receiverName);

        System.out.println("Введите заголовок сообщения:");
        String caption = scanner.nextLine();

        System.out.println("Введите текст сообщения:");
        String text = scanner.nextLine();
        return sender.sendMessage(caption, text, receiver);
    }

    public static String inbox(String userName) {
        if (!users.isExist(userName)) {
            return "Пользователя '" + userName + "' не существует";
        }
        User user = users.getUser(userName);
        return user.getInbox();
    }

    public static String outbox(String userName) {
        if (!users.isExist(userName)) {
            return "Пользователя '" + userName + "' не существует";
        }
        User user = users.getUser(userName);
        return user.getOutbox();
    }

    public static String spam(String userName) {
        if (!users.isExist(userName)) {
            return "Пользователя '" + userName + "' не существует";
        }
        User user = users.getUser(userName);
        return user.getSpam();
    }

    public static String setFilter(Scanner scanner) {
        System.out.print("Введите имя пользователя: ");
        String userName = scanner.nextLine();
        if (!users.isExist(userName)) {
            return "Пользователя '" + userName + "' не существует";
        }
        User user = users.getUser(userName);

        ArrayList<SpamFilter> filters = new ArrayList<>();
        System.out.print("Введите название фильтра (для завершения напишите 'done'): ");
        String filter = scanner.nextLine();
        while (!filter.equals("done")) {
            if (!"simple|keywords|repetition|sender".contains(filter) |filter.isEmpty()) {
                System.out.println("Спам-фильтра '" + filter + "' не существует");
            }
            else {
                if (filter.equals("simple")) {
                    filters.add(new SimpleSpamFilter());
                }
                if (filter.equals("keywords")) {
                    System.out.print("Введите ключевые слова (через пробел): ");
                    String keywordsString = scanner.nextLine();
                    ArrayList<String> keywords = new ArrayList<>(Arrays.asList(keywordsString.split("[^a-zA-Z" +
                            "йцукенгшщзхъфывапролджэячсмитьбюёЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮЁ0-9]+")));
                    if (keywords.isEmpty() | keywords.contains("")) {
                        System.out.println("Не обнаружено ни одного ключевого слова");
                        continue;
                    }
                    filters.add(new KeywordsSpamFilter(keywords));
                }
                if (filter.equals("repetition")) {
                    System.out.print("Введите максимальное число повторений одного слова: ");
                    String repetition = scanner.nextLine();
                    if (!isDigital(repetition) | repetition.contains("-")) {
                        System.out.println("Должно быть введено целое положительное десятичное число");
                        continue;
                    }
                    try {
                        Integer number = Integer.parseInt(repetition);
                        filters.add(new RepetitionSpamFilter(number));
                    } catch (NumberFormatException e) {
                        System.out.println("Должно быть введено десятичное число, не превосходящее 2*10^9");
                        continue;
                    }
                }
                if (filter.equals("sender")) {
                    System.out.print("Введите имя пользователя, чьи сообщения будут попадать в спам: ");
                    userName = scanner.nextLine();
                    if (!users.isExist(userName)) {
                        System.out.println("Пользователя '" + userName + "' не существует");
                    } else {
                        filters.add(new SenderSpamFilter(userName));
                    }
                }
            }
            System.out.print("Введите название фильтра (для завершения напишите 'done'): ");
            filter = scanner.nextLine();
        }
        if (filters.isEmpty()) {
            return "Ни одного спам-фильтра не добавлено";
        }
        return user.setSpamFilter(filters);
    }

    public static Boolean isDigital(String str) {
        boolean check = Boolean.TRUE;
        for (char symbol: str.toCharArray()) {
            if (!(Character.isDigit(symbol))) {
                check = Boolean.FALSE;
                break;
            }
        }
        return check;
    }
}


