package com.example.controleenderecos.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.controleenderecos.R;
import com.example.controleenderecos.database.LocalDatabase;
import com.example.controleenderecos.databinding.ActivityEnderecosBinding;
import com.example.controleenderecos.entity.Cidade;
import com.example.controleenderecos.entity.Endereco;

import java.util.List;

public class Enderecos extends AppCompatActivity {
    private ActivityEnderecosBinding binding;
    private LocalDatabase db;
    private List<Cidade> listaCidades;
    private List<Endereco> listaEnd;
    private Cidade cidadeSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = LocalDatabase.getDatabase(getApplicationContext());

        binding = ActivityEnderecosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnVoltarEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.btnCadEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarEnd(v);
            }
        });
        binding.btnCidadeEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Enderecos.this, Cidades.class);
                startActivity(it);
            }
        });
    }
    protected void onResume(){
        super.onResume();
        popularSpinner();
    }

    public void popularSpinner(){
        listaCidades = db.cidades().getAll();
        ArrayAdapter<Cidade> adapter = new ArrayAdapter<>(this, R.layout.spinner_editado,
                listaCidades);
        binding.spinnerCidade.setAdapter(adapter);

        binding.spinnerCidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cidadeSelecionada = listaCidades.get(position);
                Toast.makeText(Enderecos.this, "Selecionado: " + cidadeSelecionada, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void cadastrarEnd(View view){
        String descricao = binding.editTextText.getText().toString();
        String latitudestr = binding.edtLat.getText().toString();
        String longitudestr = binding.edtLong.getText().toString();

        if (descricao.isEmpty() || latitudestr.isEmpty() || longitudestr.isEmpty() || cidadeSelecionada == null) {
            Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }else{
            Double latitude = Double.parseDouble(latitudestr);
            Double longitude = Double.parseDouble(longitudestr);
            Endereco novoEnd = new Endereco(descricao, latitude, longitude, cidadeSelecionada.cidadeID);

            //String nomeCidade = db.cidades().getNomePorID(cidadeSelecionada.getCidadeID());

            if(cidadeSelecionada != null){
                db.enderecos().insert(novoEnd);
                Toast.makeText(this, "Endereço cadastrado com sucesso.",
                        Toast.LENGTH_SHORT).show();
            }
            finish();
        }
    }

}