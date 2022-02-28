package aa_practicafinalsudoku;

import java.util.ArrayList;
import java.util.List;

/**
 * @author paubonetalcover
 */
public class Sudoku {

    private static final int files = 9, columnes = 9;
    private static List listTaulers = new ArrayList();

    /**
     * Metode que crida al metode que resoldra el sudoku amb recursivitat
     * Prepara la primera cridada recursiva del metode
     *
     * @param tauler
     * @param listSolutions
     */
    public static List sudokuRecursiu(int[] tauler) {
        listTaulers.add(tauler);    //afegim el tauler sense soluionar
        sudokuRecursiu(tauler, 0, listTaulers);
        return listTaulers;
    }

    /**
     * Metode que resol el sudoku amb backtracking i recursivament
     *
     * @param tauler
     * @param posicio
     * @param listSolutions
     */
    private static void sudokuRecursiu(int[] tauler, int posicio, List listSolutions) {
        if (posicio == tauler.length) {     //cas base,  comprova si es solució
            int[] aux = new int[tauler.length];
            System.arraycopy(tauler, 0, aux, 0, tauler.length);
            listSolutions.add(aux);         //afegeix la solució

        } else if (tauler[posicio] == 0) {
            for (int i = 1; i < 10; i++) {
                if (isValid(tauler, posicio, i)) {
                    tauler[posicio] = i;
                    sudokuRecursiu(tauler, posicio + 1, listSolutions);
                    tauler[posicio] = 0;
                }
            }
        } else {
            sudokuRecursiu(tauler, posicio + 1, listSolutions);
        }
    }

    /**
     * Metode que resol el sudoku iterativament (pas de recursiu a iteratiu)
     *
     * @param tauler
     * @param listSolutions
     */
    public static List sudokuIteratiu(int[] tauler) {
        int t[] = new int[tauler.length];       //array solucions
        int auxTauler[] = new int[tauler.length];
        System.arraycopy(tauler, 0, auxTauler, 0, tauler.length);
        listTaulers.clear();        //netejam
        listTaulers.add(auxTauler); //afegim el tauler sense solucio
        boolean tornar = false;

        for (int i = 0; i < t.length; i++) t[i] = -1; //tot a -1 dins l'array

        int k = 0;                  //nivell profunditat del node que s'esta processant
        while (k >= 0) {            //encara no hem recorregut tot l'arbre
            
            if (k < tauler.length && auxTauler[k] == 0) {//no es un preestablert
                if (t[k] < 9) {         //no se han procesast tots els valors pel node k
                    t[k]++;             //processam node k;
                    if (k == tauler.length - 1  && isValid(tauler, k, t[k])) {       //cas base,  comprova si es solució
                        tauler[k] = t[k];           //se suposa que fica el valor k correcte dins el tauler
                        int[] aux = new int[tauler.length];
                        System.arraycopy(tauler, 0, aux, 0, tauler.length);
                        listTaulers.add(aux);       //afegeix la solució

                    } else if (k < tauler.length - 1 && isValid(tauler, k, t[k])) {
                        tauler[k] = t[k];   //possibilitat de solucio
                        k++;                //passam al seguent nivell
                        tornar = false;     //finalitza el tornar si estava a true;
                    }
                    
                } else {            //s'han processat tots els nodes pel nivell k;
                    tornar = true;  //posa que vol tornar si es un valor preestabler el seguent
                    tauler[k] = 0;  //deixa el tauler a 0
                    t[k] = -1;      //deixa el tauler solucions, posicio k a -1
                    k--;            //baixa al seguent nivell
                }
            } else {            //posicio preestablerta
                if (tornar) k--;//es vol tornar i es una posicio preestablerta
                else k++;       //normalment anirem cap endavant
            }
        }
        return listTaulers;
    }
     

    /**
     * Metode que comprova si una numero introduit seria valid a la seva futura
     * posicio
     *
     * @return boolea de si es valid
     */
    public static boolean isValid(int[] tauler, int posicio, int numIntroduit) {
        if (numIntroduit < 1 || numIntroduit > 9) return false;
        
        int fila = (int) (posicio / 9); //retorna la fila
        int columna = posicio % 9;      //retorna la columna
        int comencFila = 0, comencColumna = 0;

        if (fila < 3)       comencFila = 0;
        else if (fila < 6)  comencFila = 3;
        else if (fila < 9)  comencFila = 6;
       
        if (columna < 3)        comencColumna = 0;
        else if (columna < 6)   comencColumna = 3;
        else if (columna < 9)  comencColumna = 6;
        
        for (int i = 0; i <= 8; i++) {
            if (tauler[fila * 9 + i] != 0 && tauler[fila * 9 + i] == numIntroduit) {
                return false;
            }       // Si es repeteix en la fila
            if (tauler[columna + i * 9] != 0 && tauler[columna + i * 9] == numIntroduit) {
                return false;
            } // Si es repeteix en la columna
        }

        for (int i = comencFila; i < comencFila + 3; i++) { //recorrem tota la regió 3x3
            for (int j = comencColumna; j < comencColumna + 3; j++) {
                if (tauler[i * 9 + j] != 0 && tauler[i * 9 + j] == numIntroduit) {
                    return false;
                }
            }
        }
        return true;  //si compleix les 3 restriccións es possible
    }

    /**
     * Metode que treu el tauler per la consola
     *
     * @param tauler
     */
    public static void print(int[] tauler) {
        for (int i = 0; i < files; i++) {
            for (int j = 0; j < columnes; j++) {
                System.out.print(tauler[i * 9 + j] + " ");
            }
            System.out.println();
        }
        System.out.println("\n\n");
    }

    /**
     * Metode que treu per consola les solucions del sudoku si les te La primera
     * matriu es el sudoku sense resoldre, les posteriors son les solucions
     */
    public static void printSolucions() {
        if (!listTaulers.isEmpty()) {
            System.out.println("Les solucions son: \n");
            for (int i = 0; i < listTaulers.size(); i++) {
                print((int[]) listTaulers.get(i));
            }
        } else {
            System.out.println("No hi ha solucio");
        }
    }

    /**
     * Metode que retorna el tauler que pasa per paramtre
     *
     * @return listTaulers
     */
    public static int[] getTauler(int n) {
        return (int[]) listTaulers.get(n);
    }

    /**
     * Metode que retorna el numero de solucions que hi ha
     *
     * @return listTaulers
     */
    public static int getNumSolucions() {
        int numSolucions = listTaulers.size();
        return numSolucions - 1;
    }
}
