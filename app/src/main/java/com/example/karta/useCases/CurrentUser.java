package com.example.karta.useCases;

import com.example.karta.entities.Usuario;

public class CurrentUser {
    private static CurrentUser instance;
    private Usuario user;

    private CurrentUser() {}

    public static synchronized CurrentUser getInstance() {
        if (instance == null) {
            instance = new CurrentUser();
        }
        return instance;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public Usuario getUser() {
        return user;
    }
}