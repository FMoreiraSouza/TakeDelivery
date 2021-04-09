package com.example.takedelivery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.takedelivery.R;
import com.example.takedelivery.model.Cliente;
import com.example.takedelivery.model.Pedido;
import com.example.takedelivery.model.Produto;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdapterListViewPedidos extends BaseAdapter {

    private final List<Pedido> pedidos;
    private final List<Cliente> clientes;
    private final List<Produto> produtos;

    private final Context c;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance ();
    private DatabaseReference mDatabaseReference = mDatabase.getReference ();
    Cliente cliente = null;
    AdapterListViewProdutosPedido adapter;
    ListView listViewProdutos;
    ArrayList<Produto> produtosLista = new ArrayList<>();
    int position;

    public AdapterListViewPedidos(List<Pedido> pedidos, List<Cliente> clientes, List<Produto> produtos, Context context) {
        this.pedidos = pedidos;
        this.clientes = clientes;
        this.produtos = produtos;
        this.c = context;
    }

    @Override
    public int getCount() {
        return pedidos.size();
    }

    @Override
    public Pedido getItem(int position) {
        return pedidos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override

    public View getView(int position, View convertView, ViewGroup parent) {
        this.position = position;
        Pedido pedido = pedidos.get(position);

        if(clientes != null) {
            for (Cliente cli : clientes) {
                if (String.valueOf(cli.getId()).equals(pedido.getIdCliente())) {
                    cliente = cli;
                }
            }
        }
        if(produtos != null) {
            for (Produto pro : produtos) {
                for(String idPro: pedido.getProdutos())
                if (String.valueOf(pro.getId()).equals(idPro)) {
                    produtosLista.add(pro);
                }
            }
        }
            convertView = LayoutInflater.from(c).inflate(R.layout.layout_list_pedidos, parent, false);

        TextView idPedido = (TextView) convertView.findViewById(R.id.textViewIdPedido);
//        TextView statusPedido = (TextView) convertView.findViewById(R.id.textViewStatusPedido);
        TextView data = (TextView) convertView.findViewById(R.id.textViewData);
        TextView nomeCliente = (TextView) convertView.findViewById(R.id.textViewNomeCliente);
        TextView endereco = (TextView) convertView.findViewById(R.id.textViewEndereco);
        TextView bairro = (TextView) convertView.findViewById(R.id.textViewBairro);
        TextView valor = (TextView) convertView.findViewById(R.id.textViewValorPed);

        TextView pagamento = (TextView) convertView.findViewById(R.id.textViewPagamento);
        Button status = (Button) convertView.findViewById(R.id.buttonsStatus);
        if(cliente != null) {
            idPedido.setText( String.valueOf(pedido.getId()));
            data.setText(pedido.getData());
            if(pedido.getStatus().equals("Pendente aprovação")) {
                status.setText("Aprovar");
            }else if(pedido.getStatus().equals("Preparando Pedido")){
                status.setText("Liberar para entrega");
            }else{
                ((ViewGroup)status.getParent()).removeView(status);
            }

            if(produtos != null) {
                adapter = new AdapterListViewProdutosPedido(produtosLista, c);

                listViewProdutos = (ListView) convertView.findViewById(R.id.listViewProdutosPedido);

                listViewProdutos.setAdapter(adapter);
            }
            nomeCliente.setText(cliente.getNome());
            endereco.setText(cliente.getEndereco());
            bairro.setText(cliente.getBairro());
            Locale ptBr = new Locale("pt", "BR");
            valor.setText( NumberFormat.getCurrencyInstance(ptBr).format(pedido.getValorTotal()));
            pagamento.setText(pedido.getMetodoDePagamento());
        }
        return convertView;

    }
    public void botaoMudaStatus(){
        Pedido pedido = getItem(position);
        if(pedido.getStatus().equals("Pendente aprovação")) pedido.setStatus("Finalizado");
        mDatabaseReference = mDatabase.getReference ().child ("empresas").child("0").child("pedidos").child("0");
        mDatabaseReference.setValue (pedido);
    }
}