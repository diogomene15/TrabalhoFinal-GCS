package com.example.karta.database;

import android.content.Context;

import androidx.room.Database;

import com.example.karta.dao.CidadeDAO;
import com.example.karta.dao.EnderecoDAO;
import androidx.room.RoomDatabase;
import com.example.karta.dao.UsuarioDAO;
import com.example.karta.entities.Cidade;
import com.example.karta.entities.Endereco;
import com.example.karta.entities.Usuario;
import androidx.room.Room;

@Database(entities = {Usuario.class, Endereco.class, Cidade.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase{
    private static AppDatabase INSTANCE;
    public static AppDatabase getDatabase(Context context){
        if(INSTANCE==null){
            INSTANCE= Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class,"KartaApp")
                    .allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
    public abstract UsuarioDAO usuarioDao();
    public abstract EnderecoDAO enderecoDao();
    public abstract CidadeDAO cidadeDao();
}
