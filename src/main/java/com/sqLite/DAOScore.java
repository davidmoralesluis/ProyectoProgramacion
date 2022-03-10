package com.sqLite;

import com.data.Score;
import com.data.User;


import java.sql.*;

import java.text.SimpleDateFormat;


public class DAOScore {

    public DAOScore() {
    }


    /**
     * Inserta en la base de datos los datos de la puntuacion
     * @param s
     */
    public void insert(Score s) {

        Connection conn = null;

        try {

            conn = new DBConnection().getConn();

            PreparedStatement psUser = conn.prepareStatement("INSERT INTO Puntuacion(puntuacion,fecha, idUsuario) VALUES(?,?,?)");
            psUser.setInt(1, s.getScore());
            psUser.setString(2, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Timestamp(System.currentTimeMillis())));
            psUser.setString(3, s.getUserCode());
            psUser.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Elimina las puntuaciones asociadas a un usuario de la base de datos
     * @param u Usuario del que se quieren eliminar las puntuaciones
     */
    public void delete(User u) {

        Connection conn = null;

        try {

            conn = new DBConnection().getConn();

            PreparedStatement psUser = conn.prepareStatement("DELETE FROM Puntuacion WHERE idUsuario=?");
            psUser.setString(1,u.getCode());
            psUser.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Recoje las 10 mayores puntuaciones de la base de datos en una array bidimensional
     * @return Una array bidimnsional con las 10 mayores puntuaciones
     */
    public String[][] select() {

        String[][] scoreTable = new String[10][4];
        Connection conn = null;
        ResultSet result;
        DAOUser daoUser = new DAOUser();
        try {

            conn = new DBConnection().getConn();

            result = conn.createStatement().executeQuery("SELECT * FROM Puntuacion ORDER BY puntuacion DESC LIMIT 10");

            int i = 0;
            while (result.next()) {
                i++;
                String userName = daoUser.get(result.getString("idUsuario")).getName();
                scoreTable[i - 1] = new String[]{i + "#", userName + "#" + result.getString("idUsuario"), String.valueOf(result.getInt("puntuacion")), result.getTimestamp("fecha").toString()};

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return scoreTable;

    }

    /**
     *  Recoje las 10 mayores puntuaciones de un usuario en una array bidimensional
     * @param u El usuario cuyas puntuacione deseamos recojer
     * @return Una array bidimnsional con las 10 mayores puntuaciones
     */
    public String[][] get(User u) {

        String[][] userScoreTable = new String[10][3];
        Connection conn = null;
        ResultSet result;

        try {

            conn = new DBConnection().getConn();

            PreparedStatement platform=conn.prepareStatement("SELECT * FROM Puntuacion WHERE idUsuario=? ORDER BY puntuacion DESC LIMIT 10");
            platform.setString(1,u.getCode());
            result = platform.executeQuery();
            int i = 0;
            while (result.next()) {
                i++;

                userScoreTable[i - 1] = new String[]{"#" + i, String.valueOf(result.getInt("puntuacion")), result.getTimestamp("fecha").toString()};

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return userScoreTable;

    }
}
