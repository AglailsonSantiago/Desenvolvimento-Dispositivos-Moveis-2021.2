package com.ufc.naventretelas2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.ufc.naventretelas2.constants.Constants;
import com.ufc.naventretelas2.model.Carro;
import com.ufc.naventretelas2.services.DataBase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Carro> listaCarros = new ArrayList<Carro>();
    ArrayAdapter adapter;
    //ExpandableListAdapter adapter;
    ListView listViewCarros;
    ExpandableListView expandableListView;
    int selected = -1;
    DataBase db = new DataBase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listar();

    }

    public void listar(){
        listaCarros = db.listaCarros();

        adapter = new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1, listaCarros);

        listViewCarros = (ListView) findViewById(R.id.listViewCarros);
        listViewCarros.setAdapter(adapter);
        listViewCarros.setSelector(android.R.color.holo_purple);

        listViewCarros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "" + listaCarros.get(position).toString(), Toast.LENGTH_SHORT).show();
                selected = position;
            }
        });
        adapter.notifyDataSetChanged();
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
        if( listaCarros.size() > 0 && selected >= 0) {
            Intent intent = new Intent(this, ActivityADDeEDIT.class);

            Carro carro = listaCarros.get(selected);

            intent.putExtra("id", carro.getId());
            intent.putExtra("nome", carro.getNome());
            intent.putExtra("marca", carro.getMarca());
            intent.putExtra("placa", carro.getPlaca());
            intent.putExtra("ano", carro.getAno());

            startActivityForResult(intent, Constants.REQUEST_EDIT);
        }
        else {
            selected = -1;
            Toast.makeText(this, "Selecione um item!", Toast.LENGTH_SHORT).show();
        }

    }

    public void apagarItem(View view){
        if( listaCarros.size() > 0){
            listaCarros.remove(selected);
            adapter.notifyDataSetChanged();
        }
        else {
            selected = -1;
            Toast.makeText(this, "Selecione um item!", Toast.LENGTH_SHORT).show();
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