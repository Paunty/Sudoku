package aa_practicafinalsudoku;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;

/**
 * @author paubonetalcover
 */
public class GUI_Final extends JFrame {

    //EXEMPLES
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

    static int[] taulerExemple2 = { //exemple 2 sudoku 4 solucions
        8, 0, 0, 6, 0, 0, 9, 0, 5,
        0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 2, 0, 3, 1, 0,
        0, 0, 7, 3, 1, 8, 0, 6, 0,
        2, 4, 0, 0, 0, 0, 0, 7, 3,
        0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 2, 7, 9, 0, 1, 0, 0,
        5, 0, 0, 0, 8, 0, 0, 3, 6,
        0, 0, 3, 0, 0, 0, 0, 0, 0};

    //ATRIBUTS
    private boolean recursiu = true;         //resoldrel amb iteratiu o recursiu, per defecte recursiu
    private static int tauler[];      //tauler que es es veu per pantalla
    private int numeroTauler = 0;     //numero del tauler que es mostra per pantalla
    private static List listSolutions;
    private static boolean solucionat = false;

    private static Container panelContinguts;        //declaracions del GUI
    private static TaulerNumeros taulerNoSolucio;
    private static TaulerNumeros taulerSolucio;

    public GUI_Final() {
        initzalitzacio();   //lcridam al inizialitzador del programa
    }

    private void initzalitzacio() {
////////////////////////////////////////////////////////////////////////////////
//                            MANIPULADOR D'EVENTS MENU                       //
////////////////////////////////////////////////////////////////////////////////
        ActionListener manipuladorEventosGeneral = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                switch (evento.getActionCommand()) {
                    case "ENRERE": //enrere dels taulers
                        System.out.println("Enrere "+listSolutions.size());
                        System.out.println("numeroTauler"+numeroTauler);
                        if (numeroTauler != 0) {
                            numeroTauler--;
                            panelContinguts.remove(taulerSolucio);        //eliminam l'antic tauler
                            taulerSolucio = new TaulerNumeros();          //cream el nou tauler
                            panelContinguts.add(taulerSolucio, BorderLayout.CENTER);  //l'afegim
                            repaint();
                        }
                        break;

                    case "ENDAVANT": //endavant dels taulers
                        System.out.println("numeroTauler"+numeroTauler);
                        System.out.println("Endavant "+ listSolutions.size());
                        if (numeroTauler < listSolutions.size() - 1) {
                            numeroTauler++;
                            panelContinguts.remove(taulerSolucio);        //eliminam l'antic tauler
                            taulerSolucio = new TaulerNumeros();            //cream el nou tauler
                            panelContinguts.add(taulerSolucio, BorderLayout.CENTER);  //l'afegim
                            repaint();
                        }
                        break;

                    case "SOLUCIONAR":
                        System.out.println("Solucionar");
                        tauler=taulerExemple1;
                        if (recursiu) {
                            listSolutions = Sudoku.sudokuRecursiu(tauler);  //resolucio recursiva
                            solucionat = true;    //marcam que el solucionam
                        } else {
                            listSolutions = Sudoku.sudokuIteratiu(tauler);         //resolucio iterativa
                            System.out.println("Hola");
                            solucionat = true;    //marcam que el solucionam
                        }
                        panelContinguts.remove(taulerNoSolucio);        //eliminam l'antic tauler
                        taulerSolucio = new TaulerNumeros();            //cream el nou tauler
                        panelContinguts.add(taulerSolucio, BorderLayout.CENTER);  //l'afegim
                        repaint();
                        break;

                    case "RECURSIU":    //Usa recursivitat per resoldre el sudoku
                        recursiu = true;
                        break;

                    case "ITERATIU":    //Usa recursivitat per resoldre el sudoku
                        recursiu = false;
                        break;

                    case "SORTIR": //Sortir de l'applicació
                        System.exit(0);
                }
            }
        };

////////////////////////////////////////////////////////////////////////////////
//                            PANELL SUPERIOR                                 //
////////////////////////////////////////////////////////////////////////////////        
        JPanel panelMenu = new JPanel();            //INSTANCIA del menu superior
        JMenuBar barraMenu = new JMenuBar();        //DECLARACIÓ COMPONENT JMenuBar barraMenu        
        JMenu generalMenu = new JMenu("FITXER");    //DECLARACIÓ DE LES COMPONENTS JMenu 

        JMenuItem generalOpcion = new JMenuItem("SORTIR"); //DECLARACIONS COMPONENTS JMenuItem ASOCIADES
        JMenuItem generalOpcion2 = new JMenuItem("RECURSIU"); //DECLARACIONS COMPONENTS JMenuItem ASOCIADES
        JMenuItem generalOpcion3 = new JMenuItem("ITERATIU"); //DECLARACIONS COMPONENTS JMenuItem ASOCIADES

        generalOpcion.addActionListener(manipuladorEventosGeneral); //inclusió de la component JMenuItem 
        generalOpcion2.addActionListener(manipuladorEventosGeneral); //inclusió de la component JMenuItem 
        generalOpcion3.addActionListener(manipuladorEventosGeneral); //inclusió de la component JMenuItem 

        generalMenu.add(generalOpcion2);
        generalMenu.add(generalOpcion3);
        generalMenu.add(generalOpcion);  //INLCUSIÓ MENU a barraMenu

        barraMenu.add(generalMenu);      //INCLUSIÓ barraMenu en el PANELL  
        panelMenu.setLayout(new BorderLayout());
        panelMenu.add(barraMenu);

////////////////////////////////////////////////////////////////////////////////
//                            PANELL INFERIOR                                 //
////////////////////////////////////////////////////////////////////////////////        
        JPanel general = new JPanel();
        JButton botoSolucio = new JButton("SOLUCIONAR");
        botoSolucio.setFont(new Font("Serif", Font.BOLD, 14));
        botoSolucio.addActionListener(manipuladorEventosGeneral);
        general.add(botoSolucio);
        JButton botoEnrere = new JButton("ENRERE");
        JButton botoEndavant = new JButton("ENDAVANT");
        botoEnrere.addActionListener(manipuladorEventosGeneral);
        botoEndavant.addActionListener(manipuladorEventosGeneral);
        general.add(botoEnrere);
        general.add(botoEndavant);

////////////////////////////////////////////////////////////////////////////////
//                            PANELL CONTINGUTS                               //
////////////////////////////////////////////////////////////////////////////////          
        taulerNoSolucio = new TaulerNumeros();           //creacio del JPanel a traves de una clase TaulerNumeros propia
        panelContinguts = getContentPane();              //declaració panell de continguts del JFrame
        panelContinguts.setLayout(new BorderLayout());   //asociació al contenidor JFrame de l'administrador de layout BorderLayout
        panelContinguts.add(taulerNoSolucio, BorderLayout.CENTER);  //afegim els grafics
        panelContinguts.add(panelMenu, BorderLayout.NORTH);
        panelContinguts.add(general, BorderLayout.SOUTH);
        setResizable(false);
        setSize(605, 690);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

////////////////////////////////////////////////////////////////////////////////
//                            TaulerNumeros                                   //                             
////////////////////////////////////////////////////////////////////////////////           
    private class TaulerNumeros extends JPanel {

        //ATRIBUTS
        final int DIMENSIOX = 9;              //declaracio LADO X
        final int MAXIM = 600;                //número máxim de pixels del panell
        final int LADOX = MAXIM / DIMENSIOX;  //número de pixels de un ladoX
        final int DIMENSIOY = 9;              //declaracio LADO Y
        final int LADOY = MAXIM / DIMENSIOY;  //número de pixels de un ladoY

        public TaulerNumeros() {  //declaració objecte gráfic Graphics2D asociat al contenidor PanellTauler
            //tractament iteratiu dels nombres que es mostren per pantalla
            if (solucionat == false) {    //hem de crear el sudoku, es a dir hem d'introduir valors
                JFormattedTextField taulerSolucio[] = new JFormattedTextField[9 * 9];
                tauler = new int[9 * 9];                                        //cream el tauler   
                for (int i = 0; i < tauler.length - 1; i++) {tauler[i] = 0;}    //initzialitzam tot a 0
                setLayout(null);                                                //preparam el Layout

                MaskFormatter mask = null;                                      //creacio mascara de un unic valor
                try {mask = new MaskFormatter("#");
                } catch (ParseException ex) { Logger.getLogger(GUI_Final.class.getName()).log(Level.SEVERE, null, ex);
                } mask.setPlaceholderCharacter('0');

                int j = 0, i = 0;
                for (int k = 0; k < tauler.length; k++) {
                    if (k != 0 && k % 9 == 0) {                    //per reccore el tauler correctament usam i i j.
                        j++;
                        i = 0;
                    }

                    taulerSolucio[k] = new JFormattedTextField(mask);
                    taulerSolucio[k].setBounds(25 + LADOX * i, 20 + LADOY * j, 25, 30);
                    add(taulerSolucio[k]);

                    //manipulador de events del teclat dins el JTextFieldFormatted
                    taulerSolucio[k].addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyReleased(java.awt.event.KeyEvent evento) {
                            for (int z = 0; z < taulerSolucio.length - 1; z++) {
                                if (evento.getSource() == taulerSolucio[z]) {
                                    String str = taulerSolucio[z].getText();    //comprovam que  sigui un numero
                                    boolean isNumeric = str.matches("[+-]?\\d*(\\.\\d+)?");
                                    if (isNumeric) {    //si es numero:
                                        int numIntroduit = Integer.valueOf(taulerSolucio[z].getText());
                                        if (numIntroduit!= 0 && !Sudoku.isValid(tauler, z, numIntroduit)) {//numero invalid o no es 0
                                            JOptionPane.showMessageDialog(null, "Numero introduit no valid", "ERROR", JOptionPane.ERROR_MESSAGE);
                                        } else { //numero valid
                                            tauler[z] = numIntroduit;
                                        }
                                    }
                                }
                            }
                        }
                    });
                    i++;
                }
                

            } else {
                //mostra les solucions
                JLabel numSolucio;
                tauler=(int[]) listSolutions.get(numeroTauler);
                //tauler = Sudoku.getTauler(numeroTauler);    //agafam el Tauler desitjat
                int taulerInicial[] = Sudoku.getTauler(0);  //agafa sudoku sense resoldre
                
                int j = 0, i = 0;
                for (int k = 0; k < tauler.length; k++) {
                    if (k != 0 && k % 9 == 0) {                    //per reccore el tauler correctament usam i i j.
                        j++;
                        i = 0;
                    }
                    numSolucio = new JLabel();
                    if (taulerInicial[k] == 0) {    //es un numeroSolucio
                        numSolucio.setText(Integer.toString(tauler[k]));
                        numSolucio.setFont(new Font("Serif", Font.ITALIC, 16));

                    }else{                          //es un numero inmodificable
                        numSolucio.setText(Integer.toString(tauler[k]));
                        numSolucio.setFont(new Font("Serif", Font.BOLD, 16));
                    }
                    numSolucio.setBounds(25 + LADOX * i, 20 + LADOY * j, 600, 30);
                    add(numSolucio);
                    i++;
                }
            }
            setFocusable(true);  //dirijim la atenció de la execució a l'objecte PanellTauler
            setSize(608, 660);   //redimensionam l'objecte PanellTauler
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;    //instanciaciam l'objecte Graphics2D

            //tractament iteratiu que dibuixa les linies verticals i horizonstals del sudoku
            g2d.setColor(Color.BLACK);              //aplica el color a la linea
            BasicStroke prim = new BasicStroke(2);  //aplica el grosor de la linea, prim i gruixada
            BasicStroke gruix = new BasicStroke(5);

            int yHorizontal = 0;                    //inicilitzem coordenades
            int xHorizontal = 0;
            int yVertical = 0;
            int xVertical = 0;

            for (int i = 0; i < 11; i++) {
                if (i == 0 || i == 4 || i == 7 || i == 10) { //linies horizontals
                    g2d.setStroke(gruix);
                    g2d.drawLine(xHorizontal, yHorizontal, LADOX * 9, yHorizontal);
                    yHorizontal = LADOY * i;
                } else {
                    g2d.setStroke(prim);
                    g2d.drawLine(xHorizontal, yHorizontal, LADOX * 9, yHorizontal);
                    yHorizontal = LADOY * i;
                }
                if (i == 0 || i == 4 || i == 7 || i == 10) { //linies verticals
                    g2d.setStroke(gruix);
                    g2d.drawLine(xVertical, yVertical, xVertical, LADOY * 9);
                    xVertical = LADOX * i;
                } else {
                    g2d.setStroke(prim);
                    g2d.drawLine(xVertical, yVertical, xVertical, LADOY * 9);
                    xVertical = LADOX * i;
                }
            }
        }
    }
}
