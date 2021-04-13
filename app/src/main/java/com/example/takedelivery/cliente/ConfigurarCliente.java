package com.example.takedelivery.cliente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.takedelivery.empresa.ConfigurarEmpresa;
import com.example.takedelivery.firebase.ClienteFirebase;
import com.example.takedelivery.firebase.FirebaseOptions;
import com.example.takedelivery.R;
import com.example.takedelivery.helper.UsuarioFirebase;
import com.example.takedelivery.model.Cliente;
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

public class ConfigurarCliente extends AppCompatActivity {

    private EditText editClienteNome, editClienteEndereco;
    private ImageView imagemPerfilCliente;
    private static final int SELECAO_GALERIA = 200;
    private StorageReference storageReference;
    private DatabaseReference firebaseRef;
    private String idUsuarioLogado;
    private String urlImagemSelecionada = "";
    Cliente cliente;
    private EditText editNome, editEndereco, editCidade, editBairro, editTel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar_cliente);

        //Configurações iniciais
        inicializarComponentes();
        storageReference = FirebaseOptions.getFirebaseStorage();
        firebaseRef = FirebaseOptions.getFirebase();
        idUsuarioLogado = ClienteFirebase.getIdentificarCliente();

        //Configurações Toolbar
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        toolbar.setTitle("Configurações Cliente");
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imagemPerfilCliente.setOnClickListener(new View.OnClickListener() {
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

        recuperarDadosCliente();
    }

    private void recuperarDadosCliente(){

        DatabaseReference empresaRef = firebaseRef
                .child("Clientes")
                .child( idUsuarioLogado );
        empresaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if( dataSnapshot.getValue() != null ){
                    ArrayList<Pedido> pedidos = new ArrayList();

                    for (DataSnapshot ped : dataSnapshot.child("pedidos").getChildren()) {
                        pedidos.add(ped.getValue(Pedido.class));
                    }
                    cliente = new Cliente();
                    cliente.setPedidos(pedidos);
                    cliente.setID(dataSnapshot.getKey());

                    cliente.setNome(dataSnapshot.child("nome").getValue().toString());
                    cliente.setEmail(dataSnapshot.child("email").getValue().toString());
                    cliente.setSenha(dataSnapshot.child("senha").getValue().toString());
                    cliente.setTelefone(dataSnapshot.child("telefone").getValue().toString());
                    cliente.setCidade(dataSnapshot.child("cidade").getValue().toString());
                    cliente.setBairro(dataSnapshot.child("bairro").getValue().toString());
                    cliente.setEndereco(dataSnapshot.child("endereco").getValue().toString());
                    cliente.setUrlImagem(dataSnapshot.child("urlImagem").exists()? dataSnapshot.child("urlImagem").getValue().toString(): "");


                    editNome.setText(cliente.getNome());
                    editTel.setText(cliente.getTelefone());
                    editEndereco.setText(cliente.getEndereco());
                    editBairro.setText(cliente.getBairro());
                    editCidade.setText(cliente.getCidade());
                    urlImagemSelecionada = cliente.getUrlImagem();

                    if(!cliente.getUrlImagem().equals("")) {
                        final StorageReference imagemRef = storageReference
                                .child("imagens")
                                .child("Clientes")
                                .child(cliente.getUrlImagem());
                        imagemRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                // Got the download URL for 'users/me/profile.png'
                                // Pass it to Picasso to download, show in ImageView and caching
                                Picasso.get().load(uri.toString()).into(imagemPerfilCliente);
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



    public void validarDadosCliente(View view){

        //Valida se os campos foram preenchidos
        String nome = editNome.getText().toString();
        String telefone = editTel.getText().toString();
        String endereco = editEndereco.getText().toString();
        String bairro = editBairro.getText().toString();
        String cidade = editCidade.getText().toString();

        cliente.setID( idUsuarioLogado );
        cliente.setNome( nome );
        cliente.setTelefone(telefone);
        cliente.setCidade(cidade);
        cliente.setEndereco(endereco);
        cliente.setBairro(bairro);
        cliente.setUrlImagem( urlImagemSelecionada );
        cliente.salvarCliente();
        Toast.makeText(ConfigurarCliente.this, "Salvo com sucesso",
                Toast.LENGTH_SHORT).show();
        finish();


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

                    imagemPerfilCliente.setImageBitmap( imagem );

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    imagem.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                    byte[] dadosImagem = baos.toByteArray();

                    final StorageReference imagemRef = storageReference
                            .child("imagens")
                            .child("Clientes")
                            .child(idUsuarioLogado);
                    urlImagemSelecionada = idUsuarioLogado;

                    UploadTask uploadTask = imagemRef.putBytes( dadosImagem );
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ConfigurarCliente.this,
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
                            Toast.makeText(ConfigurarCliente.this,
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
       editNome = findViewById(R.id.editNomeCliConfig);
       editTel = findViewById(R.id.editTelefoneCliConfig);
       editEndereco = findViewById(R.id.editEnderecoCliConfig);
       editBairro = findViewById(R.id.editBairroCliConfig);
       editCidade = findViewById(R.id.editCidadeCliConfig);
       imagemPerfilCliente = findViewById(R.id.imagemPerfilCliente);
    }



}