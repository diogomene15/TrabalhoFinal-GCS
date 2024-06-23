package com.example.karta.entities;

import androidx.room.Embedded;

public class EnderecoCidade {
    @Embedded
    public Endereco endereco;
    @Embedded
    public Cidade cidade;
}
