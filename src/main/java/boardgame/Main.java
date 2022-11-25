package boardgame;

/**
 * Main class for running program
 * @author daniellakonert
 */
public class Main {
    private static Runner ticTacToe = new Runner();
    private static TextUI ui = new TextUI();

    /**
     * Main
     * @param args
     */
    public static void main(String[] args) {
        ui.printBoard(ticTacToe.getGameStateMessage()); //prints board in the beginning
        while (!(ticTacToe.isDone())){
            boolean validTurn = false;
            while (!validTurn) {
                ui.playerTurn(ticTacToe.getTurn());
                int choice = ui.getPlayerInput(ticTacToe.getTurn());
                if (choice == -2) { //load
                    ticTacToe.loadSavedString(FileLoadSave.load(ui.fileName()));
                    ui.printBoard(ticTacToe.getGameStateMessage());
                } else if (choice == -1){ //save
                    FileLoadSave.save(ui.fileName(),ticTacToe.getStringToSave());
                } else if (choice < 0 || choice > 8){ //out of bounds
                    ui.errorBounds();
                } else { //player position
                    int across = choice % 3;
                    int over = choice / 3;
                    validTurn = ticTacToe.takeTurn(across, over, ticTacToe.getTurn());
                    if (!validTurn) {
                        ui.errorMove(); //player played invalid move
                    }
                }
            }
            ui.printBoard(ticTacToe.getGameStateMessage());
        }
        ui.gameEnd(ticTacToe.getWinner()); //print winner/tie message
    }
}
