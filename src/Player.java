import java.io.Serializable;

public class Player implements Serializable {

    private String name;
    private char symbol;
    private boolean isComputer;
    private int levelAI;

    Player(){
        this.name = "Player 1";
        this.symbol = 'X';
    }

    Player(String name, char symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    Player(int computerAILevel) {
        this.name = "Computer";
        this.isComputer = true;
        this.levelAI = computerAILevel;
        this.symbol = 'O';
    }

    Player(int computerAILevel, char symbol) {
        this.name = "Computer";
        this.isComputer = true;
        this.levelAI = computerAILevel;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public boolean isComputer() {
        return isComputer;
    }

    public int getLevelAI() {
        return levelAI;
    }

    @Override
    public String toString() {
        return "playerInfo{" +
                "name='" + name + '\'' +
                "isComputer='" + isComputer + '\'' +
                "symbol='" + symbol + '\'' +
                '}';
    }
}
