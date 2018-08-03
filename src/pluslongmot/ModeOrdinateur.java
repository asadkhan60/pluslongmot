package pluslongmot;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Observable;

public class ModeOrdinateur extends Mode { // Vue Mode Ordinateur
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JTextField texte1;
    private JTextField texte2;
    private JButton ok;
    private JPanel panel0;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JPanel panel5;
    private JPanel panel6;

    public ModeOrdinateur(Arbre dico) {
        super(dico);
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);
        Font fontField = new Font("Times new roman", Font.BOLD, 16);
        Font fontTitre1 = new Font("Times new roman", Font.BOLD, 48);
        Font fontTitre2 = new Font("Times new roman", Font.BOLD, 18);
        Font fontTitre3 = new Font("Times new roman", Font.BOLD, 16);

        panel0 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
        panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 3));
        panel3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
        panel4 = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
        panel5 = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        panel6 = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));

        label1 = new JLabel("Le plus long mot");
        label2 = new JLabel("Mode ordinateur");
        label3 = new JLabel("Saisissez les lettres que vous voulez : ");
        label4 = new JLabel("Le plus long mot est : ");
        label1.setFont(fontTitre1);
        label2.setFont(fontTitre2);
        label2.setForeground(new Color(128, 0, 128));
        label3.setFont(fontTitre3);
        label4.setFont(fontTitre3);

        texte1 = new JTextField(20);
        texte2 = new JTextField(20);
        texte2.setEditable(false);
        texte1.setFont(fontField);
        texte2.setFont(fontField);

        ok = new JButton("Ok");
        ok.setFont(fontTitre3);

        panel1.add(label1);
        panel2.add(label2);

        panel3.add(label3);
        panel3.add(texte1);
        panel3.add(ok);

        panel4.add(label4);
        panel4.add(texte2);

        this.add(panel1);
        this.add(panel2);
        this.add(panel6);
        this.add(panel3);
        this.add(panel4);
        this.add(panel5);
    }

     public JLabel getLabel1() {
        return label1;
    }

    public JLabel getLabel2() {
        return label2;
    }

    public JLabel getLabel3() {
        return label3;
    }

    public JLabel getLabel4() {
        return label4;
    }

    public JTextField getTexte1() {
        return texte1;
    }

    public JTextField getTexte2() {
        return texte2;
    }

    public JButton getOk() {
        return ok;
    }

    public JPanel getPanel0() {
        return panel0;
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public JPanel getPanel2() {
        return panel2;
    }

    public JPanel getPanel3() {
        return panel3;
    }

    public JPanel getPanel4() {
        return panel4;
    }

    public JPanel getPanel5() {
        return panel5;
    }

    public JPanel getPanel6() {
        return panel6;
    }
    
    public void setLabel1(JLabel label1) {
        this.label1 = label1;
    }

    public void setLabel2(JLabel label2) {
        this.label2 = label2;
    }

    public void setLabel3(JLabel label3) {
        this.label3 = label3;
    }

    public void setLabel4(JLabel label4) {
        this.label4 = label4;
    }

    public void setTexte1(JTextField texte1) {
        this.texte1 = texte1;
    }

    public void setTexte2(JTextField texte2) {
        this.texte2 = texte2;
    }

    public void setOk(JButton ok) {
        this.ok = ok;
    }

    public void setPanel5(JPanel panel5) {
        this.panel5 = panel5;
    }

    public void setPanel0(JPanel panel0) {
        this.panel0 = panel0;
    }

    public void setPanel1(JPanel panel1) {
        this.panel1 = panel1;
    }

    public void setPanel2(JPanel panel2) {
        this.panel2 = panel2;
    }

    public void setPanel3(JPanel panel3) {
        this.panel3 = panel3;
    }

    public void setPanel4(JPanel panel4) {
        this.panel4 = panel4;
    }

    public void setPanel6(JPanel panel6) {
        this.panel6 = panel6;
    }
    @Override
    public void update(Observable o, Object o1) { // Fonction qui met à jour les actions effectués par le joueur
        if (o instanceof Modele && o1 instanceof String) { // Valider
            String s = (String) o1;

            if (s.length() > 0) {
                s = Trie(s.toLowerCase());
                ArrayList<String> mots = dico.Recherche(s, s, 0, new ArrayList<String>());

                if (mots.size() > 0) {
                    texte2.setText(mots.get(0).toUpperCase());
                } else {
                    JOptionPane.showMessageDialog(null, "Nous avons aucun mot qui correspond à votre recherche", "Information", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }
}
