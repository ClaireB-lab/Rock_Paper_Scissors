import javax.swing.*;
import java.awt.*;

/// Claire Battelle
/// October 5, 2026
/// Creates the GUI for the game rock paper scissors
public class RockPaperScissorsFrame extends JFrame {

    private final JTextField pWinsTF = new JTextField("0", 4), cWinsTF = new JTextField("0", 4), tiesTF = new JTextField("0", 4);
    private final JTextArea resultsArea = new JTextArea(10, 40);
    private int pWins = 0, cWins = 0, ties = 0, rCount = 0, pCount = 0, sCount = 0;
    private String lastPlayerMove = "";

    /// Creates the way the GUI looks
    /// creates a title
    /// puts images on each of the butons
    /// creates quit button
    /// makes a panel that displays the game
    /// makes a result area that shows results of all games since loading the game
    public RockPaperScissorsFrame() {
        setTitle("Rock Paper Scissors Game");
        setLayout(new BorderLayout(5, 5));

        JPanel btnPanel = new JPanel();
        btnPanel.setBorder(BorderFactory.createTitledBorder("Make Your Move"));
        String[] moves = {"Rock", "Paper", "Scissors"};
        String[] codes = {"R", "P", "S"};
        for (int i = 0; i < 3; i++) {
            String code = codes[i];
            ImageIcon icon = getResizedIcon("src/" + moves[i].toLowerCase() + ".jpg", 40, 40);
            JButton btn = new JButton(moves[i], icon);
            btn.addActionListener(e -> playGame(code));
            btnPanel.add(btn);
        }

        ImageIcon quitIcon = getResizedIcon("src/quit.jpg", 40, 40);
        JButton quitBtn = (quitIcon != null) ? new JButton("Quit", quitIcon) : new JButton("Quit");
        quitBtn.addActionListener(e -> System.exit(0));
        btnPanel.add(quitBtn);

        JPanel statsPanel = new JPanel(new GridLayout(3, 2));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Game Statistics"));
        JTextField[] fields = {pWinsTF, cWinsTF, tiesTF};
        String[] labels = {"Player Wins:", "Computer Wins:", "Ties:"};
        for (int i = 0; i < 3; i++) {
            fields[i].setEditable(false);
            statsPanel.add(new JLabel(labels[i], SwingConstants.RIGHT));
            statsPanel.add(fields[i]);
        }

        resultsArea.setEditable(false);
        JPanel displayPanel = new JPanel();
        displayPanel.add(new JScrollPane(resultsArea));

        add(btnPanel, BorderLayout.NORTH);
        add(statsPanel, BorderLayout.CENTER);
        add(displayPanel, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
    }

    /// @param playerMove
    /// creates the computer generated player
    /// sometimes it cheats (10% of the time)
    /// says what a win entails, what a tie entails, what a loss entails
    private void playGame(String playerMove) {
        int roll = (int) (Math.random() * 100) + 1;
        Strategy strategy;
        String name;

        if (roll <= 10)       { strategy = new Cheat(); name = "Cheat"; }
        else if (roll <= 30) { strategy = new LeastUsed(); name = "Least Used"; }
        else if (roll <= 50) { strategy = new MostUsed(); name = "Most Used"; }
        else if (roll <= 70) { strategy = new LastUsed(); name = "Last Used"; }
        else                 { strategy = new RandomStrategy(); name = "Random"; }

        String compMove = strategy.getMove(playerMove);

        if (playerMove.equals("R")) rCount++;
        else if (playerMove.equals("P")) pCount++;
        else if (playerMove.equals("S")) sCount++;

        if (playerMove.equals(compMove)) {
            ties++;
            resultsArea.append("Tie game! Computer: " + name + "\n");
        } else if ((playerMove.equals("R") && compMove.equals("S")) ||
                (playerMove.equals("P") && compMove.equals("R")) ||
                (playerMove.equals("S") && compMove.equals("P"))) {
            pWins++;
            resultsArea.append(getPhrase(playerMove, compMove) + " (Player wins!) Computer: " + name + "\n");
        } else {
            cWins++;
            resultsArea.append(getPhrase(compMove, playerMove) + " (Computer wins!) Computer: " + name + "\n");
        }

        lastPlayerMove = playerMove;
        pWinsTF.setText(String.valueOf(pWins));
        cWinsTF.setText(String.valueOf(cWins));
        tiesTF.setText(String.valueOf(ties));
    }

    /// @param w
    /// @param l
    /// @return
    /// creates the phrases that say what happened in the game
    private String getPhrase(String w, String l) {
        if (w.equals("R")) return "Rock breaks scissors.";
        if (w.equals("P")) return "Paper covers rock.";
        return "Scissors covers paper.";
    }

    /// creates the least used for the computer
    /// uses the element that is least used while playing
    private class LeastUsed implements Strategy {
        public String getMove(String p) {
            return (rCount <= pCount && rCount <= sCount) ? "P" : (pCount <= rCount && pCount <= sCount) ? "S" : "R";
        }
    }

    /// creates the most used play for the computer
    /// uses the element that is most used while playing
    private class MostUsed implements Strategy {
        public String getMove(String p) {
            return (rCount >= pCount && rCount >= sCount) ? "P" : (pCount >= rCount && pCount >= sCount) ? "S" : "R";
        }
    }

    /// cretaes the last used play for the computer
    /// uses whichever element was used on the last round
    private class LastUsed implements Strategy {
        public String getMove(String p) {
            return lastPlayerMove.isEmpty() ? new RandomStrategy().getMove(p) : lastPlayerMove;
        }
    }

    /// @param path
    /// @param width
    /// @param height
    /// @return
    /// resizes the images so they aren't giant and makes them not look stupid
    private ImageIcon getResizedIcon(String path, int width, int height) {
        java.io.File imgFile = new java.io.File(path);
        if (!imgFile.exists()) {
            System.err.println("IMAGE NOT FOUND: " + imgFile.getAbsolutePath());
            return null;
        }
        ImageIcon originalIcon = new ImageIcon(path);
        Image scaledImg = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }
}