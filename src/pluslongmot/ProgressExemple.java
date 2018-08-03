/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pluslongmot;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

/**
 *
 * @author ismail.baih
 */
public class ProgressExemple extends Thread { // Classe barre de progression

    JFrame f;

    JProgressBar barre_progression;
    static final int MINIMUM = 0;
    static final int MAXIMUM = 100;

    public ProgressExemple() {

        f = new JFrame("Please wait");
        barre_progression = new JProgressBar();
        f.add(barre_progression);
        barre_progression.setMinimum(MINIMUM);
        barre_progression.setMaximum(MAXIMUM);
        JPanel pnl = new JPanel();
        pnl.add(barre_progression);
        f.setLocationRelativeTo(null);
        f.setPreferredSize(new Dimension(250, 200));
        f.setTitle("Chargement");
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setContentPane(pnl);
        f.pack();
        f.setVisible(true);

        for (int i = MINIMUM; i <= MAXIMUM; i++) {
            final int percent = i;
            try {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        updateBar(percent); // On incremente la barre de progression 
                    }
                });
                java.lang.Thread.sleep(20);

            } catch (InterruptedException e) {;
            }
        }
        Thread.currentThread().interrupt();
        f.setVisible(false);
    }

    public void updateBar(int newValue) { // Fonction qui met à jour la barre de progression
        barre_progression.setValue(newValue);
    }

}
