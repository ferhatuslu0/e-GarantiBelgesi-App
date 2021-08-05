package com.example.egarantiuygulamasi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.HashMap;
import java.util.Map;

import static com.example.egarantiuygulamasi.Anasayfa.EXTRA_garantiFoto;
import static com.example.egarantiuygulamasi.Anasayfa.EXTRA_garantisure;
import static com.example.egarantiuygulamasi.Anasayfa.EXTRA_magazaAd;
import static com.example.egarantiuygulamasi.Anasayfa.EXTRA_nakitkart;
import static com.example.egarantiuygulamasi.Anasayfa.EXTRA_urunad;
import static com.example.egarantiuygulamasi.Anasayfa.EXTRA_urunfiyat;
import static com.example.egarantiuygulamasi.Anasayfa.EXTRA_urunkod;
import static com.example.egarantiuygulamasi.Anasayfa.EXTRA_uruntarih;

public class DetaySayfasi extends AppCompatActivity {
    private Button btnGeri;
    private Button btnFormGonder;
    private TextView txtFrmUrunKod;
    private TextView txtFrmUrunAd;
    private EditText edtMesaj;
    String urunkod="";
    String urunad="";
    String URL="http://192.168.43.215:8000/jsonVeri/postVeriler.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detay_sayfasi);

        btnGeri =findViewById(R.id.detayGeri);
        btnFormGonder = findViewById(R.id.frmGonder);


        Intent intent=getIntent();
        String magazaAd = intent.getStringExtra(EXTRA_magazaAd);
         urunkod = intent.getStringExtra(EXTRA_urunkod);
         urunad = intent.getStringExtra(EXTRA_urunad);
        String urunfiyat = intent.getStringExtra(EXTRA_urunfiyat);
        String uruntarih = intent.getStringExtra(EXTRA_uruntarih);
        String garantisure = intent.getStringExtra(EXTRA_garantisure);
        String nakitkart = intent.getStringExtra(EXTRA_nakitkart);
        final String garantifoto =intent.getStringExtra(EXTRA_garantiFoto);

        TextView txtmagazaAd=findViewById(R.id.magazaName);
        TextView txturunkod=findViewById(R.id.urunCode);
        TextView txturunad=findViewById(R.id.urunName);
        TextView txturunfiyat=findViewById(R.id.urunMooney);
        TextView txturuntarih=findViewById(R.id.urunDate);
        TextView txtgarantisure=findViewById(R.id.garantiTime);
        TextView txtnakitkart=findViewById(R.id.NakitKart);
        final ImageView imgResim = findViewById(R.id.garantiResim);

        txtmagazaAd.setText(magazaAd);
        txturunkod.setText(urunkod);
        txturunad.setText(urunad);
        txturunfiyat.setText(urunfiyat);
        txturuntarih.setText(uruntarih);
        txtgarantisure.setText(garantisure);
        txtnakitkart.setText(nakitkart);



        byte[] imageAsBytes = Base64.decode(garantifoto.getBytes(), Base64.DEFAULT);

        imgResim.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));


        imgResim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapDrawable drawable = (BitmapDrawable) imgResim.getDrawable();
                Bitmap bitmap = drawable.getBitmap();

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(DetaySayfasi.this);
                View mView = getLayoutInflater().inflate(R.layout.activity_detay_resim, null);
                PhotoView photoView = mView.findViewById(R.id.image_detay_resim);
                photoView.setImageBitmap(bitmap);
                mBuilder.setView(mView);
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });


        btnGeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetaySayfasi.this,MainActivity.class));
            }
        });

        btnFormGonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(DetaySayfasi.this, ""+urunkod, Toast.LENGTH_SHORT).show();


                final BottomSheetDialog bottomSheetDialog =new BottomSheetDialog (
                        DetaySayfasi.this, R.style.BottomSheetDialogTheme2
                );
                View bottomSheetView = LayoutInflater.from(getApplicationContext())
                        .inflate(
                                R.layout.layout_frm_gonder,
                                (LinearLayout)v.findViewById(R.id.bottomSheetContainer2)

                        );
                txtFrmUrunKod = bottomSheetView.findViewById(R.id.frmUrunKod);
                txtFrmUrunAd = bottomSheetView.findViewById(R.id.frmUrunAd);
                edtMesaj =bottomSheetView.findViewById(R.id.frmSikayet);
                txtFrmUrunKod.setText(""+urunkod);
                txtFrmUrunAd.setText(""+urunad);

                bottomSheetView.findViewById(R.id.frmGonder).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        postJson();
                        Toast.makeText(DetaySayfasi.this, "En kısa sürede size geri dönüş yapılacaktır. Teşekkürler", Toast.LENGTH_LONG).show();
                        bottomSheetDialog.dismiss();


                    }
                });

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();

            }
        });

    }

    private void postJson() {

        StringRequest stringRequest =new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                //Toast.makeText(getApplication(),response,Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(DetaySayfasi.this, error+"", Toast.LENGTH_SHORT).show();
            }

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                String urnKod=urunkod;
                String urnAd=urunad;
                String edtsikayet=edtMesaj.getText().toString();
                String frmMail = Login.tutulacakeMail;
                String frmDurum= "okunmadi";
                params.put("iletiUrunKod",urnKod);
                params.put("iletiUrunAd",urnAd);
                params.put("iletiMesaj",edtsikayet);
                params.put("iletiEmail",frmMail);
                params.put("durum",frmDurum);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
