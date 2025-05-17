package spamFilters;

import mail.Message;
import mail.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SenderSpamFilterTest {

    @Test
    public void testIsSpam() {
        User sender = new User("Mary");
        User receiver = new User("Mike");
        Message message = new Message("caption", "text", sender, receiver);

        assertTrue(new SenderSpamFilter("Mary").isSpam(message));
    }

    @Test
    public void testNotSpam() {
        User sender = new User("Mary");
        User receiver = new User("Mike");
        Message message = new Message("caption", "text", sender, receiver);

        assertFalse(new SenderSpamFilter("Mike").isSpam(message));
    }

}