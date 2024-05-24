package com.example.controleenderecos.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.controleenderecos.dao.CidadeDAO;
import com.example.controleenderecos.database.LocalDatabase;

@Entity(foreignKeys = @ForeignKey(entity = Cidade.class, parentColumns = "cidadeID", childColumns = "cidadeIDFK", onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE))
public class Endereco {
    @PrimaryKey(autoGenerate = true) private int enderecoID;
    private String descricao;
    private double latitude;
    private double longitude;
    private int cidadeIDFK;

    public Endereco(String descricao, double latitude, double longitude, int cidadeIDFK){
        this.descricao = descricao;
        this.latitude = latitude;
        this.longitude = longitude;
        this.cidadeIDFK = cidadeIDFK;
    }
    public int getEnderecoID() {
        return enderecoID;
    }

    public void setEnderecoID(int enderecoID) {
        this.enderecoID = enderecoID;
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
    public int getCidadeIDFK(){
        return cidadeIDFK;
    }

    public String toString() {
        return getDescricao() +" - " + getCidadeIDFK();
    }
}
