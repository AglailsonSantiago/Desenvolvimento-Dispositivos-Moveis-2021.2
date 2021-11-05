package com.ufc.componentesbasicos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class ApostaActivity extends AppCompatActivity {
    String teste = "teste";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aposta);

        Button maisComponentes = (Button) findViewById(R.id.mais);

        String time1 = (String) getIntent().getExtras().get("time1");
        String time2 = (String) getIntent().getExtras().get("time2");
        String result = (String) getIntent().getExtras().get("resultado");

        TextView textTime1 = findViewById(R.id.textViewTime1);
        TextView textTime2 = findViewById(R.id.textViewTime2);
        TextView textAposta = findViewById(R.id.textViewMinhaAposta);
        TextView gols1 = findViewById(R.id.gols1);
        TextView gols2 = findViewById(R.id.gols2);

        Log.d("ApostaActivity", "Time 1 vence");

        int gol1 = new Random().nextInt(6);
        int gol2 = new Random().nextInt(6);

        textTime1.setText(time1);
        textTime2.setText(time2);
        textAposta.setText(result);
        gols1.setText(""+ gol1);
        gols2.setText(""+ gol2);

        TextView resultadoFinal = findViewById(R.id.textViewResultado);

        if((gol1 > gol2 && result.equals("Time 1 vence")) || (gol1 < gol2 && result.equals("Time 2 vence")) || (gol1 == gol2 && result.equals("Empate"))){

            resultadoFinal.setText("Você venceu!");

        }
        else resultadoFinal.setText("Você perdeu!");
    }

    public void voltar(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_overflow, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_1:
                Toast.makeText(ApostaActivity.this, "Botão 1", Toast.LENGTH_LONG).show();
                break;
            case R.id.menu_2:
                Toast.makeText(ApostaActivity.this, "Botão 2", Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void mais(View view){
        Intent intent = new Intent(this, CompActivity.class);
        intent.putExtra("teste", teste);
        startActivity(intent);
    }

}
