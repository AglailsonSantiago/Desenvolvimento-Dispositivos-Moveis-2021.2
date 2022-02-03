package com.ufc.taskmanager_projetofinal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ufc.taskmanager_projetofinal.R;
import com.ufc.taskmanager_projetofinal.model.Topico;

import java.util.List;

public class TopicoAdapter extends RecyclerView.Adapter<TopicoAdapter.MyViewHolder>{

    private List<Topico> topicos;
    private Context context;

    public TopicoAdapter(List<Topico> listaTopicos, Context c) {
        this.topicos = listaTopicos;
        this.context = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_topico, parent, false);

        return new TopicoAdapter.MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Topico topico = topicos.get(position);
        holder.titulo.setText(topico.getTitulo());

    }

    @Override
    public int getItemCount() {
        return topicos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titulo;

        public MyViewHolder(View itemView){
            super(itemView);

            titulo = itemView.findViewById(R.id.textTopico);

        }

    }

}
