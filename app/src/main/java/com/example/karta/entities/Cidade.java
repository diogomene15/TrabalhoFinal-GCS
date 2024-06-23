package com.example.karta.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Cidade {
    @PrimaryKey(autoGenerate = true)
    int cidadeId;
    String cidade;
    String estado;

    public Cidade(String cidade, String estado) {
        this.cidade = cidade;
        this.estado = estado;
    }

    public int getCidadeId() {
        return cidadeId;
    }

    public void setCidadeId(int cidadeId) {
        this.cidadeId = cidadeId;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String toString(){
        return this.getCidade() + " - " + this.getEstado();
    }

}
