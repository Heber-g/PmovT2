package com.example.controleenderecos.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class Cidade {
    @PrimaryKey(autoGenerate = true) public int cidadeID;
    private String cidade;
    private String estado;

    public Cidade(String cidade, String estado){
        this.cidade = cidade;
        this.estado = estado;
    }

    public int getCidadeID() {
        return cidadeID;
    }//retirei o set, mas da para testar a necessidade de se possuir um set depois.

    public void setCidadeID(int cidadeID){
        this.cidadeID = cidadeID;
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

    public String toString() {
        return getCidade() +" - " + getEstado();
    }
}
