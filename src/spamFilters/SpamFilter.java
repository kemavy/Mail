package spamFilters;

import mail.Message;

public interface SpamFilter {
    boolean isSpam(Message message);
}
