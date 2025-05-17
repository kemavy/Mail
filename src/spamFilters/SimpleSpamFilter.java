package spamFilters;

import mail.Message;

import java.util.Locale;

public class SimpleSpamFilter implements SpamFilter {
    public boolean isSpam(Message message) {
        String caption = message.getCaption().toLowerCase(Locale.ROOT);
        String text = message.getText().toLowerCase(Locale.ROOT);
        for (String word: caption.split("[^a-zA-ZйцукенгшщзхъфывапролджэячсмитьбюёЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬ" +
                "БЮЁ0-9]+")) {
            if (word.equals("spam")) {
                return true;
            }
        }
        for (String word: text.split("[^a-zA-ZйцукенгшщзхъфывапролджэячсмитьбюёЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМ" +
                "ИТЬБЮЁ0-9]+")) {
            if (word.equals("spam")) {
                return true;
            }
        }
        return false;
    }
}
