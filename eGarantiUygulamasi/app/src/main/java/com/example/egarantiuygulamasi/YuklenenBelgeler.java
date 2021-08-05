package com.example.egarantiuygulamasi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class YuklenenBelgeler extends Fragment {

    private RecyclerView mRecyclerView;
    ArrayList<Yuklenenler> urunDoldur;
    private YukleAdapter mYukleAdap;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef=database.getReference();

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.activity_yuklenen, container, false);

        mRecyclerView=(RecyclerView) v.findViewById(R.id.recycler_viewYuk);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutManager.scrollToPosition(0);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        urunDoldur=new ArrayList<>();

        verileriListele();

        return v;

    }

    private void verileriListele() {


        myRef.child("yuklenenler").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Yuklenenler  fbaseveri = snapshot.getValue(Yuklenenler.class);

                        urunDoldur.add(
                                new Yuklenenler(

                                        fbaseveri.getYukleUrunKod(),
                                        fbaseveri.getYukleUrunAd(),
                                        fbaseveri.getYukleUrunFiyat(),
                                        fbaseveri.getYukleUrunTarih(),
                                        fbaseveri.getYukleUrunGaranti(),
                                        fbaseveri.getYukleMail()

                                )

                        );

                }

                mYukleAdap = new YukleAdapter(getContext(),urunDoldur);
                mRecyclerView.setAdapter(mYukleAdap);

            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });


    }

}
