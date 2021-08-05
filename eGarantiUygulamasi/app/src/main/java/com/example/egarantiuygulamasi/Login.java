package com.example.egarantiuygulamasi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Login extends AppCompatActivity {

    private FirebaseAuth auth;
    private Button btnGiris,btnKayit;
    private TextView textEmail,textPassword;
    ArrayList<Profil> uyeler;
    Profil user;
    static public String tutulacakeMail;
    static public String tutulacakAdSoyad;
    static public String tutulacakTel;
    int sayi=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        uyeler = new ArrayList<>();

        auth = FirebaseAuth.getInstance();

        textEmail =(EditText) findViewById(R.id.loginEmail);
        textPassword =(EditText) findViewById(R.id.loginPassword);
        btnGiris =(Button) findViewById(R.id.girisButton);
        btnKayit =(Button) findViewById(R.id.uyeOlButton);


        btnGiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();

                myRef.child("Profil").addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            user = snapshot.getValue(Profil.class);
                            uyeler.add(
                                    new Profil(
                                            user.getIdArttir(),
                                            user.getTel(),
                                            user.getadSoyad(),
                                            user.getEmail(),
                                            user.getSifre()

                                    )

                            );

                            //Toast.makeText(Login.this,"" + user.getEmail() + "   " + user.getSifre() + "  " + " "+ textEmail.getText() +" "+ textPassword.getText(),Toast.LENGTH_LONG).show();
                            if(user.getSifre().equals(textPassword.getText().toString()) && user.getEmail().equals(textEmail.getText().toString()))
                            {
                                Toast.makeText(Login.this,"Giriş Başarılı",Toast.LENGTH_LONG).show();

                                sayi = user.getIdArttir();
                                gonder();

                            }

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                    }
                });

            }

            private void gonder() {

                tutulacakAdSoyad = user.getadSoyad();
                tutulacakeMail = user.getEmail();
                tutulacakTel =user.getTel();



                String code = "90";
                String tell= tutulacakTel;

                String phonenumber = "+" + code + tell;

                Intent intent = new Intent(Login.this, TelefonKod.class);
                intent.putExtra("phonenumber", phonenumber);
                startActivity(intent);


            }
        });




        btnKayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,KayitOl.class));

            }
        });

    }


}