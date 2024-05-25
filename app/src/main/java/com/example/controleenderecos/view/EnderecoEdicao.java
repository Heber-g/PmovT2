package com.example.controleenderecos.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
    private int dbEnderecoID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = LocalDatabase.getDatabase(getApplicationContext());

        binding = ActivityEnderecoEdicaoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbEnderecoID = getIntent().getIntExtra("Endereco_Selecionado_ID", -1);
        preencher(dbEnderecoID);

        binding.btnVoltarEdtEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.btnSalvarEdicao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificarEndereco(dbEnderecoID);
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

    public void preencher(int id) {
        Endereco endereco = db.enderecos().getEndID(id);
        if (endereco != null) {
            binding.editTextText2.setText(endereco.getDescricao());
            binding.edtModLatitude.setText(String.valueOf(endereco.getLatitude()));
            binding.edtModLongitude.setText(String.valueOf(endereco.getLongitude()));
        } else{
            Log.e("EnderecoEdicao", "Endereço não encontrado para o ID: " + id);
        }
    }

    public void modificarEndereco(int id){
        String desc = binding.editTextText2.getText().toString();
        String latstr = binding.edtModLatitude.getText().toString();
        String longistr = binding.edtModLongitude.getText().toString();

        if(desc.isEmpty() || latstr.isEmpty() || longistr.isEmpty()){
            Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }else{
            Double lat = Double.parseDouble(latstr);
            Double longi = Double.parseDouble(longistr);
            Endereco novoEnd = db.enderecos().getEndID(id);
            novoEnd.setDescricao(desc);
            novoEnd.setLatitude(lat);
            novoEnd.setLongitude(longi);
            db.enderecos().update(novoEnd);
            Toast.makeText(this, "Endereço atualizado com sucesso.",
                    Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    public void excluirEnd(){

    }
}