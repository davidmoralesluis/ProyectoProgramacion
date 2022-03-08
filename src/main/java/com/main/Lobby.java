package com.main;


import com.Temporal;
import com.buscaminas.Tablero;
import com.data.DataMethods;
import com.data.User;
import com.supertragaperras.Supertragaperras;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;


public class Lobby extends JFrame implements ActionListener {


    private final String CARPETA = "src" + File.separator + "main" + File.separator + "java" + File.separator + "com" + File.separator + "main" + File.separator + "imagenes" + File.separator;

    private JLabel fondo;
    private JLabel titulo;
    private JButton slot;
    private JButton mina;
    private JButton logUser;
    private JButton score;
    private JLabel maq;
    private Timer halfsec;
    private boolean sec;
    private int bling = 0;
    private boolean blingUp = false;
    private User user = new User("1","Iker","123",10);


    // Imagenes
    private ArrayList<Icon> img = new ArrayList<Icon>();


    //getter
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Lobby() {
        super("GAMES");

        //maquina
        this.setBounds(1000, 100, 480, 640);
        this.setResizable(false);
        this.setLayout(null);
        this.setLocationRelativeTo(null);


        // Timer
        halfsec = new Timer(500, this);
        halfsec.start();


        // Imagenes

        img.add(new ImageIcon(CARPETA + "wall.png"));// 0
        img.add(new ImageIcon(CARPETA + "xogos.png"));// 1
        img.add(new ImageIcon(CARPETA + "red777.png"));// 2
        img.add(new ImageIcon(CARPETA + "mine.png"));// 3
        img.add(new ImageIcon(CARPETA + "userIcon.png"));// 4
        img.add(new ImageIcon(CARPETA + "score.png"));// 5

        // #EABE3F oro

        // SLOT

        titulo = new JLabel();
        titulo.setBounds(85, 110, 325, 100);
        titulo.setIcon(img.get(1));
        add(titulo);

        slot = new JButton();
        slot.setBounds(50, 400, 100, 148);
        slot.setBackground(Color.BLACK);
        slot.setOpaque(true);
        slot.setIcon(img.get(2));
        slot.addActionListener(this);
        add(slot);

        mina = new JButton();
        mina.setBounds(320, 434, 100, 100);
        mina.setOpaque(true);
        mina.setIcon(img.get(3));
        mina.addActionListener(this);
        add(mina);

        logUser = new JButton();
        logUser.setBounds(390, 20, 50, 50);
        logUser.setOpaque(true);
        logUser.setIcon(img.get(4));
        logUser.addActionListener(this);
        add(logUser);

        score = new JButton();
        score.setBounds(320, 20, 50, 50);
        score.setOpaque(true);
        score.setIcon(img.get(5));
        score.addActionListener(this);
        add(score);


        fondo = new JLabel();
        fondo.setBounds(0, 0, 480, 640);
        fondo.setIcon(img.get(0));
        add(fondo);
        System.out.println("fin");

        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


    //Inicia el tablero del buscaminas
    public void tableroMina() {

        int dimensiones = 20;

        int dificultad = 2;

        Tablero t = new Tablero(dimensiones, dificultad);

        t.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent evt) {

                if (t.isTerminada()) {

                    if (user != null) {
                        DataMethods.saveScore(user, t.getPuntuacion());
                    }

                    if (Temporal.confirmMessage(t, "Quieres volver a jugar?", "Otra partida?")) {

                        tableroMina();

                    } else Lobby.super.setVisible(true);
                } else Lobby.super.setVisible(true);
            }
        });

        super.setVisible(false);
    }


    @Override
    public void actionPerformed(ActionEvent action) {

        if (sec) {
            sec = false;
        } else {
            sec = true;
        }

        if (action.getSource() == slot) {

            Supertragaperras window;

            if (user != null) window = new Supertragaperras(user.getSaldo());
            else window = new Supertragaperras(0);

            window.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent evt) {

                    if (user != null) {
                        user.setSaldo(window.getDinero());
                        DataMethods.updateUserSaldo(user);
                    }
                    window.dispose();
                    Lobby.super.setVisible(true);
                }
            });

            super.setVisible(false);
        }


        if (action.getSource() == mina) {

            tableroMina();

        }

        if (action.getSource() == score) {


            int seleccion=Temporal.simpleSelector("Elige una opcion", "Puntuaciones", new String[]{"Usuario", "Global"}, this,new ImageIcon(CARPETA+"trophy.png"));
            if (seleccion==0)

                DataMethods.showUserScore(user, this);

            else if(seleccion==1)

                DataMethods.showGlobalScore(this);
        }


        if (action.getSource() == logUser) {

            /*TODO Hacer una pestaña que compruebe si hay un usuario conectado

                TODO En caso de no estar conectado abrir pestaña de registro/inicio de sesion

                    TODO En las celdas de inicio de sesion que aparezcan pistas de como son el usuario y contraseña

                TODO Si hay un usuario conectado, dar la opcion de salir de sesion o de borrar usuario

                    TODO Para borrar que te pida la contraseña a mano otra vez
            */
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