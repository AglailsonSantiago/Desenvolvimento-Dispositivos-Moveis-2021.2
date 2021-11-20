package com.ufc.navegacaoentretelas;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.ufc.navegacaoentretelas.model.Carro;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.SyncStateContract;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Carro> listaCarros;
    ArrayAdapter adapter;
    ListView listViewCarros;
    int selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selected = -1;

        listaCarros = new ArrayList<Carro>();

        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, listaCarros);

        listViewCarros = findViewById(R.id.listCarros);
        listViewCarros.setAdapter(adapter);
        listViewCarros.setSelector(android.R.color.holo_blue_light);

        listViewCarros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "" + listaCarros.get(position).toString(), Toast.LENGTH_SHORT).show();
                selected = position;
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    public void adicionar(View view){
        Intent intent = new Intent(this, ActivityADDeEDIT.class);
        startActivityForResult(intent, Constants.REQUEST_ADD);
    }

    public void editar(View view){
        Intent intent = new Intent(this, ActivityADDeEDIT.class);

        Carro carro = listaCarros.get(selected);

        intent.putExtra("id", carro.getId());
        intent.putExtra("nome", carro.getNome());
        intent.putExtra("marca", carro.getMarca());
        intent.putExtra("placa", carro.getPlaca());
        intent.putExtra("ano", carro.getAno());

        startActivityForResult(intent, Constants.REQUEST_EDIT);

    }

    public void apagarItem(View view){
        if( listaCarros.size() > 0){
            listaCarros.remove(selected);
            adapter.notifyDataSetChanged();
        }
        else {
            selected = -1;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Constants.REQUEST_ADD && resultCode == Constants.RESULT_ADD){

            String nome = (String) data.getExtras().get("nome");
            String marca = (String) data.getExtras().get("marca");
            String placa = (String) data.getExtras().get("placa");
            String ano = (String) data.getExtras().get("ano");

            Carro carro = new Carro(nome, marca, placa, ano);
            Log.d("Main", carro.getNome());
            listaCarros.add(carro);
            adapter.notifyDataSetChanged();
        }
        else if(requestCode == Constants.REQUEST_EDIT && resultCode == Constants.RESULT_ADD){

            String nome = (String) data.getExtras().get("nome");
            String marca = (String) data.getExtras().get("marca");
            String placa = (String) data.getExtras().get("placa");
            String ano = (String) data.getExtras().get("ano");
            int idEditar = (int) data.getExtras().get("id");

            for(Carro carro : listaCarros){
                if(carro.getId() == idEditar){
                    carro.setNome(nome);
                    carro.setMarca(marca);
                    carro.setPlaca(placa);
                    carro.setAno(ano);
                }
            }
            adapter.notifyDataSetChanged();
        }
        else if(resultCode == Constants.RESULT_CANCEL){
            Toast.makeText(this, "Cancelado!", Toast.LENGTH_SHORT).show();
            adapter.notifyDataSetChanged();
        }

    }

}
