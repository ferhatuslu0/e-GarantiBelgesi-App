package com.example.egarantiuygulamasi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class KayitOl extends AppCompatActivity {

    EditText madSoyad,memail,mpassword;
    Button btnKayit,uyeGeriBtn;
    int sayi=0,sayi2=0;
    EditText mTel;

    private String value;

    ArrayList<Profil> uyeler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ol);
        uyeler = new ArrayList<>();

        btnKayit = (Button)findViewById(R.id.btnKayit);
        madSoyad = (EditText)findViewById(R.id.AdSoyad);
        memail = (EditText)findViewById(R.id.emailKayit);
        mpassword = (EditText)findViewById(R.id.passwordKayit);
        uyeGeriBtn = (Button) findViewById(R.id.btnGeri);
        mTel=(EditText) findViewById(R.id.tel);



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();



        myRef.child("Profil").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Profil user = snapshot.getValue(Profil.class);
                    uyeler.add(
                            new Profil(
                                    user.getIdArttir(),
                                    user.getTel(),
                                    user.getadSoyad(),
                                    user.getEmail(),
                                    user.getSifre()
                            )
                    );

                    if(user.getIdArttir()>sayi) sayi= user.getIdArttir();

                    sayi2 = sayi + 1;

                }

                array();
            }

            private void array() {


                btnKayit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Profil");
                        dbRef.child(String.valueOf(sayi2)).setValue(
                                new Profil(
                                        sayi2,
                                        mTel.getText().toString(),
                                        madSoyad.getText().toString(),
                                        memail.getText().toString(),
                                        mpassword.getText().toString()
                                )
                        );

                        Toast.makeText(KayitOl.this,"Başarılı şekilde kayıt yapıldı.",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(KayitOl.this,Login.class));
                    }
                });

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {}

        });

        uyeGeriBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KayitOl.this,Login.class));
            }
        });



    }
}
