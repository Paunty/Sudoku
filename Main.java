package aa_practicafinalsudoku;

import java.util.ArrayList;
import java.util.List;

/**
 * @author paubonetalcover
 */
public class Main {

    private static List listSolutions;

    static int[] taulerExemple1 = { //exemple 1 sudoku
        3, 0, 6, 5, 0, 8, 4, 0, 0,
        5, 2, 0, 0, 0, 0, 0, 0, 0,
        0, 8, 7, 0, 0, 0, 0, 3, 1,
        0, 0, 3, 0, 1, 0, 0, 8, 0,
        9, 0, 0, 8, 6, 3, 0, 0, 5,
        0, 5, 0, 0, 9, 0, 0, 0, 0,
        1, 3, 0, 0, 0, 0, 2, 5, 0,
        0, 0, 0, 0, 0, 0, 0, 7, 4,
        0, 0, 5, 2, 0, 6, 3, 0, 0};
    
    static int[] taulerExemple2 = { //exemple 2 sudoku
        3, 1, 6, 5, 7, 8, 4, 0, 0,
        5, 2, 0, 0, 0, 0, 0, 0, 0,
        0, 8, 7, 0, 0, 0, 0, 3, 1,
        0, 0, 3, 0, 1, 0, 0, 8, 0,
        9, 0, 0, 8, 6, 3, 0, 0, 5,
        8, 5, 1, 7, 9, 2, 6, 4, 3,
        1, 3, 8, 9, 4, 7, 2, 5, 6,
        6, 9, 2, 3, 5, 1, 8, 7, 4,
        7, 4, 5, 2, 8, 6, 3, 1, 9 };

    /**
     * 
     * @param args 
     */
    
    public static void main(String[] args) {
        //Execucio normal
        //
        GUI_Final s = new GUI_Final();
        
        //Proves Iteratiu / Recursiu
        //
        //listSolutions = Sudoku.sudokuIteratiu(taulerExemple1);
        //listSolutions = Sudoku.sudokuRecursiu(taulerExemple1);
        //for (int i = 0; i < listSolutions.size(); i++) Sudoku.print((int[]) listSolutions.get(i));
    }
}
