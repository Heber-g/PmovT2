package com.example.controleenderecos.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.controleenderecos.R;
import com.example.controleenderecos.database.LocalDatabase;
import com.example.controleenderecos.databinding.ActivityCidadesBinding;
import com.example.controleenderecos.entity.Cidade;
import com.example.controleenderecos.entity.Usuario;

import java.util.List;

public class Cidades extends AppCompatActivity {
    private ActivityCidadesBinding binding;
    private LocalDatabase db;

    private List<Cidade> cidadesList;

    private ListView listViewCidade;
    private Intent edtIntent;

    private Cidade dbCidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCidadesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = LocalDatabase.getDatabase(getApplicationContext());
        listViewCidade = binding.listViewCidades;

        binding.btnCadastroCidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarCidades(v);
            }
        });
    }
    @Override
    protected void onResume(){
        super.onResume();
        edtIntent = new Intent(this, CidadesEdicao.class);
        preencherCidades();
    }

    private void preencherCidades(){
        cidadesList = db.cidades().getAll();

        if (cidadesList != null && !cidadesList.isEmpty()) {
            binding.txtCidades.setVisibility(View.VISIBLE);
        } else {
            binding.txtCidades.setText("");
        }
        ArrayAdapter<Cidade> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                cidadesList);
        listViewCidade.setAdapter(adapter);

        listViewCidade.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cidade cidadeSelected = cidadesList.get(position);
                edtIntent.putExtra("Cidade_Selecionada_ID", cidadeSelected.getCidadeID());
                startActivity(edtIntent);
            }
        });
    }

    public void salvarCidades(View v){
        String nome = binding.edtNomeCidade.getText().toString();
        String endereco = binding.edtEstadoCidade.getText().toString();

        if(nome.isEmpty()){
            Toast.makeText(this, "Preencha o nome da cidade",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if(endereco.isEmpty()){
            Toast.makeText(this, "Preencha o nome do estado",
                    Toast.LENGTH_SHORT).show();
            return;
        }else{
            binding.txtCidades.setText("Cidades");
            Cidade cidade = new Cidade(nome, endereco);
            if(dbCidade != null){
                //cidade.setCidadeID();
                db.cidades().update(cidade);
                Toast.makeText(this, "Cidade atualizada com sucesso.",
                        Toast.LENGTH_SHORT).show();
            }else {
                db.cidades().insert(cidade);
                Toast.makeText(this, "Cidade inserida com sucesso.",
                        Toast.LENGTH_SHORT).show();
                binding.edtNomeCidade.setText("");
                binding.edtEstadoCidade.setText("");
            }
            preencherCidades();
        }
    }
}