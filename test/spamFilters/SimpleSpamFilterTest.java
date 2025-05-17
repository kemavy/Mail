package spamFilters;

import mail.Message;
import mail.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleSpamFilterTest {

    @Test
    public void testTextIsSpam() {
        User sender = new User("Mary");
        User receiver = new User("Mike");
        Message message = new Message("caption", "spam", sender, receiver);

        assertTrue(new SimpleSpamFilter().isSpam(message));
    }

    @Test
    public void testCaptionIsSpam() {
        User sender = new User("Mary");
        User receiver = new User("Mike");
        Message message = new Message("spam", "text", sender, receiver);

        assertTrue(new SimpleSpamFilter().isSpam(message));
    }

    @Test
    public void testNotSpam() {
        User sender = new User("Mary");
        User receiver = new User("Mike");
        Message message = new Message("caption", "text", sender, receiver);

        assertFalse(new SimpleSpamFilter().isSpam(message));
    }
}