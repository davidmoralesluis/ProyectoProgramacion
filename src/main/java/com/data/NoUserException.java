package com.data;

public class NoUserException extends Exception {

    public NoUserException(String errorMessage) {
        super("Error: No se ha iniciado sesion con ningun usuario. " + errorMessage);
    }

}
