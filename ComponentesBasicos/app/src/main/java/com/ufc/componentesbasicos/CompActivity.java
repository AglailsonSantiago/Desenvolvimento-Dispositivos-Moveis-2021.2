package com.ufc.componentesbasicos;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CompActivity extends AppCompatActivity {

    public String [] times = {
            "",
            "América",
            "Athletico PR",
            "Atlético GO",
            "Atlético MG",
            "Bahia",
            "Ceará",
            "Chapecoense",
            "Corinthians",
            "Cuiabá",
            "Flamengo",
            "Fluminense",
            "Fortaleza",
            "Grêmio",
            "Internacional",
            "Juventude",
            "Palmeiras",
            "RB Bragantino",
            "Santos",
            "São Paulo",
            "Sport"
    };

    @Override
    public  void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_componentes);

        AutoCompleteTextView t1 = findViewById(R.id.autoCompleteTextView);

        ArrayAdapter<String> adp = new ArrayAdapter <String> ( this , android.R.layout.simple_dropdown_item_1line, times);

        t1.setThreshold ( 1 );
        t1.setAdapter (adp);

        TextView txtView = (TextView) findViewById(R.id.textViewPressionar);
        txtView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(),
                        "Sucesso!", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        txtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "Falha!", Toast.LENGTH_LONG).show();
            }
        });
    }

}
