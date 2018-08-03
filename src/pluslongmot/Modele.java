package pluslongmot;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

public class Modele extends Observable { // Modele

    private Character[] lettres;
    private String mot;

    Modele() {
        lettres = new Character[10];
        mot = "";
    }

    Character[] getLettres() {
        return this.lettres;
    }

    String getMot() {
        return this.mot;
    }

    void setLettres(Character[] l) {
        this.lettres = l;
    }

    void setMot(String m) {
        this.mot = m;
    }

    void change() {
        this.setChanged(); 
        this.notifyObservers(lettres); // Fonction qui informe les changements aux vues
    }

    void ok(String s) { // Fonction quand on valide les lettres saisis pour le mode ordinateur
        mot = s;
        this.setChanged();
        this.notifyObservers(mot); 
    }

    void commencer(Character[] lettres) { // Fonction lorsqu'on commence la partie
        this.lettres = lettres;
        setChanged();
        notifyObservers(this.lettres);
    }

    void choixLettre(Integer i) { // Fonction lorsqu'on clique sur une lettre pour le mode joueur
        setChanged();
        notifyObservers(i);
    }
}
