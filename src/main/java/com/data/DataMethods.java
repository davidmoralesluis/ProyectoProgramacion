package com.data;


import com.Temporal;
import com.sqLite.DAOScore;
import com.sqLite.DAOUser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;

public class DataMethods {

    public static User login(String completeUserName, String password, Component parent) {

        DAOUser daoUser = new DAOUser();

        String[] userName = completeUserName.split("#");

        User logUser = daoUser.get(userName[1]);

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
    }

    public static User register(String userName, String password, Component parent) {

        if (userName.contains("#")) {

            Temporal.showMessage(parent, "El nombre de usuario nopuede incluir el caracter '#'.", "Error");

            return null;
        } else if (userName.length() > 16 || password.length() > 8) {

            Temporal.showMessage(parent, "Nombre o contraseña demasiado largos. El maximo para usuario es 16 caracteres y para la contraseña 8", "Error");

            return null;
        } else {

            DAOUser daoUser = new DAOUser();

            daoUser.insert(new User(null, userName, password, 0));

            return daoUser.getRegister(userName);

        }

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


    //Selector
    public static User changeUserData(User u, Component parent) {

        try {
            if (u == null) throw new noUser("No es posible cambiar las credenciales del usuario");

            DAOUser daoUser = new DAOUser();


            if (1 == 1) u.setName("Pedir Nombre");


            else if (1 == 2) u.setPassword("Pedir contraseña");

            else {

                u.setName("Pedir Nombre");

                u.setPassword("Pedir contraseña");

            }
            daoUser.update(u);

            return u;
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
