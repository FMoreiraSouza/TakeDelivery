package com.example.takedelivery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.takedelivery.R;
import com.example.takedelivery.model.Empresa;
import com.example.takedelivery.model.Pedido;

import java.util.List;

public class AdapterListViewEmpresas extends BaseAdapter {

    private final List<Empresa> empresas;
    private final Context c;

    public AdapterListViewEmpresas(List<Empresa> empresas, Context context) {
        this.empresas = empresas;
        this.c = context;
        ;
    }

    @Override
    public int getCount() {
        return empresas.size();
    }

    @Override
    public Empresa getItem(int position) {
        return empresas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override

    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(c).inflate(R.layout.layout_list_empresa, parent, false);
        Empresa empresa = empresas.get(position);

        TextView nome = (TextView) convertView.findViewById( R.id.textViewNomeRestaurante);
        nome.setText(empresa.getNomeFantasia());

        return convertView;

    }
}
