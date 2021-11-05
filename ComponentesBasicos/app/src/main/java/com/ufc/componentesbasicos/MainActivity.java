package com.ufc.componentesbasicos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    public static int RESULT_ADD = 1;
    public static int RESULT_CANCEL = 2;

    Spinner spnr, spnr2;

    RadioButton result;

    RadioGroup radioG;

    int itemRGselecionado;

    String aposta;

    MediaPlayer som;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        som = MediaPlayer.create(this, R.raw.vinheta_champions_league);
        som.start();

        spnr = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, times);

        spnr.setAdapter(adapter);
        spnr.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {

                        int position = spnr.getSelectedItemPosition();
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }

                }
        );

        spnr2 = (Spinner)findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, times);

        spnr2.setAdapter(adapter2);
        spnr2.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {

                        int position = spnr2.getSelectedItemPosition();
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }

                }
        );

    }

    public void playSom (View view){
        if(som.isPlaying()){
            som.pause();
        }
        else{
            som.start();
        }
    }

    public void jogar(View view){

        radioG = (RadioGroup)findViewById(R.id.radioGroup);

        itemRGselecionado = radioG.getCheckedRadioButtonId();
        result = findViewById(itemRGselecionado);
        aposta = result.getText().toString();

        Intent intent = new Intent(this, ApostaActivity.class);
        intent.putExtra("time1", times[spnr.getSelectedItemPosition()]);
        intent.putExtra("time2", times[spnr2.getSelectedItemPosition()]);
        intent.putExtra("resultado", aposta);
        som.stop();
        startActivity(intent);
    }
}
