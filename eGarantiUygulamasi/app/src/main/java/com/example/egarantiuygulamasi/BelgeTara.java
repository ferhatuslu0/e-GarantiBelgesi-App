package com.example.egarantiuygulamasi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.io.IOException;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class BelgeTara extends Fragment {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Button btn;
    ImageView imgResim;
    Bitmap imageBitmap;
    private Button btnKaydet;
    private EditText yukletxtKod;
    private EditText yukletxtAd;
    private EditText yukletxtFiyat;
    private EditText yukletxtTarih;
    private EditText yukletxtGaranti;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myref= database.getReference();



    private void belgeKaydet(String yukleUrunKod,String yukleUrunAd,String yukleUrunFiyat,String yukleUrunTarih,String yukleUrunGaranti,String yukleMail){

        Yuklenenler yeniYukle=new Yuklenenler(yukleUrunKod,yukleUrunAd,yukleUrunFiyat,yukleUrunTarih,yukleUrunGaranti,yukleMail);

        String id=myref.push().getKey();
        myref.child("yuklenenler").child(id).setValue(yeniYukle);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_tara, container, false);

        imgResim=v.findViewById(R.id.imgUpload);
        btnKaydet= v.findViewById(R.id.kaydet);
        yukletxtKod =v.findViewById(R.id.txtyukleUrunKod);
        yukletxtAd =v.findViewById(R.id.txtyukleUrunAd);
        yukletxtFiyat =v.findViewById(R.id.txtyukleUrunFiyat);
        yukletxtTarih =v.findViewById(R.id.txtyukleUrunTarih);
        yukletxtGaranti =v.findViewById(R.id.txtyukleUrunGarantiSure);

        final String mailGetir =Login.tutulacakeMail;

         final BottomSheetDialog bottomSheetDialog =new BottomSheetDialog (
                getActivity(), R.style.BottomSheetDialogTheme
        );
        View bottomSheetView =LayoutInflater.from(getActivity())
                .inflate(
                        R.layout.layout_bottom_sheet,
                        (LinearLayout)v.findViewById(R.id.bottomSheetContainer)
                );
        bottomSheetView.findViewById(R.id.layoutPhotoYukle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new Intent(getActivity(),BelgeTara.class));
                bottomSheetDialog.dismiss();
                dispatchTakePictureIntent();
               // dene.setText("");


            }
        });
        bottomSheetView.findViewById(R.id.layoutManuel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetDialog.dismiss();

            }
        });

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();


        btnKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(yukletxtAd.equals(""))
                {
                    Toast.makeText(getContext(), "Boş geçilmemesi gereken alanlar var !!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Yuklenenler yukle=new Yuklenenler();

                    belgeKaydet(yukletxtKod.getText().toString(),yukletxtAd.getText().toString(),
                            yukletxtFiyat.getText().toString(),
                            yukletxtTarih.getText().toString(),
                            yukletxtGaranti.getText().toString(),yukle.getYukleMail());
                    Toast.makeText(getContext(), "Belge Başarılı bir şekilde kaydedildi.", Toast.LENGTH_SHORT).show();
                    yukletxtAd.setText("");
                    yukletxtFiyat.setText("");
                    yukletxtGaranti.setText("");
                    yukletxtKod.setText("");
                    yukletxtTarih.setText("");
                }



            }
        });

        return v;

    }


    private void dispatchTakePictureIntent() {

       // Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Intent takePictureIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
/*
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imgResim.setImageBitmap(imageBitmap);
        }
*/
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            Uri uri =data.getData();
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri);
                imgResim.setImageBitmap(imageBitmap);
            } catch (IOException ex){
                ex.printStackTrace();
            }

        }

        detectTextFromimage();
    }



    private void detectTextFromimage() {

        if (imageBitmap == null)
        {
            Toast.makeText(getActivity(), "Resim Yok", Toast.LENGTH_SHORT).show();
        }
        else
        {

            FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(imageBitmap);
            FirebaseVisionTextRecognizer firebaseVisionTextRecognizer = FirebaseVision.getInstance().getOnDeviceTextRecognizer();
            firebaseVisionTextRecognizer.processImage(firebaseVisionImage).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                @Override
                public void onSuccess(FirebaseVisionText firebaseVisionText) {
                    displayTextFromImage(firebaseVisionText);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("Error: ",e.getMessage());
                }
            });

        }


    }

    private void displayTextFromImage(FirebaseVisionText firebaseVisionText) {
        String text="";
        int sayac=0;
        List<FirebaseVisionText.TextBlock> blockList = firebaseVisionText.getTextBlocks();
        if (blockList.size() == 0)
        {
            Toast.makeText(getActivity(), "Resim Üzerinde Metin Algılanmadı.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            for (FirebaseVisionText.TextBlock block : blockList)
            {
                text = block.getText();


                if( text.contains("ÜRÜN ADI") || text.contains("URUN ADI") )
                {
                    yukletxtAd.setText(text.substring(10));
                }
                if(text.contains("ÜRÜN KODU") || text.contains("URUN KODU") )
                {
                    yukletxtKod.setText(text.substring(10));
                }
                if(text.contains("TOPLAM FiYAT") || text.contains("TOPLAM FIYAT") || text.contains("TOPLAM FYAT"))
                {
                    yukletxtFiyat.setText(text.substring(12));
                }
                if(text.contains("ALIŞ TARİHİ") || text.contains("ALIS TARIH"))
                {
                    yukletxtTarih.setText(text.substring(12));
                }
                if (text.contains("GARANTİ SÜRESİ") || text.contains("GARANTI SURESI") || text.contains("GARANTİ SURESİ") || text.contains("GARANTI SÜRE"))
                {
                    yukletxtGaranti.setText(text.substring(15));
                }


            }


        }


    }


}