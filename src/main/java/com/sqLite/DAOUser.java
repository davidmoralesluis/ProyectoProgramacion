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

    /**
     * Inserta en la base de datos los datos del usuario
     * @param u Usuario cuyos datos quieren añadirse
     */
    public void insert(User u) {

        Connection conn = null;

        try {

            conn = new DBConnection().getConn();

            PreparedStatement psUser = conn.prepareStatement("INSERT INTO Usuario(nomUsuario, conUsuario) VALUES(?,?)");
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

    /**
     * Elimina los datos de un usuario de la base de datos
     * @param u Usuario del que se quiere eliminar los datos
     */
    public void delete(User u) {

        Connection conn = null;

        try {

            conn = new DBConnection().getConn();

            PreparedStatement psUser = conn.prepareStatement("DELETE FROM Usuario WHERE idUsuario=?");
            psUser.setString(1, u.getCode() );
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
     * Actualiza los datos del usuario en la base de datos
     * @param u Usuario del que se quiere actualizar los datos
     */
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

    /**
     * Mete en una ArrayList de usuarios a todos los usuarios de la base de datos
     * @return ArrayList de usuarios
     */
    public ArrayList<User> select() {

        ArrayList<User> userList = new ArrayList<User>();
        Connection conn = null;
        ResultSet result;

        try {

            conn = new DBConnection().getConn();

            result = conn.createStatement().executeQuery("SELECT idUsuario,nomUsuario FROM Usuario");

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
        ResultSet result;

        try {

            conn = new DBConnection().getConn();

            PreparedStatement platform=conn.prepareStatement("SELECT * FROM Usuario WHERE idUsuario=?");
            platform.setString(1, id );
            result = platform.executeQuery();

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


    /**
     * Update que solo se utiliza para actualizar el saldo en el monedero del usuario
     * @param u Usuario del que se quiere actualizar el saldo
     */
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

    /**
     * Get de uso unico que se utiliza para iniciar sesion.
     * @param userName Nombre del usuario a buscar en la base de datos
     * @return Los datos del usuario con el id más alto (último insert) que tenga el nombre introducido por parámetro
     */

    public User getRegister(String userName) {
        User u = null;
        Connection conn = null;
        ResultSet result;

        try {

            conn = new DBConnection().getConn();
            PreparedStatement platform=conn.prepareStatement("SELECT * FROM Usuario WHERE idUsuario = (SELECT MAX(idUsuario) FROM Usuario WHERE nomUsuario =?)");
            platform.setString(1,userName);
            result =platform.executeQuery();

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
