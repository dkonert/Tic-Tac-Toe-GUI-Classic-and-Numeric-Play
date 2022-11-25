package boardgame;

import java.util.Objects;

/**
 * Class for running and storing a game of Tic Tac Toe
 */
public class NumericalTicTacToe extends BoardGame implements Saveable {
    //init variables

    private int depth;
    private char[] board =
            {'X', '|', 'X', '|', 'X', '\n',
                    '-', '+', '-', '+', '-', '\n',
                    'X', '|', 'X', '|', 'X', '\n',
                    '-', '+', '-', '+', '-', '\n',
                    'X', '|', 'X', '|', 'X', '\n'};
    private final int[] posToIndex = {0, 2, 4, 12, 14, 16, 24, 26, 28};
    private final int length = 9;
    private int turn = 1;
    private String[] playerOneMoves = {"1","3","5","7","9"};
    private String[] playerTwoMoves = {"2","4","6","8"};

    /**
     * Main class for running Tic Tac Toe Game
     */
    NumericalTicTacToe() {
        super(3,3);
        depth = 0; //and depth at zero
    }


    /**
     * Determines whether someone has won the game
     *
     * @return true as long as game has not been won
     */
    public int getWinner() {
        int[] numbers = new int[9];
        for (int i = 0; i < 9; i++){ //convert array from char to int
            numbers[i] = Character.getNumericValue(board[posToIndex[i]]);
        }
        int winner = calcWinner(numbers);
        if (depth == 9) {
            return 0;
        } //if depth hits 9 game is a tie
        if (winner == 1){
            return 2;
        } else if (winner == 2) {
            return 1;
        }
        return -1;
    }

    public int calcWinner(int[] numbers){
        int winner = -1; //init question mark character to winner variable
        for (int i = 0; i < 3; i++) { //checks to see if player made a line
            if ((numbers[i] + numbers[i + 3] + numbers[i + 6]) == 15) {
                winner = turn; //there is a winner they made a line
                break; //leaves loop
            }
        }
        if (winner == -1) { //winner made diagonal line and sets winne
            if ((numbers[0] + numbers[4] + numbers[8]) == 15){
                winner = turn;
            }
            if ((numbers[2] + numbers[4] + numbers[6]) == 15){
                winner = turn;
            }
        }
        if (winner == -1) {
            for (int i = 0; i <= 6; i += 3) { //checks which player made horizontal/vertical line
                if ((numbers[i] + numbers[i + 1] + numbers[i + 2]) == 15) {
                    winner = turn; //sets player in i position as winner
                    break; //leaves loop
                }
            }
        }
        return winner;
    }
    private boolean checkBoard(int position) { //function that checks if position has been taken
        if (board[posToIndex[position]] != 'X') {
            return true;
        }
        return false;
    }

    private boolean checkMove(String input){
        if (turn == 1) {
            for (int i = 0; i < playerOneMoves.length; i++){
                if (Objects.equals(playerOneMoves[i], input)){
                    playerOneMoves[i] = "X";
                    return true;
                }
            }
        } else if (turn == 2) {
            for (int i = 0; i < playerTwoMoves.length; i++){
                if (Objects.equals(playerTwoMoves[i], input)){
                    playerTwoMoves[i] = "X";
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    public boolean takeTurn(int across, int over, String input) {  //init function to track move
        int position = (3 * over) + across;

        if ((position >= length) || (position < 0)) {  //if player chooses a position out of bounds
            return false;
        } else {

            if (this.checkBoard(position)) { //if player chooses a position that has already been chosen
                return false;
            } else if (this.checkMove(input)){
                depth++; //depth into game increases
                board[posToIndex[position]] = input.charAt(0); //place number on the board
                setValue(across + 1,over + 1, input);
            } else {
                return false;
            }
        }
        if (turn == 1){
            turn = 2;
        } else {
            turn = 1;
        }
        return true;
    }

    @Override
    public boolean takeTurn(int across, int down, int input){
        if (input >= 1 && input <= 9){
            return takeTurn(across, down, Integer.toString(input));
        }
        return false;
    }

    @Override
    public boolean isDone(){
        int done = getWinner();
        if (done == -1) {
            return false;
        }
        return true;
    }

    @Override
    public String getGameStateMessage(){
        return String.valueOf(board);
    }

    @Override
    public String getStringToSave() {
        String data = "";
        for (int i = 1; i <= 3; i++){
            for (int j = 1; j <= 3; j++){
                data = data + getCell(j,i) + ",";
            }
        }
        return data;
    }

    @Override
    public void loadSavedString(String toLoad) {
        int evenCount = 0;
        int oddCount = 0;
        int i = 0;
        String[] moves = toLoad.split(",");
        for (String move: moves) {
            int across = i % 3;
            int over = i / 3;
            if (!move.equals(" ") && !move.equals("")){
                if (Integer.parseInt(move) % 2 == 1){
                    oddCount += 1;
                }else{
                    evenCount += 1;
                }
                board[posToIndex[i]] = move.charAt(0);
            }

            setValue(across +1, over + 1, move);
            i += 1;
        }
        if (oddCount > evenCount) {
            turn = 2;
        } else {
            turn = 1;
        }
        depth = oddCount + evenCount;
    }

    @Override
    public void newGame(){
        super.newGame();
        turn = 1;
        board = new char[] {'X', '|', 'X', '|', 'X', '\n',
                '-', '+', '-', '+', '-', '\n',
                'X', '|', 'X', '|', 'X', '\n',
                '-', '+', '-', '+', '-', '\n',
                'X', '|', 'X', '|', 'X', '\n'};
        depth = 0;
        playerOneMoves = new String[]{"1", "3", "5", "7", "9"};
        playerTwoMoves = new String[]{"2", "4", "6", "8"};
    }


    public int getTurn(){
        return turn;
    }
}


