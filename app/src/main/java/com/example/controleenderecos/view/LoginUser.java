package com.example.controleenderecos.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.controleenderecos.R;
import com.example.controleenderecos.database.LocalDatabase;
import com.example.controleenderecos.databinding.ActivityLoginUserBinding;
import com.example.controleenderecos.databinding.ActivityMainBinding;
import com.example.controleenderecos.entity.Usuario;

import java.util.List;

public class LoginUser extends AppCompatActivity {

    private ActivityLoginUserBinding binding;
    private LocalDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = LocalDatabase.getDatabase(this);//

        binding = ActivityLoginUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnCancelaLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.btnConfirmaLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizaLogin();
            }
        });
    }
    protected void onResume(){
        super.onResume();

    }

    private void realizaLogin(){
        String email = binding.edtEmailLogin.getText().toString();
        String senha = binding.edtSenhaLogin.getText().toString();

        if (email.isEmpty()) {
            Toast.makeText(this, "Preencha o email do usuário",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (senha.isEmpty()) {
            Toast.makeText(this, "Preencha o campo de senha",
                    Toast.LENGTH_SHORT).show();
            return;
        } else {
            Usuario user = db.usuarios().login(email, senha);
            if(user == null){
                Toast.makeText(this, "Usuário não cadastrado.",
                        Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(LoginUser.this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show();
                Intent it = new Intent(LoginUser.this, Cidades.class);
                startActivity(it);
            }
        }
    }
}