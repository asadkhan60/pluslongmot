package pluslongmot;

import java.io.Serializable;
import java.util.ArrayList;

public class Arbre implements Serializable {

    private char caractere;
    private Arbre[] fils;
    private ArrayList<String> mots;

    public Arbre() {
        fils = new Arbre[27];
        mots = new ArrayList<>();
    }

    public Arbre(char c) {
        caractere = c;
        fils = new Arbre[27];
        mots = new ArrayList<>();
    }

    char getCaractere() {
        return this.caractere;
    }

    Arbre[] getFils() {
        return this.fils;
    }

    ArrayList<String> getMots() {
        return this.mots;
    }

    void setCaractere(char c) {
        this.caractere = c;
    }

    void setFils(Arbre[] f) {
        this.fils = f;
    }
    
    void setMots(ArrayList<String> m){
        this.mots = m;
    }

    public void remplir(String l, String s, int indice) { // Fonction permettant de remplir l'arbre
        if (indice == l.length()) {
            if (!this.mots.contains(s)) {
                this.mots.add(s);
            }
        } else {
            char c = l.charAt(indice);
            int index = ((int) c - 97);

            try {
                this.fils[index].remplir(l, s, indice + 1);
            } catch (NullPointerException npe) {
                this.fils[index] = new Arbre(c);
                this.fils[index].remplir(l, s, indice + 1);
            }
        }
    }

    public boolean TestMot(String s, int indice) { // Fonction qui va testé si un mot existe dans l'arbre ou pas
        int j;                                      
        if (indice == s.length()) {
            if (this.mots.contains(s)) {
                return true;
            } else {
                return false;
            }
        } else {
            char c = s.charAt(indice);
            int index = ((int) c - 97);

            try {
                this.fils[index].TestMot(s, indice + 1);
            } catch (NullPointerException npe) {
                System.out.println("pas de mots");
            }
        }
        return false;
    }

    private ArrayList<String> TestListe(ArrayList<String> sacMot) { // Foncton qui va testé si les mots du noeud courant sont plus grand 
        int j;                                                      // que la liste qui stocke les mots les plus long
        if (!this.mots.isEmpty()) {
            if (!sacMot.isEmpty()) {
                if (sacMot.get(0).length() < this.mots.get(0).length()) {
                    sacMot = this.mots;
                } else if (sacMot.get(0).length() == this.mots.get(0).length()) {
                    for (j = 0; j < this.mots.size(); j++) {
                        if (!sacMot.contains(this.mots.get(j))) {
                            sacMot.add(this.mots.get(j));
                        }
                    }
                }
            } else {
                sacMot = this.mots;
            }
        }
        return sacMot;
    }

    public ArrayList<String> Recherche(String mot, String s, int prof, ArrayList<String> sacMot) { // Fonction recherche des mots les plus longs 
        int i, j;                                                                                  // stocké dans la liste sacMot 

        if (prof == mot.length()) {
            sacMot = this.TestListe(sacMot);
        } else {
            for (i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                int index = ((int) c - 97);

                try {
                    sacMot = this.fils[index].Recherche(mot, s.substring(i + 1, s.length()), prof + 1 + i, sacMot);
                    sacMot = this.TestListe(sacMot);
                } catch (NullPointerException npe) {
                    sacMot = this.TestListe(sacMot);
                }
            }
        }
        return sacMot;
    }
}
