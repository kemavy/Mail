package spamFilters;

import mail.Message;
import mail.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class KeywordsSpamFilterTest {

    @Test
    public void testIsSpam() {
        User sender = new User("Mary");
        User receiver = new User("Mike");
        Message message = new Message("caption", "keyword", sender, receiver);
        ArrayList<String> keywords = new ArrayList<>();
        keywords.add("keyword");

        assertTrue(new KeywordsSpamFilter(keywords).isSpam(message));
    }

    @Test
    public void testNotSpam() {
        User sender = new User("Mary");
        User receiver = new User("Mike");
        Message message = new Message("caption", "text", sender, receiver);
        ArrayList<String> keywords = new ArrayList<>();
        keywords.add("keyword");

        assertFalse(new KeywordsSpamFilter(keywords).isSpam(message));
    }

}