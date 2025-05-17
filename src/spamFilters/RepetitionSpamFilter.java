package spamFilters;

import mail.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;

public class RepetitionSpamFilter implements SpamFilter {
    private final Integer count;

    public RepetitionSpamFilter(Integer repetition_count) {
        count = repetition_count;
        // добавить вывод если введено слишком большое число
    }

    public boolean isSpam(Message message) {
        String text = message.getText().toLowerCase(Locale.ROOT);
        HashMap<String, Integer> countValues = new HashMap<>();
        for (String word: text.split("[^a-zA-Zйцукенгшщзхъ" +
                "фывапролджэячсмитьбюёЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮЁ0-9]+")) {
            if (countValues.containsKey(word)) {
                countValues.compute(word, (k, c) -> c + 1);
            } else {
                countValues.put(word, 1);
            }
        }
        Integer ma = Collections.max(countValues.values());
        return ma > count;
    }
}
