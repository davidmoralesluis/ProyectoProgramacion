package com.data;


import com.Temporal;
import com.sqLite.DAOScore;
import com.sqLite.DAOUser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;

public class DataMethods {

    public static User login(String completeUsername, String password, Component parent) {

        DAOUser daoUser = new DAOUser();

        String[] username = completeUsername.split("#");


        if (checkCredentials(username[1],username[0],password,parent)) {

            User logUser = daoUser.get(username[1]);

            if (logUser == null) {

                Temporal.showMessage(parent, "Lo sentimos, el usuario indicado no existe", "Error");

                return null;

            } else if (logUser.getPassword().equals(password)) {

                Temporal.showMessage(parent, "Sesion iniciada correctamente", "Error");

                return logUser;

            } else {

                Temporal.showMessage(parent, "La contraseña no es correcta. Porfavor vuelva a intentarlo.", "Error");

                return null;
            }
        }else{
            return null;
        }
    }

    public static User register(String username, String password, Component parent) {

        if (checkCredentials("0",username,password,parent)){

            DAOUser daoUser = new DAOUser();

            daoUser.insert(new User(null, username, password, 0));

            return daoUser.getRegister(username);

        }else return null;

    }

    public static User deleteUser(User u, Component parent) {

        try {
            if (u == null) throw new noUser(" No se puede eliminar Usuario.");


            DAOUser daoUser = new DAOUser();
            DAOScore daoScore = new DAOScore();

            daoUser.delete(u);
            daoScore.delete(u);

            return null;
        } catch (noUser e) {

            Temporal.showMessage(parent, e.getMessage(), "Error");
            return null;
        }
    }


    public static boolean checkCredentials(String code,String userName,String password, Component parent){

        if (userName.contains("#")) {

            Temporal.showMessage(parent, "El nombre de usuario nopuede incluir el caracter '#'.", "Error");

            return false;
        } else if (userName.length() > 16 || password.length() > 8) {

            Temporal.showMessage(parent, "Nombre o contraseña demasiado largos. El maximo para usuario es 16 caracteres y para la contraseña 8", "Error");

            return false;

        }else if(Temporal.isNumeric(code)){

            Temporal.showMessage(parent, "Error en la clave", "Error");

            return false;
        }else return true;

    }


    //Selector
    public static User changeUserData(User u, Component parent,int option) {

        try {
            if (u == null) throw new noUser("No es posible cambiar las credenciales del usuario");

            DAOUser daoUser = new DAOUser();

            User temporalUser=new User(u.getCode(),u.getName(),u.getPassword(),u.getSaldo());

            if (option == 1) temporalUser.setName("Pedir Nombre");

            else if (option == 2) temporalUser.setPassword("Pedir contraseña");

            else if (option==3){

                temporalUser.setName("Pedir Nombre");

                temporalUser.setPassword("Pedir contraseña");

            }else return u;


            if (checkCredentials(temporalUser.getCode(),temporalUser.getName(),temporalUser.getPassword(),parent)) {
                daoUser.update(u);
                return temporalUser;
            }
            else{
                Temporal.showMessage(parent,"Credenciales actualizadas no validas", "Error");

                return u;
            }


        } catch (noUser e) {

            Temporal.showMessage(parent, e.getMessage(), "Error");
            return null;
        }
    }


    public static void updateUserSaldo(User u) {

        DAOUser daoUser = new DAOUser();

        daoUser.updateSaldo(u);

    }


    public static void saveScore(User u, int score) {

        try {
            if (u == null) throw new noUser("No es posible guardar la puntuacion.");

            DAOScore daoScore = new DAOScore();

            daoScore.insert(new Score(u, score));
        } catch (noUser e) {

            e.printStackTrace();
        }
    }

    public static void showUserScore(User u, Component parent) {

        try {

            if (u == null) throw new noUser("No es posible importar la lista de puntuaciones");

            DAOScore daoScore = new DAOScore();

            String[][] userScoreTable = daoScore.get(u);
            String[] tableTitle = {"Posicion", "Puntuacion", "Fecha"};

            String fullUsername=u.getName()+"#"+u.getCode();
            createTable(userScoreTable,tableTitle,"Puntuacion de "+fullUsername+"",parent,u);

        } catch (noUser e) {

            Temporal.showMessage(parent, e.getMessage(), "Error");
        }
    }

    public static void showGlobalScore(Component parent) {

        DAOScore daoScore = new DAOScore();

        String[][] globalScoreTable = daoScore.select();
        String[] tableTitle = {"Posicion", "Jugador", "Puntuacion", "Fecha"};

        createTable(globalScoreTable,tableTitle,"Puntuacion global",parent,null);

    }

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

            String path=Temporal.selectPath(parent,"Guardar Puntuacion");

            if (path!=null) Temporal.fileWrite(path+"Puntuaciones_"+user+".txt",data,true);

        }


    }



}
