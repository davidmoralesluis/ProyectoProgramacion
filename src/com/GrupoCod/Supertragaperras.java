package com.GrupoCod;
import java.util.ArrayList;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Supertragaperras extends JFrame implements ActionListener {

    JLabel titulo;
    JLabel firstIcon;
    JLabel secondIcon;
    JLabel thirdIcon;
    JLabel money;
    JLabel saldo;
    JLabel winLose;
    JLabel insertArrow;
    JLabel rightArrow;
    JLabel leftArrow;
    JLabel info;
    JButton startboton;
    JButton insertCoin;
    Timer sec;
    Timer ganacias;
    Timer mili300;
    boolean segundo=false;
    boolean mili=false;
    boolean cash=false;
    double dinero=0;

    //Imagenes
    ArrayList <Icon> img = new ArrayList <Icon>();


    public Supertragaperras() {

        super("Supertragaperras");

        //maquina
        this.setBounds(1000, 300, 400, 480);
        this.setLayout(null);
        this.getContentPane().setBackground(Color.BLACK);

        //Imagenes
        img.add(new ImageIcon("good-luck.png"));//0
        img.add(new ImageIcon("button.png"));//1
        img.add(new ImageIcon("insert-coin.png"));//2
        img.add(new ImageIcon("dollar.png"));//3
        img.add(new ImageIcon("insert-arrow.png"));//4
        img.add(new ImageIcon("right-arrow.png"));//5
        img.add(new ImageIcon("left-arrow.png"));//6
        img.add(new ImageIcon("7.png"));//7
        img.add(new ImageIcon("herz.png"));//8
        img.add(new ImageIcon("diamond.png"));//9
        img.add(new ImageIcon("clover.png"));//10
        img.add(new ImageIcon("cherry.png"));//11
        img.add(new ImageIcon("limon.png"));//12
        img.add(new ImageIcon("fail.png"));//13


        //Timer
        sec = new Timer(1000,this);
        sec.start();

        mili300 = new Timer(300,this);
        mili300.start();

        ganacias = new Timer(800,this);

        //Titulo
        titulo = new JLabel();
        titulo.setText("PLAY 'N' WIN");
        titulo.setFont(new Font("Courier 10 Pitch", Font.BOLD+Font.CENTER_BASELINE, 25));
        titulo.setBounds(40, 20, 200, 30);
        add(titulo);

        //Informacion
        info = new JLabel();
        info.setFont(new Font("Courier 10 Pitch", Font.BOLD+Font.CENTER_BASELINE, 30));
        info.setForeground(Color.WHITE);
        info.setBounds(50, 370, 290, 30);
        info.setOpaque(true);
        info.setVisible(false);
        add(info);

        //SLOT
        firstIcon = new JLabel();
        firstIcon.setBounds(50, 90, 64, 64);

        firstIcon.setIcon(img.get(0));
        add(firstIcon);

        secondIcon = new JLabel();
        secondIcon.setBounds(168, 90, 64, 64);
        secondIcon.setIcon(img.get(0));
        add(secondIcon);

        thirdIcon = new JLabel();
        thirdIcon.setBounds(286, 90, 64, 64);
        thirdIcon.setIcon(img.get(0));
        add(thirdIcon);

        //start
        startboton = new JButton();
        startboton.setBounds(168, 200, 64, 64);
        startboton.setBackground(new Color(0,0,0,0));
        startboton.setIcon(img.get(1));
        startboton.setOpaque(false);
        startboton.setBorderPainted(false);
        startboton.setFocusPainted(false);
        startboton.addActionListener(this);
        add(startboton);
        leftArrow = new JLabel();
        leftArrow.setBounds(240, 216, 32, 32);
        leftArrow.setIcon(img.get(6));
        leftArrow.setVisible(false);
        add(leftArrow);
        rightArrow = new JLabel();
        rightArrow.setBounds(128, 216, 32, 32);
        rightArrow.setIcon(img.get(5));
        rightArrow.setVisible(false);
        add(rightArrow);

        //coin
        insertCoin = new JButton();
        insertCoin.setBounds(320, 10, 64, 64);
        insertCoin.setBackground(new Color(0,0,0,0));
        insertCoin.setIcon(img.get(2));
        insertCoin.setOpaque(false);
        insertCoin.setBorderPainted(false);
        insertCoin.setFocusPainted(false);
        insertCoin.addActionListener(this);
        add(insertCoin);
        insertArrow = new JLabel();
        insertArrow.setBounds(280, 26, 32, 32);
        insertArrow.setIcon(img.get(4));
        insertArrow.setVisible(false);
        add(insertArrow);

        //money
        money = new JLabel();
        money.setBounds(20, 290, 64, 64);
        money.setIcon(img.get(3));
        add(money);

        //saldo
        saldo = new JLabel();
        saldo.setText(dinero+"$");//(String.format("%05.2f",dinero));
        saldo.setFont(new Font("Courier 10 Pitch", Font.BOLD+Font.CENTER_BASELINE, 30));
        saldo.setForeground(Color.YELLOW);
        saldo.setBounds(100, 300, 120, 35);
        add(saldo);

        winLose = new JLabel();
        winLose.setFont(new Font("Courier 10 Pitch", Font.BOLD+Font.CENTER_BASELINE, 40));
        winLose.setBounds(230, 300, 150, 35);
        add(winLose);


    }

    @Override
    public void actionPerformed(ActionEvent action) {

        if (dinero>1) {
            cash=true;
        }else{
            cash=false;
        }

        if (action.getSource()==mili300) {
            if (mili) {
                if (cash) {
                    rightArrow.setBounds(128, 216, 32, 32);
                    leftArrow.setBounds(240, 216, 32, 32);
                    rightArrow.setVisible(true);
                    leftArrow.setVisible(true);
                } else {
                    insertArrow.setBounds(280, 26, 32, 32);
                    rightArrow.setVisible(false);
                    leftArrow.setVisible(false);
                }
                mili=false;
            } else {
                if (cash) {
                    insertArrow.setVisible(false);
                    rightArrow.setBounds(128, 216, 32, 32);
                    leftArrow.setBounds(250, 216, 32, 32);
                } else {
                    insertArrow.setVisible(true);
                    insertArrow.setBounds(270, 26, 32, 32);
                }
                mili=true;
            }
        }

        if (action.getSource()==sec) {
            if (segundo) {
                titulo.setForeground(Color.RED);
                info.setBackground(Color.MAGENTA);
                info.setForeground(Color.CYAN);
                segundo=false;
            } else {
                titulo.setForeground(Color.ORANGE);
                info.setBackground(Color.CYAN);
                info.setForeground(Color.MAGENTA);
                segundo=true;
            }
        }

        if (action.getSource()==ganacias) {
            ganacias.stop();;
            winLose.setVisible(false);
        }

        if (action.getSource()==insertCoin) {
            dinero+=10.0;
            saldo.setText(String.format("%02.2f", dinero));
            winLose.setText("+10$");
            winLose.setForeground(Color.GREEN);
            ganacias.start();
            winLose.setVisible(true);
            titulo.setText("PLAY 'N' WIN");
            firstIcon.setIcon(img.get(0));
            secondIcon.setIcon(img.get(0));
            thirdIcon.setIcon(img.get(0));
        }

        if (action.getSource()==startboton) {
            if (dinero<1) {
                firstIcon.setIcon(img.get(13));
                secondIcon.setIcon(img.get(13));
                thirdIcon.setIcon(img.get(13));
                titulo.setText("INSERTA >>>");
            } else {
                titulo.setText("PLAY 'N' WIN");
                dinero--;

                Icon [] slot=new Icon [3];
                int pix=0;

                for (int i = 0; i < slot.length; i++) {
                    pix=(int)(Math.random()*6+1);
                    slot[i]=img.get(pix+6);
                }

                firstIcon.setIcon(slot[0]);
                secondIcon.setIcon(slot[1]);
                thirdIcon.setIcon(slot[2]);

                if (slot[0].equals(slot[1])&&slot[0].equals(slot[2])) {
                    info.setText("!! JACKPOT !!");
                    info.setVisible(true);
                    dinero+=5;
                    saldo.setText(dinero+"$");
                    winLose.setText("+5.00$");
                    winLose.setForeground(Color.GREEN);
                    ganacias.start();
                    winLose.setVisible(true);
                }else{
                    if (slot[0].equals(slot[2])||slot[0].equals(slot[1])||slot[1].equals(slot[2])) {
                        info.setText("!! Has Ganado !!");
                        info.setVisible(true);
                        dinero+=1.5;
                        saldo.setText(dinero+"$");
                        winLose.setText("+1.50$");
                        winLose.setForeground(Color.GREEN);
                        ganacias.start();
                        winLose.setVisible(true);
                    }else{
                        info.setText("Mala Suerte :(");
                        info.setVisible(true);
                        saldo.setText(dinero+"$");
                        winLose.setText("-1$");
                        winLose.setForeground(Color.RED);
                        ganacias.start();
                        winLose.setVisible(true);
                    }
                }
            }
        }
    }
}
