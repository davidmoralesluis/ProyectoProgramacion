package com.data;

public class noUser extends Exception {

    public noUser(String errorMessage) {
        super("Error: No se ha iniciado sesion con ningun usuario. " + errorMessage);
    }

}
