package com.example.takedelivery.empresa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.takedelivery.cliente.CarrinhoActivity;
import com.example.takedelivery.cliente.ClienteActivity;
import com.example.takedelivery.cliente.ConfigurarCliente;
import com.example.takedelivery.firebase.EmpresaFirebase;
import com.example.takedelivery.firebase.FirebaseOptions;
import com.example.takedelivery.R;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.takedelivery.helper.UsuarioFirebase;
import com.example.takedelivery.model.Empresa;
import com.example.takedelivery.model.Pedido;
import com.example.takedelivery.model.Produto;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ConfigurarEmpresa extends AppCompatActivity {

    private EditText editEmpresaNome, editEmpresaCategoria,
            editEmpresaTempo, editEmpresaTaxa, editEmpresaCEP, editEmpresaEndereco, editEmpresaCidade, editEmpresaEstado, editEmpresaBairro, editEmpresaTel;
    private ImageView imagePerfilEmpresa;

    private static final int SELECAO_GALERIA = 200;
    private StorageReference storageReference;
    private DatabaseReference firebaseRef;
    private String idUsuarioLogado;
    private String urlImagemSelecionada = "";
    Empresa empresa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar_empresa);

        //Configurações iniciais
        inicializarComponentes();
        storageReference = FirebaseOptions.getFirebaseStorage();
        firebaseRef = FirebaseOptions.getFirebase();
        idUsuarioLogado = EmpresaFirebase.getIdentificarEmpresa();

        //Configurações Toolbar
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        toolbar.setTitle("Configurações Empresa");
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imagePerfilEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                );
                if( i.resolveActivity(getPackageManager()) != null ){
                    startActivityForResult(i, SELECAO_GALERIA);
                }
            }
        });

        recuperarDadosEmpresa();
    }

    private void recuperarDadosEmpresa(){

        DatabaseReference empresaRef = firebaseRef
                .child("Empresas")
                .child( idUsuarioLogado );
        empresaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if( dataSnapshot.getValue() != null ){
                    ArrayList<Produto> produtos = new ArrayList();
                    ArrayList<Pedido> pedidos = new ArrayList();

                    for (DataSnapshot pro : dataSnapshot.child("produtos").getChildren()) {
                        produtos.add(pro.getValue(Produto.class));
                    }
                    for (DataSnapshot ped : dataSnapshot.child("pedidos").getChildren()) {
                        pedidos.add(ped.getValue(Pedido.class));
                    }
                    empresa = new Empresa();
                    empresa.setProdutos(produtos);
                    empresa.setPedidos(pedidos);
                    empresa.setId(dataSnapshot.getKey());

                    empresa.setNome(dataSnapshot.child("nome").getValue().toString());
                    empresa.setEmail(dataSnapshot.child("email").getValue().toString());
                    empresa.setSenha(dataSnapshot.child("senha").getValue().toString());
                    empresa.setCnpj(dataSnapshot.child("cnpj").getValue().toString());
                    empresa.setNomeFantasia(dataSnapshot.child("nomeFantasia").getValue().toString());
                    empresa.setTelefone(dataSnapshot.child("telefone").getValue().toString());
                    empresa.setCep(dataSnapshot.child("cep").getValue().toString());
                    empresa.setEstado(dataSnapshot.child("estado").getValue().toString());
                    empresa.setCidade(dataSnapshot.child("cidade").getValue().toString());
                    empresa.setBairro(dataSnapshot.child("bairro").getValue().toString());
                    empresa.setEndereco(dataSnapshot.child("endereco").getValue().toString());
                    empresa.setUrlImagem(dataSnapshot.child("urlImagem").exists()? dataSnapshot.child("urlImagem").getValue().toString(): "");


                    editEmpresaNome.setText(empresa.getNomeFantasia());
                    editEmpresaTel.setText(empresa.getTelefone());
                    editEmpresaCEP.setText(empresa.getCep());
                    editEmpresaEndereco.setText(empresa.getEndereco());
                    editEmpresaBairro.setText(empresa.getBairro());
                    editEmpresaCidade.setText(empresa.getCidade());
                    editEmpresaEstado.setText(empresa.getEstado());
                    urlImagemSelecionada = empresa.getUrlImagem();

                    if(empresa.getUrlImagem() != null) {

                        final StorageReference imagemRef = storageReference
                                .child("imagens")
                                .child("Empresas")
                                .child(empresa.getUrlImagem());
                        imagemRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                // Got the download URL for 'users/me/profile.png'
                                // Pass it to Picasso to download, show in ImageView and caching
                                Picasso.get().load(uri.toString()).into(imagePerfilEmpresa);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                            }
                        });
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



    public void validarDadosEmpresa(View view){

        //Valida se os campos foram preenchidos
        String nome = editEmpresaNome.getText().toString();
        String telefone = editEmpresaTel.getText().toString();
        String cep = editEmpresaCEP.getText().toString();
        String endereco = editEmpresaEndereco.getText().toString();
        String bairro = editEmpresaBairro.getText().toString();
        String cidade = editEmpresaCidade.getText().toString();
        String estado = editEmpresaEstado.getText().toString();

//        if( !nome.isEmpty()){
//            if( !taxa.isEmpty()){
//                if( !categoria.isEmpty()){
//                    if( !tempo.isEmpty()){
                        empresa.setNomeFantasia(nome);
                        empresa.setTelefone(telefone);
                        empresa.setEndereco(endereco);
                        empresa.setCidade(cidade);
                        empresa.setCep(cep);
                        empresa.setBairro(bairro);
                        empresa.setEstado(estado);
                        empresa.setUrlImagem( urlImagemSelecionada );
                        empresa.salvarEmpresa();
//                        empresa.addImagem();
        Toast.makeText(ConfigurarEmpresa.this, "Salvo com sucesso",
                Toast.LENGTH_SHORT).show();
                        finish();
        startActivity(new Intent(this, EmpresaActivity.class));

//                    }else{
//                        exibirMensagem("Digite um tempo de entrega");
//                    }
//                }else{
//                    exibirMensagem("Digite uma categoria");
//                }
//            }else{
//                exibirMensagem("Digite uma taxa de entrega");
//            }
//        }else{
//            exibirMensagem("Digite um nome para a empresa");
//        }

    }

    private void exibirMensagem(String texto){
        Toast.makeText(this, texto, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( resultCode == RESULT_OK){
            Bitmap imagem = null;

            try {

                switch (requestCode) {
                    case SELECAO_GALERIA:
                        Uri localImagem = data.getData();
                        imagem = MediaStore.Images
                                .Media
                                .getBitmap(
                                        getContentResolver(),
                                        localImagem
                                );
                        break;
                }

                if( imagem != null){

                    imagePerfilEmpresa.setImageBitmap( imagem );

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    imagem.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                    byte[] dadosImagem = baos.toByteArray();

                    final StorageReference imagemRef = storageReference
                            .child("imagens")
                            .child("Empresas")
                            .child(idUsuarioLogado);
                    urlImagemSelecionada = idUsuarioLogado;

                    UploadTask uploadTask = imagemRef.putBytes( dadosImagem );
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ConfigurarEmpresa.this,
                                    "Erro ao fazer upload da imagem",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            imagemRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    Uri url = task.getResult();
                                }
                            });
                            Toast.makeText(ConfigurarEmpresa.this,
                                    "Sucesso ao fazer upload da imagem",
                                    Toast.LENGTH_SHORT).show();

                        }
                    });

                }

            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }

    private void inicializarComponentes(){
        editEmpresaNome = findViewById(R.id.editTextNomeFantasiaConfig);
        editEmpresaTel = findViewById(R.id.editTextTelefoneConfig);
        editEmpresaCEP = findViewById(R.id.editTextCEPConfig);
        editEmpresaEndereco = findViewById(R.id.editTextEnderecoConfig);
        editEmpresaBairro = findViewById(R.id.editTextBairroConfig);
        editEmpresaCidade = findViewById(R.id.editTextCidadeConfig);
        editEmpresaEstado = findViewById(R.id.editTextEstadoConfig);
        imagePerfilEmpresa = findViewById(R.id.imagemPerfilEmpresa);



    }

}