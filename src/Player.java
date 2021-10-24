import java.util.ArrayList;

public class Player {

    String name;
    boolean isComputer;
    int computerAILevel;
    char symbol;
    ArrayList<Integer> positions = new ArrayList<>();


    Player(){
        this.name = "Player 1";
        this.symbol = 'X';
        this.positions = new ArrayList<>();
    }

    Player(String name, char symbol){
        this.name = name;
        this.symbol = symbol;
        this.positions = new ArrayList<>();
    }

    Player(int computerAILevel){
        this.name = "Computer";
        this.isComputer = true;
        this.computerAILevel = computerAILevel;
        this.symbol = 'O';
        this.positions = new ArrayList<>();
    }

    Player(int computerAILevel, char symbol){
        this.name = "Computer";
        this.isComputer = true;
        this.computerAILevel = computerAILevel;
        this.symbol = symbol;
        this.positions = new ArrayList<>();
    }
}
