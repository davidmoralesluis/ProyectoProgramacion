package com.data;



import com.sqLite.DAOScore;
import com.sqLite.DAOUser;
import libreriaProyecto.LiProyecto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;

public class DataMethods {

    /**
     * Busca un usuario guardado en una base de datos e inicia sesion
     * @param completeUsername Nombre de usuario, compuesto por: nombre#numero cuenta
     * @param password Contraseña del usuario
     * @param parent Componente padre de la pestaña. En caso de no haber, escribir null.
     * @return Si el usuario existe y la contraseña es correcta devuelve el usuario, en caso contrario devuelve null.
     */
    public static User login(String completeUsername, String password, Component parent) {

        DAOUser daoUser = new DAOUser();

        if (completeUsername==null) {

            return null;

        }
            String[] username = completeUsername.split("#");


        if (checkCredentials(username[1],username[0],password,parent)) {

            User logUser = daoUser.get(username[1]);

            if (logUser == null) {

                LiProyecto.showMessage(parent, "Lo sentimos, el usuario indicado no existe", "Error");

                return null;

            } else if (logUser.getPassword().equals(password)) {

                LiProyecto.showMessage(parent, "Sesion iniciada correctamente", "Sesion Iniciada");

                return logUser;

            } else {

                LiProyecto.showMessage(parent, "La contraseña no es correcta. Porfavor vuelva a intentarlo.", "Error");

                return null;
            }
        }else{
            return null;
        }
    }

    /**
     * Registra al usuario en la base de datos.
     * @param username Nombre del usuario.
     * @param password COntraseña del usuario.
     * @param parent Componente padre de la pestaña. En caso de no haber, escribir null.
     * @return Si el usuario que se introduce es valido (cumple los requisitos de la base de datos), devuelve el usuario creado.
     *  En caso contrario devuelve null.
     */
    public static User register(String username, String password, Component parent) {

        if (checkCredentials("0",username,password,parent)){

            DAOUser daoUser = new DAOUser();

            daoUser.insert(new User(null, username, password, 0));

            return daoUser.getRegister(username);

        }else return null;

    }

    /**
     * Elimina los datos del usuario (incluidadlas puntuaciones) de la base de datos
     * @param u El usuario a eliminar.
     * @param parent Componente padre de la pestaña. En caso de no haber, escribir null.
     * @return Devuelve null.
     */
    public static User deleteUser(User u, Component parent) {

        try {
            if (u == null) throw new NoUserException(" No se puede eliminar Usuario.");


            DAOUser daoUser = new DAOUser();
            DAOScore daoScore = new DAOScore();

            daoUser.delete(u);
            daoScore.delete(u);

            return null;
        } catch (NoUserException e) {

            LiProyecto.showMessage(parent, e.getMessage(), "Error");
            return null;
        }
    }

    /**
     * Comprueba que las credenciales de usuario son correctas.
     * @param code Codigo de usuario.
     * @param userName Nombre de usuario.
     * @param password Contraseña de usuario.
     * @param parent Componente padre de la pestaña. En caso de no haber, escribir null.
     * @return Devuelve true si las credenciales son validas y false si no lo son.
     */
    public static boolean checkCredentials(String code,String userName,String password, Component parent){

        try {

            if (userName.contains("#")) {

                LiProyecto.showMessage(parent, "El nombre de usuario no puede incluir el caracter '#'.", "Error");

                return false;
            } else if (userName.length() > 16 || password.length() > 8) {

                LiProyecto.showMessage(parent, "Nombre o contraseña demasiado largos. El maximo para usuario es 16 caracteres y para la contraseña 8", "Error");

                return false;

            } else if (!LiProyecto.isNumeric(code)) {

                LiProyecto.showMessage(parent, "Error en la clave", "Error");

                return false;
            } else return true;
        }catch (NullPointerException e){

         return false;
        }
    }


    /**
     *  Modifica las credenciales del usuario
     * @param u Usuario a modificar
     * @param parent Componente padre de la pestaña. En caso de no haber, escribir null.
     * @param option Un entero que dependiando de su valor modificara el nombre(0), contraseña(1) o ambas credenciales(2) del usuario
     * @return Devuelve el usuario con las credenciales cambiadas si estos cambios son validos, sinio devuelve el usuario sin variar
     */
    public static User changeUserData(User u, Component parent,int option) {

        try {
            if (u == null) throw new NoUserException("No es posible cambiar las credenciales del usuario");

            DAOUser daoUser = new DAOUser();

            User temporalUser=new User(u.getCode(),u.getName(),u.getPassword(),u.getSaldo());

            if (option == 0) temporalUser.setName(LiProyecto.askString("Introduce el nuevo nombre de usuario",parent));

            else if (option == 1) temporalUser.setPassword(LiProyecto.askString("Introduce la nueva contraseña",parent));

            else if (option==2){

                temporalUser.setName(LiProyecto.askString("Introduce el nuevo nombre de usuario",parent));

                temporalUser.setPassword(LiProyecto.askString("Introduce la nueva contraseña",parent));

            }else return u;


            if (checkCredentials(temporalUser.getCode(),temporalUser.getName(),temporalUser.getPassword(),parent)) {
                daoUser.update(temporalUser);
                return temporalUser;
            }
            else{
                LiProyecto.showMessage(parent,"Credenciales actualizadas no validas", "Error");

                return u;
            }


        } catch (NoUserException e) {

            LiProyecto.showMessage(parent, e.getMessage(), "Error");
            return null;
        }
    }

    /**
     * Actualiza el saldo de la supertragaperras en la base de datos
     * @param u El usuario al que queremos actualizarle el saldo
     */
    public static void updateUserSaldo(User u) {
        try{
            if (u==null) throw new NoUserException("No puede guardarse saldo en el monedero");
            DAOUser daoUser = new DAOUser();

            daoUser.updateSaldo(u);
        }catch(NoUserException e){

            e.printStackTrace();
        }


    }

    /**
     * Guarda la puntuacion del buscaminas en la base de datos
     * @param u El usuario que ha sacado la puntuacion
     * @param score La puntuacion generada durante la partida
     */
    public static void saveScore(User u, int score) {

        try {
            if (u == null) throw new NoUserException("No es posible guardar la puntuacion.");

            DAOScore daoScore = new DAOScore();

            daoScore.insert(new Score(u, score));
        } catch (NoUserException e) {

            e.printStackTrace();
        }
    }

    /**
     * Genera una array bidimensional con las 10 mejores puntuaciones del usuario
     * @param u Usuario del que se importan las puntuaciones
     * @param parent Componente padre de la pestaña. En caso de no haber, escribir null.
     */
    public static void showUserScore(User u, Component parent) {

        try {

            if (u == null) throw new NoUserException("No es posible importar la lista de puntuaciones");

            DAOScore daoScore = new DAOScore();

            String[][] userScoreTable = daoScore.get(u);
            String[] tableTitle = {"Posicion", "Puntuacion", "Fecha"};

            String fullUsername=u.getName()+"#"+u.getCode();
            createTable(userScoreTable,tableTitle,"Puntuacion de "+fullUsername+"",parent,u);

        } catch (NoUserException e) {

            LiProyecto.showMessage(parent, e.getMessage(), "Error");
        }
    }

    /**
     * Genera una array bidimensional con las 10 mejores puntuaciones globales
     * @param parent Componente padre de la pestaña. En caso de no haber, escribir null.
     */
    public static void showGlobalScore(Component parent) {

        DAOScore daoScore = new DAOScore();

        String[][] globalScoreTable = daoScore.select();
        String[] tableTitle = {"Posicion", "Jugador", "Puntuacion", "Fecha"};

        createTable(globalScoreTable,tableTitle,"Puntuacion global",parent,null);

    }


    /**
     * Crea una tabla de puntuaciones y la saca por un JOptionPane
     * @param data Array bidireccional que contiene las puntuaciones
     * @param title Array con los titulos de cada columna
     * @param messageTitle El titulo de la pestaña con la tabla
     * @param parent Componente padre de la pestaña. En caso de no haber, escribir null.
     * @param u Usuario que recive por si acaso las puntuaciones son de usuario
     */
    private static void createTable( String[][] data,String[] title,String messageTitle,Component parent,User u) {

        String user;
        if (u==null)user="Global";
        else user=u.getName()+"#"+u.getCode();

        TableModel model = new DefaultTableModel(data, title)
        {
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.getTableHeader().setReorderingAllowed(false);

        int selection=JOptionPane.showOptionDialog(parent, new JScrollPane(table){
            public Dimension getPreferredSize() {
                return new Dimension(800, 183);
            }
        },messageTitle,JOptionPane.INFORMATION_MESSAGE, JOptionPane.INFORMATION_MESSAGE,null,new String[] {"Guardar Fichero","Salir"},-1);

        if (selection==0){

            String path=LiProyecto.selectPath(parent,"Guardar Puntuacion");

            if (path!=null) LiProyecto.fileWrite(path+"Puntuaciones_"+user+".txt",data,true);

        }


    }



}
