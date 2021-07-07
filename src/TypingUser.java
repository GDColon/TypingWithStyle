import java.util.ArrayList;
import java.util.HashMap;

public class TypingUser {

    private HashMap <String, ArrayList <Integer>> scores;

    public TypingUser() {
        this.scores = new HashMap<>();
        this.scores.put("short", new ArrayList<>());
        this.scores.put("medium", new ArrayList<>());
        this.scores.put("long", new ArrayList<>());
    }

    public ArrayList<Integer> getScores(String type) {
        if (type == "all") {
            ArrayList<Integer> combinedScores = new ArrayList<Integer>();
            combinedScores.addAll(this.scores.get("short"));
            combinedScores.addAll(this.scores.get("medium"));
            combinedScores.addAll(this.scores.get("long"));
            return combinedScores;
        }
        else return this.scores.get(type);
    };

    public double getAverageWPM(String type) {
        ArrayList<Integer> scoreList = this.getScores(type);
        if (scoreList.size() <= 0) return 0;
        double avg = 0;
        for (int i=0; i<scoreList.size(); i++) avg += scoreList.get(i);
        return avg / scoreList.size();
    }

    public double getMedianWPM(String type) {
        ArrayList<Integer> scoreList = this.getScores(type);
        if (scoreList.size() <= 0) return 0;
        else if (scoreList.size() == 1) return scoreList.get(0);
        double halfSize = scoreList.size() / 2.0;
        if (halfSize % 1 != 0) {
            return scoreList.get((scoreList.size() - 1) / 2);
        }
        else {
            return (scoreList.get((int)halfSize) + scoreList.get((int)(halfSize - 1))) / 2.0;
        }
    }

    public void addScore(String type, int WPM) {
        this.getScores(type).add(Math.abs(WPM));
    }

    public String getTypeStats(String type) {
        ArrayList<Integer> scoreList = this.getScores(type);

        // use HTML since it supports line breaks
        String statsMsg = "<html>Completed: " + scoreList.size();
        statsMsg += "<br>Average WPM: " + String.format("%.1f", this.getAverageWPM(type));
        statsMsg += "<br>Median WPM: " + String.format("%.1f", this.getMedianWPM(type)) + "<br>";
        for (int i=scoreList.size(); i > Math.max(0, scoreList.size() - 7); i--) {
            statsMsg += "<br>#" + i + " - " + scoreList.get(i-1) + " WPM";
        }
        statsMsg += "</html>";
        return statsMsg;
    }
}
