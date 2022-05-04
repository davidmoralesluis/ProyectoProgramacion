package com.xxo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class XxO extends JFrame implements ActionListener {

    private final String CARPETA = "src" + File.separator + "main" + File.separator + "java" + File.separator + "com" + File.separator + "xxo" + File.separator + "img" + File.separator;


    private JLabel fin;

    private JLabel tablero,titulo;

    private JButton a1,a2,a3,b1,b2,b3,c1,c2,c3;

    private boolean xPlayer=true;
    private int icono=0;

    //Imagenes
    private ArrayList<Icon> img = new ArrayList<Icon>();

    public XxO() {
        super("X-X-O");

        img.add(new ImageIcon(CARPETA + "board2.png"));//0
        img.add(new ImageIcon(CARPETA + "x.png"));//1
        img.add(new ImageIcon(CARPETA + "o.png")); //2
        img.add(new ImageIcon(CARPETA + "verical.png")); //2

        this.setBounds(1000, 300, 400, 480);
        this.setLayout(null);
        this.getContentPane().setBackground(Color.orange);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);

        titulo = new JLabel();
        titulo.setText("Tic Tac Toe");
        titulo.setBounds(100,10,200,50);
        titulo.setBackground(Color.green);
        titulo.setOpaque(false);
        add(titulo);

        tablero = new JLabel();
        tablero.setBounds(40,100,300,300);
        tablero.setIcon(img.get(0));
        tablero.setOpaque(false);
        add(tablero);



        a1 = new JButton();
        a1.setBounds(80,110,64,64);
        a1.setBackground(Color.orange);
        a1.setBorder(null);
        a1.addActionListener(this);
        add(a1);

        b1 = new JButton();
        b1.setBounds(175,110,64,64);
        b1.setBackground(Color.orange);
        b1.setBorder(null);
        b1.addActionListener(this);
        add(b1);

        c1 = new JButton();
        c1.setBounds(270,110,64,64);
        c1.setBackground(Color.orange);
        c1.setBorder(null);
        c1.addActionListener(this);
        add(c1);

        a2 = new JButton();
        a2.setBounds(80,200,64,64);
        a2.setBackground(Color.orange);
        a2.setBorder(null);
        a2.addActionListener(this);
        add(a2);

        b2 = new JButton();
        b2.setBounds(175,200,64,64);
        b2.setBackground(Color.orange);
        b2.setBorder(null);
        b2.addActionListener(this);
        add(b2);

        c2 = new JButton();
        c2.setBounds(270,200,64,64);
        c2.setBackground(Color.orange);
        c2.setBorder(null);
        c2.addActionListener(this);
        add(c2);

        a3 = new JButton();
        a3.setBounds(80,290,64,64);
        a3.setBackground(Color.orange);
        a3.addActionListener(this);
        a3.setBorder(null);
        add(a3);

        b3 = new JButton();
        b3.setBounds(165,290,64,64);
        b3.setBackground(Color.orange);
        b3.addActionListener(this);
        b3.setBorder(null);
        add(b3);

        c3 = new JButton();
        c3.setBounds(270,290,64,64);
        c3.setBackground(Color.orange);
        c3.addActionListener(this);
        c3.setBorder(null);
        add(c3);


        fin = new JLabel();
        fin.setBounds(-50,10,300,300);
        fin.setIcon(img.get(3));
        fin.setOpaque(false);
        fin.setVisible(false);
        tablero.add(fin);
    }

    public void gameOver(){


    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (xPlayer){
            xPlayer=false;
            icono=1;
        }else{
            xPlayer=true;
            icono=2;
        }

        System.out.println(" ---"+icono+xPlayer+" -------");

        if (e.getSource()==a1){
            a1.setIcon(img.get(icono));
            fin.setVisible(true);
            toFront();
            a1.removeActionListener(this);
            a2.removeActionListener(this);
            a3.removeActionListener(this);
        }

        if (e.getSource()==b1){
            b1.setIcon(img.get(icono));
        }

        if (e.getSource()==c1){
            c1.setIcon(img.get(icono));
        }

        if (e.getSource()==a2){
            a2.setIcon(img.get(icono));
        }

        if (e.getSource()==b2){
            b2.setIcon(img.get(icono));
        }

        if (e.getSource()==c2){
            c2.setIcon(img.get(icono));
        }

        if (e.getSource()==a3){
            a3.setIcon(img.get(icono));
        }

        if (e.getSource()==b3){
            b3.setIcon(img.get(icono));
        }

        if (e.getSource()==c3){
            c3.setIcon(img.get(icono));
        }


    }
}
