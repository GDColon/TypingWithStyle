public class Paragraph {
    private String message;
    private String type;
    int lettersTyped = 0;
    long startTime = 0;

    public Paragraph(String msg, String type) {
        // regex to remove any non-ascii characters I forgot to tak out (ensures keyboard typeable)
        this.message = msg.replaceAll("[^ -~|\\n]", "").trim();
        this.type = type;
    }

    public void trackLetter(int index) {
        if (lettersTyped < index+1) {
            lettersTyped++;
            if (lettersTyped == 1) startTime = System.currentTimeMillis();
        }
    }

    public Boolean compareText(String msg, int lenience) {
        return this.message.startsWith(msg.substring(0, Math.max(0, msg.length() - lenience)));
    };

    public String getMsg() {
        return this.message;
    }

    public String getType() {
        return this.type;
    }

    public int getWPM() {
        double WPM = 0;
        double wordsTyped = lettersTyped / 5.0;
        double elapsed = 1 + (System.currentTimeMillis() - startTime) / 1000.0;
        WPM = Math.round(wordsTyped / elapsed * 60.0);
        return (int)WPM;
    }

    public double getCompletion() {
        return (lettersTyped / (double)this.message.length()) * 100.0;
    }
}
