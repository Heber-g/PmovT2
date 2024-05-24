package com.example.controleenderecos.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<Integer, String> cidadeMap = obterMapaCidades(); // Método para obter o mapa de IDs de cidade para nomes de cidade

        if (endList != null && !endList.isEmpty()) {
            binding.txtApresentaEnd.setVisibility(View.VISIBLE);
        } else {
            binding.txtApresentaEnd.setText("Não há endereços cadastrados.");
        }

        ArrayAdapter<Endereco> adapter = new ArrayAdapter<Endereco>(this, android.R.layout.simple_list_item_1, endList) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
                }

                TextView textView = convertView.findViewById(android.R.id.text1);
                Endereco endereco = getItem(position);
                if (endereco != null) {
                    String cidadeNome = cidadeMap.get(endereco.getCidadeIDFK());
                    String enderecoCompleto = endereco.getDescricao() + " - " + cidadeNome;
                    textView.setText(enderecoCompleto);
                }

                return convertView;
            }
        };

        listViewEnderecos.setAdapter(adapter);
    }

    private Map<Integer, String> obterMapaCidades() {
        List<Cidade> cidades = db.cidades().getAll();
        Map<Integer, String> cidadeMap = new HashMap<>();
        for (Cidade cidade : cidades) {
            cidadeMap.put(cidade.getCidadeID(), cidade.getCidade());
        }
        return cidadeMap;
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