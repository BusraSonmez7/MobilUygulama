package com.busrasonmez.kitapgunlugu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class KullaniciGirisi extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    Toolbar toolbar;
    private EditText edtEmail, edtSifre;
    private String email, sifre;
    Veritabani veritabani = new Veritabani(this);
    ArrayList<Kullanici> kullaniciListesi = new ArrayList<>();

    private static int kullaniciid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kullanici_girisi);

        NesneDahilEtme();

        toolbar.setTitle("ÜYE GİRİŞİ");

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = preferences.edit();

        String giris = String.valueOf(preferences.getInt("giris",0));

        if(giris.equals("2")){
            kullaniciid = preferences.getInt("kullaniciid",0);
            Intent intent = new Intent(this, Anasayfa.class);
            startActivity(intent);
            finish();
            Toast.makeText(KullaniciGirisi.this,"Hoşgeldin",Toast.LENGTH_LONG).show();
        }
    }

    public void NesneDahilEtme(){
        edtEmail = findViewById(R.id.edtEposta);
        edtSifre = findViewById(R.id.edtSifre);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
    }

    public static int getKullaniciid() {
        return kullaniciid;
    }

    public static void setKullaniciid(int kullaniciId) {
        KullaniciGirisi.kullaniciid = kullaniciId;
    }

    public void GirisYap(View view){
        email = edtEmail.getText().toString();
        sifre = edtSifre.getText().toString();

        kullaniciListesi = (ArrayList<Kullanici>)veritabani.KullaniciId();

        int sonuc =0;

        for (int i = 0; i<kullaniciListesi.size();i++){
            if (kullaniciListesi.get(i).getEposta().equals(email) && kullaniciListesi.get(i).getSifre().equals(sifre)){
                kullaniciid = kullaniciListesi.get(i).getKullaniciId();

                editor.putInt("giris",2);
                editor.commit();
                editor.putInt("kullaniciid",kullaniciid);
                editor.commit();

                Toast.makeText(KullaniciGirisi.this,"Giriş başarılı",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, Anasayfa.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                sonuc++;
                finish();
                break;
            }
        }

        if (sonuc==0){
            AlertDialog.Builder alert = new AlertDialog.Builder(KullaniciGirisi.this);
            alert.setTitle("Giriş yapılamadı");
            alert.setMessage("Eposta veya şifre yanlış! Lütfen bilgilerinizi kontrol ederek tekrar deneyiniz");
            alert.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alert.show();
        }

    }
    public void UyeOl(View view){
        Intent intent = new Intent(this, KullaniciKayit.class);
        startActivity(intent);
    }


}