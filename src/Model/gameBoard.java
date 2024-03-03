package Model;

import java.util.Random;

public class gameBoard {
    private static final int board_size = 10;
    private char[][] board;
    private char [][] hiddenBoard;

    public gameBoard() {
        initializeBoard();
        this.hiddenBoard = new char[board_size][board_size];
    }

    public gameBoard(int no_of_opponents) {
        initializeBoard();
        this.hiddenBoard = new char[board_size][board_size];
        this.generatePolyominos(no_of_opponents);
    }

    public char getChar(int x, int y) {
        return board[x][y];
    }

    public void setChar(int x, int y, char ch) {
        board[x][y] = ch;
    }

    private void initializeBoard() {
        board = new char[board_size][board_size];
        for (int i = 0; i < board_size; i++) {
            for (int j = 0; j < board_size; j++) {
                board[i][j] = '.';
            }
        }
    }

    public char [][] generatePolyominos(int size) {
        for(int i = 0; i < size; i++) {
            this.getRandomPolyominoShape(i);
        }
        return this.board;
    }

    public void getRandomPolyominoShape(int i) {
        Random random = new Random();

        final int max_attemts = 5;
        int attempts = 0;

        int startX;
        int startY;

        do {
            if(attempts > max_attemts) {
                System.out.println("Error: Unable to place 20 on the board.");
                System.out.println("Try running game again with fewer forts.");
                return;
            }

            startX = random.nextInt(10);
            startY = random.nextInt(10);

            attempts++;
        } while (this.board[startX][startY] != '.');

        this.board[startX][startY] = (char) ('A' + i);

        int prevDirection = -1;
        int fortSize = 1; 

        do {
            int newDirection;
            do {
                newDirection = random.nextInt(4);
            } while (isOppositeDirection(prevDirection, newDirection) || isValidDirection(startX, startY, newDirection));

            prevDirection = newDirection;
            switch (newDirection) {
                case 0:
                    // Up
                    if (startX - 1 >= 0 && this.board[startX - 1][startY] == '.') {
                        this.board[startX - 1][startY] = (char) ('A' + i);
                        startX--;
                        fortSize++;

                    }
                    break;

                case 1:
                    // Down
                    if (startX + 1 < 10 && this.board[startX + 1][startY] == '.') {
                        this.board[startX + 1][startY] = (char) ('A' + i);
                        startX++;
                        fortSize++;

                    }
                    break;

                case 2:
                    // Left
                    if (startY - 1 >= 0 && this.board[startX][startY - 1] == '.') {
                        this.board[startX][startY - 1] = (char) ('A' + i);
                        startY--;
                        fortSize++;

                    }
                    break;

                case 3:
                    // Right
                    if (startY + 1 < 10 && this.board[startX][startY + 1] == '.') {
                        this.board[startX][startY + 1] = (char) ('A' + i);
                        startY++;;
                        fortSize++;

                    }
                    break;

                default:
                    break;
            }
        } while (fortSize < 5); 
    }

    private boolean isOppositeDirection(int prevDirection, int newDirection) {
        return (prevDirection == 0 && newDirection == 1) ||
               (prevDirection == 1 && newDirection == 0) ||
               (prevDirection == 2 && newDirection == 3) ||
               (prevDirection == 3 && newDirection == 2);
    }

    private boolean isValidDirection(int startX, int startY, int newDirection) {
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

    public void updateHiddenBoard() {
        for(int i = 0; i < board_size; i++) {
            for(int j = 0; j < board_size; j++) {
                char cell = board[i][j];
                if(cell == ' ' || Character.isLowerCase(cell)) {
                    hiddenBoard[i][j] = cell;
                }
                else {
                    hiddenBoard[i][j] = '~';
                }
            }
        }

    }

    public void printHiddenBoard() {
        updateHiddenBoard();
        System.out.print("  ");
        for (int i = 0; i < board_size; i++) {
            System.out.print(i + 1 + " ");
        }
        System.out.println();

        for (int i = 0; i < board_size; i++) {
            System.out.print((char)('A' + i) + " ");
            for (int j = 0; j < board_size; j++) {
                System.out.print(hiddenBoard[i][j] + " ");
            }
            System.out.println();
        }
    }
}