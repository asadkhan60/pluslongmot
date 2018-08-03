package pluslongmot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.regex.Pattern;

public class Controleur extends JFrame implements ActionListener {

    private Arbre dico;

    private Menu menu;
    private ModeOrdinateur modeordinateur;
    private ModeJoueur modejoueur;
    private Modele modele;
    private Barre barre;
    private JPanel pan;

    private int seconde = 0;
    private Timer timer;
    private ActionListener tache_timer;

    Controleur() {
        super("Le plus long mot");
        InitJeu();
        menu = new Menu();
        modeordinateur = new ModeOrdinateur(dico);
        modejoueur = new ModeJoueur(dico);
        modele = new Modele();
        barre = new Barre();
        pan = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));

        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        pan.add(barre);
        this.add(pan);
        this.add(menu);
        this.setPreferredSize(new Dimension(800, 650));
        this.pack();
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.menu.getBoutonMJ().addActionListener(this);
        this.menu.getBoutonMO().addActionListener(this);
        this.modeordinateur.getOk().addActionListener(this);

        this.barre.getItemModeJoueur().addActionListener(this);
        this.barre.getItemModeOrdinateur().addActionListener(this);
        this.barre.getItemFermer().addActionListener(this);

        this.barre.getItemHelp().addActionListener(this);
        this.barre.getItemInformation().addActionListener(this);

        for (int i = 0; i < modejoueur.getLettresAlea().length; i++) {
            modejoueur.getLettresAlea()[i].addActionListener(this);
            modejoueur.getLettresSaisis()[i].addActionListener(this);
        }

        this.modejoueur.getBoutonValider().addActionListener(this);
        this.modejoueur.getBoutonReinitialiser().addActionListener(this);

        tache_timer = new ActionListener() {
            public void actionPerformed(ActionEvent e1) {
                seconde++;
                if (seconde != 30) {
                    modejoueur.getChrono().setText("" + seconde);
                } else {
                    modejoueur.getChrono().setText("" + seconde);
                    JOptionPane.showMessageDialog(null, "Temps écoulés ! \n  ", "Fin de la partie", JOptionPane.WARNING_MESSAGE);
                    ValiderModeJoueur();
                }
            }
        };

        timer = new Timer(1000, tache_timer);
    }

    Arbre getDico() {
        return this.dico;
    }

    Menu getMenu() {
        return this.menu;
    }

    ModeJoueur getModeJoueur() {
        return this.modejoueur;
    }

    ModeOrdinateur getModeOrdinateur() {
        return this.modeordinateur;
    }

    Modele getModele() {
        return this.modele;
    }

    Barre getBarre() {
        return this.barre;
    }

    int getSeconde() {
        return this.seconde;
    }

    Timer getTimer() {
        return this.timer;
    }

    ActionListener getTacheTimer() {
        return this.tache_timer;
    }

    void setDico(Arbre d) {
        this.dico = d;
    }

    void setMenu(Menu m) {
        this.menu = m;
    }

    void setBarre(Barre b) {
        this.barre = b;
    }

    void setModele(Modele m) {
        this.modele = m;
    }

    void setModeJoueur(ModeJoueur mj) {
        this.modejoueur = mj;
    }

    void setModeOrdinateur(ModeOrdinateur mo) {
        this.modeordinateur = mo;
    }

    void setSeconde(int s) {
        this.seconde = s;
    }

    void setTimer(Timer t) {
        this.timer = t;
    }

    void setTacheTimer(ActionListener t) {
        this.tache_timer = t;
    }

    public static String sansAccents(String source) { // Fonction qui va enlevé tout les accents d'un mot
        return Normalizer.normalize(source, Normalizer.Form.NFD).replaceAll(
                "[\u0300-\u036F]", "");
    }

    public static String Trie(String mot) { // Fonction qui trie un mot par ordre croissant des lettres
        char[] c;
        c = mot.toCharArray();
        Arrays.sort(c);
        return new String(c);
    }

    public void InitJeu() { // Fonction qui initialise le jeu
        File file = new File("arbre.txt");
        if (!file.exists()) {
            System.out.println("fichier n'existe pas");
            dico = InitDictionnaire();
            Ecrire(file, dico);
        } else {
            System.out.println("fihcier existe");
            dico = Lire(file);
        }
    }

    public Arbre InitDictionnaire() { // Fonction qui va initialisé le dictionnaire à partir du fichier teste dic.txt
        Arbre a = new Arbre('#');
        String fichier = "dic.txt";
        try {
            InputStream is = new FileInputStream(fichier); // On va ouvrir le fichier dic.txt en lecture
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line, l;
            while ((line = br.readLine()) != null) { // On lit le fichier ligne par ligne
                line = sansAccents(line); // On enleve les accents du mot
                l = Trie(line); // On trie le mot par ordre croissant des lettres
                if (Pattern.matches("[a-z]{2,10}", line)) {
                    a.remplir(l, line, 0); // On ajoute le mot dans l'arbre
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return a;
    }

    public void Ecrire(File file, Arbre a) { // Fonction qui permet de sérialisé l'arbre 
        try {
            try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
                oos.writeObject(a);
                oos.flush();
            }
        } catch (FileNotFoundException e) {
        } catch (IOException ioe) {
        }
    }

    public Arbre Lire(File file) { // Fonction permettant de désérialisé l'arbre
        Arbre arbre = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
            try {
                arbre = (Arbre) ois.readObject();
            } catch (ClassNotFoundException e) {
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return arbre;
    }

    public boolean test_lettre(String s) { // Fonction qui va testé si les lettres sont bien compris entre 2 et 10
        char[] c;                           // Et qu'il y a au moins deux voyelles
        int a, cpt = 0, w = 0;
        ArrayList<Integer> x = new ArrayList<>();
        x.add(0);
        x.add(8);
        x.add(4);
        x.add(14);
        x.add(20);
        x.add(24);
        c = s.toCharArray();
        for (int i = 0; i < s.length(); i++) {
            a = ((int) s.charAt(i));
            a = a - 97;
            if (x.contains(a)) {
                cpt++;
            }
            if (a > 26 || a < 0) {
                w++;
            }
        }
        if (cpt >= 2 && w == 0 && s.length() > 2 && s.length() <= 10) {
            return true;
        } else {
            return false;
        }
    }

    public Character[] lettre_aleatoire() { // Fonction random qui tire aléatoirement 10 lettres avec au moins deux voyelles
        int a;
        Character[] c = new Character[10];
        char[] tab_v = {'a', 'e', 'u', 'i', 'o', 'y'};
        Random rand = new Random();
        for (int i = 0; i <= 1; i++) {
            a = (int) rand.nextInt(5 - 0 + 1) + 0;
            c[i] = tab_v[a];
        }
        for (int i = 2; i <= 9; i++) {
            a = (int) (int) rand.nextInt(25 - 0 + 1) + 0;
            c[i] = (char) (a + 97);
        }
        return c;
    }

    public void info() {
        String info = "Projet le plus long mot réalisé par KHAN Asad et BAIH Ismail";
        JOptionPane.showMessageDialog(null, info);
    }

    public void help() {
        String info = "Pour chaque tirage, on a au minimum 2 voyelles quelque soit le mode de jeu et vous devez composer avec ces lettres le plus long mot possible.\n"
                + "Tous les mots de 2 à 10 lettres sont acceptés à l’exception :\n"
                + "     - Des préfixes, suffixes et symboles.\n"
                + "     - Des formes personnelles verbales\n"
                + "     - Des interjections, onomatopées et sigle. \n"
                + "     - Les mots comportant une partie entre parenthèses\n"
                + "     - Les locutions verbales (exemple : sac à main)\n"
                + "     - Les mots composés (exemple : carte-mère)";
        JOptionPane.showMessageDialog(null, info);
    }

    public void barremo() {
        if (this.getContentPane() != modeordinateur) {
            this.getContentPane().removeAll();
            this.add(pan);
            this.add(modeordinateur);
            this.repaint();
            this.revalidate();
            modele.addObserver(modeordinateur);
        } else {
            JOptionPane.showMessageDialog(null, "Vous êtes déjà dans le mode ordinateur ", "Attention", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void barremj() {
        if (this.getContentPane() != modejoueur) {
            this.getContentPane().removeAll();
            this.add(pan);
            this.add(modejoueur);
            this.repaint();
            this.revalidate();
            modele.addObserver(modejoueur);
        } else {
            JOptionPane.showMessageDialog(null, "Vous êtes déjà dans le mode joueur ", "Attention", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void InitModeJoueur() {
        this.getContentPane().removeAll();
        this.add(pan);
        this.add(modejoueur);
        this.repaint();
        this.revalidate();
        modele.addObserver(modejoueur);
    }

    private void InitModeOrdinateur() {
        this.getContentPane().removeAll();
        this.add(pan);
        this.add(modeordinateur);
        this.repaint();
        this.revalidate();
        modele.addObserver(modeordinateur);
    }

    private void ValiderModeOrdi() {
        String s;
        if (!"".equals(modeordinateur.getTexte1().getText())) {
            s = modeordinateur.getTexte1().getText();
            if (!test_lettre(s.toLowerCase())) {
                JOptionPane.showMessageDialog(null, "Veuillez respecter les règles \n  ", "Attention", JOptionPane.WARNING_MESSAGE);
            } else {
                modele.ok(modeordinateur.getTexte1().getText().toLowerCase());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Veuillez saisir entre 2 et 10 lettres ", "Attention", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void Commencer() {
        timer.start();
        for (int i = 0; i < modejoueur.getLettresAlea().length; i++) {
            modejoueur.getLettresAlea()[i].setEnabled(true);
            modejoueur.getLettresSaisis()[i].setEnabled(true);
        }
        modejoueur.getBoutonValider().setText("Valider");
        modejoueur.getBoutonReinitialiser().setEnabled(true);

        Character[] lettres = lettre_aleatoire();
        modele.commencer(lettres);
    }

    private void ValiderModeJoueur() {
        int i;
        String mot = "";
        timer.stop();
        modejoueur.getBoutonReinitialiser().setText("Rejouer");
        modejoueur.getBoutonValider().setEnabled(false);

        for (i = 0; i < modejoueur.getLettresSaisis().length; i++) {
            modejoueur.getLettresAlea()[i].setEnabled(false);
            modejoueur.getLettresSaisis()[i].setEnabled(false);
            mot += modejoueur.getLettresAlea()[i].getText();
        }

        modele.ok(mot);
    }

    private void Reinitialiser() {
        for (int i = 0; i < modejoueur.getLettresSaisis().length; i++) {
            modejoueur.getLettresAlea()[i].setEnabled(true);
            modejoueur.getLettresSaisis()[i].setText("-");
        }
        modele.change();
    }

    private void Rejouer() {
        seconde = 0;
        modejoueur.getChrono().setText("" + seconde);
        timer.start();
        modejoueur.getAffichage().setText("");
        modejoueur.getBoutonReinitialiser().setText("Reinitialiser");
        modejoueur.getBoutonValider().setEnabled(true);

        for (int i = 0; i < modejoueur.getLettresSaisis().length; i++) {
            modejoueur.getLettresAlea()[i].setEnabled(true);
            modejoueur.getLettresSaisis()[i].setEnabled(true);
            modejoueur.getLettresSaisis()[i].setText("-");
        }

        Character[] lettres = lettre_aleatoire();
        modele.commencer(lettres);
    }

    private void ChoixLettre(int index) {
        modele.choixLettre(index);
    }

    private void AnnuleChoixLettre(int index) {
        if (modejoueur.getLettresSaisis()[index].getPosition() != -1) {
            int position = modejoueur.getLettresSaisis()[index].getPosition();
            modejoueur.getLettresSaisis()[index].setText("-");
            modejoueur.getLettresSaisis()[index].setPosition(-1);
            modejoueur.getLettresAlea()[position].setEnabled(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menu.getBoutonMJ()) {
            this.InitModeJoueur();
        }
        if (e.getSource() == menu.getBoutonMO()) {
            this.InitModeOrdinateur();
        }

        if (e.getSource() == modeordinateur.getOk()) {
            this.ValiderModeOrdi();
        }

        if (e.getSource() == modejoueur.getBoutonValider()) {
            if ("Commencer".equals(modejoueur.getBoutonValider().getText())) {
                this.Commencer();
            } else {
                this.ValiderModeJoueur();
            }
        }

        if (e.getSource() == modejoueur.getBoutonReinitialiser()) {
            if ("Reinitialiser".equals(modejoueur.getBoutonReinitialiser().getText())) {
                this.Reinitialiser();
            } else {
                this.Rejouer();
            }
        }

        for (int i = 0; i < modejoueur.getLettresAlea().length; i++) {
            if (e.getSource() == modejoueur.getLettresAlea()[i]) {
                this.ChoixLettre(i);
            }

            if (e.getSource() == modejoueur.getLettresSaisis()[i]) {
                this.AnnuleChoixLettre(i);
            }
        }

        if (e.getSource() == barre.getItemModeJoueur()) {
            this.modeordinateur = new ModeOrdinateur(this.dico);
            this.modeordinateur.getOk().addActionListener(this);
            this.barremj();
        }
        if (e.getSource() == barre.getItemModeOrdinateur()) {
            this.seconde = 0;
            this.timer.stop();
            this.modejoueur = new ModeJoueur(dico);

            for (int i = 0; i < modejoueur.getLettresAlea().length; i++) {
                modejoueur.getLettresAlea()[i].addActionListener(this);
            }

            this.modejoueur.getBoutonValider().addActionListener(this);
            this.modejoueur.getBoutonReinitialiser().addActionListener(this);
            this.barremo();
        }
        if (e.getSource() == barre.getItemFermer()) {
            System.exit(0);
        }
        if (e.getSource() == barre.getItemHelp()) {
            this.help();
        }
        if (e.getSource() == barre.getItemInformation()) {
            this.info();
        }

    }
}
