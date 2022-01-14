package com.ufc.naventretelas2.services;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ufc.naventretelas2.model.Carro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataBase {
    final FirebaseFirestore db = FirebaseFirestore.getInstance();


    public void addCarro(Carro car){
        Map<String, Object> carro = new HashMap<>();

        carro.put("nome", car.getNome());
        carro.put("marca", car.getMarca());
        carro.put("placa", car.getPlaca());
        carro.put("ano", car.getAno());

        db.collection("carros")
                .add(carro)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("DB", "Documento adicionado com ID:" + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("DB", "Erro: " + e);
                    }
                });

    }

    public ArrayList<Carro> listaCarros(){
        ArrayList<Carro> listaCarros = new ArrayList<>();

        db.collection("carros")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                Log.d("DB", "Carro: " + document.getId() + "=> " + document.getString("nome"));

                                listaCarros.add(new Carro(document.getString("nome"), document.getString("marca"),
                                        document.getString("placa"), document.getString("ano")));

                            }
                        } else{
                            Log.w("DB", "Erro: " + task.getException());
                        }
                    }
                });

        return listaCarros;
    }

}
