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
import android.widget.Toast;

import com.example.takedelivery.R;
import com.example.takedelivery.acesso.AcessoCliente;
import com.example.takedelivery.firebase.CryptografiaBase64;
import com.example.takedelivery.firebase.FirebaseItems;
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

    private DatabaseReference database = FirebaseItems.getFirebaseDatabase();;
    Pedido pedido;
    private final Context c;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance ();
    private DatabaseReference mDatabaseReference = mDatabase.getReference ();
    Cliente cliente = null;
    AdapterListViewProdutosPedido adapter;
    ListView listViewProdutos;
    Boolean isEmpresa;
    ArrayList<Produto> produtosLista = new ArrayList<>();
    int position;

    public AdapterListViewPedidos(List<Pedido> pedidos, Boolean isEmpresa, Context context) {
        this.pedidos = pedidos;
        this.isEmpresa = isEmpresa;
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
        pedido = pedidos.get(position);

        convertView = LayoutInflater.from(c).inflate(R.layout.layout_list_pedidos, parent, false);

        TextView data = (TextView) convertView.findViewById(R.id.textViewData);
        TextView nomeCliente = (TextView) convertView.findViewById(R.id.textViewNomeCliente);
        TextView endereco = (TextView) convertView.findViewById(R.id.textViewEndereco);
        TextView statusPed = (TextView) convertView.findViewById(R.id.textViewStatusPedidoList);
        TextView valor = (TextView) convertView.findViewById(R.id.textViewValorPed);
        TextView qtd = (TextView) convertView.findViewById(R.id.textViewqtdPro);
        TextView produto = (TextView) convertView.findViewById(R.id.textViewPro);
        TextView preco = (TextView) convertView.findViewById(R.id.textViewPreco);
        TextView pagamento = (TextView) convertView.findViewById(R.id.textViewPagamento);

        Button status = (Button) convertView.findViewById(R.id.buttonsStatus);

        if(isEmpresa) {
            if (pedido.getStatus().equals("Pendente aprovação")) {
                status.setText("Aprovar");
            } else if (pedido.getStatus().equals("Preparando pedido")) {
                status.setText("Liberar para entrega");
            }else if(pedido.getStatus().equals("Saiu para entrega")){
                status.setText("Finalizar");
            }
            else {
                ((ViewGroup) status.getParent()).removeView(status);
            }
        }else{
            ((ViewGroup) status.getParent()).removeView(status);
        }
        nomeCliente.setText(pedido.getCliente().getNome());
        endereco.setText(pedido.getCliente().getEndereco());
        statusPed.setText(pedido.getStatus());
        produto.setText(pedido.getProduto().getNome());
        qtd.setText(String.valueOf(pedido.getQtd()) + "x");
        Locale ptBr = new Locale("pt", "BR");
        preco.setText( NumberFormat.getCurrencyInstance(ptBr).format(pedido.getProduto().getPreco()));
        valor.setText( NumberFormat.getCurrencyInstance(ptBr).format(pedido.getValorTotal()));
        data.setText(pedido.getData());
        pagamento.setText(pedido.getMetodoDePagamento());

        return convertView;

    }
    public void botaoMudaStatus(DatabaseReference empresaLogadaRef ){
        String idCliente = CryptografiaBase64.codificarBase64( pedido.getCliente().getEmail() );
//        DatabaseReference clienteRef = database.child("Clientes").child(pedido.getCliente().getID());
        DatabaseReference clienteRef = database.child("Clientes").child(idCliente);

        Pedido pedido = getItem(position);
        if(pedido.getStatus().equals("Pendente aprovação")) {
            pedido.setStatus("Preparando pedido");
        }else if(pedido.getStatus().equals("Preparando pedido")){
            pedido.setStatus("Saiu para entrega");
        }else if(pedido.getStatus().equals("Saiu para entrega")) {
            pedido.setStatus("Finalizado");
        }

        pedido.salvar(empresaLogadaRef, clienteRef);

    }
}