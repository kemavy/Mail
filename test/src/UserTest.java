package mail;

import org.junit.jupiter.api.Test;
import spamFilters.CompositeSpamFilter;
import spamFilters.SenderSpamFilter;
import spamFilters.SimpleSpamFilter;
import spamFilters.SpamFilter;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    public void testGetName() {
        User user = new User("Mary");
        assertEquals("Mary", user.getName());
    }

    @Test
    public void testGetEmptyInbox() {
        User user = new User("Mary");
        assertEquals("Сообщений не обнаружено", user.getInbox());
    }

    @Test
    public void testGetInbox() {
        User sender = new User("Mary");
        User receiver = new User("Mike");
        sender.sendMessage("caption", "text", receiver);
        assertEquals("""
                =======================================================
                caption
                text
                =======================================================""", receiver.getInbox());
    }

    @Test
    public void testGetEmptyOutbox() {
        User user = new User("Mary");
        assertEquals("Сообщений не обнаружено", user.getOutbox());
    }

    @Test
    public void testGetOutbox() {
        User sender = new User("Mary");
        User receiver = new User("Mike");
        sender.sendMessage("caption", "text", receiver);
        assertEquals("""
                =======================================================
                caption
                text
                =======================================================""", sender.getOutbox());
    }

    @Test
    public void testGetEmptySpam() {
        User user = new User("Mary");
        assertEquals("Сообщений не обнаружено", user.getSpam());
    }

    @Test
    public void testGetSpam() {
        User sender = new User("Mary");
        User receiver = new User("Mike");
        ArrayList<SpamFilter> filter = new ArrayList<>();
        filter.add(new SimpleSpamFilter());
        receiver.setSpamFilter(filter);
        sender.sendMessage("caption", "spam", receiver);
        assertEquals("""
                =======================================================
                caption
                spam
                =======================================================""", receiver.getSpam());
    }

    @Test
    public void testSetSpamFilter() {
        User user = new User("Mary");
        ArrayList<SpamFilter> filter = new ArrayList<>();
        filter.add(new SimpleSpamFilter());
        assertEquals("Спам-фильтр успешно установлен", user.setSpamFilter(filter));
    }

    @Test
    public void testSetSpamFilters() {
        User user = new User("Mary");
        ArrayList<SpamFilter> filter = new ArrayList<>();
        filter.add(new SimpleSpamFilter());
        filter.add(new SenderSpamFilter("Mike"));
        assertEquals("Спам-фильтры успешно установлены", user.setSpamFilter(filter));
    }
}