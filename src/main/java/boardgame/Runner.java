package boardgame;

/**
 * Main class for running and storing a game of Tic Tac Toe
 * @author daniellakonert
 */
public class Runner extends BoardGame implements Saveable {
    //init variables

    private int depth;
    private char[] board =
            {'0', '|', '1', '|', '2', '\n',
                    '-', '+', '-', '+', '-', '\n',
                    '3', '|', '4', '|', '5', '\n',
                    '-', '+', '-', '+', '-', '\n',
                    '6', '|', '7', '|', '8', '\n'};
    private final int[] posToIndex = {0, 2, 4, 12, 14, 16, 24, 26, 28};
    private final int length = 9;
    private int turn = 1;
    /**
     * Main class for running Tic Tac Toe Game
     */
    Runner() {
        super(3,3);
        depth = 0; //and depth at zero
    }


    /**
     * Determines whether someone has won the game
     *
     * @return true as long as game has not been won
     */
    public int getWinner() {

        char winner = calcWinner();
        if (depth == 9) { //if depth hits 9 game is a tie
            return 0;
        } else if (winner != '?') { //if winner does not equal question mark character the winner is the player set
            if (winner == 'X'){
                return 1;
            } else {
                return 2;
            }
        }

        return -1;
    }

    /**
     * Calculates if someone won the game
     * @return winner
     */
    public char calcWinner() {
        char winner = '?'; //init question mark character to winner variable
        for (int i = 0; i < 3; i++) { //checks to see if player made a line
            if (board[posToIndex[i]] == board[posToIndex[i + 3]]
                    && board[posToIndex[i + 3]] == board[posToIndex[i + 6]]) {
                winner = board[posToIndex[i]]; //there is a winner they made a line
                break; //leaves loop
            }
        }
        if (winner == '?') {
            if (board[posToIndex[0]] == board[posToIndex[4]]
                    && board[posToIndex[4]] == board[posToIndex[8]]) {
                winner = board[posToIndex[0]]; //winner made diagonal line and sets player to winner variable
            }
            if (board[posToIndex[2]] == board[posToIndex[4]]
                    && board[posToIndex[4]] == board[posToIndex[6]]) {
                winner = board[posToIndex[2]]; //winner made diagonal line and sets player to winner variable
            }
        }
        if (winner == '?') {
            for (int i = 0; i <= 6; i += 3) { //checks which player made horizontal/vertical line
                if (board[posToIndex[i]] == board[posToIndex[i + 1]]
                        && board[posToIndex[i + 1]] == board[posToIndex[i + 2]]) {
                    winner = board[posToIndex[i]]; //sets player in i position as winner
                    break; //leaves loop
                }
            }
        }
        return winner;
    }

    /**
     * Checks if the position the user picked was taken
     * @param position
     * @return true if they did false otherwise
     */
    private boolean checkBoard(int position) { //function that checks if position has been taken
        if (board[posToIndex[position]] == 'X' || board[posToIndex[position]] == 'O') {
            return true;
        }
        return false;
    }

    /**
     * Tracks user move sees
     * Position on board
     *  @param across across index, 1 based
     *  @param over  down index, 1 based
     * Player
     *  @param input  String input from game
     * @return false if position is out of bounds or taken true if it has been placed
     */
    @Override
    public boolean takeTurn(int across, int over, String input) {  //init function to track move
        int position = (3 * over) + across;
        char player;

        if (turn == 1) {
            player = 'X';
        }else{
            player = 'O';
        }
        if ((position >= length) || (position < 0)) {  //if player chooses a position out of bounds
            return false;
        } else {

            if (this.checkBoard(position)) { //if player chooses a position that has already been chosen
                return false;

            } else {
                depth++; //depth into game increases
                board[posToIndex[position]] = player; //makes the position equal to player
                setValue(across+1,over+1,""+player);
            }
        }
        if (turn == 1){
            turn = 2;
        }else {
            turn = 1;
        }
        return true;
    }

    /**
     * Inputs player
     * Position on board
     *  @param across across index, zero based
     *  @param down  down index, zero based
     * Number refering to player
     *  @param input  int input from game
     * @return player false otherwise
     */
    @Override
    public boolean takeTurn(int across, int down, int input){
        if (input == 1){
            return takeTurn(across,down,"X");
        } else if (input == 2){
            return takeTurn(across,down,"O");
        }
        return false;
    }

    /**
     * Is game done
     * @return true if game is done false otherwise
     */
    @Override
    public boolean isDone(){
        int done = getWinner();
        if (done == -1) {
            return false;
        }
        return true;
    }

    /**
     * Board
     * @return board
     */
    @Override
    public String getGameStateMessage(){
        return String.valueOf(board);
    }

    /**
     * Saves board
     * @return data of board
     */
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

    /**
     * loads file and outputs board
     * @param toLoad loading data from board
     */
    @Override
    public void loadSavedString(String toLoad) {
        int xCount = 0;
        int oCount = 0;
        int i = 0;
        String[] moves = toLoad.split(",");
        for (String move: moves) {
            int across = i % 3;
            int over = i / 3;
            if (move.equals("X")){ //X
                board[posToIndex[i]] = 'X';
                xCount += 1;
            } else if (move.equals("O")) { //O
                board[posToIndex[i]] = 'O';
                oCount += 1;
            }
            setValue(across +1, over + 1, move);
            i += 1;
        }
        if (xCount > oCount) {
            turn = 2;
        } else {
            turn = 1;
        }
        depth = xCount + oCount;
    }

    /**
     * Reset Game
     */
    @Override
    public void newGame(){
        super.newGame();
        turn = 1;
        board = new char[]{'0', '|', '1', '|', '2', '\n',
                '-', '+', '-', '+', '-', '\n',
                '3', '|', '4', '|', '5', '\n',
                '-', '+', '-', '+', '-', '\n',
                '6', '|', '7', '|', '8', '\n'};
        depth = 0;
    }

    /**
     * Returns which players turn it is
     * @return which player's turn
     */
    int getTurn(){
        return turn;
    }
}


