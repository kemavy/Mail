package spamFilters;

import mail.Message;

public class SenderSpamFilter implements SpamFilter {
    private final String senderName;

    public SenderSpamFilter(String name) {
        senderName = name;
    }

    public boolean isSpam(Message message) {
        return message.getSender().equals(senderName);
    }
}