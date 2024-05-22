package com.example.controleenderecos.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.controleenderecos.entity.Cidade;

import java.util.List;

@Dao
public interface CidadeDAO {
    @Query("SELECT * FROM Cidade WHERE cidadeID = :id LIMIT 1")
    Cidade getCidadeID(int id);

    @Query("Select * from Cidade")
    List<Cidade> getAll();

    @Update
    void update(Cidade cidade);

    @Insert
    void insert(Cidade cidade);

    @Delete
    void delete(Cidade cidade);
}
