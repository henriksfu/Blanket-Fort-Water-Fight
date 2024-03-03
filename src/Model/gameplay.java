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
        char rowLetter;
        int columnNumber;

        while (true) {
            try {
                rowLetter = Character.toUpperCase(userInput.charAt(0));
                columnNumber = Integer.parseInt(userInput.substring(1));
                if (rowLetter >= 'A' && rowLetter <= 'J' && columnNumber >= 1 && columnNumber <= 10) {
                    break; 
                }

                System.out.println("Invalid target. Please enter a coordinate such as D10.");

            } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
                System.out.println("Invalid target. Please enter a coordinate such as D10.");
            }
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter a move: ");
            userInput = scanner.nextLine();
            scanner.close();
        }
        int rowIndex = rowLetter - 'A';
        int columnIndex = columnNumber - 1;
        return new int[]{rowIndex, columnIndex};
    }


    public void playerHit(String position) {
        int[] coordinates = convertMoveToMatrixCoordinates(position);

        if (board.getChar(coordinates[0], coordinates[1]) != '.') {
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

        if(board.getChar(coordinates[0], coordinates[1]) == '.') {
            board.setChar(coordinates[0], coordinates[1], ' ');
            System.out.println("Miss!");
        }
    }

    public void opponentHit() {
        for(int i = 0; i < no_of_opponents; i++) {
            total_score += opponents[i].getDamage();
            System.out.println("Opponent #" + (i + 1) + " of " + no_of_opponents + " shot you for " + opponents[i].getDamage() + " points");
        }
    }

    public void playGame() {

        do {
            if(this.turn == 0){
                System.out.println("Game Board: ");
                board.printHiddenBoard();

                Scanner sc = new Scanner(System.in);
                System.out.println("Enter your move: ");
                String position = sc.nextLine();

                playerHit(position);
                System.out.println();
                System.out.println();
                setTurn(1);

                sc.close();
            }
            else {
                opponentHit();
                System.out.println();
                System.out.println();
                setTurn(0);
            }

            System.out.println("Opponent score: " + total_score + "/2500");
        } while (!allOpponentsDefeated() && total_score < 2500);

        if(allOpponentsDefeated()) {
            System.out.println("Congratulations! You won!");
        }
        else {
            System.out.println("I'm sorry, your fort is all wet! They win!");
        }
        System.out.println();
        System.out.println("Game Board: ");
        board.printBoard();
        System.out.println("Opponent score: " + total_score + "/2500");
        System.out.println("(Lower case fort letters are where you shot.)");
        System.out.println();
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

    

}