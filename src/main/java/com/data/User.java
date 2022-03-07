package com.data;

public class User {

    private String code;

    private String name;

    private String password;

    private double saldo;

    public User(String code, String name, String password,double saldo) {
        this.code = code;
        this.name = name;
        this.password = password;
        this.saldo=saldo;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}
