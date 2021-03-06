package com.example.egarantiuygulamasi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class TelefonKod extends AppCompatActivity {

    private String verificationid;
    private FirebaseAuth mAuth;
    private TextView tryKod;
    private EditText tell;
    private PhoneAuthProvider.ForceResendingToken mResendToken;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kod);

        mAuth = FirebaseAuth.getInstance();



        tryKod=(TextView) findViewById(R.id.tryKodiste);
        tell = findViewById(R.id.telKod);

        String phonenumber = getIntent().getStringExtra("phonenumber");
        sendVerificationCode(phonenumber);

    //    final String phonenumber2 = phonenumber;



        findViewById(R.id.kodGiris).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = tell.getText().toString().trim();

                if ((code.isEmpty() || code.length() < 6)){

                    tell.setError("Enter code...");
                    tell.requestFocus();
                    return;
                }
                verifyCode(code);

            }
        });

        /*
        tryKod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(TelefonKod.this,"istegi??iniz ba??ar??yle g??nderildi bekleyin...",Toast.LENGTH_SHORT).show();
                sendVerificationCode(phonenumber2);

            }
        });  */



    }





    private void verifyCode(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationid, code);
        signInWithCredential(credential);

    }


    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            Intent intent = new Intent(TelefonKod.this, MainActivity.class);
                            // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        } else {
                            Toast.makeText(TelefonKod.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                });
    }

    private void sendVerificationCode(String number){


        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD, //this or TaskExecutors.MAIN_THREAD
                mCallBack,
                mResendToken
        );

    }



    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationid = s;
            mResendToken = forceResendingToken;

        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {


           String codeOlus = phoneAuthCredential.getSmsCode();

            if (codeOlus != null){

                verifyCode(codeOlus);

            }



        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(TelefonKod.this, e.getMessage(),Toast.LENGTH_LONG).show();

        }

    };



}
