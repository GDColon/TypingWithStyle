import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.io.IOException;

public class Typing implements DocumentListener {

    String oldText = "";
    String windowName = "Typing With Style";

    JFrame frame = new JFrame(windowName);
    JLayeredPane main = new JLayeredPane();
    JLayeredPane title = new JLayeredPane();

    JTextArea textbox = new JTextArea();
    JTextArea input = new JTextArea();

    JLabel header = new JLabel();
    JLabel wpmCount = new JLabel();
    JLabel msgProgress = new JLabel("", SwingConstants.RIGHT);
    JLabel titleText = new JLabel(windowName, SwingConstants.CENTER);
    JLabel titleWPM = new JLabel("Press a button to start!", SwingConstants.CENTER);

    JButton shortButton = new JButton("Short Paragraph");
    JButton mediumButton = new JButton("Medium Paragraph");
    JButton longButton = new JButton("Long Paragraph");

    JLabel shortStats = new JLabel();
    JLabel mediumStats = new JLabel();
    JLabel longStats = new JLabel();

    JButton resetButton = new JButton("Retry");
    JButton quitButton = new JButton("Quit");

    Font font = new Font("Arial", Font.PLAIN, 16);
    Insets margin = new Insets(5,0,5,0);

    Color transparent = new Color(0, 0, 0, 0);

    TypingUser profile = new TypingUser();
    ParagraphCollection paragraphs = new ParagraphCollection();
    Paragraph para = new Paragraph("Z4QQQ🦇", "short");

    public static void main(String[] args) throws IOException {
        Typing ui = new Typing();
    }

    public Typing() throws IOException {

        header.setBounds(10, 5, 491, 30);
        header.setFont(new Font("Arial", Font.BOLD, 20));

        textbox.setEditable(false);
        textbox.setLineWrap(true);
        textbox.setWrapStyleWord(true);
        textbox.setFont(font);
        textbox.setBackground(transparent);
        textbox.setForeground(new Color(0, 0, 0, 168));
        textbox.setBounds(10, 40, 670, 130);
        textbox.setOpaque(false);

        input.setEditable(true);
        input.setLineWrap(true);
        input.setWrapStyleWord(true);
        input.getDocument().addDocumentListener(this);
        input.setFont(font);
        input.setMargin(margin);
        input.setBounds(10, 170, 670, 150);

        wpmCount.setBounds(10, 326, 500, 30);
        wpmCount.setFont(font);

        msgProgress.setBounds(50, 326, 465, 30);
        msgProgress.setFont(font);

        resetButton.setBounds(530, 325, 70, 30);
        resetButton.setFont(font);
        resetButton.addActionListener(e -> startParagraph(para.getType()));
        resetButton.setMargin(new Insets(1, 0, 0, 0));

        quitButton.setBounds(610, 325, 70, 30);
        quitButton.setFont(font);
        quitButton.addActionListener(e -> quitToMenu());
        quitButton.setMargin(new Insets(1, 0, 0, 0));

        titleText.setBounds(0, 10, 704, 30);
        titleText.setFont(new Font("Arial", Font.BOLD, 20));

        titleWPM.setBounds(0, 40, 704, 30);
        titleWPM.setFont(font);

        shortButton.setFocusPainted(false);
        shortButton.setBounds(72, 80, 160, 50);
        shortButton.setFont(font);
        shortButton.addActionListener(e -> startParagraph("short"));
        shortButton.setMargin(margin);
        shortStats.setBounds(72, 140, 160, 300);
        shortStats.setFont(font);
        shortStats.setVerticalAlignment(JLabel.TOP);

        mediumButton.setBounds(267, 80, 160, 50);
        mediumButton.setFont(font);
        mediumButton.addActionListener(e -> startParagraph("medium"));
        mediumButton.setMargin(margin);
        mediumStats.setBounds(267, 140, 160, 300);
        mediumStats.setFont(font);
        mediumStats.setVerticalAlignment(JLabel.TOP);

        longButton.setBounds(462, 80, 160, 50);
        longButton.setFont(font);
        longButton.addActionListener(e -> startParagraph("long"));
        longButton.setMargin(margin);
        longStats.setBounds(462, 140, 160, 300);
        longStats.setFont(font);
        longStats.setVerticalAlignment(JLabel.TOP);

        main.add(header, 1);
        main.add(textbox, 2);
        main.add(input, 3);
        main.add(wpmCount, 4);
        main.add(msgProgress, 5);
        main.add(resetButton, 6);
        main.add(quitButton, 7);

        title.add(titleText, 1);
        title.add(titleWPM, 2);
        title.add(shortButton, 3);
        title.add(mediumButton, 4);
        title.add(longButton, 5);
        title.add(shortStats, 6);
        title.add(mediumStats, 7);
        title.add(longStats, 8);

        updateStats(false);
        frame.setSize(704, 396);
        frame.setLocation(100, 200);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(title);
    }

    public void setInputText(String msg) {
        SwingUtilities.invokeLater(() -> input.setText(msg));
    }

    public void updateWPM() {
        int currentWPM = para.getWPM();
        String currentProgress = String.format("%.2f", para.getCompletion());
        SwingUtilities.invokeLater(() -> {
            wpmCount.setText("Words Per Minute: " + currentWPM);
            msgProgress.setText(currentProgress + "% complete");
        });
        //frame.setTitle(windowName + " (" + currentWPM + " WPM)");
    }

    public void startParagraph(String length) {
        frame.getContentPane().removeAll();
        frame.repaint();
        frame.getContentPane().add(main);
        String selectedParagraph = paragraphs.chooseParagraph(length);
        para = new Paragraph(selectedParagraph, length);
        header.setText("Type the following paragraph:");
        wpmCount.setText("Type to start!");
        msgProgress.setText("");
        input.setText("");
        input.setBackground(Color.WHITE);
        textbox.setText(para.getMsg());
    }

    public void quitToMenu() {
        frame.getContentPane().removeAll();
        frame.repaint();
        frame.getContentPane().add(title);
        frame.setVisible(true);
    }

    public void finish(int WPM) {
        profile.addScore(para.getType(), WPM);
        updateStats(true);
        quitToMenu();
    }

    public void updateStats(Boolean updateTitle) {
        shortStats.setText(profile.getTypeStats("short"));
        mediumStats.setText(profile.getTypeStats("medium"));
        longStats.setText(profile.getTypeStats("long"));
        if (updateTitle) titleWPM.setText(String.format("%.1f", profile.getAverageWPM("all")) + " WPM  •  " + profile.getScores("all").size() + " Completed");
    }

    public void handleText() {
        String msg = input.getText();
        if (msg.length() < 1) {}
        else if (!para.compareText(msg, 5) || msg.length() > oldText.length() + 1) setInputText(oldText); // anti-cheat
        else {
            if (para.compareText(msg,0)) {
                para.trackLetter(msg.length() - 1);
                input.setBackground(Color.WHITE);
            }

            else input.setBackground(new Color(255, 225, 225));

            if (msg.equals(para.getMsg())) {
                finish(para.getWPM());
            }
            else if (msg.equals("CHEAT")) {
                int wpm = para.getWPM();
                finish(wpm > 0 ? wpm : (int)(Math.random() * 100));
            }
            else {
                oldText = msg;
                updateWPM();
            }
        }
    }

    public void insertUpdate(DocumentEvent e) {
        handleText();
    }

    public void removeUpdate(DocumentEvent e) {
        handleText();
    }

    public void changedUpdate(DocumentEvent e) {
        handleText();
    }

}