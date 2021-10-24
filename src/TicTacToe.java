import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class TicTacToe {

    static ArrayList<Integer> playerPositions = new ArrayList<>();
    static ArrayList<Integer> cpuPositions = new ArrayList<>();

    public static void main(String[] args) {

        char[][] gameBoard = {
                {'1', '|', '2', '|', '3'},
                {'—', '+', '—', '+', '—'},
                {'4', '|', '5', '|', '6'},
                {'—', '+', '—', '+', '—'},
                {'7', '|', '8', '|', '9'}}; //добавил нумерацию игрового поля при старте

        printGameBoard(gameBoard);

        for(int x = 0; x < 5; x = x + 2) {
            for (int y = 0; y < 5; y = y + 2) {
                gameBoard[x][y]=' ';
            }
        }

        while (true) {
            Scanner scan = new Scanner(System.in);
            int playerPos;
            System.out.print("Enter your placement (1-9): ");
            String name = scan.nextLine();
            while (!name.matches("[1-9]")) {
                System.out.print("Position incorrect! Enter a correct position:");
                scan = new Scanner(System.in);
                name = scan.nextLine();
            }
            playerPos = Integer.parseInt(name);
            while (playerPositions.contains(playerPos) || cpuPositions.contains(playerPos)) {
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

            placePiece(gameBoard, playerPos, "player");

            String result = checkWinner();
            if (result.length() > 0) {
                System.out.println(result);
                break;
            }

            Random rand = new Random();
            int cpuPos = rand.nextInt(9) + 1;
            while(playerPositions.contains(cpuPos) || cpuPositions.contains(cpuPos)) {
                cpuPos = rand.nextInt(9) + 1;
            }
            placePiece(gameBoard, cpuPos, "cpu");

            printGameBoard(gameBoard);

            result = checkWinner();
            if (result.length() > 0) {
                System.out.println(result);
                break;
            }

        }

    }

    public static void printGameBoard(char[][] gameBoard) {
        for (char[] row : gameBoard) {
            for (char c : row) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    public static void placePiece(char[][] gameBoard, int pos, String user) {

        char symbol = ' ';

        if (user.equals("player")) {
            symbol = 'X';
            playerPositions.add(pos);
        } else {
            symbol = 'O';
            cpuPositions.add(pos);
        }
        switch (pos) {
            case 1:
                gameBoard[0][0] = symbol;
                break;
            case 2:
                gameBoard[0][2] = symbol;
                break;
            case 3:
                gameBoard[0][4] = symbol;
                break;
            case 4:
                gameBoard[2][0] = symbol;
                break;
            case 5:
                gameBoard[2][2] = symbol;
                break;
            case 6:
                gameBoard[2][4] = symbol;
                break;
            case 7:
                gameBoard[4][0] = symbol;
                break;
            case 8:
                gameBoard[4][2] = symbol;
                break;
            case 9:
                gameBoard[4][4] = symbol;
                break;
            default:
                break;
        }

    }


    public static String checkWinner() {

        List topRow = Arrays.asList(1, 2, 3);
        List midRow = Arrays.asList(4, 5, 6);
        List botRow = Arrays.asList(7, 8, 9);
        List leftCol = Arrays.asList(1, 4, 7);
        List midCol = Arrays.asList(2, 5, 8);
        List rightRow = Arrays.asList(3, 6, 9);
        List cross1 = Arrays.asList(1, 5, 9);
        List cross2 = Arrays.asList(3, 5, 7);

        List<List> winningConditions = new ArrayList<>();

        winningConditions.add(topRow);
        winningConditions.add(midRow);
        winningConditions.add(botRow);
        winningConditions.add(leftCol);
        winningConditions.add(midCol);
        winningConditions.add(rightRow);
        winningConditions.add(cross1);
        winningConditions.add(cross2);

        for (List l : winningConditions) {
            if (playerPositions.containsAll(l)) {
                return "Congratulation you won!";
            }
            if (cpuPositions.containsAll(l)) {
                return "CPU wins! Sorry :(";
            }
        }
        // Вынес следующий if из цикла, т.к. если игрок делает последний возможный ход и он не попадает под первое условие победы, то объявляется ничья.
        // Хотя в видео он включен в цикл, ссылка с таймкодом: https://youtu.be/gQb3dE-y1S4?t=1380
        if (playerPositions.size() + cpuPositions.size() == 9) return "TIE!";
        return "";
    }
}