import java.util.*;

public class Game {

    private static final List<Integer> TOPROW   = Arrays.asList(1, 2, 3);
    private static final List<Integer> MIDROW   = Arrays.asList(4, 5, 6);
    private static final List<Integer> BOTROW   = Arrays.asList(7, 8, 9);
    private static final List<Integer> LEFTCOL  = Arrays.asList(1, 4, 7);
    private static final List<Integer> MIDCOL   = Arrays.asList(2, 5, 8);
    private static final List<Integer> RIGHTROW = Arrays.asList(3, 6, 9);
    private static final List<Integer> CROSS1   = Arrays.asList(1, 5, 9);
    private static final List<Integer> CROSS2   = Arrays.asList(3, 5, 7);

    private static final List<List<Integer>> WINCONDITIONS = new ArrayList<>(){{
        add(TOPROW);
        add(MIDROW);
        add(BOTROW);
        add(LEFTCOL);
        add(MIDCOL);
        add(RIGHTROW);
        add(CROSS1);
        add(CROSS2);
    }};

    public void newGame(Player player1, Player player2, boolean player1Move) {

        char[][] gameBoard = {
                {'1', '|', '2', '|', '3'},
                {'—', '+', '—', '+', '—'},
                {'4', '|', '5', '|', '6'},
                {'—', '+', '—', '+', '—'},
                {'7', '|', '8', '|', '9'}}; //добавил нумерацию игрового поля при старте

        Player currentPlayer;

        ArrayList<Integer> player1Moves = new ArrayList<>();
        ArrayList<Integer> player2Moves = new ArrayList<>();
        var currentPlayerMovesList = player1Moves;

        if (player1Move) {
            currentPlayer = player1;
            currentPlayerMovesList = player1Moves;
        } else {
            currentPlayer = player2;
            currentPlayerMovesList = player2Moves;
        }

        int moveNumber = 0;

        printGameBoard(gameBoard);

        //очищаем стартовую нумерацию поля
        for (int x = 0; x < 5; x = x + 2) {
            for (int y = 0; y < 5; y = y + 2) {
                gameBoard[x][y] = ' ';
            }
        }

        boolean isPvP = !player1.isComputer && !player2.isComputer;
        String result;
        int playerPos;

        while (true) {
            moveNumber++;
            if (player1Move) {
                currentPlayer = player1;
                currentPlayerMovesList = player1Moves;
            } else {
                currentPlayer = player2;
                currentPlayerMovesList = player2Moves;
            }
            player1Move = !player1Move;

            if (isPvP) {
                System.out.println("Now turn " + currentPlayer.name);
            }

            if (!currentPlayer.isComputer) {
                Scanner scan = new Scanner(System.in);
                System.out.print("Enter your placement (1-9): ");
                playerPos = checkInputPosition(scan, player1Moves, player2Moves);

                placePiece(gameBoard, playerPos, currentPlayer, currentPlayerMovesList);
                //printGameBoard(gameBoard);

                result = checkWinner(currentPlayer, currentPlayerMovesList, moveNumber);
                if (result.length() > 0) {
                    printGameBoard(gameBoard);
                    System.out.println(result);
                    System.out.println("Enter any key to return main menu");
                    scan.nextLine();
                    break;
                }
            } else  {
                int cpuPos = currentPlayer.turn();

                while (player1Moves.contains(cpuPos) || player2Moves.contains(cpuPos)) {
                    cpuPos = currentPlayer.turn();
                }

                placePiece(gameBoard, cpuPos, currentPlayer, currentPlayerMovesList);


                //printGameBoard(gameBoard);

                result = checkWinner(currentPlayer, currentPlayerMovesList, moveNumber);
                if (result.length() > 0) {
                    printGameBoard(gameBoard);
                    System.out.println(result);
                    System.out.println("Enter any key to return main menu");
                    Scanner scan = new Scanner(System.in);
                    scan.nextLine();
                    break;
                }
            }
            printGameBoard(gameBoard);
        }
    }

    private static void printGameBoard(char[][] gameBoard) {
        for (char[] row : gameBoard) {
            for (char c : row) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    private static void placePiece(char[][] gameBoard, int pos, Player player, ArrayList<Integer> currentPlayerMovesList) {
        currentPlayerMovesList.add(pos);
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

    private static int checkInputPosition(Scanner scan, ArrayList<Integer> player1Moves, ArrayList<Integer> player2Moves){
        int position;
        var input = scan.nextLine();

        while (!input.matches("[1-9]")) {
            System.out.print("Position incorrect! Enter a correct position:");
            scan = new Scanner(System.in);
            input = scan.nextLine();
        }
        position = Integer.parseInt(input);

        while (player1Moves.contains(position) || player2Moves.contains(position)) {
            System.out.print("Position taken! Enter a free position:");
            scan = new Scanner(System.in);
            input = scan.nextLine();
            while (!input.matches("[1-9]")) {
                System.out.print("Position incorrect! Enter a correct position:");
                scan = new Scanner(System.in);
                input = scan.nextLine();
            }
            position = Integer.parseInt(input);
        }
        return position;
    }

    private static String checkWinner(Player currentPlayer, List<Integer> currentPlayerMovesList, int moveNumber) {
        for (List<Integer> l : WINCONDITIONS) {
            if (currentPlayerMovesList.containsAll(l)) {
                if (!currentPlayer.isComputer) {
                    return "Congratulation " + currentPlayer.name + ", you won!";
                } else return "Computer wins! Sorry :(";
            }
        }
        // Вынес следующий if из цикла, т.к. если игрок делает последний возможный ход и он не попадает под первое условие победы, то объявляется ничья.
        // Хотя в видео он включен в цикл, ссылка с таймкодом: https://youtu.be/gQb3dE-y1S4?t=1380
        if (moveNumber == 9) return "TIE!";
        return "";
    }
}
