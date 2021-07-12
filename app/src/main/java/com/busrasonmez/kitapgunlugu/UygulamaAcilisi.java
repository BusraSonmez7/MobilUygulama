package com.busrasonmez.kitapgunlugu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

public class UygulamaAcilisi extends AppCompatActivity {

    Button btnKayitOl, btnUyeGirisi;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uygulama_acilisi);

        btnKayitOl=findViewById(R.id.btn_kayitol1);
        btnUyeGirisi=findViewById(R.id.btn_girisyap);


        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = preferences.edit();

        if (preferences.getInt("giris",0) == 2){
            Intent intent = new Intent(UygulamaAcilisi.this,KullaniciGirisi.class);
            startActivity(intent);
        }
        else if(preferences.getInt("giris",0)==1){

        }
        else {
            editor.putInt("giris",1).apply();
        }
    }

    public void UyeGirisi(View view){
        Intent intent = new Intent(getApplicationContext(),KullaniciGirisi.class);
        startActivity(intent);
    }
    public void KayitOl(View view){
        Intent intent = new Intent(getApplicationContext(),KullaniciKayit.class);
        startActivity(intent);
    }
}