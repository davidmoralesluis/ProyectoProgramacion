package com.sqLite;

import com.data.Score;
import com.data.User;


import java.sql.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DAOScore {

    public DAOScore() {
    }

    public void insert(Score s) {


        Connection conn = null;

        String insert = "INSERT INTO Puntuacion(puntuacion,fecha, idUsuario) VALUES(?,?,?)";

        try {

            conn = new DBConnection().getConn();

            PreparedStatement psUser = conn.prepareStatement(insert);
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

    public void delete(User u) {

        Connection conn = null;

        try {

            conn = new DBConnection().getConn();

            PreparedStatement psUser = conn.prepareStatement("DELETE FROM Puntuacion WHERE idUsuario='" + u.getCode() + "'");
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

    public String[][] select() {

        String[][] scoreTable = new String[10][4];
        Connection conn = null;
        String select = "SELECT * FROM Puntuacion ORDER BY puntuacion DESC LIMIT 10";
        ResultSet result;
        DAOUser daoUser = new DAOUser();
        try {

            conn = new DBConnection().getConn();

            result = conn.createStatement().executeQuery(select);

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

    public String[][] get(User u) {

        String[][] userScoreTable = new String[10][3];
        Connection conn = null;
        String get = "SELECT * FROM Puntuacion WHERE idUsuario='" + u.getCode() + "' ORDER BY puntuacion DESC LIMIT 10";
        ResultSet result;

        try {

            conn = new DBConnection().getConn();

            result = conn.createStatement().executeQuery(get);

            int i = 0;
            while (result.next()) {
                i++;

                userScoreTable[i - 1] = new String[]{i + "#", String.valueOf(result.getInt("puntuacion")), result.getTimestamp("fecha").toString()};

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
