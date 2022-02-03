package com.ufc.taskmanager_projetofinal.model;

import com.google.firebase.database.DatabaseReference;
import com.ufc.taskmanager_projetofinal.config.ConfiguracaoFirebase;

import java.util.List;

public class Tarefa {

    private String id;
    private String nome;
    private String foto;
    private List<Topico> topicos;
    private List<User> membros;

    public Tarefa() {

        DatabaseReference database = ConfiguracaoFirebase.getFirebaseDatabase();
        DatabaseReference tarefaRef = database.child("tarefas");

        String idTarefaFirebase = tarefaRef.push().getKey();
        setId(idTarefaFirebase);

    }

    public void salvar(){

        DatabaseReference database = ConfiguracaoFirebase.getFirebaseDatabase();
        DatabaseReference tarefaRef = database.child("tarefas");

        tarefaRef.child(getId()).setValue(this);

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public List<Topico> getTopicos() {
        return topicos;
    }

    public void setTopicos(List<Topico> topicos) {
        this.topicos = topicos;
    }

    public List<User> getMembros() {
        return membros;
    }

    public void setMembros(List<User> membros) {
        this.membros = membros;
    }
}
