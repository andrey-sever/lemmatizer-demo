import java.util.Map;

public class LemmaDemo {

    public static void main(String[] args) {

        String text = "православные церковные праздники";

        Map<String, Integer> listWord = LemmaBuilder.getListLemma(text);
        for (Map.Entry<String, Integer> entry : listWord.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }
}
