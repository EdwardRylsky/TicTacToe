import java.util.*;

public class Game {

    static char[][] gameBoard = {
            {'1', '|', '2', '|', '3'},
            {'—', '+', '—', '+', '—'},
            {'4', '|', '5', '|', '6'},
            {'—', '+', '—', '+', '—'},
            {'7', '|', '8', '|', '9'}}; //добавил нумерацию игрового поля при старте

    static List<Integer> topRow = Arrays.asList(1, 2, 3);
    static List<Integer> midRow = Arrays.asList(4, 5, 6);
    static List<Integer> botRow = Arrays.asList(7, 8, 9);
    static List<Integer> leftCol = Arrays.asList(1, 4, 7);
    static List<Integer> midCol = Arrays.asList(2, 5, 8);
    static List<Integer> rightRow = Arrays.asList(3, 6, 9);
    static List<Integer> cross1 = Arrays.asList(1, 5, 9);
    static List<Integer> cross2 = Arrays.asList(3, 5, 7);

    static List<List<Integer>> winningConditions = new ArrayList<>();

    static boolean isPvP = false;

    static Player currentPlayer;

    static String result;

    public static void startGame(Player player1, Player player2, boolean player1First){
        winningConditions.add(topRow);
        winningConditions.add(midRow);
        winningConditions.add(botRow);
        winningConditions.add(leftCol);
        winningConditions.add(midCol);
        winningConditions.add(rightRow);
        winningConditions.add(cross1);
        winningConditions.add(cross2);

        int playerPos;

        isPvP = !player1.isComputer && !player2.isComputer;

        printGameBoard();

        for(int x = 0; x < 5; x = x + 2) {
            for (int y = 0; y < 5; y = y + 2) {
                gameBoard[x][y]=' ';
            }
        }

        while (true) {
            if (isPvP) {
                if (player1First) {
                    currentPlayer = player1;
                } else currentPlayer = player2;
                System.out.println("Now turn " + currentPlayer.name);
            }

            if (isPvP || (!isPvP && player1First)) {
                Scanner scan = new Scanner(System.in);
                System.out.print("Enter your placement (1-9): ");
                String name = scan.nextLine();
                while (!name.matches("[1-9]")) {
                    System.out.print("Position incorrect! Enter a correct position:");
                    scan = new Scanner(System.in);
                    name = scan.nextLine();
                }
                playerPos = Integer.parseInt(name);
                while (player1.positions.contains(playerPos) || player2.positions.contains(playerPos)) {
                    System.out.print("Position taken! Enter a free position:");
                    scan = new Scanner(System.in);
                    name = scan.nextLine();
                    while (!name.matches("[1-9]")) {
                        System.out.print("Position incorrect! Enter a correct position:");
                        scan = new Scanner(System.in);
                        name = scan.nextLine();
                    }
                    playerPos = Integer.parseInt(name);
                }

                placePiece(playerPos, currentPlayer);
                printGameBoard();

                if (player1First) player1First = false;
                else player1First = true;

                result = checkWinner(player1, player2);
                if (result.length() > 0) {
                    printGameBoard();
                    System.out.println(result);
                    player1.positions.clear();
                    player2.positions.clear();
                    System.out.println("Enter any key to return main menu");
                    scan.nextLine();
                    break;
                }
            }

        if(!isPvP) {
            Random rand = new Random();
            int cpuPos = rand.nextInt(9) + 1;

            while (player1.positions.contains(cpuPos) || player2.positions.contains(cpuPos)) {
                cpuPos = rand.nextInt(9) + 1;
            }

            placePiece(cpuPos, player2);

            printGameBoard();

            result = checkWinner(player1, player2);
            if (result.length() > 0) {
                printGameBoard();
                System.out.println(result);
                player1.positions.clear();
                player2.positions.clear();
                System.out.println("Enter any key to return main menu");
                Scanner scan = new Scanner(System.in);
                scan.nextLine();
                break;
            }
        }
    }
}

    public static void printGameBoard() {
        for (char[] row : gameBoard) {
            for (char c : row) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    public static void placePiece(int pos, Player player) {
        player.positions.add(pos);
        switch (pos) {
            //этот сахар подсказала IntelliJ IDEA
            case 1 -> gameBoard[0][0] = player.symbol;
            case 2 -> gameBoard[0][2] = player.symbol;
            case 3 -> gameBoard[0][4] = player.symbol;
            case 4 -> gameBoard[2][0] = player.symbol;
            case 5 -> gameBoard[2][2] = player.symbol;
            case 6 -> gameBoard[2][4] = player.symbol;
            case 7 -> gameBoard[4][0] = player.symbol;
            case 8 -> gameBoard[4][2] = player.symbol;
            case 9 -> gameBoard[4][4] = player.symbol;
        }
    }

    public static String checkWinner(Player player1, Player player2) {
        for (List<Integer> l : winningConditions) {
            if (player1.positions.containsAll(l)) {
                return "Congratulation "+player1.name+", you won!";
            }
            if (player2.positions.containsAll(l)) {
                if(isPvP) {
                    return "Congratulation " + player2.name + ", you won!";
                }
                return "Computer wins! Sorry :(";
            }
        }
        // Вынес следующий if из цикла, т.к. если игрок делает последний возможный ход и он не попадает под первое условие победы, то объявляется ничья.
        // Хотя в видео он включен в цикл, ссылка с таймкодом: https://youtu.be/gQb3dE-y1S4?t=1380
        if (player1.positions.size() + player2.positions.size() == 9) return "TIE!";
        return "";
    }
}
