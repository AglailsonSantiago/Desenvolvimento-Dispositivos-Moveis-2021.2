package com.ufc.taskmanager_projetofinal.model;

import com.google.firebase.database.DatabaseReference;
import com.ufc.taskmanager_projetofinal.config.ConfiguracaoFirebase;

public class Conversa {

    private String idRemetente;
    private String idDestinatario;
    private String ultimamensagem;
    private User userExibicao;

    public Conversa() {
    }

    public void salvar(){
        DatabaseReference databaseReference = ConfiguracaoFirebase.getFirebaseDatabase();
        DatabaseReference conversaRef = databaseReference.child("conversas");

        conversaRef.child(this.getIdRemetente())
                .child(this.getIdDestinatario())
                .setValue(this);

    }

    public String getIdRemetente() {
        return idRemetente;
    }

    public void setIdRemetente(String idRemetente) {
        this.idRemetente = idRemetente;
    }

    public String getIdDestinatario() {
        return idDestinatario;
    }

    public void setIdDestinatario(String idDestinatario) {
        this.idDestinatario = idDestinatario;
    }

    public String getUltimamensagem() {
        return ultimamensagem;
    }

    public void setUltimamensagem(String ultimamensagem) {
        this.ultimamensagem = ultimamensagem;
    }

    public User getUserExibicao() {
        return userExibicao;
    }

    public void setUserExibicao(User userExibicao) {
        this.userExibicao = userExibicao;
    }
}
