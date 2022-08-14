import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        String str = "леса";

        LuceneMorphology luceneMorphology = new RussianLuceneMorphology();
        List<String> wordBaseWorms = luceneMorphology.getNormalForms(str);
        String wordExceptions = luceneMorphology.getMorphInfo(str).get(0);
        System.out.println("Лемм");
        wordBaseWorms.forEach(System.out::println);
        System.out.println("Части речи");
        System.out.println(wordExceptions);
    }
}
