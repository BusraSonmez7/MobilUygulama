package com.busrasonmez.kitapgunlugu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EpostaSifreDegistirme extends AppCompatActivity {

    EditText edteposta, edtsifre;
    TextView txteposta, txtsifre;
    Button btnvazgec, btnonay;

    String sifre1, sifre2, eposta1;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    ArrayList<Kullanici> kullanicilar = new ArrayList<>();

    Veritabani veritabani = new Veritabani(this);

    private String eposta;
    private String sifre;
    private static int btngiris = 0;

    public static int getBtngiris() {
        return btngiris;
    }

    public static void setBtngiris(int btngiris) {
        EpostaSifreDegistirme.btngiris = btngiris;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eposta_sifre_degistirme);

        edteposta=findViewById(R.id.edteposta);
        edtsifre = findViewById(R.id.edtsifre);
        btnonay = findViewById(R.id.giris);
        btnvazgec = findViewById(R.id.vazgec);
        txteposta = findViewById(R.id.textView3);
        txtsifre = findViewById(R.id.textView4);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = preferences.edit();

        editor.putInt("btnonay",0).apply();

    }

    public void Giris(View view){

        final Intent intent = getIntent();
        int giris = preferences.getInt("epostasifre",1);

        KullaniciGirisi kullaniciGirisi = new KullaniciGirisi();
        int kullaniciid = kullaniciGirisi.getKullaniciid();

        kullanicilar = (ArrayList<Kullanici>)veritabani.KullaniciId();

        for(int i=0;i<kullanicilar.size();i++){
            if(kullanicilar.get(i).getKullaniciId()==kullaniciid){
                eposta = kullanicilar.get(i).getEposta();
                sifre = kullanicilar.get(i).getSifre();
                break;
            }
        }


        if(preferences.getInt("btnonay",1) > 0){

            eposta1 = edteposta.getText().toString();
            sifre1 = edteposta.getText().toString();
            sifre2 = edtsifre.getText().toString();

            //Eğer eposta değiştirilmek isteniyorsa ve kullanıcı eposta alanını doldurmuşsa
            if(!eposta1.equals("") && giris == 100){
                if(EpostaKontrol(eposta1)){
                    if(KullaniciKayitKontrol(eposta1)){
                        veritabani.EpostaSifreDegistirme(eposta1,1);

                        Toast.makeText(EpostaSifreDegistirme.this,"Güncellendi",Toast.LENGTH_LONG).show();
                        Intent intent2 = new Intent(this, ProfilActivity.class);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent2);
                        finish();
                    }
                    else {
                        HataMesaji(7);
                    }
                }
                else {
                    HataMesaji(4);
                }
            }
            else {
                if(giris == 100){
                    HataMesaji(2);
                }
            }

            //Eğer şifre değiştirilmek isteniyorsa ve şifre alanları doldurulmuşsa
            if(!sifre1.matches("") && giris == 200){
                if(SifreKontrol(sifre1,sifre2)){
                    veritabani.EpostaSifreDegistirme(sifre1,2);
                    Toast.makeText(EpostaSifreDegistirme.this,"Güncellendi",Toast.LENGTH_LONG).show();
                    Intent intent2 = new Intent(this, ProfilActivity.class);
                    intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent2);
                    finish();
                }
                else {
                    HataMesaji(5);
                }
            }
            else {
                if(giris == 200){
                    HataMesaji(3);
                }
            }
        }

        //ilk giris butonuna basıldığında yapılacaklar
        if(edteposta.getText().toString().equals(eposta) && edtsifre.getText().toString().equals(sifre) && preferences.getInt("btnonay",1) == 0){

            editor.putInt("btnonay",1);
            editor.commit();

            if(giris==100){

                edtsifre.setVisibility(View.INVISIBLE);
                txtsifre.setVisibility(View.INVISIBLE);
                edteposta.setText("");
                btnonay.setText("DEĞİŞTİR");

            }
            else{
                txteposta.setText("Şifre:");
                txtsifre.setText("Şifre tekrarı:");
                edteposta.setText("");
                edtsifre.setText("");
                btnonay.setText("DEĞİŞTİR");

                edteposta.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                edteposta.setTransformationMethod(PasswordTransformationMethod.getInstance());


            }
        }
        else {
            if(preferences.getInt("btnonay",1) == 0){
                HataMesaji(1);
                editor.putInt("btnonay",0);
                editor.commit();
            }
        }

    }

    public boolean KullaniciKayitKontrol(String eposta1){
        int sonuc = 0;
        for(int i = 0; i< kullanicilar.size();i++){
            if(kullanicilar.get(i).getEposta().equals(eposta1)){
                sonuc++;
            }
        }
//
        if(sonuc == 0){
            return true;
        }
        else {
            return false;
        }
    }

    public void Vazgec(View view){
        Intent intent = new Intent(getApplicationContext(),ProfilActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    public boolean EpostaKontrol(String epostak){
            String karakterler = "0123456789.@qwertyuopasdfghjklizxcvbnm";

            int say = 0;
            for(int i=0;i<epostak.length();i++){
                int index = karakterler.indexOf(epostak.charAt(i));
                if(index<0){

                }
                else{
                    say++;
                }
            }

            if(say == epostak.length()){
                return true;
            }
            else{
                return false;
            }

    }

    public boolean SifreKontrol(String sifre, String sifre2){

            if(!sifre.equals(sifre2)){
                HataMesaji(5);
                return false;
            }
            else if(sifre.length()<8){
                HataMesaji(6);
                return false;
            }
            else {
                return true;
            }

    }

    public void HataMesaji(int key){
        AlertDialog.Builder alert;
        switch (key){
            case 1:
                alert= new AlertDialog.Builder(EpostaSifreDegistirme.this);
                alert.setTitle("HATA");
                alert.setMessage("Eposta veya şifre yanlış. Kontrol edip tekrar deneyiniz.");
                alert.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();
                break;
            case 2:
                alert= new AlertDialog.Builder(EpostaSifreDegistirme.this);
                alert.setTitle("HATA");
                alert.setMessage("Lütfen eposta adresi girin");
                alert.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();
                break;
            case 3:
                alert= new AlertDialog.Builder(EpostaSifreDegistirme.this);
                alert.setTitle("HATA");
                alert.setMessage("Lütfen şifre girin");
                alert.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();
                break;
            case 4:
                alert= new AlertDialog.Builder(EpostaSifreDegistirme.this);
                alert.setTitle("Kayıt Tamamlanamadı");
                alert.setMessage("E-posta adresi sadece sayı, harf veya '_' içermelidir.");
                alert.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();
                break;
            case 5:
                alert= new AlertDialog.Builder(EpostaSifreDegistirme.this);
                alert.setTitle("Kayıt Tamamlanamadı");
                alert.setMessage("Şifreler uyuşmuyor!");
                alert.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();
                break;
            case 6:
                alert= new AlertDialog.Builder(EpostaSifreDegistirme.this);
                alert.setTitle("Kayıt Tamamlanamadı");
                alert.setMessage("Şifre en az 8 karakterden oluşmalıdır!");
                alert.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();
                break;
            case 7:
                alert= new AlertDialog.Builder(EpostaSifreDegistirme.this);
                alert.setTitle("Kayıt Tamamlanamadı");
                alert.setMessage("Bu e-posta adresine ait bir hesap bulunmaktadır.");
                alert.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();
                break;
        }
    }

}