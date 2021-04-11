package com.example.takedelivery.adapter;

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


    private final Context c;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance ();
    private DatabaseReference mDatabaseReference = mDatabase.getReference ();
    Cliente cliente = null;
    AdapterListViewProdutosPedido adapter;
    ListView listViewProdutos;
    ArrayList<Produto> produtosLista = new ArrayList<>();
    int position;

    public AdapterListViewPedidos(List<Pedido> pedidos, Context context) {
        this.pedidos = pedidos;
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

        convertView = LayoutInflater.from(c).inflate(R.layout.layout_list_pedidos, parent, false);

        TextView idPedido = (TextView) convertView.findViewById(R.id.textViewIdPedido);
        TextView data = (TextView) convertView.findViewById(R.id.textViewData);
        TextView nomeCliente = (TextView) convertView.findViewById(R.id.textViewNomeCliente);
        TextView endereco = (TextView) convertView.findViewById(R.id.textViewEndereco);
        TextView bairro = (TextView) convertView.findViewById(R.id.textViewBairro);
        TextView valor = (TextView) convertView.findViewById(R.id.textViewValorPed);
        TextView qtd = (TextView) convertView.findViewById(R.id.textViewqtdPro);
        TextView produto = (TextView) convertView.findViewById(R.id.textViewPro);
        TextView preco = (TextView) convertView.findViewById(R.id.textViewPreco);


        TextView pagamento = (TextView) convertView.findViewById(R.id.textViewPagamento);
        Button status = (Button) convertView.findViewById(R.id.buttonsStatus);


        nomeCliente.setText(pedido.getCliente().getNome());
        endereco.setText(pedido.getCliente().getEndereco());
        bairro.setText(pedido.getCliente().getBairro());
        produto.setText(pedido.getProduto().getNome());
        qtd.setText(String.valueOf(pedido.getQtd()) + "x");
        Locale ptBr = new Locale("pt", "BR");
        preco.setText( NumberFormat.getCurrencyInstance(ptBr).format(pedido.getProduto().getPreco()));
        valor.setText( NumberFormat.getCurrencyInstance(ptBr).format(pedido.getValorTotal()));
        pagamento.setText(pedido.getMetodoDePagamento());

        return convertView;

    }
    public void botaoMudaStatus(){
        Pedido pedido = getItem(position);
        if(pedido.getStatus().equals("Pendente aprovação")) pedido.setStatus("Finalizado");
        mDatabaseReference = mDatabase.getReference ().child ("empresas").child("0").child("pedidos").child("0");
        mDatabaseReference.setValue (pedido);
    }
}