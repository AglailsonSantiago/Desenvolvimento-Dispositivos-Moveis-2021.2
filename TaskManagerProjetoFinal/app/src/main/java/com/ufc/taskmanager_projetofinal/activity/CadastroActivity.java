package com.ufc.taskmanager_projetofinal.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.ufc.taskmanager_projetofinal.R;
import com.ufc.taskmanager_projetofinal.config.ConfiguracaoFirebase;
import com.ufc.taskmanager_projetofinal.model.User;

public class CadastroActivity extends AppCompatActivity {

    private EditText nome, email, senha;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        nome = findViewById(R.id.editNome);
        email = findViewById(R.id.editEmail);
        senha = findViewById(R.id.editSenha);

    }

    public void cadastrarUsuario(User user){
        auth = ConfiguracaoFirebase.getAuth();
        auth.createUserWithEmailAndPassword(
                user.getEmail(), user.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                String excecao = "";

                if(task.isSuccessful()){
                    Toast.makeText(CadastroActivity.this,
                            "Sucesso ao cadastrar usuário!",
                            Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e){
                        excecao = "Digite uma senha mais forte!";
                    } catch (FirebaseAuthInvalidCredentialsException e){
                        excecao = "Por favor, digite um e-mail válido!";
                    } catch (FirebaseAuthUserCollisionException e){
                        excecao = "Esta conta já foi cadastrada!";
                    } catch (Exception e) {
                        excecao = "Erro ao cadastrar o usuário: " + e.getMessage();
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void validarCadastro(View view){

        String textoNome = nome.getText().toString();
        String textoEmail = email.getText().toString();
        String textoSenha = senha.getText().toString();

        if(!textoNome.isEmpty()){
            if(!textoEmail.isEmpty()){
                if(!textoSenha.isEmpty()){
                    User user = new User();
                    user.setNome(textoNome);
                    user.setEmail(textoEmail);
                    user.setSenha(textoSenha);

                    cadastrarUsuario(user);
                }
                else {
                    Toast.makeText(CadastroActivity.this,
                            "Preencha a senha!", Toast.LENGTH_SHORT).show();
                }

            }
            else{
                Toast.makeText(CadastroActivity.this,
                        "Preencha o e-mail!", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(CadastroActivity.this,
                    "Preencha o nome!", Toast.LENGTH_SHORT).show();
        }

    }
}