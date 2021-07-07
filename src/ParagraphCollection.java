import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ParagraphCollection {

    private HashMap <String, ArrayList <String>> paragraphs;

    public ParagraphCollection() throws IOException {
        this.paragraphs = new HashMap<>();
        this.paragraphs.put("short", new ArrayList<String>());
        this.paragraphs.put("medium", new ArrayList<String>());
        this.paragraphs.put("long", new ArrayList<String>());

        FileReader fr = new FileReader("paragraphs.txt");
        BufferedReader reader = new BufferedReader(fr);

        String nextLine = "";
        do {
            nextLine = reader.readLine();
            if (nextLine != null && nextLine.length() > 1) {
                if (nextLine.length() <= 200) this.paragraphs.get("short").add(nextLine);
                else if (nextLine.length() <=  350) this.paragraphs.get("medium").add(nextLine);
                else this.paragraphs.get("long").add(nextLine);
            }
        } while (nextLine != null);

//        System.out.println("Short: " + this.paragraphs.get("short").size());
//        System.out.println("Medium: " + this.paragraphs.get("medium").size());
//        System.out.println("Long: " + this.paragraphs.get("long").size());
    }

    public String chooseParagraph(String diff) {
        ArrayList <String> paragraphList = this.paragraphs.get(diff);
        return paragraphList.get((int)Math.floor(Math.random() * paragraphList.size()));
    }
}
