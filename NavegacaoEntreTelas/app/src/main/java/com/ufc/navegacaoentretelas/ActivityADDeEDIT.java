package com.ufc.navegacaoentretelas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class ActivityADDeEDIT extends AppCompatActivity {

    EditText editNome;
    EditText editMarca;
    EditText editPlaca;
    EditText editAno;


    boolean edit;
    int idCarroEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        editNome = findViewById(R.id.nome);
        editMarca = findViewById(R.id.marca);
        editPlaca = findViewById(R.id.placa);
        editAno = findViewById(R.id.ano);

        edit = false;

        if(getIntent().getExtras() != null){
            String nome = (String) getIntent().getExtras().get("nome");
            String marca = (String) getIntent().getExtras().get("marca");
            String placa = (String) getIntent().getExtras().get("placa");
            String ano = (String) getIntent().getExtras().get("ano");
            idCarroEditar = (int) getIntent().getExtras().get("id");

            editNome.setText(nome);
            editMarca.setText(marca);
            editPlaca.setText(placa);
            editAno.setText(ano);

            edit = true;
        }

    }

    public void cancelar( View view ){
        setResult( Constants.RESULT_CANCEL );
        finish();
    }

    public void salvar( View view ){

        Intent intent = new Intent();

        String nome = editNome.getText().toString();
        String marca = editMarca.getText().toString();
        String placa = editPlaca.getText().toString();
        String ano = editAno.getText().toString();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> carros = new HashMap<>();

        carros.put("nome", nome);
        carros.put("marca", marca);
        carros.put( "placa", placa );
        carros.put( "ano", ano );

        DocumentReference documentReference = db.collection("carros").document();
        documentReference.set(carros).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("db", "Sucesso ao salvar dados!");
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("db", "Erro ao salvar dados!" + e.toString());
            }
        });

        intent.putExtra( "nome", nome );
        intent.putExtra( "marca", marca );
        intent.putExtra( "placa", placa );
        intent.putExtra( "ano", ano );

        if( edit ) intent.putExtra( "id", idCarroEditar );

        setResult( Constants.RESULT_ADD, intent );
        finish();
    }

}
