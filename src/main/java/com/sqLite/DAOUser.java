package com.sqLite;

import com.data.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOUser {

    public DAOUser() {
    }


    public void insert(User u) {

        Connection conn = null;

        String insert = "INSERT INTO Usuario(nomUsuario, conUsuario) VALUES(?,?)";

        try {

            conn = new DBConnection().getConn();

            PreparedStatement psUser = conn.prepareStatement(insert);
            psUser.setString(1, u.getName());
            psUser.setString(2, u.getPassword());
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

            PreparedStatement psUser = conn.prepareStatement("DELETE FROM Usuario WHERE idUsuario='" + u.getCode() + "'");
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

    public void update(User u) {

        Connection conn = null;

        try {

            conn = new DBConnection().getConn();

            PreparedStatement platform = conn.prepareStatement("UPDATE Usuario SET nomUsuario=?,conUsuario=? WHERE idUsuario=?");
            platform.setString(1, u.getName());
            platform.setString(2, u.getPassword());
            platform.setString(3, u.getCode());
            platform.executeUpdate();

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

    public ArrayList<User> select() {

        ArrayList<User> userList = new ArrayList<User>();
        Connection conn = null;
        String select = "SELECT idUsuario,nomUsuario FROM Usuario";
        ResultSet result;

        try {

            conn = new DBConnection().getConn();

            result = conn.createStatement().executeQuery(select);

            while (result.next()) {

                userList.add(new User(result.getString("idUsuario"), result.getString("nomUsuario"), "********", result.getDouble("saldo")));
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

        return userList;

    }

    public User get(String id) {

        User u = null;
        Connection conn = null;
        String get = "SELECT * FROM Usuario WHERE idUsuario='" + id + "'";
        ResultSet result = null;

        try {

            conn = new DBConnection().getConn();

            result = conn.createStatement().executeQuery(get);

            if (result.next())
                u = new User(result.getString("idUsuario"), result.getString("nomUsuario"), result.getString("conUsuario"), result.getDouble("saldo"));

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return u;

    }

    public void updateSaldo(User u) {

        Connection conn = null;

        try {

            conn = new DBConnection().getConn();

            PreparedStatement platform = conn.prepareStatement("UPDATE Usuario SET saldo=? WHERE idUsuario=?");
            platform.setDouble(1, u.getSaldo());
            platform.setString(2, u.getCode());
            platform.executeUpdate();

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


    public User getRegister(String userName) {
        User u = null;
        Connection conn = null;
        String get = "SELECT * FROM bugs WHERE ID = (SELECT MAX(ID) FROM bugs WHERE user ='" + userName + "')";
        ResultSet result;

        try {

            conn = new DBConnection().getConn();

            result = conn.createStatement().executeQuery(get);

            if (result.next())
                u = new User(result.getString("idUsuario"), result.getString("nomUsuario"), result.getString("conUsuario"), result.getDouble("saldo"));

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return u;

    }

}
