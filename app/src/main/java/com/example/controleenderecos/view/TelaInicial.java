package com.example.controleenderecos.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.controleenderecos.R;
import com.example.controleenderecos.database.LocalDatabase;
import com.example.controleenderecos.databinding.ActivityTelaInicialBinding;
import com.example.controleenderecos.entity.Cidade;
import com.example.controleenderecos.entity.Endereco;

import java.util.List;

public class TelaInicial extends AppCompatActivity {
    private ActivityTelaInicialBinding binding;
    private LocalDatabase db;
    private Intent edtIntent;
    private ListView listViewEnderecos;
    private List<Endereco> endList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTelaInicialBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        listViewEnderecos = binding.listViewEnd;
        db = LocalDatabase.getDatabase(getApplicationContext());
        binding.btnCadCidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(TelaInicial.this, Cidades.class);
                startActivity(it);
            }
        });

        binding.btnCadEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(TelaInicial.this, Enderecos.class);
                startActivity(it);
            }
        });
    }
    protected void onResume(){
        super.onResume();
        preencherLista();
    }
    public void preencherLista(){
        endList = db.enderecos().getAll();

        if (endList != null && !endList.isEmpty()) {
            binding.txtApresentaEnd.setVisibility(View.VISIBLE);
        } else {
            binding.txtApresentaEnd.setText("Não há endereços cadastrados.");
        }
        ArrayAdapter<Endereco> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                endList);
        listViewEnderecos.setAdapter(adapter);

        listViewEnderecos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Endereco end1 = endList.get(position);
                edtIntent.putExtra("Endereco_Selecionado_ID", end1.getEnderecoID());
                startActivity(edtIntent);

            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Tem certeza de que deseja sair desta tela? Você será deslogado!")
                .setCancelable(false)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        TelaInicial.super.onBackPressed();
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }

}