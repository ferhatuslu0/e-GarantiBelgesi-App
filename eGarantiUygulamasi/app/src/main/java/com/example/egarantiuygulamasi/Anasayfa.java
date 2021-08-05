package com.example.egarantiuygulamasi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Anasayfa extends Fragment  {

    private Context context;
    private RecyclerView mRecyclerView;
    private JsonVeriAdapter mJsonVerilerAdap;
    private ArrayList<JsonVeriler> verilerList;
    private RequestQueue mRequestQueue;
    private JsonVeriAdapter.OnItemClickListener mListener;
     ArrayList<JsonVeriler> urunVeriler;
    ArrayList<JsonVeriler> doldur;


    public static final String EXTRA_magazaAd="magazaAd";
    public static final String EXTRA_urunkod="urunkod";
    public static final String EXTRA_urunad="urunad";
    public static final String EXTRA_urunfiyat="urunfiyat";
    public static final String EXTRA_uruntarih="uruntarih";
    public static final String EXTRA_garantisure="garantisure";
    public static final String EXTRA_nakitkart="nakitkart";
    public static final String EXTRA_garantiFoto="garantifoto";

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef=database.getReference();




    private  void writeNewUrun(String mImageUrl,String magazaAd, String urunKod, String urunAd, String urunFiyat, String tarih,String garantiSure,String nakKart,String garantiFoto,String eMail,String tel){
        JsonVeriler urun=new JsonVeriler(mImageUrl,magazaAd,urunKod,urunAd,urunFiyat,tarih,garantiSure,nakKart,garantiFoto,eMail,tel);

        String id=myRef.push().getKey();
        myRef.child("urunler").child(id).setValue(urun);
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        context =getActivity();
        final View v = inflater.inflate(R.layout.activity_anasayfa, container, false);


        mRecyclerView=(RecyclerView) v.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

         verilerList=new ArrayList<>();
        urunVeriler = new ArrayList<>();
         doldur = new ArrayList<>();


        mListener = new JsonVeriAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent detay=new Intent(getContext(),DetaySayfasi.class);
                JsonVeriler clickedItem =urunVeriler.get(position);

                detay.putExtra(EXTRA_magazaAd,clickedItem.getMagazaAd());
                detay.putExtra(EXTRA_urunkod,clickedItem.getUrunKod());
                detay.putExtra(EXTRA_urunad,clickedItem.getUrunAd());
                detay.putExtra(EXTRA_urunfiyat,clickedItem.getUrunFiyat());
                detay.putExtra(EXTRA_uruntarih,clickedItem.getTarih());
                detay.putExtra(EXTRA_garantisure,clickedItem.getGarantiSure());
                detay.putExtra(EXTRA_nakitkart,clickedItem.getNakKart());
                detay.putExtra(EXTRA_garantiFoto,clickedItem.getGarantiFoto());
                startActivity(detay);

            }
        };



        mRequestQueue= Volley.newRequestQueue(this.getActivity());

        parseJSON();
        verileriListele();



        return v;

    }

    private void verileriListele() {


        myRef.child("urunler").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    JsonVeriler  fbaseveri = snapshot.getValue(JsonVeriler.class);

                    if(Login.tutulacakeMail.equals(fbaseveri.geteMail())) {
                        urunVeriler.add(
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
                    }
                }

                        mJsonVerilerAdap = new JsonVeriAdapter(getContext(),urunVeriler);
                        mRecyclerView.setAdapter(mJsonVerilerAdap);
                        mJsonVerilerAdap.setOnItemClickListener(mListener);


               // Toast.makeText(context, "qw"+urunVeriler.size(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });


    }


    private void parseJSON(){

        String url="http://192.168.43.215:8000/jsonVeri/verilerim.php";

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        try {


                            JSONArray jsonArray=response.getJSONArray("gelenVeriler");

                            for (int i=0;i<jsonArray.length();i++){

                                JSONObject veri=jsonArray.getJSONObject(i);

                                final String  urlResim =veri.getString("urunFoto");
                                final String  magazaAdi =veri.getString("magazaAdi");
                                final String  urunKodu =veri.getString("urunKodu");
                                final String  urunAdi =veri.getString("urunAdi");
                                final String  urunFiyat =veri.getString("urunFiyat");
                                final String  urunTarih =veri.getString("urunTarih");
                                final String  Nakitkart =veri.getString("nakitKart");
                                final String  garantiSure =String.valueOf(veri.getInt("garantiSure"));
                                final String  garantiFoto =veri.getString("garantiFoto");
                                final String gelenMail = veri.getString("eMail");
                                final String gelenTel = veri.getString("tel");

                                verilerList.add(new JsonVeriler(urlResim,magazaAdi,urunKodu,urunAdi,urunFiyat,urunTarih,garantiSure,Nakitkart,garantiFoto,gelenMail,gelenTel));
                               // writeNewUrun(urlResim,magazaAdi,urunKodu,urunAdi,urunFiyat,urunTarih,garantiSure,Nakitkart,garantiFoto,gelenMail,gelenTel);

                            }

                            myRef.child("urunler").addValueEventListener(new ValueEventListener() {

                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        JsonVeriler  fbaseveri = snapshot.getValue(JsonVeriler.class);
                                        urunVeriler.add(
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

                                    }
                                    int k=0;

                                    for (int j=0; j < verilerList.size();j++)
                                    {
                                        for (int n=0;n<urunVeriler.size();n++) {

                                            if (verilerList.get(j).getUrunKod().equals(urunVeriler.get(n).getUrunKod()) && verilerList.get(j).geteMail().equals(urunVeriler.get(n).geteMail()) )
                                            {
                                                k++;
                                            }
                                        }

                                        if (k==0)
                                        {

                                            writeNewUrun(verilerList.get(j).getmImageUrl(), verilerList.get(j).getMagazaAd(), verilerList.get(j).getUrunKod(),
                                                    verilerList.get(j).getUrunAd(), verilerList.get(j).getUrunFiyat(), verilerList.get(j).getTarih(),
                                                    verilerList.get(j).getGarantiSure(), verilerList.get(j).getNakKart(), verilerList.get(j).getGarantiFoto(),
                                                    verilerList.get(j).geteMail(), verilerList.get(j).getmTel());

                                            k=0;
                                        }
                                        else k=0;

                                    }


                                }

                                @Override
                                public void onCancelled(DatabaseError error) {
                                }
                            });


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(request);


        urunVeriler.clear();

    }

}