package com.example.controleenderecos.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.controleenderecos.database.LocalDatabase;
import com.example.controleenderecos.databinding.ActivityRegistroUserViewBinding;
import com.example.controleenderecos.entity.Usuario;

public class RegistroUserView extends AppCompatActivity {
    private ActivityRegistroUserViewBinding binding;
    private LocalDatabase db;
    private int dbUsuarioID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegistroUserViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = LocalDatabase.getDatabase(getApplicationContext());
        dbUsuarioID = getIntent().getIntExtra("Usuario_Selecionado_ID", -1);

        preencherCampos(dbUsuarioID);

        binding.btnCancela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btnModifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModificarUsuarios(v);
            }
        });

        binding.btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluirUsuario(v);
            }
        });
    }

        public void preencherCampos(int ID){
            Usuario user = db.usuarios().getUsuariosPorID(ID);
            binding.edtNovoNome.setText(user.getNome());
            binding.edtNovoEmail.setText(user.getEmail());
            binding.edtNovaSenha.setText(user.getSenha());
        }

        public void ModificarUsuarios(View view) {
            String email = binding.edtNovoEmail.getText().toString();
            String senha = binding.edtNovaSenha.getText().toString();
            String nome = binding.edtNovoNome.getText().toString();
            if (email.equals("")) {
                Toast.makeText(this, "Preencha o email do usuário",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (senha.equals("")) {
                Toast.makeText(this, "Preencha o campo de senha",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if(nome.equals("")){
                Toast.makeText(this, "Escreva o nome do Usuário",
                        Toast.LENGTH_SHORT).show();
            } else {
                Usuario novoUsuario = new Usuario(nome, email, senha);
                novoUsuario.setUsuarioID(dbUsuarioID);
                db.usuarios().update(novoUsuario);
                Toast.makeText(this, "Usuário atualizado com sucesso.",
                Toast.LENGTH_SHORT).show();
            }
            finish();
        }

    public void excluirUsuario(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Exclusão")
                .setMessage("Deseja excluir esse Usuário?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        excluir();
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }
    public void excluir() {
        Usuario userX = new Usuario("", "", "");
        userX.setUsuarioID(dbUsuarioID);
        db.usuarios().delete(userX);
        Toast.makeText(this, "Usuário excluído com sucesso.", Toast.LENGTH_SHORT).show();
        finish();
    }

}