package Model;

import java.util.Random;

public class gameBoard {
    private static final int board_size = 10;
    private char[][] board;
    private Fort[] forts;

    public gameBoard() {
        initializeBoard();
    }

    public void placeElemets(int x, int y, char element) {
        board[x][y] = element;
    }

    //gameBoard constructor
    public gameBoard(int no_of_opponents) {
        initializeBoard();
        placeFortsRandomly(no_of_opponents);
    }

    //gameBoard initializer
    private void initializeBoard() {
        board = new char[board_size][board_size];
        for (int i = 0; i < board_size; i++) {
            for (int j = 0; j < board_size; j++) {
                board[i][j] = '~';
            }
        }
    }

    private void placeFortsRandomly(int no_of_opponents) {
        forts = new Fort[no_of_opponents];
        for(int i = 0; i < no_of_opponents; i++) {
            Fort fort = generateRandomFort();
            while(!isValidFort(fort)) {
                fort = generateRandomFort();
            }
            placeFort(fort);
            forts[i] = fort;
        }
    }

    private  Fort generateRandomFort() {
        Random random = new Random();
        // char[][] shape = getRandomPolyominoShape();
        char[][] shape = {{'F', 'F'}, {'F', 'F'}};
        int x = random.nextInt(board_size - shape.length + 1);
        int y = random.nextInt(board_size - shape[0].length + 1);

        return new Fort(x, y, shape);
    }

    public char [][] generatePolyominos(int size) {
        for(int i = 0; i < size; i++) {
            this.getRandomPolyominoShape(i);
        }

        return this.board;
    }

    public void getRandomPolyominoShape(int i) {
        Random random = new Random();

        int startX;
        int startY;

        do {
            startX = random.nextInt(10);
            startY = random.nextInt(10);
        } while (this.board[startX][startY] != '~');

        this.board[startX][startY] = (char) ('A' + i);
        int tempY = startY + 1;
        char startXChar = (char) ('A' + startX);
        String position = startXChar + Integer.toString(tempY);
        System.out.println("StartX: " + position);

        int prevDirection = -1; // Initialize prevDirection
        int fortSize = 1; // Start with 1 since the first cell is already filled
        // System.out.println("EMPTY BOARD");
        // this.printBoard();

        do {
            // Get a new direction that is not opposite to the previous direction
            int newDirection;
            do {
                newDirection = random.nextInt(4);
            } while (isOppositeDirection(prevDirection, newDirection) || isValidDirection(startX, startY, newDirection));

            prevDirection = newDirection;
            

            System.out.println("New Direction: " + newDirection);

            // Update the board based on the new direction
            switch (newDirection) {
                case 0:
                    // Up
                    if (startX - 1 >= 0 && this.board[startX - 1][startY] == '~') {
                        this.board[startX - 1][startY] = (char) ('A' + i);
                        startX--;
                        fortSize++;

                    }
                    break;

                case 1:
                    // Down
                    if (startX + 1 < 10 && this.board[startX + 1][startY] == '~') {
                        this.board[startX + 1][startY] = (char) ('A' + i);
                        startX++;
                        fortSize++;

                    }
                    break;

                case 2:
                    // Left
                    if (startY - 1 >= 0 && this.board[startX][startY - 1] == '~') {
                        this.board[startX][startY - 1] = (char) ('A' + i);
                        startY--;
                        fortSize++;

                    }
                    break;

                case 3:
                    // Right
                    if (startY + 1 < 10 && this.board[startX][startY + 1] == '~') {
                        this.board[startX][startY + 1] = (char) ('A' + i);
                        startY++;;
                        fortSize++;

                    }
                    break;

                default:
                    break;
            }
        } while (fortSize < 5); // Change the loop condition to fortSize <= 5

        System.out.println("Board with Fort");
        this.printBoard();
    }

    private boolean isOppositeDirection(int prevDirection, int newDirection) {
        // Check if the new direction is opposite to the previous direction
        return (prevDirection == 0 && newDirection == 1) ||
               (prevDirection == 1 && newDirection == 0) ||
               (prevDirection == 2 && newDirection == 3) ||
               (prevDirection == 3 && newDirection == 2);
    }

    private boolean isValidDirection(int startX, int startY, int newDirection) {
        // Check if the new direction is valid
        switch (newDirection) {
            case 0:
                // Up
                return startX - 1 < 0;

            case 1:
                // Down
                return startX + 1 > 10;

            case 2:
                // Left
                return startY - 1 < 0;

            case 3:
                // Right
                return startY + 1 > 10;

            default:
                return false;
        }
    }

    private boolean isValidFort(Fort fort) {
        return board[fort.getX()][fort.getY()] == '~';
    }

    private void placeFort(Fort fort) {
        board[fort.getX()][fort.getY()] = 'F';
    }

    public void printBoard() {
        System.out.print("  ");
        for (int i = 0; i < board_size; i++) {
            System.out.print(i + 1 + " ");
        }
        System.out.println();

        for (int i = 0; i < board_size; i++) {
            System.out.print((char)('A' + i) + " ");
            for (int j = 0; j < board_size; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void printShape(char[][] shape) {
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                System.out.print(shape[i][j] + " ");
            }
            System.out.println();
        }
    }


    public static void main(String[] args) {
        gameBoard board = new gameBoard();
        // board.printBoard();
        board.generatePolyominos(5);

    //     String charValue = String.valueOf(100);
    //     System.out.println(charValue);
    }

}