import java.util.Random;

/// Claire Battelle
/// October 5, 2026
/// creates the random strategy for the computer
/// uses one of the 4 other strategies, at random
public class RandomStrategy implements Strategy{
    private Random rnd = new Random();

    @Override
    public String getMove(String playerMove) {
        String[] moves = {"R", "P", "S"};
        return moves[rnd.nextInt(3)];
    }
}
