package com.example.egarantiuygulamasi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class Ayarlar extends Fragment {

    private TextView adSoyad;
    private TextView mail;
    private Button sifreDegis;
    private Button yardim;
    private Button sifreGuncelle;
    private LinearLayout linear;
    private TextView yardimAl;
    private EditText yeniSifre;
    private EditText sabitMail;
    public static CircleImageView resimYukle;
    Uri imageUri;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef=database.getReference();


    private static final int IMAGE_PICK = 1;


    private  void writeNewMesaj(int idArttir ,String tel,String adSoyad,String email, String sifre){
        Profil profilArray=new Profil(idArttir,tel,adSoyad,email,sifre);

        myRef.child("Profil").child(String.valueOf(idArttir)).setValue(profilArray);



    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_ayarlar, container, false);
        adSoyad =(TextView) v.findViewById(R.id.adTut);
        mail =(TextView) v.findViewById(R.id.mailTut);
        yardimAl =(TextView) v.findViewById(R.id.yardimKonusu);
        sifreDegis=v.findViewById(R.id.sifreDegis);
        yardim=v.findViewById(R.id.btnyardim);
        linear=v.findViewById(R.id.layoutGizle);
        resimYukle=(CircleImageView) v.findViewById(R.id.profile_image);
        sifreGuncelle=(Button) v.findViewById(R.id.guncelleButton);
        yeniSifre=(EditText) v.findViewById(R.id.guncelPassword);
        sabitMail=(EditText) v.findViewById(R.id.guncelEmail);

        adSoyad.setText(Login.tutulacakAdSoyad);
        mail.setText(Login.tutulacakeMail);
        yardimAl.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        // sabitMail.setText(Login.tutulacakeMail);

        prepareForm();

        sifreDegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linear.setVisibility(view.getVisibility());
                yardimAl.setVisibility(View.GONE);

            }
        });

        yardim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linear.setVisibility(View.GONE);
                yardimAl.setVisibility(view.getVisibility());
            }
        });

        resimYukle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Bir Fotograf Secin"), IMAGE_PICK);

            }
        });

        sifreGuncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Profil kisi = new Profil();

                writeNewMesaj(kisi.getIdArttir(),Login.tutulacakTel,Login.tutulacakAdSoyad,Login.tutulacakeMail,yeniSifre.getText().toString());
                Toast.makeText(v.getContext(), "Şifre başarıyla Güncelle.", Toast.LENGTH_SHORT).show();

            }
        });



        return v;

    }



    private void prepareForm() {

        SharedPreferences preferences = getActivity().getSharedPreferences("myprefs",MODE_PRIVATE);

        String img_str=preferences.getString("userphoto", "");
        String esitMail =preferences.getString("textMail","");

        String texttMail;
       texttMail =mail.getText().toString();

            if (texttMail.equals(esitMail) ){

                String base=img_str;
                byte[] imageAsBytes = Base64.decode(base.getBytes(), Base64.DEFAULT);
                resimYukle.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));

            }


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bitmap;

        if (requestCode == IMAGE_PICK && resultCode==RESULT_OK)
        {
            imageUri =data.getData();

            try {
                 bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),imageUri);
                resimYukle.setImageBitmap(bitmap);
                MainActivity.imgResim.setImageBitmap(bitmap);

            } catch (IOException e){
                e.printStackTrace();
            }
        }
        String textMail;
        textMail =mail.getText().toString();

        resimYukle.buildDrawingCache();
        Bitmap baseResim = resimYukle.getDrawingCache();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        baseResim.compress(Bitmap.CompressFormat.PNG, 90, stream);
        byte[] image=stream.toByteArray();
        String img_str = Base64.encodeToString(image, 0);
        SharedPreferences preferences = getActivity().getSharedPreferences("myprefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userphoto",img_str);
        editor.putString("textMail",textMail);
        editor.commit();




    }


}
