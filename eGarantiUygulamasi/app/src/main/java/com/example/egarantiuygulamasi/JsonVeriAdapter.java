package com.example.egarantiuygulamasi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class JsonVeriAdapter extends RecyclerView.Adapter<JsonVeriAdapter.ViewHolder> {
    private ArrayList<JsonVeriler> urunVeriler;
     Context mContext;
    private OnItemClickListener mListener;


    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener =  listener;
    }


    public JsonVeriAdapter(Context mContext, ArrayList<JsonVeriler> verilerList) {
        this.urunVeriler = verilerList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { // her bir satır için temsil edilecek arayuz seçilir

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_veriler,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        JsonVeriler currentItem=urunVeriler.get(position);

        String mail = currentItem.geteMail();
        String imageUrl=currentItem.getmImageUrl();
        String mgzAd=currentItem.getMagazaAd();
        String kod=currentItem.getUrunKod();
        String urnAd=currentItem.getUrunAd();
        String urnFiyat=currentItem.getUrunFiyat();
        String alTarih=currentItem.getTarih();
        String grntSure = currentItem.getGarantiSure();
        String garantiresim = currentItem.getGarantiFoto();

        holder.mMail = mail;
        Picasso.with(mContext).load(imageUrl).fit().centerInside().into(holder.mImageView);
       // holder.magazaAdi.setText(mgzAd);
        holder.urunKodu.setText(kod);
        holder.urunAdi.setText(urnAd);
        holder.urunFiyati.setText(urnFiyat);
        holder.alisTarihi.setText(alTarih);
       // holder.garantiSur.setText("" + grntSure);

    }

    @Override
    public int getItemCount() {
        return urunVeriler.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder
    {

       String mMail;
        public ImageView mImageView;
       // TextView magazaAdi;
        TextView urunKodu;
        TextView urunAdi;
        TextView urunFiyati;
        TextView alisTarihi;
       // TextView garantiSur;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageView=itemView.findViewById(R.id.imageUrunResim);
           // magazaAdi =itemView.findViewById(R.id.magazaName);
            urunKodu =itemView.findViewById(R.id.urunKod);
            urunAdi =itemView.findViewById(R.id.urunAd);
            urunFiyati =itemView.findViewById(R.id.urunFiyat);
            alisTarihi =itemView.findViewById(R.id.urunTarih);
           // garantiSur =itemView.findViewById(R.id.garantiTime);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        int position=getAdapterPosition();
                        if(position !=RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });


        }
    }
}
