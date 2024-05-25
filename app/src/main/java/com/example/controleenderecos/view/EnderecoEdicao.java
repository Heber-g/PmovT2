package com.example.controleenderecos.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.controleenderecos.R;
import com.example.controleenderecos.database.LocalDatabase;
import com.example.controleenderecos.databinding.ActivityEnderecoEdicaoBinding;
import com.example.controleenderecos.entity.Endereco;
import com.example.controleenderecos.entity.Usuario;

public class EnderecoEdicao extends AppCompatActivity {
    private ActivityEnderecoEdicaoBinding binding;
    private LocalDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = LocalDatabase.getDatabase(getApplicationContext());

        binding = ActivityEnderecoEdicaoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnVoltarEdtEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.btnSalvarEdicao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificarEndereco();
            }
        });
        binding.btnExcluirEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluirEnd();
            }
        });
        binding.btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(EnderecoEdicao.this, Enderecos.class);
                startActivity(it);
            }
        });
    }

    public void modificarEndereco(){

    }

    public void excluirEnd(){

    }
}