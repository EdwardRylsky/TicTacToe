import java.util.*;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Player player1 = new Player();
        Player player2 = new Player("Player 2", 'O');
        Player computer = new Player(0);

        int[] commandByLevel = {-1, -1, -1};

        while (true) {
            while (commandByLevel[0] == -1){
                printStartMenu();
                commandByLevel[0] = checkInputCommand(scanner, 1);
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
                        Game game = new Game();
                        game.startGame(player1, computer, true);
                        commandByLevel[2] = -1;
                        commandByLevel[1] = -1;
                        commandByLevel[0] = -1;
                    }
                    if (commandByLevel[2] == 2) {
                        Game game = new Game();
                        game.startGame(player1, computer, false);
                        commandByLevel[2] = -1;
                        commandByLevel[1] = -1;
                        commandByLevel[0] = -1;
                    }
                    if (commandByLevel[2] == 3) {
                        commandByLevel[2] = -1;
                        commandByLevel[1] = -1;
                    }
                }
                //PvP
                if (commandByLevel[1] == 2) {
                    while (commandByLevel[2] == -1) {
                        System.out.println("Current player 1 name '" + player1.name + "' and symbol '" + player1.symbol + "'");
                        System.out.println("Current player 2 name '" + player2.name + "' and symbol '" + player2.symbol + "'");
                        printStartPvPGameMenu();
                        commandByLevel[2] = checkInputCommand(scanner, 4);
                    }

                    if (commandByLevel[2] == 1) {
                        Game game = new Game();
                        game.startGame(player1, player2, true);
                        commandByLevel[2] = -1;
                        commandByLevel[1] = -1;
                        commandByLevel[0] = -1;
                    }

                    if (commandByLevel[2] == 2) {
                        Game game = new Game();
                        game.startGame(player1, player2, false);
                        commandByLevel[2] = -1;
                        commandByLevel[1] = -1;
                        commandByLevel[0] = -1;
                    }
                    //Customize player 1
                    if (commandByLevel[2] == 3) {
                        customizePlayer(scanner, player1);
                        commandByLevel[2] = -1;
                    }
                    if (commandByLevel[2] == 4) {
                        customizePlayer(scanner, player2);
                        commandByLevel[2] = -1;
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
            } else if (commandByLevel[0] == 0) {
                System.out.println("Good bye!");
                break;
            }
        }
    }

    public static void printStartMenu() {
        System.out.println("1. Start game");
        //System.out.println("2. Load game");
        System.out.println("0. Exit");
    }

    public static void printStartGameMenu() {
        System.out.println("1. Versus Computer");
        System.out.println("2. Versus Player");
        System.out.println("0. Back");
    }

    public static void printStartPvEGameMenu() {
        System.out.println("1. Start game");
        System.out.println("2. Start game. Computer turn first");
        System.out.println("3. Change difficulty");
        System.out.println("0. Back");
    }

    public static void printStartPvPGameMenu() {
        System.out.println("1. Start game. Player 1 turn first");
        System.out.println("2. Start game. Player 2 turn first");
        System.out.println("3. Customize player 1");
        System.out.println("4. Customize player 2");
        System.out.println("0. Back");
    }

    public static void printStartCustomizeGameMenu() {
        System.out.println("1. Customize player name");
        System.out.println("2. Customize player symbol");
        System.out.println("0. Back");
    }

    public static int checkInputCommand(Scanner scanner, int maxMenuValue){
        String inputCommand = scanner.nextLine();
        int command = -1;

        if (!inputCommand.matches("[0-"+maxMenuValue+"]")) {
            System.out.println("Sorry, there is no such command.");
        } else command = Integer.parseInt(inputCommand);

        return command;
    }

    public static void customizePlayer(Scanner scanner, Player player) {
        System.out.println("Current player name '" + player.name + "' and symbol '" + player.symbol + "'");
        int command = -1;
        while (command == -1) {
            printStartCustomizeGameMenu();
            command = checkInputCommand(scanner, 2);
        }
        if (command == 1) {
            command = -1;
            System.out.print("Enter new name for "+player.name+": ");
            player.name = scanner.nextLine();
        }
        if (command == 2) {
            command = -1;
            System.out.print("Enter new symbol for "+player.name+": ");
            String input = scanner.nextLine();
            if (input.length() > 1) System.out.print("You enter a string, not a symbol. Game will take first symbol from string");

            player.symbol = input.charAt(0);
        }
    }

    public static char checkPlayerSymbol(Scanner scanner){
        String inputCommand = scanner.nextLine();
        char symbol = '1';

        return symbol;
    }

}