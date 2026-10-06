/// Claire Battelle
/// October 5, 2026
/// creates the computer play where the computer lowkey cheats on you
public class Cheat implements Strategy {
    @Override
    public String getMove(String playerMove)
    {
        String computerMove = "";
        switch (playerMove)
        {
            case "R":
                computerMove = "P";
                break;
            case "P":
                computerMove = "S";
                break;
            case "S":
                computerMove = "R";
                break;
            default:
                computerMove = "X";
                break;
        }
        return computerMove;
        }
    }

