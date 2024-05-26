package com.example.controleenderecos.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.controleenderecos.database.LocalDatabase;
import com.example.controleenderecos.databinding.ActivityCidadesEdicaoBinding;
import com.example.controleenderecos.entity.Cidade;

public class CidadesEdicao extends AppCompatActivity {
    private ActivityCidadesEdicaoBinding binding;
    private LocalDatabase db;
    private int dbCidadeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCidadesEdicaoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = LocalDatabase.getDatabase(getApplicationContext());
        dbCidadeID = getIntent().getIntExtra("Cidade_Selecionada_ID", -1);

        preencherCampos(dbCidadeID);

        binding.btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificarCidade(v);
            }
        });
        binding.btnExclui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluirCidade();
            }
        });
    }
    public void preencherCampos(int ID){
        Cidade cidade = db.cidades().getCidadeID(ID);
        binding.edtNovoNomeCidade.setText(cidade.getCidade());
        binding.edtNovoNomeEstado.setText(cidade.getEstado().toUpperCase());
    }
    public void modificarCidade(View view){
        String novoNome = binding.edtNovoNomeCidade.getText().toString();
        String novoEstado = binding.edtNovoNomeEstado.getText().toString().toUpperCase();

        if(novoNome.isEmpty()){
            Toast.makeText(this, "Preencha o nome da cidade", Toast.LENGTH_SHORT).show();
            return;
        }
        if(novoEstado.isEmpty()){
            Toast.makeText(this, "Preencha o estado da cidade", Toast.LENGTH_SHORT).show();
            return;
        }else{
            Cidade cidade = new Cidade(novoNome, novoEstado);
            cidade.setCidadeID(dbCidadeID);

            db.cidades().update(cidade);
            Toast.makeText(this, "Usuário atualizado", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    public void excluirCidade(){
        new AlertDialog.Builder(this)
                .setTitle("Exclusão")
                .setMessage("Deseja excluir essa cidade?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        excluir();
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }
    public void excluir(){
        Cidade cidadeExclusao = new Cidade("", "");
        cidadeExclusao.setCidadeID(dbCidadeID);
        db.cidades().delete(cidadeExclusao);
        Toast.makeText(this, "Cidade excluída com sucesso.", Toast.LENGTH_SHORT).show();
        finish();
    }
}