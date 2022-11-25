package boardgame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Main class for managing loading and saving files
 * @author daniellakonert
 */
public class FileLoadSave {

    /**
     * Loads a saved file to continue the game
     * @param filename the file the user wants to open
     * @return board if it loaded and empty string if it failed to do so
     */

    public static String load(String filename) {
        String board = "";

        try {
            File file = new File(filename);
            Scanner io = new Scanner(file);


            while (io.hasNext()){
                board = board + io.next();
            }

        } catch (FileNotFoundException e) {
            return ""; //Invalid
        }

        return board; //Valid

    }

    /**
     * Saves the board to a file of the user's choosing
     * @param filename the user wants the file saved to
     * @return 0 if file saved -1 if it failed
     */
    public static int save(String filename, String board) {

        File file = new File(filename);

        FileWriter fw = null; //file writer object

        try {
            //create file writer for saving files - set to overwrite
            fw = new FileWriter(file, false);
            fw.write(board);
        } catch (IOException e) { //Handle file exceptions
            e.printStackTrace();
            return -1; //failed to save
        } finally { //close file writer after save
            try {
                if (fw != null) {
                    fw.flush();
                    fw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
}
