package com.example.egarantiuygulamasi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class YukleAdapter extends RecyclerView.Adapter<YukleAdapter.ViewHolder>  {

    private ArrayList<Yuklenenler> yukUrunVeriler;
    Context mContext;


    public YukleAdapter(Context mContext, ArrayList<Yuklenenler> yukUrunVeriler) {
        this.yukUrunVeriler = yukUrunVeriler;
        this.mContext = mContext;
    }



    @NonNull
    @Override
    public YukleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_yuklenenveriler,parent,false);

        return new YukleAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull YukleAdapter.ViewHolder holder, int position) {

        Yuklenenler currentItem=yukUrunVeriler.get(position);



        String yukkod=currentItem.getYukleUrunKod();
        String yukurnAd=currentItem.getYukleUrunAd();
        String yukurnFiyat=currentItem.getYukleUrunFiyat();
        String yukalTarih=currentItem.getYukleUrunTarih();
        String yukgrntSure = currentItem.getYukleUrunGaranti();



        holder.urunKodu.setText(yukkod);
        holder.urunAdi.setText(yukurnAd);
        holder.urunFiyati.setText(yukurnFiyat);
        holder.alisTarihi.setText(yukalTarih);
        holder.garantiSur.setText(yukgrntSure);

    }

    @Override
    public int getItemCount() {
        return yukUrunVeriler.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {


        TextView urunKodu;
        TextView urunAdi;
        TextView urunFiyati;
        TextView alisTarihi;
        TextView garantiSur;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            urunKodu =itemView.findViewById(R.id.yukUrunKod);
            urunAdi =itemView.findViewById(R.id.yukUrunAd);
            urunFiyati =itemView.findViewById(R.id.yukUrunFiyat);
            alisTarihi =itemView.findViewById(R.id.yukUrunTarih);
             garantiSur =itemView.findViewById(R.id.yukUrunGaranti);



        }
    }
}
