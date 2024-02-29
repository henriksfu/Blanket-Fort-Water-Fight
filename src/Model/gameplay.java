package Model;


import java.util.Scanner;

public class gameplay {
    private gameBoard board;
    private int total_score;
    private int no_of_opponents;
    private Opponent[] opponents;
    private int turn; // 0 for player, 1 for opponent
    

    public gameplay() {
        this.board = new gameBoard();
        this.total_score = 0;
        this.no_of_opponents = 0;
        // Opponent[] opponents = new Opponent[no_of_opponents];
        this.turn = 0;
    }

    public gameplay(int no_of_opponents) {
        this.board = new gameBoard(no_of_opponents);
        this.total_score = 0;
        this.no_of_opponents = no_of_opponents;
        this.opponents = new Opponent[no_of_opponents];
        this.turn = 0;
        for(int i = 0; i < no_of_opponents; i++) {
            opponents[i] = new Opponent((char)('A' + i));
        }
    }

    public void getOpponentsArray() {
        for(int i = 0; i < no_of_opponents; i++) {
            System.out.println(opponents[i].getID());
        }
    }

    public void getTurn() {
        if(turn == 0) {
            System.out.println("Player's turn");
        }
        else {
            System.out.println("Opponent's turn");
        }
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public gameBoard getBoard() {
        return board;
    }


    public int getTotal_score() {
        return total_score;
    }

    public void setTotal_score(int total_score) {
        this.total_score = total_score;
    }

    public int getNo_of_opponents() {
        return no_of_opponents;
    }

    public void setNo_of_opponents(int no_of_opponents) {
        this.no_of_opponents = no_of_opponents;
        this.board.generatePolyominos(no_of_opponents);
    }

    public Opponent findOpponentByID(Opponent[] opponents, char ID) {
        for(int i = 0; i < no_of_opponents; i++) {
            if(opponents[i].getID() == ID) {
                return opponents[i];
            }
        }
        return null;
    }

    public boolean allOpponentsDefeated() {
        for(int i = 0; i < no_of_opponents; i++) {
            if(opponents[i].getUndamaged_forts() > 0) {
                return false;
            }
        }
        return true;
    }

    public static int[] convertMoveToMatrixCoordinates(String userInput) {
        // Extract row and column information from user input
        char rowLetter = userInput.charAt(0);
        int columnNumber = Integer.parseInt(userInput.substring(1));

        // Convert row letter to numeric index
        int rowIndex = rowLetter - 'A';

        // Adjust column number to match array indices (starting from 0)
        int columnIndex = columnNumber - 1;

        // Return the matrix coordinates as an array
        return new int[]{rowIndex, columnIndex};
    }


    public void playerHit(String position) {
        
        System.out.println("Hitting at " + position);
        int[] coordinates = convertMoveToMatrixCoordinates(position);

        if (board.getChar(coordinates[0], coordinates[1]) != '~') {
            char ch = board.getChar(coordinates[0], coordinates[1]);
            board.setChar(coordinates[0], coordinates[1], Character.toLowerCase(ch));
            System.out.println("Hit!");
            if(findOpponentByID(opponents, ch) != null) {
                if(findOpponentByID(opponents, ch).getUndamaged_forts() > 0) {
                    findOpponentByID(opponents, ch).decreaseUndamaged_forts();
                }
                else
                {
                    System.out.println("Opponent has been defeated!");
                }
            }
            else {
                System.out.println("SAME HIT AGAIN!");
            }
            
        }

        if(board.getChar(coordinates[0], coordinates[1]) == '~') {
            board.setChar(coordinates[0], coordinates[1], ' ');
            System.out.println("Miss!");
        }
    }

    public void opponentHit() {
        for(int i = 0; i < no_of_opponents; i++) {
            total_score += opponents[i].getDamage();
            System.out.println("Opponent " + opponents[i].getID() + " has done " + opponents[i].getDamage() + " damage!");
        }
    }

    public void playGame() {

        do {
            if(this.turn == 0){
                board.printHiddenBoard();

                Scanner sc = new Scanner(System.in);
                System.out.println("Enter the position to hit: ");
                String position = sc.nextLine();

                playerHit(position);
                System.out.println();
                System.out.println();
                setTurn(1);
            }
            else {
                opponentHit();
                System.out.println();
                System.out.println();
                setTurn(0);
            }

            System.out.println("Total score: " + total_score + "/2500");
        } while (!allOpponentsDefeated() && total_score < 2500);

        if(allOpponentsDefeated()) {
            System.out.println("Congratulations! You have defeated all the opponents!");
        }
        else {
            System.out.println("You have lost the game!");
        }
    }

    public void printHiddenBoard() {
        board.printHiddenBoard();
    }

    public void checkCheat(String no_of_opponents) {
        if(no_of_opponents.endsWith("--cheat")) {
            System.out.println("Cheat code activated!");
            board.printBoard();           
        }
    }

    public void getUserInput() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of opponents: ");
        String no_of_opponents = scanner.nextLine();

        char firstChar = no_of_opponents.charAt(0);
        int convertedValue = Character.getNumericValue(firstChar);
        gameplay game = new gameplay(convertedValue);
        game.checkCheat(no_of_opponents);

        game.playGame();
    }

}