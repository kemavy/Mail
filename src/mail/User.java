package mail;

import spamFilters.*;

import java.util.ArrayList;

public class User {
    private final String userName;
    private final ArrayList<Message> inbox;
    private final ArrayList<Message> outbox;
    private final ArrayList<Message> spam;
    private SpamFilter spamFilter;

    public User(String name) {
        userName = name;
        inbox = new ArrayList<>();
        outbox = new ArrayList<>();
        spam = new ArrayList<>();
        spamFilter = new CompositeSpamFilter(new ArrayList<>());
    }

    public String getName(){
        return userName;
    }

    public String getInbox() {
        StringBuilder returnInbox = new StringBuilder();
        if (inbox.isEmpty()) {
            returnInbox = new StringBuilder("Сообщений не обнаружено");
        }
        else {
            returnInbox.append("=======================================================");
            for (Message message: inbox) {
                returnInbox.append("\n").append(message.getCaption());
                returnInbox.append("\n").append(message.getText());
                returnInbox.append("\n=======================================================");
            }
        }
        return returnInbox.toString();
    }

    public String getOutbox() {
        StringBuilder returnOutbox = new StringBuilder();
        if (outbox.isEmpty()) {
            returnOutbox = new StringBuilder("Сообщений не обнаружено");
        }
        else {
            returnOutbox.append("=======================================================");
            for (Message message: outbox) {
                returnOutbox.append("\n").append(message.getCaption());
                returnOutbox.append("\n").append(message.getText());
                returnOutbox.append("\n=======================================================");
            }
        }
        return returnOutbox.toString();
    }

    public String getSpam() {
        StringBuilder returnSpam = new StringBuilder();
        if (spam.isEmpty()) {
            returnSpam = new StringBuilder("Сообщений не обнаружено");
        }
        else {
            returnSpam.append("=======================================================");
            for (Message message: spam) {
                returnSpam.append("\n").append(message.getCaption());
                returnSpam.append("\n").append(message.getText());
                returnSpam.append("\n=======================================================");
            }
        }
        return returnSpam.toString();
    }

    public String setSpamFilter(ArrayList<SpamFilter> filters) {
        spamFilter = new CompositeSpamFilter(filters);
        if (filters.size() == 1) {
            return "Спам-фильтр успешно установлен";
        }
        return "Спам-фильтры успешно установлены";
    }

    public String sendMessage(String caption, String text, User receiver) {
        Message message = new Message(caption, text, this, receiver);
        outbox.add(message);
        if (receiver.spamFilter.isSpam(message)) {
            receiver.spam.add(message);
        }
        else {
            receiver.inbox.add(message);
        }
        return "Сообщение '" + caption + "' успешно отправлено";
    }
}
