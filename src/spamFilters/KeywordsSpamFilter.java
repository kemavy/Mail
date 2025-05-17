package spamFilters;

import mail.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class KeywordsSpamFilter implements SpamFilter {
    private final ArrayList<String> keywords;

    public KeywordsSpamFilter(ArrayList<String> words) {
        keywords = words;
    }

    public boolean isSpam(Message message) {
        String caption = message.getCaption().toLowerCase(Locale.ROOT);
        String text = message.getText().toLowerCase(Locale.ROOT);
        String[] splitCaption =
                caption.split("[^a-zA-ZйцукенгшщзхъфывапролджэячсмитьбюёЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮЁ0-9]+");
        String[] splitText =
                text.split("[^a-zA-ZйцукенгшщзхъфывапролджэячсмитьбюёЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮЁ0-9]+");
        for (String keyword: keywords) {
            if (Arrays.asList(splitCaption).contains(keyword.toLowerCase(Locale.ROOT))
                    | Arrays.asList(splitText).contains(keyword.toLowerCase(Locale.ROOT))) {
                return true;
            }
        }
        return false;
    }
}
