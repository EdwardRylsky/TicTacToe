import java.util.*;

public class Main {

    static int[] commandByLevel = {-1, -1, -1, -1};

    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        Player player1 = new Player();
        Player player2 = new Player("Player 2", 'O');
        Player computer = new Player(0);

        while (true) {
            while (commandByLevel[0] == -1){
                printStartMenu();
                commandByLevel[0] = checkInputCommand(scanner, 2);
            }
            if (commandByLevel[0] == 1) {
                while (commandByLevel[1] == -1){
                    printStartGameMenu();
                    commandByLevel[1] = checkInputCommand(scanner, 2);
                }
                //PvE
                if (commandByLevel[1] == 1) {
                    while (commandByLevel[2] == -1) {
                        printStartPvEGameMenu();
                        commandByLevel[2] = checkInputCommand(scanner, 3);
                    }
                    if (commandByLevel[2] == 1) {
                        Game game = Game.getInstance();
                        game.newGame(player1, computer, true);
                        goToMainMenu();
                    }
                    if (commandByLevel[2] == 2) {
                        Game game = Game.getInstance();
                        game.newGame(player1, computer, false);
                        goToMainMenu();
                    }
                    if (commandByLevel[2] == 3) {
                        commandByLevel[2] = -1;
                        commandByLevel[1] = -1;
                    }
                }
                //PvP
                if (commandByLevel[1] == 2) {
                    while (commandByLevel[2] == -1) {
                        System.out.println("Current player 1 name '" + player1.getName() + "' and symbol '" + player1.getSymbol() + "'");
                        System.out.println("Current player 2 name '" + player2.getName() + "' and symbol '" + player2.getSymbol() + "'");
                        if(player1.getName().equalsIgnoreCase(player2.getName())) System.out.println("!!!WARNING!!! Both player have same name");
                        printStartPvPGameMenu();
                        commandByLevel[2] = checkInputCommand(scanner, 4);
                    }

                    if (commandByLevel[2] == 1) {
                        Game game = Game.getInstance();
                        game.newGame(player1, player2, true);
                        goToMainMenu();
                    }

                    if (commandByLevel[2] == 2) {
                        Game game = Game.getInstance();
                        game.newGame(player1, player2, false);
                        goToMainMenu();
                    }
                    //Customize player 1
                    if (commandByLevel[2] == 3) {
                        customizePlayer(scanner, player1);
                    }
                    if (commandByLevel[2] == 4) {
                        customizePlayer(scanner, player2);
                    }
                    if (commandByLevel[2] == 0) {
                        commandByLevel[2] = -1;
                        commandByLevel[1] = -1;
                    }
                }
                if (commandByLevel[1] == 0) {
                    commandByLevel[0] = -1;
                    commandByLevel[1] = -1;
                }
            } else if (commandByLevel[0] == 2) {
                Game game = Game.getInstance();
                game.loadGame();
                goToMainMenu();
            } else if (commandByLevel[0] == 0) {
                System.out.println("Good bye!");
                break;
            }
        }
    }

    private static void printStartMenu() {
        System.out.println("1. Start game");
        System.out.println("2. Load game");
        System.out.println("0. Exit");
    }

    private static void printStartGameMenu() {
        System.out.println("1. Versus Computer");
        System.out.println("2. Versus Player");
        System.out.println("0. Back");
    }

    private static void printStartPvEGameMenu() {
        System.out.println("1. Start game");
        System.out.println("2. Start game. Computer turn first");
        System.out.println("3. Change difficulty");
        System.out.println("0. Back");
    }

    private static void printStartPvPGameMenu() {
        System.out.println("1. Start game. Player 1 turn first");
        System.out.println("2. Start game. Player 2 turn first");
        System.out.println("3. Customize player 1");
        System.out.println("4. Customize player 2");
        System.out.println("0. Back");
    }

    private static void printCustomizePlayerMenu() {
        System.out.println("1. Customize player name");
        System.out.println("2. Customize player symbol");
        System.out.println("0. Back");
    }

    private static int checkInputCommand(Scanner scanner, int maxMenuValue){
        var inputCommand = scanner.nextLine();
        int command = -1;

        if (!inputCommand.matches("[0-"+maxMenuValue+"]")) {
            System.out.println("Sorry, there is no such command.");
        } else command = Integer.parseInt(inputCommand);

        return command;
    }

    private static void customizePlayer(Scanner scanner, Player player) {
        System.out.println("Current player name '" + player.getName() + "' and symbol '" + player.getSymbol() + "'");
        while (commandByLevel[3] == -1) {
            printCustomizePlayerMenu();
            commandByLevel[3] = checkInputCommand(scanner, 2);
        }
        if (commandByLevel[3] == 1) {
            commandByLevel[3] = -1;
            System.out.print("Enter new name for "+player.getName()+": ");
            player.setName(scanner.nextLine());
        }
        if (commandByLevel[3] == 2) {
            commandByLevel[3] = -1;
            System.out.print("Enter new symbol for "+player.getName()+": ");
            String input = scanner.nextLine();
            if (input.length() > 1) System.out.print("You enter a string, not a symbol. Game will take first symbol from string");

            player.setSymbol(input.charAt(0));
        }
        if (commandByLevel[3] == 0) {
            commandByLevel[3] = -1;
            commandByLevel[2] = -1;
        }
    }

    private static void goToMainMenu(){
        int len = commandByLevel.length;

        for(int i = 0; i < len; i++){
            commandByLevel[i] = -1;
        }
    }
}