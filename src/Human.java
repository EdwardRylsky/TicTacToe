public class Human extends Player{
    Human(){
        this.name = "Player 1";
        this.symbol = 'X';
    }

    Human(String name, char symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    @Override
    protected int turn() {
        return 0;
    }
}
