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

import com.example.takedelivery.firebase.FirebaseOptions;
import com.example.takedelivery.R;
import com.example.takedelivery.helper.UsuarioFirebase;
import com.example.takedelivery.model.Cliente;
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

public class ConfigurarCliente extends AppCompatActivity {

    private EditText editClienteNome, editClienteEndereco;
    private ImageView imagemPerfilCliente;
    private static final int SELECAO_GALERIA = 200;
    private StorageReference storageReference;
    private DatabaseReference firebaseRef;
    private String idUsuarioLogado;
    private String urlImagemSelecionada = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar_cliente);

        //Configurações iniciais
        inicializarComponentes();
        storageReference = FirebaseOptions.getFirebaseStorage();
        firebaseRef = FirebaseOptions.getFirebase();
        idUsuarioLogado = UsuarioFirebase.getIdUsuario();

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

        DatabaseReference clienteRef = firebaseRef
                .child("Clientes")
                .child( idUsuarioLogado );
        clienteRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if( dataSnapshot.getValue() != null ){
                    Cliente cliente = dataSnapshot.getValue(Cliente.class);
                    editClienteNome.setText(cliente.getNome());
                    editClienteEndereco.setText(cliente.getEndereco());

                    urlImagemSelecionada = cliente.getUrlImagem();
                    if( urlImagemSelecionada != "" ){
                        Picasso.get()
                                .load(urlImagemSelecionada)
                                .into(imagemPerfilCliente);
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
        String nome = editClienteNome.getText().toString();
        String endereco = editClienteEndereco.getText().toString();

        if( !nome.isEmpty()){
            if( !endereco.isEmpty()){

                        Cliente cliente = new Cliente();
                        cliente.setID( idUsuarioLogado );
                        cliente.setNome( nome );
                        cliente.setEndereco(endereco);
                        cliente.setUrlImagem( urlImagemSelecionada );
                        cliente.salvarCliente();
                        finish();

            }else{
                exibirMensagem("Digite um endereço para o cliente");
            }
        }else{
            exibirMensagem("Digite um nome para o cliente");
        }

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
                            .child(idUsuarioLogado + "jpeg");

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
        editClienteNome = findViewById(R.id.editClienteNome);
//        editClienteEndereco = findViewById(R.id.editClienteEndereco);
        imagemPerfilCliente= findViewById(R.id.imagemPerfilCliente);
    }



}