package com.example.takedelivery.empresa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

public class ConfigurarEmpresa extends AppCompatActivity {

    private EditText editEmpresaNome, editEmpresaCategoria,
            editEmpresaTempo, editEmpresaTaxa;
    private ImageView imagePerfilEmpresa;

    private static final int SELECAO_GALERIA = 200;
    private StorageReference storageReference;
    private DatabaseReference firebaseRef;
    private String idUsuarioLogado;
    private String urlImagemSelecionada = "";

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
                    Empresa empresa = new Empresa();
                    empresa.setNomeFantasia(dataSnapshot.child("nomeFantasia").getValue().toString());
                    empresa.setUrlImagem(dataSnapshot.child("urlImagem").exists() ? dataSnapshot.child("urlImagem").getValue().toString(): "");
                    editEmpresaNome.setText(empresa.getNome());
                    editEmpresaCategoria.setText(empresa.getCategoria());
//                    editEmpresaTaxa.setText(empresa.getPrecoEntrega().toString());
                    editEmpresaTempo.setText(empresa.getTempo());

                    urlImagemSelecionada = empresa.getUrlImagem();
                    if(!urlImagemSelecionada.equals("") ){
                        Picasso.get()
                                .load(urlImagemSelecionada)
                                .into(imagePerfilEmpresa);
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
        String taxa = editEmpresaTaxa.getText().toString();
        String categoria = editEmpresaCategoria.getText().toString();
        String tempo = editEmpresaTempo.getText().toString();

//        if( !nome.isEmpty()){
//            if( !taxa.isEmpty()){
//                if( !categoria.isEmpty()){
//                    if( !tempo.isEmpty()){

                        Empresa empresa = new Empresa();
                        empresa.setId( idUsuarioLogado );
//                        empresa.setNome( nome );
//                        empresa.setPrecoEntrega( Double.parseDouble(taxa) );
//                        empresa.setCategoria(categoria);
//                        empresa.setTempo( tempo );
                        empresa.setUrlImagem( urlImagemSelecionada );
                        empresa.addImagem();
                        finish();

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
        editEmpresaNome = findViewById(R.id.editEmpresaNome);
        editEmpresaCategoria = findViewById(R.id.editEmpresaCategoria);
        editEmpresaTaxa = findViewById(R.id.editEmpresaTaxa);
        editEmpresaTempo = findViewById(R.id.editEmpresaTempo);
        imagePerfilEmpresa = findViewById(R.id.imagemPerfilEmpresa);
    }

}