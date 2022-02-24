package com.GrupoCod;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Lobby extends JFrame implements ActionListener {

    JLabel fondo;
    JLabel titulo;
    JButton slot;
    JLabel maq;
    Timer halfsec;
    boolean sec;
    int bling = 0;
    boolean blingUp = false;
    // Imagenes
    ArrayList<Icon> img = new ArrayList<Icon>();

    public Lobby() {
        super("GAMES");

        // maquina
        this.setBounds(1000, 100, 480, 640);
        this.setLayout(null);
        // Timer
        halfsec = new Timer(500, this);
        halfsec.start();

        // Imagenes

        img.add(new ImageIcon("wall.png"));// 0
        img.add(new ImageIcon("xogos.png"));// 1
        img.add(new ImageIcon("red777.png"));// 2

        // #EABE3F oro
        // SLOT

        titulo = new JLabel();
        titulo.setBounds(85, 110, 325, 100);
        titulo.setIcon(img.get(1));
        add(titulo);

        slot = new JButton();
        slot.setBounds(50, 400, 100, 145);
        slot.setBackground(Color.BLACK);
        slot.setOpaque(true);
        slot.setIcon(img.get(2));
        slot.addActionListener(this);
        add(slot);


        fondo = new JLabel();
        fondo.setBounds(0, 0, 480, 640);
        fondo.setIcon(img.get(0));
        add(fondo);
        System.out.println("fin");
    }

    @Override
    public void actionPerformed(ActionEvent action) {

        if (sec) {
            sec = false;
        } else {
            sec = true;
        }

        if (action.getSource() == slot) {
            Supertragaperras window = new Supertragaperras();

            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setVisible(true);
            window.setResizable(true);
            super.dispose();
        }

        if (action.getSource() == halfsec) {

            if (sec) {
                bling++;
                System.out.println("*takt*" + bling);
                slot.setBackground(Color.BLACK);
            } else {
                slot.setBackground(Color.decode("#EABE3F"));
            }
        }
    }
}
