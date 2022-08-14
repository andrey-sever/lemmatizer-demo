import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;

import java.io.IOException;
import java.util.*;

public class LemmaBuilder {

    private static final String[] partsSpeechExceptions = {"МЕЖД", "ПРЕДЛ", "СОЮЗ", "ЧАСТ"};
    private static LuceneMorphology luceneMorphology;
    private static Map<String, Integer> listWordRepetitions;
    private static HashSet<String> listWordUnique;

    public static Map<String, Integer> getListLemma(String text) {
        listWordRepetitions = new HashMap<>();
        text = textCorrection(text);

        if (text.isEmpty()) return listWordRepetitions;

        try {
            luceneMorphology = new RussianLuceneMorphology();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] temp = text.split(" ");
        for (String word : temp) {
            word = word.toLowerCase();
            if (!wordIsValid(word)) continue;

            List<String> listLemma = luceneMorphology.getNormalForms(word);
            setWordToMap(listLemma, word);
        }

        return listWordRepetitions;
    }

    private static String textCorrection(String text) {
        String textCorr = text.replaceAll("[-]", " ");
        textCorr = textCorr.replaceAll("[^а-я^А-Я^ ]", ""); //Пока без английского/
        textCorr = textCorr.replaceAll("[\\s]+", " ");
        return  textCorr;
    }

    private static Boolean wordIsValid(String word) {
        String wordExceptions = luceneMorphology.getMorphInfo(word).get(0);
        for (String part : partsSpeechExceptions) {
            if (wordExceptions.contains(part)) return false;
        }
        return true;
    }

    private static void setWordToMap(List<String> listLemma, String word) {
        String wordLemma;

        if (listLemma.size() == 1) {
            wordLemma = listLemma.get(0);
        } else {
            if (listLemma.contains(word)) {
                wordLemma = word;
            } else {
                return;
            }
        }

        if (listWordRepetitions.containsKey(wordLemma)) {
            listWordRepetitions.put(wordLemma, listWordRepetitions.get(wordLemma) + 1);
        } else {
            listWordRepetitions.put(wordLemma, 1);
        }
    }
}
