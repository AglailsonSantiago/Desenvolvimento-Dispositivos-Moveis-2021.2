package com.ufc.naventretelas2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.ufc.naventretelas2.constants.Constants;
import com.ufc.naventretelas2.model.Carro;
import com.ufc.naventretelas2.services.DataBase;

public class ActivityADDeEDIT extends AppCompatActivity {

    EditText editNome;
    EditText editMarca;
    EditText editPlaca;
    EditText editAno;

    boolean edit;
    String idCarroEditar;


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
            idCarroEditar = (String) getIntent().getExtras().get("id");

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

        DataBase db = new DataBase();
        Intent intent = new Intent();
        Carro carro = new Carro();

        String nome = editNome.getText().toString();
        String marca = editMarca.getText().toString();
        String placa = editPlaca.getText().toString();
        String ano = editAno.getText().toString();

        carro.setNome(nome);
        carro.setMarca(marca);
        carro.setPlaca(placa);
        carro.setAno(ano);

        intent.putExtra( "nome", nome );
        intent.putExtra( "marca", marca );
        intent.putExtra( "placa", placa );
        intent.putExtra( "ano", ano );

        if( edit == true ) {
            intent.putExtra("id", idCarroEditar);
            db.updateCarro(carro, idCarroEditar);
        }
        else {
            carro.setId(db.addCarro(carro));
        }

        setResult( Constants.RESULT_ADD, intent );

        finish();
    }

}
