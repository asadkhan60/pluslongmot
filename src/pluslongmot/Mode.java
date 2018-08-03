package pluslongmot;

import java.util.Arrays;
import java.util.Observer;
import javax.swing.*;

public abstract class Mode extends JPanel implements Observer {

    protected Arbre dico;

    Mode(Arbre d) {
        dico = d;
    }
    
    Arbre getDico(){
        return this.dico;
    }
    
    void setDico(Arbre dico){
        this.dico = dico;
    }

    public static String Trie(String mot) {
        char[] c;
        c = mot.toCharArray();
        Arrays.sort(c);
        return new String(c);
    }
}
