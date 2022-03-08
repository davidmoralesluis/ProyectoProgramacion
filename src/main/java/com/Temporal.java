package com;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

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

    public static int simpleSelector(String mensaje, String titulo, String[] opciones, Component parent,Icon icon) {

        return JOptionPane.showOptionDialog(parent, mensaje, titulo,
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, icon, opciones, -1);
    }

    /**
     * Funcion que escribe en un fichero los distintos objetos String de una ArrayList. Se realiza un salto de linea despues de cada String escrito
     * @param fileName Nombre del fichero donde escribir el texto
     * @param table Array de dos dimensiones de tipo string con el texto que se desea guardar en fichero
     * @param newFile Valor "boolean" que indica si se desea escribir el texto en un fichero nuevo (true) o mantener el anterior (false)
     */
    public static void fileWrite(String fileName, String[][] table, Boolean newFile) {

        FileWriter fich = null;

        try {
            fich = new FileWriter(fileName, !newFile);

            for(String[] x:table){

                String line= String.join(";",x);
                fich.write(line+"\n");

            }

        } catch (IOException ex) {

            System.out.println("Error Escritura:" + ex.getMessage());

        } finally {
            try {
                fich.close();
            }catch (IOException ex) {

                System.out.println("Error Escritura:" + ex.getMessage());

            }
        }
    }


    public static String selectPath(Component parent,String title){

        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File(System.getProperty("user.home")));
        chooser.setDialogTitle(title);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        chooser.setFileHidingEnabled(true);

        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {

            return chooser.getSelectedFile().getPath()+ File.separator;

        } else {
            return null;
        }

    }

    public static String askString(String mensaje,Component parent) {

        return JOptionPane.showInputDialog(parent,mensaje);
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }


}
