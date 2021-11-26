package com.ufc.navegacaoentretelas;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.ufc.navegacaoentretelas.model.Carro;

import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    Context context;
    List<Carro> listaCarros;

    public ExpandableListAdapter(Context context, List<Carro> listaCarros) {
        this.context = context;
        this.listaCarros = listaCarros;
    }

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Carro carro = (Carro) getGroup(groupPosition);
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group, null);
        }

        TextView nomeCarro = convertView.findViewById(R.id.listHeader);
        nomeCarro.setTypeface(null, Typeface.BOLD);
        nomeCarro.setText(carro.getNome());

        return convertView;
    }

    public Object getGroup(int groupPosition) {
        return listaCarros.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listaCarros.get(groupPosition);
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        Carro carro = (Carro) getChild(groupPosition, childPosition);

        if(convertView == null){
            LayoutInflater inflaInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflaInflater.inflate(R.layout.row_layout,null);
        }

        TextView marca = convertView.findViewById(R.id.marca);
        TextView placa = convertView.findViewById(R.id.placa);
        TextView ano = convertView.findViewById(R.id.ano);

        marca.setText(carro.getMarca());
        placa.setText(carro.getPlaca());
        ano.setText(carro.getAno());

        return convertView;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return listaCarros.get( groupPosition ).getId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return listaCarros.get(groupPosition).getId();
    }

    @Override
    public int getGroupCount() {
        return listaCarros.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
