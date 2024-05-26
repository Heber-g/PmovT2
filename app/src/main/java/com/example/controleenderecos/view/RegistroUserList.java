package com.example.controleenderecos.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.controleenderecos.R;
import com.example.controleenderecos.database.LocalDatabase;
import com.example.controleenderecos.databinding.ActivityRegistroUserListBinding;
import com.example.controleenderecos.entity.Usuario;

import java.util.List;

public class RegistroUserList extends AppCompatActivity {
    private ActivityRegistroUserListBinding binding;
    private LocalDatabase db;
    private ListView listViewUser;
    private List<Usuario> usuarioList;
    private Intent edtIntent;
    private Usuario dbUser;
    private List listaUsuarios;
    private int dbUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegistroUserListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = LocalDatabase.getDatabase(getApplicationContext());
        listViewUser = binding.listViewUsuarios;

        binding.btnVoltarInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarUsuarios(v);
            }
        });
    }
    @Override
    protected void onResume(){
        super.onResume();
        edtIntent = new Intent(this, RegistroUserView.class);
        preencherUsuarios();
    }
    private void preencherUsuarios(){
        usuarioList = db.usuarios().getAll();

        if (usuarioList != null && !usuarioList.isEmpty()) {
            binding.txtModificar.setVisibility(View.VISIBLE);
        } else {
            binding.txtModificar.setText("");
        }
        ArrayAdapter<Usuario> adapter = new ArrayAdapter<>(this, R.layout.layout_modelo,
                usuarioList);
        listViewUser.setAdapter(adapter);

        listViewUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Usuario usuarioSelecionado = usuarioList.get(position);
                edtIntent.putExtra("Usuario_Selecionado_ID", usuarioSelecionado.getUsuarioID());
                startActivity(edtIntent);
            }
        });
    }
    public void salvarUsuarios(View view) {
        String email = binding.edtEmail.getText().toString();
        String senha = binding.edtSenha.getText().toString();
        String nome = binding.edtNome.getText().toString();
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
        }else {
            binding.txtModificar.setText("Modificar");
            Usuario novoUsuario = new Usuario(nome, email, senha);
            //novoUsuario.setUsuarioID(usuarioList.get(listViewUser.getSelectedItemPosition()).getUsuarioID());
            if (dbUser != null) {
                novoUsuario.setUsuarioID(dbUserID);
                db.usuarios().update(novoUsuario);
                Toast.makeText(this, "Usuário atualizado com sucesso.",
                        Toast.LENGTH_SHORT).show();
            } else {
                db.usuarios().insert(novoUsuario);
                Toast.makeText(this, "Usuário cadastrado com sucesso.",
                        Toast.LENGTH_SHORT).show();
            }
            preencherUsuarios();
        }
    }

}