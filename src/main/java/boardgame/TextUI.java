package boardgame;

import java.util.Scanner;

/**
 * Main class for interacting with user for Tic Tac Toe
 * @author daniellakonert
 */
public class TextUI {

    private static final Scanner IO = new Scanner(System.in);

    /**
     * Prints out current board
     */
    public void printBoard(String board) {

        System.out.println(board);
    }

    /**
     * Tells user game ends with whatever winner or tie
     * @param code
     */
    public void gameEnd(int code){
        if (code == 0) {
            System.out.println("The Game was a Tie!");
        } else if (code == 1) {
            System.out.println("The winner is Player X");
        } else if (code == 2) {
            System.out.println("The winner is Player O");
        }

    }

    /**
     * Error - out of bounds
     */
    public void errorBounds(){
        System.out.println("Error - Out of Bounds");
    }

    /**
     * error - illegal move
     */
    public void errorMove(){
        System.out.println("Illegal Move! Try Again");
    }

    /**
     * which players turn it is
     * @param turn
     */
    public void playerTurn(int turn){
        if (turn == 1){
            System.out.println("Turn = X");
        } else {
            System.out.println("Turn = O");
        }

    }

    /**
     * Asks user to enter piece to place
     * @param player
     * @return piece
     */
    public int getPlayerInput(int player){
        int input;

        System.out.println("Enter position to place piece (0-8) or enter -1 to save or -2 to load: ");

        try {
            input = IO.nextInt();
        } catch (Exception e){
            input = -1;
        }
        IO.nextLine();
        return input;
    }

    /**
     * Asks user for a file name
     * @return filename
     */
    public String fileName() {
        System.out.println("Enter your saved filename: ");
        String input = IO.nextLine();

        return input;
    }
}
