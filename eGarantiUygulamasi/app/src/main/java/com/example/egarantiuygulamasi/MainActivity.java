package com.example.egarantiuygulamasi;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private Toolbar toolbar;
    public TextView girisMail,girisAdSoyad;
    public static CircleImageView imgResim;
    ArrayList<JsonVeriler> verilerr;
    ArrayList<Yuklenenler> Yukverilerr;
    static public String mailKars="";

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef=database.getReference();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);


            verilerr =new ArrayList<>();
            Yukverilerr =new ArrayList<>();
            toolbar = findViewById(R.id.toolbarr);

        SharedPreferences preferences = getSharedPreferences("myprefs",MODE_PRIVATE);
        String img_str=preferences.getString("userphoto", "");
        String esitMail =preferences.getString("textMail","");

        String base=img_str;
        byte[] imageAsBytes = Base64.decode(base.getBytes(), Base64.DEFAULT);


        //setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //giriş yapıldıktan sonra menu tarafında Ad soyad ve email bilgilerini göstermek için kullanıldı
        View headView=navigationView.getHeaderView(0);
        girisMail =(TextView) headView.findViewById(R.id.textMail);
        girisAdSoyad =(TextView) headView.findViewById(R.id.textAdSoyad);
        imgResim =headView.findViewById(R.id.imageView);
        if(Login.tutulacakeMail.equals(esitMail))
        {
            imgResim.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length) );
        }


        girisMail.setText(Login.tutulacakeMail);
        girisAdSoyad.setText(Login.tutulacakAdSoyad);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Anasayfa()).commit();
            navigationView.setCheckedItem(R.id.anasayfa);
        }

        myRef.child("yuklenenler").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Yuklenenler  yuk = snapshot.getValue(Yuklenenler.class);

                    if(Login.tutulacakeMail.equals(yuk.getYukleMail())) {
                        Yukverilerr.add(
                                new Yuklenenler(

                                        yuk.getYukleMail(),
                                        yuk.getYukleUrunAd(),
                                        yuk.getYukleUrunFiyat(),
                                        yuk.getYukleUrunTarih(),
                                        yuk.getYukleUrunGaranti(),
                                        yuk.getYukleUrunKod()


                                )

                        );
                        MainActivity.mailKars =yuk.getYukleMail();
                        String gun="";
                        String ay="";
                        String yil="";
                        String bitisGun="";
                        String bitisAy="";
                        String bitisYil="";


                            gun = yuk.getYukleUrunTarih().substring(0,2);
                            ay = yuk.getYukleUrunTarih().substring(3,5);
                            yil = yuk.getYukleUrunTarih().substring(6,10);

                        bitisGun = gun;
                        bitisAy = ay;
                        bitisYil = String.valueOf(Integer.parseInt(yil) + Integer.parseInt(yuk.getYukleUrunGaranti()));

                        natification(bitisGun,bitisAy,bitisYil);
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });


        myRef.child("urunler").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    JsonVeriler  fbaseveri = snapshot.getValue(JsonVeriler.class);

                    if(Login.tutulacakeMail.equals(fbaseveri.geteMail())) {
                        verilerr.add(
                                new JsonVeriler(

                                        fbaseveri.getmImageUrl(),
                                        fbaseveri.getMagazaAd(),
                                        fbaseveri.getUrunKod(),
                                        fbaseveri.getUrunAd(),
                                        fbaseveri.getUrunFiyat(),
                                        fbaseveri.getTarih(),
                                        fbaseveri.getGarantiSure(),
                                        fbaseveri.getNakKart(),
                                        fbaseveri.getGarantiFoto(),
                                        fbaseveri.geteMail(),
                                        fbaseveri.getmTel()

                                )

                        );
                        MainActivity.mailKars =fbaseveri.geteMail();
                        String gun="";
                        String ay="";
                        String yil="";
                        String bitisGun="";
                        String bitisAy="";
                        String bitisYil="";


                                gun = fbaseveri.getTarih().substring(0,2);
                                ay = fbaseveri.getTarih().substring(3,5);
                                yil = fbaseveri.getTarih().substring(6,10);

                            bitisGun = gun;
                            bitisAy = ay;
                            bitisYil = String.valueOf(Integer.parseInt(yil) + Integer.parseInt(fbaseveri.getGarantiSure()));
                        natification(bitisGun,bitisAy,bitisYil);
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });


    }

    private void natification(String gun,String ay, String yil) {


        Date simdikiZaman = new Date();
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String degisken=df.format(simdikiZaman);
        String tempGun="";
        String tempAy="";
        String tempYil="";
        tempYil = degisken.substring(0,4);
        tempAy = df.format(simdikiZaman).substring(5,7);
        tempGun = df.format(simdikiZaman).substring(8,10);
        int gercekYil = Integer.parseInt(tempYil);
        int gercekAy = Integer.parseInt(tempAy);

            String hesapGun =String.valueOf(Integer.parseInt(tempGun) - Integer.parseInt(gun));


        if((Login.tutulacakeMail.equals(MainActivity.mailKars)) && (Integer.parseInt(hesapGun) == 2) && gercekAy == Integer.parseInt(ay) && gercekYil==Integer.parseInt(yil))
        {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_watch_later)
                    .setContentTitle("Garanti Süreniz bitmek Üzere!!!")
                    .setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE))
                    .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                    .setContentText("Garanti sürenizin bitmesine son 2 gün ?");



            Intent notificationIntent = new Intent(this, Anasayfa.class);
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(contentIntent);


            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(0, builder.build());

        }
        if(Login.tutulacakeMail.equals(MainActivity.mailKars) && (Integer.parseInt(tempGun) == Integer.parseInt(gun))  && gercekAy == Integer.parseInt(ay) && gercekYil == Integer.parseInt(yil))
        {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_watch_later)
                    .setContentTitle("Garanti Süreniz bitmiştir!!!")
                    .setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE))
                    .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                    .setContentText("UYARI ?");



            Intent notificationIntent = new Intent(this, MainActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(contentIntent);


            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(0, builder.build());
        }



    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) { //fragmentlere yani sayfalara yönlendirmek için kullanıldı


        switch (item.getItemId()) {
            case R.id.anasayfa:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Anasayfa()).commit();
                break;
            case R.id.tara:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new BelgeTara()).commit();
                break;
            case R.id.yuklediklerim:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new YuklenenBelgeler()).commit();
                break;


            case R.id.nav_share:
                Intent mPaylas=new Intent(Intent.ACTION_SEND);
                mPaylas.setType("text/plain");
                mPaylas.putExtra(Intent.EXTRA_SUBJECT,"Uygulama bilgisi");
                mPaylas.putExtra(Intent.EXTRA_TEXT,"paylaşacağımız link");
                startActivity(Intent.createChooser(mPaylas,"Seçiniz !"));
                break;

            case R.id.nav_send:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Ayarlar()).commit();
                break;


            case  R.id.cikis:
                startActivity(new Intent(MainActivity.this,Login.class));
        }



        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}
