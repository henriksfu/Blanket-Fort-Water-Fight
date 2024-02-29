package UI;

import java.util.Scanner;

import Model.gameplay;

public class TextUI {

    public static void main(String[] args) {
        

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of opponents: ");
        String no_of_opponents = scanner.nextLine();

        String[] parts = no_of_opponents.split("\\s+"); 
        int convertedValue;

        try {
            convertedValue = Integer.parseInt(parts[0]);
        } catch (NumberFormatException e) {
            convertedValue = 5;
        }
    

        gameplay game = new gameplay(convertedValue);       

        System.out.println("Starting the game with " + game.getNo_of_opponents() + " forts");
        System.out.println("------------------------");
        System.out.println("Welcome to Fort Defence");
        System.out.println("By Henrik Sachdeva");
        System.out.println("------------------------");


        if(no_of_opponents.endsWith("--cheat")) {
            game.getBoard().printBoard();
        }

        game.checkCheat(String.valueOf(game.getNo_of_opponents()));
        game.playGame();

        scanner.close();
    }
    
}
