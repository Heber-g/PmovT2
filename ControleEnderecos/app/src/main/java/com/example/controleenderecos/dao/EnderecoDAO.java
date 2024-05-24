package com.example.controleenderecos.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.controleenderecos.entity.Endereco;

import java.util.List;

@Dao
public interface EnderecoDAO {
    @Query("SELECT * FROM Endereco")
    List<Endereco> getAll();

    @Query("SELECT * FROM Endereco WHERE enderecoID = :id LIMIT 1")
    Endereco getEndID(int id);

    @Update
    void update(Endereco endereco);

    @Delete
    void delete(Endereco endereco);

    @Insert
    void insert(Endereco endereco);
}
