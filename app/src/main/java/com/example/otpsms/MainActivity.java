package com.example.otpsms;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    String otp,phone;
    String message = "is your verifiction code.";
    Button button, button1;
    EditText etOtp, etPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.sms);
        etOtp = findViewById(R.id.etotp);
        etPhone = findViewById(R.id.etphone);
        button1 = findViewById(R.id.btn1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rand = new Random();

                int number = rand.nextInt(10000)+4;
                etOtp.setText(String.valueOf(number));
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){
                sendOTP();
            }else{
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.SEND_SMS},100);
            }
        }


        });

    }

    @SuppressLint("MissingSuperCall")
    @Override
            public void onRequestPermissionsResult(int requestcode, @NonNull String[] permissions,@NonNull int[] grantResults){
                if (requestcode == 100){
                    if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                        sendOTP();
                    }else{
                        Toast.makeText(this,"Permission Denied!",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            private void sendOTP() {
                otp = etOtp.getText().toString();
                phone = etPhone.getText().toString();

                SmsManager smsManager= SmsManager.getDefault();
                ArrayList<String> parts = smsManager.divideMessage(otp + "" +message);
                String phonenumber = phone;
                smsManager.sendMultipartTextMessage(phonenumber,null,parts,null,null);
            }
            }
