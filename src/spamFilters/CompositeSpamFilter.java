package spamFilters;

import mail.Message;

import java.util.ArrayList;

public class CompositeSpamFilter implements SpamFilter {
    private final ArrayList<SpamFilter> spamFilters;

    public CompositeSpamFilter(ArrayList<SpamFilter> filters) {
        spamFilters = filters;
    }

    public boolean isSpam(Message message) {
        for (SpamFilter filter: spamFilters) {
            if (filter.isSpam(message)) {
                return true;
            }
        }
        return false;
    }
}
