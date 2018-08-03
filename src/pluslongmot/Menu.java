package pluslongmot;

import java.awt.*;
import javax.swing.*;

public class Menu extends JPanel {

    private JLabel label1, label2;
    private JButton mjo, mordi;

    Menu() {
        JPanel panel1, panel2, panel3, panel4, panel5, panel6;

        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);

        Font fontTitre1 = new Font("Times new roman", Font.BOLD, 48);
        Font fontTitre2 = new Font("Times new roman", Font.BOLD, 18);
        Font fontTitre3 = new Font("Times new roman", Font.BOLD, 20);

        label1 = new JLabel("Le plus long mot");
        label1.setFont(fontTitre1);
        label2 = new JLabel("Menu");
        label2.setFont(fontTitre2);

        mjo = new JButton(" Mode joueur ");
        mordi = new JButton(" Mode ordinateur ");
        mjo.setFont(fontTitre3);
        mordi.setFont(fontTitre3);
        label2.setForeground(new Color(128, 0, 128));

        panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
        panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 3));
        panel3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
        panel4 = new JPanel(new FlowLayout(FlowLayout.CENTER, 117, 10));
        panel5 = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        panel6 = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));

        panel1.add(label1);
        panel2.add(label2);
        panel4.add(mjo);
        panel5.add(mordi);

        this.add(panel1);
        this.add(panel2);
        this.add(panel3);
        this.add(panel4);
        this.add(panel5);
        this.add(panel6);
    }

    JLabel getLabel1() {
        return this.label1;
    }

    JLabel getLabel2() {
        return this.label2;
    }

    JButton getBoutonMJ() {
        return this.mjo;
    }

    JButton getBoutonMO() {
        return this.mordi;
    }

    void setLabel1(JLabel l) {
        this.label1 = l;
    }

    void setLabel2(JLabel l) {
        this.label2 = l;
    }

    void setBoutonMJ(JButton mj) {
        this.mjo = mj;
    }

    void setBoutonMO(JButton mo) {
        this.mordi = mo;
    }
}
