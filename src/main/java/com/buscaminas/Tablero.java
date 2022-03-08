package com.buscaminas;


import com.Temporal;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;

public class Tablero extends JFrame implements ActionListener {

    private final String CARPETA = "src" + File.separator + "main" + File.separator + "java" + File.separator + "com" + File.separator + "main" + File.separator + "imagenes" + File.separator;

    int dimensiones;
    int dificultad;
    Celda[][] celdas;
    JButton[][] botones;
    int minas;
    int puntuacion;
    boolean terminada = false;
    long playtime;

    public int getPuntuacion() {
        return puntuacion;
    }

    public boolean isTerminada() {
        return terminada;
    }

    public Tablero(int dimension, int dif) {
        super("Buscaminas");
        this.setSize(50 * dimension, 50 * dimension);
        this.setResizable(false);
        this.dimensiones = dimension;
        this.dificultad = dif;
        this.minas = dimensiones * dificultad;
        this.playtime = System.currentTimeMillis();

        this.botones = new JButton[dimensiones][dimensiones];
        this.celdas = new Celda[dimensiones][dimensiones];
        this.setLayout(new GridLayout(dimensiones, dimensiones));
        this.setLocationRelativeTo(null);
        for (int i = 0; i < dimensiones; i++) {
            for (int j = 0; j < dimensiones; j++) {
                this.celdas[i][j] = new Celda();
                this.botones[i][j] = new JButton();
                //Esto almacena en el boton la posicin para luego interactuar con la celda asociada
                this.botones[i][j].setActionCommand(i + "-" + j);
                this.botones[i][j].addActionListener(this);

                //Esto añade un evento "click" de raton
                int fila = i;
                int columna = j;
                this.botones[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

                        //Esto comprueba si es un click derecho
                        if (SwingUtilities.isRightMouseButton(e)) {

                            if (!celdas[fila][columna].isDestapada()) {

                                botones[fila][columna].setEnabled(!botones[fila][columna].isEnabled());

                                if (!botones[fila][columna].isEnabled()) {

                                    botones[fila][columna].setIcon(new ImageIcon(CARPETA + "flag.png"));

                                } else {

                                    botones[fila][columna].setIcon(null);

                                }
                            }
                        }
                    }
                });

                this.add(this.botones[i][j]);
            }
        }
        ponerMinas();
        this.repaint();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }


    private void ponerMinas() {

        //Coloca minas hasta llegar al limite establecido
        for (int i = 0; i < minas; i++) {

            int posX;
            int posY;

            //Esto se asegura que la posicion dada no sea una mina
            do {

                posX = Temporal.getRandomPosArray(dimensiones);

                posY = Temporal.getRandomPosArray(dimensiones);

            } while (celdas[posX][posY].isMina());

            //Esto asigna el valor de mina a la celda
            celdas[posX][posY].setMina(true);

            //Este bloque comprueba los alrededores de la casilla donde se va a colocar la mina
            for (int x = posX - 1; x <= posX + 1; x++) {

                for (int y = posY - 1; y <= posY + 1; y++) {

                    if (x >= 0 && x < this.dimensiones && y >= 0 && y < this.dimensiones) {

                        //Esto se asegura de que la casilla comprobada no es donde se ha generado la mina
                        if ((y != posY ^ x != posX) || (y != posY && x != posX)) {

                            //Esto incrementa el valor de las minas adyacentes a la celda
                            Celda celdaAdyacente = celdas[x][y];

                            celdaAdyacente.incrementarMinas();

                        }
                    }
                }
            }

        }
    }


    public void comprobar(int posX, int posY, JButton boton, Celda c) {

        if (c.isMina()) {

            boton.setBackground(Color.darkGray);

            calculateResult(false);

        } else {

            //Esto cambia el boton si no habia una mina en la cleda asociada
            boton.setBackground(Color.white);
            if (c.getMinasAdyacentes() == 0) boton.setText("");
            else boton.setText(String.valueOf(c.getMinasAdyacentes()));

            //Esto activa un escaneo alrededor de una celda si esta no tiene ninguna mina pegada
            if (c.getMinasAdyacentes() == 0) {

                for (int i = posX - 1; i <= posX + 1; i++) {

                    for (int j = posY - 1; j <= posY + 1; j++) {

                        if (i >= 0 && i < this.dimensiones && j >= 0 && j < this.dimensiones) {

                            if ((j != posY ^ i != posX) || (j != posY && i != posX)) {

                                Celda celdaAdyacente = celdas[i][j];
                                JButton botonAdyacente = botones[i][j];

                                //Esto hace que no se realice la operacion si la celda ya esta destapada
                                if (!celdaAdyacente.isDestapada()) {

                                    //Esto evita que se realice la operacion si la celda es una mina
                                    if (!celdaAdyacente.isMina()) {

                                        botonAdyacente.setBackground(Color.white);
                                        botonAdyacente.removeActionListener(this);
                                        botonAdyacente.setIcon(null);
                                        botonAdyacente.setEnabled(true);
                                        botonAdyacente.setText(String.valueOf(celdaAdyacente.getMinasAdyacentes()));
                                        celdaAdyacente.setDestapada(true);


                                        //Esto hace que si una de las celdas encontradas vuelve a no tener minas adyacentes, se  repita el escaneo
                                        if (celdaAdyacente.getMinasAdyacentes() == 0) {

                                            comprobar(i, j, botonAdyacente, celdaAdyacente);

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            //Esto hace que no puedas interactuar con los botones ya destapados ( pulsados o escaneados)
            boton.removeActionListener(this);
        }

        this.repaint();
        int destapadas = getDestapadas();

        //Esto comprueba si el numero de minas destapadas ya es iguala a las minas totales, si es asi, se gana el juego
        if (destapadas == dimensiones * dimensiones - minas) {

            calculateResult(true);

        }
    }

    //Esto comprueba las celdas destapadas del tablero
    private int getDestapadas() {
        int contador = 0;
        for (int i = 0; i < celdas.length; i++) {
            for (int j = 0; j < celdas.length; j++) {
                if (celdas[i][j].isDestapada()) {
                    contador++;
                }
            }
        }
        return contador;
    }

    public void calculateResult(boolean victory) {

        playtime = System.currentTimeMillis() - playtime;

        playtime /= 1000;
        float penalty = 1.5f;

        while (playtime >= 10) {

            playtime -= 10;

            penalty += 0.1f;

        }

        puntuacion = (int) (getDestapadas() * 8 / penalty);

        String title = null;

        if (victory) {

            puntuacion *= 8;

            title = "Enhorabuena, has ganado!";

        } else {

            title="Lastima,has perdido!";

        }

        Temporal.showMessage(this, "La puntuacion es de " + puntuacion + " puntos", title);
        terminada = true;
        this.dispose();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton boton = (JButton) e.getSource();

        //De aqui scamos las cordenadas del boton pulsado para pasarselas a las celdas
        String[] coordenadas = boton.getActionCommand().split("-");
        int posX = Integer.parseInt(coordenadas[0]);
        int posY = Integer.parseInt(coordenadas[1]);
        Celda c = celdas[posX][posY];
        c.setDestapada(true);

        //Comprueba la celda asociada al boton pulsado
        comprobar(posX, posY, boton, c);
    }

}
