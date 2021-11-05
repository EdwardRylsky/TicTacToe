import java.util.ArrayList;
import java.util.Random;

public class AI extends Player{

    int levelAI;
    /*
    0 - default level. Take random position.
    1 -
     */


    AI(int computerAILevel){
        this.name = "Computer";
        this.isComputer = true;
        this.levelAI = computerAILevel;
        this.symbol = 'O';
    }

    AI(int computerAILevel, char symbol){
        this.name = "Computer";
        this.isComputer = true;
        this.levelAI = computerAILevel;
        this.symbol = symbol;
    }

    @Override
    public int turn(){
        int position = 1;
        switch(this.levelAI){
            case 0 -> {
                Random rand = new Random();
                return rand.nextInt(9) + 1;
            }
        }

        return position;
    }
}
