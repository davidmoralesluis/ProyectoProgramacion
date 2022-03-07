package com;

import javax.swing.*;
import java.awt.*;

public class Temporal {

    public static int getRandomPosArray(int dimension) {

        return (int) (Math.random() * dimension);

    }


    public static void showMessage(Component parent, String mensaje, String title) {

        JOptionPane.showMessageDialog(parent, mensaje, title, JOptionPane.INFORMATION_MESSAGE);

    }

    public static boolean confirmMessage(Component parent, String mensaje, String title) {

        return JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(parent, mensaje, title, JOptionPane.YES_NO_OPTION);

    }

    public static int simpleSelector(String mensaje, String titulo, String[] opciones, Component parent) {

        return JOptionPane.showOptionDialog(parent, mensaje, titulo,
                JOptionPane.YES_NO_CANCEL_OPTION, 0, null, opciones, -1);
    }


}
