import java.io.*;
import java.util.*;

public class Game {

    private static Game instance;

    private Game(){}

    public static synchronized Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    private static final String FILE_NAME = "tictactoeSaveFile.txt";

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

    private Player player1;
    private Player player2;
    private Player currentPlayer;

    private List<Integer> player1Moves = new ArrayList<>(5);
    private List<Integer> player2Moves = new ArrayList<>(5);
    private List<Integer> currentPlayerMovesList;

    private Boolean player1Move;
    private Boolean gameStarted;

    private final char[][] gameBoard = {
            {'1', '|', '2', '|', '3'},
            {'—', '+', '—', '+', '—'},
            {'4', '|', '5', '|', '6'},
            {'—', '+', '—', '+', '—'},
            {'7', '|', '8', '|', '9'}};

    public void newGame(Player player1, Player player2, boolean player1Move) {

        this.player1 = player1;
        this.player2 = player2;
        this.player1Move = player1Move;

        printGameBoard();

        //очищаем стартовую нумерацию поля
        for (int x = 0; x < 5; x = x + 2) {
            for (int y = 0; y < 5; y = y + 2) {
                gameBoard[x][y] = ' ';
            }
        }

        startGame();
    }

    private void startGame(){

        gameStarted = true;

        if (player1Move) {
            currentPlayer = player1;
            currentPlayerMovesList = player1Moves;
        } else {
            currentPlayer = player2;
            currentPlayerMovesList = player2Moves;
        }

        boolean isPvP = !player1.isComputer() && !player2.isComputer();
        String result;
        int playerPos;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            if (player1Move) {
                currentPlayer = player1;
                currentPlayerMovesList = player1Moves;
            } else {
                currentPlayer = player2;
                currentPlayerMovesList = player2Moves;
            }

            if (isPvP) {
                System.out.println("Now turn " + currentPlayer.getName());
            }

            if (!currentPlayer.isComputer()) {
                System.out.print("Enter your placement (1-9): ");

                playerPos = checkInputPosition();

                placePiece(playerPos);

            } else  {
                Integer position = null;

                while (position == null || player1Moves.contains(position) || player2Moves.contains(position)) {
                    Random rand = new Random();
                    position = rand.nextInt(9) + 1;
                }
                placePiece(position);
            }

            if(currentPlayerMovesList.size() >= 3) {
                result = checkWinner();
                if (result.length() > 0) {
                    printGameBoard();
                    System.out.println(result);
                    System.out.println("Enter any key to return main menu");
                    scanner.nextLine();
                    resetGame();
                    break;
                }
            }

            if(isPvP || currentPlayer.isComputer()) {
                printGameBoard();
            }

            player1Move = !player1Move;
        }
    }

    private void printGameBoard() {
        for (char[] row : gameBoard) {
            for (char c : row) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    private void placePiece(int playerPos) {
        currentPlayerMovesList.add(playerPos);
        switch (playerPos) {
            //этот сахар подсказала IDE
            case 1 -> gameBoard[0][0] = currentPlayer.getSymbol();
            case 2 -> gameBoard[0][2] = currentPlayer.getSymbol();
            case 3 -> gameBoard[0][4] = currentPlayer.getSymbol();
            case 4 -> gameBoard[2][0] = currentPlayer.getSymbol();
            case 5 -> gameBoard[2][2] = currentPlayer.getSymbol();
            case 6 -> gameBoard[2][4] = currentPlayer.getSymbol();
            case 7 -> gameBoard[4][0] = currentPlayer.getSymbol();
            case 8 -> gameBoard[4][2] = currentPlayer.getSymbol();
            case 9 -> gameBoard[4][4] = currentPlayer.getSymbol();
        }
    }

    private void placePiece(int playerPos, Player player) {
        switch (playerPos) {
            case 1 -> gameBoard[0][0] = player.getSymbol();
            case 2 -> gameBoard[0][2] = player.getSymbol();
            case 3 -> gameBoard[0][4] = player.getSymbol();
            case 4 -> gameBoard[2][0] = player.getSymbol();
            case 5 -> gameBoard[2][2] = player.getSymbol();
            case 6 -> gameBoard[2][4] = player.getSymbol();
            case 7 -> gameBoard[4][0] = player.getSymbol();
            case 8 -> gameBoard[4][2] = player.getSymbol();
            case 9 -> gameBoard[4][4] = player.getSymbol();
        }
    }

    private int checkInputPosition(){
        Scanner scanner = new Scanner(System.in);
        Integer position = null;
        String input;

        while (position == null || player1Moves.contains(position) || player2Moves.contains(position)) {
            if (position != null) {
                System.out.print("Position taken! Enter a free position:");
            }
            input = scanner.nextLine();
            while (!input.matches("[0-9]")) {
                System.out.print("Position incorrect! Enter a correct position:");
                input = scanner.nextLine();
            }

            position = Integer.parseInt(input);

            if(position == 0){
                position = null;
                int command = -1;
                while(command == -1) {
                    try {
                        printPauseMenu();
                        var inputCommand = scanner.nextLine();
                        command = Integer.parseInt(inputCommand);
                        switch (command) {
                            case 1 -> saveGame();
                            case 2 -> loadGame();
                            //TODO придумать как завершать игру (из идей: пробрасывать exception и ловить его в main)
                            /*case 3 :
                                break;*/
                            case 0 -> System.out.print("Enter your placement (1-9): ");
                            default -> {
                                command = -1;
                                System.out.println("Sorry, there is no such command.");
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Sorry, there is no such command.");
                    }
                }
            }
        }
        return position;
    }

    private String checkWinner() {
        for (List<Integer> l : WINCONDITIONS) {
            if (currentPlayerMovesList.containsAll(l)) {
                if (!currentPlayer.isComputer()) {
                    return "Congratulation " + currentPlayer.getName() + ", you won!";
                } else return "Computer wins! Sorry :(";
            }
        }
        // Вынес следующий if из цикла, т.к. если игрок делает последний возможный ход
        // и он не попадает под первое условие победы, то объявляется ничья.
        // Хотя в видео он включен в цикл, ссылка с таймкодом: https://youtu.be/gQb3dE-y1S4?t=1380
        if (player1Moves.size() + player2Moves.size() == 9) return "TIE!";
        return "";
    }

    public void printPauseMenu(){
        System.out.println("1. Save game");
        System.out.println("2. Load game");
        //System.out.println("3. Quit game");
        System.out.println("0. Resume game");
    }

    private void resetGame(){
        //очищаем стартовую нумерацию поля
        int i = '1';
        for (int x = 0; x < 5; x = x + 2) {
            for (int y = 0; y < 5; y = y + 2) {
                gameBoard[x][y] = (char) i;
                i++;
            }
        }

        //очищаем списки ходов игроков
        this.player1Moves.clear();
        this.player2Moves.clear();
    }

    private void saveGame(){
        File yourFile = new File(FILE_NAME);
        try {
            yourFile.createNewFile();
            FileOutputStream oFile = new FileOutputStream(yourFile, false);

            ObjectOutputStream objectStream = new ObjectOutputStream(oFile);

            objectStream.writeObject(player1);
            objectStream.writeObject(player2);
            objectStream.writeObject(player1Moves);
            objectStream.writeObject(player2Moves);
            objectStream.writeObject(player1Move);

            objectStream.close();
            oFile.close();

        } catch (IOException e){
            //TODO обработать exception
            System.out.println(e.getMessage());
        }
    }

    public void loadGame() throws Exception{
        File file = new File(FILE_NAME);
        if(file.exists()){
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            this.player1 = (Player) objectInputStream.readObject();
            this.player2 = (Player) objectInputStream.readObject();

            this.player1Moves = (ArrayList<Integer>) objectInputStream.readObject();
            this.player2Moves = (ArrayList<Integer>) objectInputStream.readObject();

            this.player1Move = (Boolean) objectInputStream.readObject();

            fileInputStream.close();

            for (int x = 0; x < 5; x = x + 2) {
                for (int y = 0; y < 5; y = y + 2) {
                    gameBoard[x][y] = ' ';
                }
            }

            for (int pos : player1Moves) {
                placePiece(pos, player1);
            }
            for (int pos : player2Moves) {
                placePiece(pos, player2);
            }

            printGameBoard();

            System.out.println("Welcome back!");
            if(!this.gameStarted) {
                startGame();
            }
        } else System.out.println("Save file not found");
    }
}