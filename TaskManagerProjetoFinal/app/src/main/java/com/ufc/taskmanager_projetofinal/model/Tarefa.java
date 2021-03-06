package com.ufc.taskmanager_projetofinal.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.ufc.taskmanager_projetofinal.config.ConfiguracaoFirebase;
import com.ufc.taskmanager_projetofinal.helper.Base64Custom;
import com.ufc.taskmanager_projetofinal.helper.UserFirebase;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tarefa implements Serializable {

    private String id;
    private String nome;
    private String foto;
    private String descricao;
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

        //salvar conversa para membros da tarefa
        for(User membro : getMembros()){

            String idRemetente = Base64Custom.codificarBase64(membro.getEmail());
            String idDestinatario = getId();

            Conversa conversa = new Conversa();
            conversa.setIdRemetente(idRemetente);
            conversa.setIdDestinatario(idDestinatario);
            conversa.setUltimamensagem("");
            conversa.setIsTarefa("true");
            conversa.setTarefa(this);

            conversa.salvar();
        }

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<User> getMembros() {
        return membros;
    }

    public void setMembros(List<User> membros) {
        this.membros = membros;
    }
}
