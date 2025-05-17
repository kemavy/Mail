package mail;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {

    @Test
    public void testGetCaption() {
        User sender = new User("Mary");
        User receiver = new User("Mike");
        Message message = new Message("caption", "text", sender, receiver);
        assertEquals("caption", message.getCaption());
    }

    @Test
    public void testGetText() {
        User sender = new User("Mary");
        User receiver = new User("Mike");
        Message message = new Message("caption", "text", sender, receiver);
        assertEquals("text", message.getText());
    }

    @Test
    public void testGetSender() {
        User sender = new User("Mary");
        User receiver = new User("Mike");
        Message message = new Message("caption", "text", sender, receiver);
        assertEquals("Mary", message.getSender());
    }

    @Test
    public void testGetReceiver() {
        User sender = new User("Mary");
        User receiver = new User("Mike");
        Message message = new Message("caption", "text", sender, receiver);
        assertEquals("Mike", message.getReceiver());
    }
}