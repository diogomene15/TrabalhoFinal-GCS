package com.example.karta.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(
        foreignKeys = {
                @ForeignKey(entity = Cidade.class, parentColumns = "cidadeId", childColumns = "cidadeIdFk")
        }
)
public class Endereco implements Serializable {
    @PrimaryKey(autoGenerate = true)
    int enderecoId;
    String descricao;
    double latitude;
    double longitude;
    int cidadeIdFk;

    public Endereco(String descricao, double latitude, double longitude, int cidadeIdFk) {
        this.descricao = descricao;
        this.latitude = latitude;
        this.longitude = longitude;
        this.cidadeIdFk = cidadeIdFk;
    }

    public Endereco(){}

    public int getEnderecoId() {
        return enderecoId;
    }

    public void setEnderecoId(int enderecoId) {
        this.enderecoId = enderecoId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getCidadeIdFk() {
        return cidadeIdFk;
    }
    public void setCidadeIdFk(int cidadeIdFk) {
        this.cidadeIdFk = cidadeIdFk;
    }
}
