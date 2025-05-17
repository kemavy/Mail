package spamFilters;

import mail.Message;
import mail.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CompositeSpamFilterTest {

    @Test
    public void testIsSpam() {
        User sender = new User("Mary");
        User receiver = new User("Mike");
        Message message = new Message("caption", "text", sender, receiver);
        ArrayList<SpamFilter> filters = new ArrayList<>();
        filters.add(new SimpleSpamFilter());
        filters.add(new SenderSpamFilter("Mary"));

        assertTrue(new CompositeSpamFilter(filters).isSpam(message));
    }

    @Test
    public void testNotSpam() {
        User sender = new User("Mary");
        User receiver = new User("Mike");
        Message message = new Message("caption", "text", sender, receiver);
        ArrayList<SpamFilter> filters = new ArrayList<>();
        filters.add(new SimpleSpamFilter());

        assertFalse(new CompositeSpamFilter(filters).isSpam(message));
    }

}