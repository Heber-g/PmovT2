package com.example.controleenderecos.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.controleenderecos.dao.CidadeDAO;
import com.example.controleenderecos.dao.EnderecoDAO;
import com.example.controleenderecos.dao.UsuarioDAO;
import com.example.controleenderecos.entity.Cidade;
import com.example.controleenderecos.entity.Endereco;
import com.example.controleenderecos.entity.Usuario;

@Database(entities = {Cidade.class, Endereco.class, Usuario.class}, version = 1)
public abstract class LocalDatabase extends RoomDatabase {
    private static LocalDatabase INSTANCE;
    public static LocalDatabase getDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), LocalDatabase.class, "ControleDeEnderecos").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
    public abstract UsuarioDAO usuarios();
    public abstract CidadeDAO cidades();
    public abstract EnderecoDAO enderecos();

}
