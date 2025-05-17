package mail;

import org.junit.jupiter.api.Test;
import spamFilters.SimpleSpamFilter;
import spamFilters.SpamFilter;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;


public class MainTest {

    @Test
    public void testEmptyList() {
        Main.users = new UserStorage();
        assertEquals("Ни одного пользователя не добавлено", Main.list());
    }

    @Test
    public void testAdd() {
        Main.users = new UserStorage();
        Main.add("Mary");
        assertTrue(Main.users.isExist("Mary"));
    }

    @Test
    public void testAddExistedUser() {
        Main.users = new UserStorage();
        Main.add("Alex");
        assertEquals("Пользователь с именем 'Alex' уже существует", Main.add("Alex"));
    }

    @Test
    public void testList() {
        Main.users = new UserStorage();
        Main.add("Polly");
        assertEquals("Polly", Main.list());
    }

    @Test
    public void testInboxUnexistent() {
        Main.users = new UserStorage();
       assertEquals("Пользователя 'A' не существует", Main.inbox("A"));
    }

    @Test
    public void testInbox() {
        Main.users = new UserStorage();
        Main.add("V");
        Main.users.getUser("V").sendMessage("caption", "text", Main.users.getUser("V"));
        assertEquals("=======================================================\ncaption\ntext\n===============" +
                "========================================", Main.inbox("V"));
    }

    @Test
    public void testOutboxUnexistent() {
        Main.users = new UserStorage();
        assertEquals("Пользователя 'Mike' не существует", Main.outbox("Mike"));
    }

    @Test
    public void testOutbox() {
        Main.users = new UserStorage();
        Main.add("C");
        Main.users.getUser("C").sendMessage("caption", "text", Main.users.getUser("C"));
        assertEquals("=======================================================\ncaption\ntext\n===============" +
                "========================================", Main.outbox("C"));
    }

    @Test
    public void testSpamUnexistent() {
        Main.users = new UserStorage();
        assertEquals("Пользователя 'Denis' не существует", Main.spam("Denis"));
    }

    @Test
    public void testSpam() {
        Main.users = new UserStorage();
        Main.add("B");
        ArrayList<SpamFilter> filters = new ArrayList<>();
        filters.add(new SimpleSpamFilter());
        Main.users.getUser("B").setSpamFilter(filters);
        Main.users.getUser("B").sendMessage("caption", "spam", Main.users.getUser("B"));
        assertEquals("""
                =======================================================
                caption
                spam
                ===============\
                ========================================""", Main.spam("B"));
    }

    @Test
    public void testSendUnexistedSender() {
        InputStream sysInBackup = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream("Mary1\n".getBytes());
        System.setIn(in);

        String result = Main.send(new Scanner(System.in));
        assertEquals("Пользователя 'Mary1' не существует", result);

        System.setIn(sysInBackup);
    }

    @Test
    public void testSendUnexistedReceiver() {
        Main.users = new UserStorage();
        Main.add("J");
        InputStream sysInBackup = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream("J\nL".getBytes());
        System.setIn(in);

        String result = Main.send(new Scanner(System.in));
        assertEquals("Пользователя 'L' не существует", result);

        System.setIn(sysInBackup);
    }

    @Test
    public void testSend() {
        Main.users = new UserStorage();
        Main.add("K");
        Main.add("Q");
        InputStream sysInBackup = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream("K\nQ\ncaption\ntext".getBytes());
        System.setIn(in);

        String result = Main.send(new Scanner(System.in));
        assertEquals("""
                =======================================================
                caption
                text
                =========\
                ==============================================""", Main.users.getUser("K").getOutbox());

        System.setIn(sysInBackup);
    }

    @Test
    public void testSetFilterUnexistentUser() {
        InputStream sysInBackup = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream("Mary2\n".getBytes());
        System.setIn(in);

        String result = Main.setFilter(new Scanner(System.in));
        assertEquals("Пользователя 'Mary2' не существует", result);

        System.setIn(sysInBackup);
    }

    @Test
    public void testSetUnexistentFilter() {
        Main.users = new UserStorage();
        Main.add("K9");
        InputStream sysInBackup = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream("K9\njava\ndone".getBytes());
        System.setIn(in);

        String result = Main.setFilter(new Scanner(System.in));
        assertEquals("Ни одного спам-фильтра не добавлено", result);

        System.setIn(sysInBackup);
    }

    @Test
    public void testSetFilterSimple() {
        Main.users = new UserStorage();
        Main.add("K10");
        InputStream sysInBackup = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream("K10\nsimple\ndone".getBytes());
        System.setIn(in);

        String result = Main.setFilter(new Scanner(System.in));
        Main.users.getUser("K10").sendMessage("caption", "spam", Main.users.getUser("K10"));
        assertEquals("""
                =======================================================
                caption
                spam
                =======================================================""", Main.users.getUser("K10").getSpam());

        System.setIn(sysInBackup);
    }

    @Test
    public void testSetFilterKeywords() {
        Main.users = new UserStorage();
        Main.add("K10");
        InputStream sysInBackup = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream("K10\nkeywords\nhi\ndone".getBytes());
        System.setIn(in);

        String result = Main.setFilter(new Scanner(System.in));
        Main.users.getUser("K10").sendMessage("caption", "hi", Main.users.getUser("K10"));
        assertEquals("""
                =======================================================
                caption
                hi
                =======================================================""", Main.users.getUser("K10").getSpam());

        System.setIn(sysInBackup);
    }

    @Test
    public void testSetFilterRepetition() {
        Main.users = new UserStorage();
        Main.add("K10");
        InputStream sysInBackup = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream("K10\nrepetition\n2\ndone".getBytes());
        System.setIn(in);

        String result = Main.setFilter(new Scanner(System.in));
        Main.users.getUser("K10").sendMessage("caption", "hi hi hi", Main.users.getUser("K10"));
        assertEquals("""
                =======================================================
                caption
                hi hi hi
                =======================================================""", Main.users.getUser("K10").getSpam());

        System.setIn(sysInBackup);
    }

    @Test
    public void testSetFilterSender() {
        Main.users = new UserStorage();
        Main.add("K10");
        InputStream sysInBackup = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream("K10\nsender\nK10\ndone".getBytes());
        System.setIn(in);

        String result = Main.setFilter(new Scanner(System.in));
        Main.users.getUser("K10").sendMessage("caption", "hi hi hi", Main.users.getUser("K10"));
        assertEquals("""
                =======================================================
                caption
                hi hi hi
                =======================================================""", Main.users.getUser("K10").getSpam());

        System.setIn(sysInBackup);
    }

    @Test
    public void testIsDigitalIfDigital() {
        assertTrue(Main.isDigital("52"));
    }

    @Test
    public void testIsDigitalIfNot() {
        assertFalse(Main.isDigital("spb"));
    }

}