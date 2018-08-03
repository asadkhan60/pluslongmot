package pluslongmot;

public class Pluslongmot {

    static Controleur c;
    static ProgressExemple pe;

    public static void main(String[] argv) throws InterruptedException { // Programme principal
        Thread p = new Thread(new Progression()); // On lance la barre de progression en même temps que le lancement de la fenêtre
        Thread j = new Thread(new Jeu());
        p.start();
        j.start();
        p.join();
        j.join();
    }
}
