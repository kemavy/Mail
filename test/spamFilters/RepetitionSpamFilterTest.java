package spamFilters;

import mail.Message;
import mail.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RepetitionSpamFilterTest {

    @Test
    public void testIsSpam() {
        User sender = new User("Mary");
        User receiver = new User("Mike");
        Message message = new Message("caption", "text text text", sender, receiver);

        assertTrue(new RepetitionSpamFilter(2).isSpam(message));
    }

    @Test
    public void testNotSpam() {
        User sender = new User("Mary");
        User receiver = new User("Mike");
        Message message = new Message("caption", "text text text", sender, receiver);

        assertFalse(new RepetitionSpamFilter(3).isSpam(message));
    }

}