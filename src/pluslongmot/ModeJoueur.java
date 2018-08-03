package pluslongmot;

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class ModeJoueur extends Mode { // Vue Mode Joueur

    private JLabel time, chrono;
    private Lettres[] lettresAlea;
    private Lettres[] lettresSaisis;
    private JButton valider;
    private JButton reinitialiser;
    private JLabel affichage, label1, label2;
    private Barre barre;

    ModeJoueur(Arbre dico) {
        super(dico);
        int i;

        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);

        Font fontTitre1 = new Font("Times new roman", Font.BOLD, 48);
        Font fontTitre2 = new Font("Times new roman", Font.BOLD, 18);
        Font fontTitre3 = new Font("Times new roman", Font.BOLD, 20);

        label1 = new JLabel("Le plus long mot");
        label1.setFont(fontTitre1);
        label2 = new JLabel("Mode joueur");
        label2.setFont(fontTitre2);
        label2.setForeground(new Color(128, 0, 128));

        JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0));
        JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 3));
        JPanel pan0 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel pan1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JPanel pan2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JPanel pan3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0));
        JPanel pan4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel pan5 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        barre = new Barre();
        panel1.add(label1);
        panel2.add(label2);
        this.add(panel1);
        this.add(panel2);

        time = new JLabel("Time : ");
        chrono = new JLabel("0");

        pan0.add(time);
        pan0.add(chrono);
        this.add(pan0);

        //lettres = new String[10];
        lettresAlea = new Lettres[10];
        lettresSaisis = new Lettres[10];
        valider = new JButton("Commencer");
        reinitialiser = new JButton("Reinitialiser");
        affichage = new JLabel("");
        affichage.setFont(fontTitre2);
        affichage.setForeground(new Color(15, 5, 107));

        for (i = 0; i < lettresAlea.length; i++) {
            lettresAlea[i] = new Lettres();
            lettresAlea[i].setPosition(i);
            lettresAlea[i].setEnabled(false);
            pan1.add(lettresAlea[i]);
        }

        this.add(pan1);

        for (i = 0; i < lettresSaisis.length; i++) {
            lettresSaisis[i] = new Lettres();
            lettresSaisis[i].setPosition(-1);
            lettresSaisis[i].setEnabled(false);
            pan2.add(lettresSaisis[i]);
        }

        this.add(pan2);

        pan3.add(valider);

        pan3.add(reinitialiser);
        reinitialiser.setEnabled(false);
        this.add(pan3);

        pan4.add(affichage);
        this.add(pan4);

        valider.setFont(fontTitre3);
        reinitialiser.setFont(fontTitre3);
    }

    JLabel getTime() {
        return this.time;
    }

    JLabel getChrono() {
        return this.chrono;
    }

    Lettres[] getLettresAlea() {
        return this.lettresAlea;
    }

    Lettres[] getLettresSaisis() {
        return this.lettresSaisis;
    }

    JButton getBoutonValider() {
        return this.valider;
    }

    JButton getBoutonReinitialiser() {
        return this.reinitialiser;
    }

    JLabel getAffichage() {
        return this.affichage;
    }

    JLabel getLabel1() {
        return this.label1;
    }

    JLabel getLabel2() {
        return this.label2;
    }

    Barre getBarre() {
        return this.barre;
    }

    void setTime(JLabel t) {
        this.time = t;
    }

    void setChrono(JLabel c) {
        this.chrono = c;
    }

    void setLettresAlea(Lettres[] la) {
        this.lettresAlea = la;
    }

    void setLettresSaisis(Lettres[] ls) {
        this.lettresSaisis = ls;
    }

    void setBoutonValider(JButton v) {
        this.valider = v;
    }

    void setBoutonReinitialiser(JButton r) {
        this.reinitialiser = r;
    }

    void setAffichage(JLabel a) {
        this.affichage = a;
    }

    void setLabel1(JLabel l) {
        this.label1 = l;
    }

    void setLabel2(JLabel l) {
        this.label2 = l;
    }

    void setBarre(Barre b) {
        this.barre = b;
    }

    @Override
    public void update(Observable o, Object o1) { // Fonction qui met a jour les changements effectués
        if (o instanceof Modele && o1 instanceof Character[]) { // Initialisation des lettres aleatoires
            Character[] l = (Character[]) o1;
            for (int i = 0; i < lettresAlea.length; i++) {
                lettresAlea[i].setText(l[i].toString().toUpperCase());
            }
        }

        if (o instanceof Modele && o1 instanceof Integer) { // Clique sur une lettre
            Integer i = (Integer) o1;
            lettresAlea[i].setEnabled(false);
            for (int x = 0; x < lettresSaisis.length; x++) {
                if (lettresSaisis[x].getText() == "-") {
                    lettresSaisis[x].setText(lettresAlea[i].getText());
                    lettresSaisis[x].setPosition(i);
                    break;
                }
            }
        }

        if (o instanceof Modele && o1 instanceof String) { // Valider 
            String lettres = (String) o1;
            String trie = Trie(lettres.toLowerCase());

            String motSaisi = "";

            for (int i = 0; i < lettresSaisis.length; i++) {
                if (!"-".equals(lettresSaisis[i].getText())) {
                    motSaisi += lettresSaisis[i].getText();
                } else {
                    break;
                }
            }

            ArrayList<String> liste = dico.Recherche(trie, trie, 0, new ArrayList<String>());

            if (liste.contains(motSaisi.toLowerCase())) {
                affichage.setText("Gagné ! vous avez trouvé le mot le plus long : " + motSaisi);
            } else {
                affichage.setText("Perdu ! le mot le plus long est : " + liste.get(0).toUpperCase());
            }

        }
    }
}
