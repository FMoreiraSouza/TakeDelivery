package com.example.takedelivery;

import android.util.Log;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.takedelivery.model.Produto;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BancoDeDados extends Thread {
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance ();
    private DatabaseReference mDatabaseReference = mDatabase.getReference ();

    CardapioEmpresaActivity activity;
    ArrayList<Produto> produtos =  new ArrayList<>();
    ListView view;public BancoDeDados( CardapioEmpresaActivity activity, ListView view ){
        this.activity = activity;
        this.view = view;
    }
    
    

    public void run(){
        mDatabaseReference.child("produtos").addValueEventListener(new ValueEventListener() {
            @Override//                activity.updateCardapio( cardapio );

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String value = dataSnapshot.getValue(String.class);

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    produtos.add(ds.getValue(Produto.class));
                    Log.d(  "firebase", "Value is: " + ds.getValue(Produto.class));

                } }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        mDatabaseReference.child("produtos").addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                Produto changedPost = dataSnapshot.getValue(Produto.class);
//                Log.d(  "firebase", "Value is: " + changedPost);
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                activity.updateCardapio( produtos );
            }
        };

        view.post( runnable );
    }
}
