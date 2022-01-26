package com.ufc.taskmanager_projetofinal.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ufc.taskmanager_projetofinal.R;
import com.ufc.taskmanager_projetofinal.model.User;

import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContatosAdapter extends RecyclerView.Adapter<ContatosAdapter.MyViewHolder> {

    private List<User> contatos;
    private Context context;

    public ContatosAdapter(List<User> listaContatos, Context c) {
        this.contatos = listaContatos;
        this.context = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_contatos, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        User user = contatos.get(position);

        holder.nome.setText(user.getNome());
        holder.email.setText(user.getEmail());

        if(user.getFoto() != null){
            Uri uri = Uri.parse(user.getFoto());
            Glide.with(context).load(uri).into(holder.foto);
        } else{
            holder.foto.setImageResource(R.drawable.padrao);
        }

    }

    @Override
    public int getItemCount() {
        return contatos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        CircleImageView foto;
        TextView nome, email;

        public MyViewHolder(View itemView){
            super(itemView);

            foto = itemView.findViewById(R.id.imageViewFotoContato);
            nome = itemView.findViewById(R.id.textNomeContato);
            email = itemView.findViewById(R.id.textEmailContato);

        }

    }

}
