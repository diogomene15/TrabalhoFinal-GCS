package com.example.karta.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.karta.entities.Cidade;
import com.example.karta.entities.Endereco;

import java.util.List;

@Dao
public interface CidadeDAO {
    @Query("SELECT * FROM Cidade WHERE cidadeId=:idCidade LIMIT 1")
    public Cidade getEndereco(int idCidade);
    @Query("SELECT * FROM Cidade")
    public List<Cidade> getAll();

    @Query("SELECT * FROM Cidade WHERE cidade=:cidade AND estado=:estado LIMIT 1")
    public Cidade getByCidadeAndEstado(String cidade, String estado);

    @Insert
    void insert(Cidade cidade);

    @Update
    void update(Cidade cidade);

    @Delete
    void delete(Cidade cidade);
}
