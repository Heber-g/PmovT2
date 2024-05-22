package com.example.controleenderecos.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.controleenderecos.entity.Usuario;

import java.util.List;
@Dao
public interface UsuarioDAO {
    @Query("SELECT * FROM Usuario WHERE usuarioID = :id LIMIT 1")
    Usuario getUsuariosPorID(int id);

    @Query("SELECT * FROM USUARIO")
    List<Usuario> getAll();

    @Query("Select * FROM Usuario WHERE email = :email AND senha = :senha LIMIT 1")
    Usuario login(String email, String senha);

    @Update
    void update(Usuario usuario);

    @Insert
    void insert(Usuario usuario);

    @Delete
    void delete(Usuario usuario);

}
