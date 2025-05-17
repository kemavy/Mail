package mail;

public class Message {
    private final String caption;
    private final String text;
    private final User sender;
    private final User receiver;

    public Message(String mes_caption, String mes_text, User mes_sender, User mes_receiver) {
        caption = mes_caption;
        text = mes_text;
        sender = mes_sender;
        receiver = mes_receiver;
    }

    public String getCaption() {
        return caption;
    }

    public String getText() {
        return text;
    }

    public String getSender() {
        return sender.getName();
    }

    public String getReceiver() {
        return receiver.getName();
    }
}
