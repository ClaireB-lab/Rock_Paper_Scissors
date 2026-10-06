import javax.swing.JFrame;

/// Claire Battelle
/// October 5, 2026
/// opens the rock paper scissors game and has the player play it
/// when x button is hit the game closes
public class RockPaperScissorsRunner {
    public static void main(String[] args) {
        RockPaperScissorsFrame frame = new RockPaperScissorsFrame();
        frame.setTitle("Rock Paper Scissors Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
