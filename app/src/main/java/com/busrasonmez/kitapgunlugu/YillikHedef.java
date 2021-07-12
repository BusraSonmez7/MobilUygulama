package com.busrasonmez.kitapgunlugu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class YillikHedef extends AppCompatActivity {

    Toolbar toolbar;
    EditText edthedef;
    TextView txtokundu, txtsayfa1, txtsayfa2, txtdakika1, txtdakika2, txtyuzde, textviewsene;
    ProgressBar progressBar;
    int progressilerleme = 0;
    Handler handler = new Handler();

    ArrayList<Ay> yillikKitapHedefi = new ArrayList<>();
    ArrayList<Kutuphane> okunanKitapBilgileri = new ArrayList<>();

    Veritabani veritabani = new Veritabani(this);
    int toplamSayfa = 0;
    int okunanKitap = 0;
    int toplamHedef = 0;
    int kullaniciid;
    int yil, gunsayisi;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yillik_hedef);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("YILLIK HEDEFİM");

        NesneDahilEtme();

        VeritabanindenVeriCekme();
        KullaniciGirisi kullaniciGirisi = new KullaniciGirisi();
        kullaniciid = kullaniciGirisi.getKullaniciid();

        new Thread(new Runnable() {
            @Override
            public void run() {
                if(toplamHedef!=0){
                    progressilerleme = (int) ((okunanKitap*100)/toplamHedef);
                    txtyuzde.setText("%"+progressilerleme);

                }
                else {
                    progressilerleme = 0;
                    txtyuzde.setText("%"+progressilerleme);

                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setProgress(progressilerleme);
                    }
                });
            }
        }).start();
    }

    public void NesneDahilEtme(){
        edthedef = findViewById(R.id.edthedef);
        txtokundu = findViewById(R.id.txtokundu);
        txtsayfa1 = findViewById(R.id.txtsayfa1);
        txtsayfa2 = findViewById(R.id.txtsayfa2);
        txtdakika1 = findViewById(R.id.txtdakika1);
        txtdakika2 = findViewById(R.id.txtdakika2);
        progressBar = findViewById(R.id.progressbar1);
        txtyuzde = findViewById(R.id.txtyuzde);
        textviewsene = findViewById(R.id.textviewsene);
    }

    public void VeritabanindenVeriCekme(){

        Calendar calendar = Calendar.getInstance();
        yil = calendar.get(Calendar.YEAR);
        gunsayisi = calendar.get(Calendar.DAY_OF_YEAR);

        textviewsene.setText(yil+"");

        yillikKitapHedefi = veritabani.HedefListele(yil);

        if(!yillikKitapHedefi.isEmpty()){
            if(yillikKitapHedefi.get(0).getYillikHedef().equals("0")){

                toplamHedef += Integer.parseInt(yillikKitapHedefi.get(0).getOcak());
                toplamHedef += Integer.parseInt(yillikKitapHedefi.get(0).getSubat());
                toplamHedef += Integer.parseInt(yillikKitapHedefi.get(0).getMart());
                toplamHedef += Integer.parseInt(yillikKitapHedefi.get(0).getNisan());
                toplamHedef += Integer.parseInt(yillikKitapHedefi.get(0).getMayis());
                toplamHedef += Integer.parseInt(yillikKitapHedefi.get(0).getHaziran());
                toplamHedef += Integer.parseInt(yillikKitapHedefi.get(0).getTemmuz());
                toplamHedef += Integer.parseInt(yillikKitapHedefi.get(0).getAgustos());
                toplamHedef += Integer.parseInt(yillikKitapHedefi.get(0).getEylul());
                toplamHedef += Integer.parseInt(yillikKitapHedefi.get(0).getEkim());
                toplamHedef += Integer.parseInt(yillikKitapHedefi.get(0).getKasim());
                toplamHedef += Integer.parseInt(yillikKitapHedefi.get(0).getAralik());

                edthedef.setText(toplamHedef+"");
            }
            else {

                edthedef.setText(yillikKitapHedefi.get(0).getYillikHedef());
                toplamHedef = Integer.parseInt(edthedef.getText().toString());
            }
        }
        else {
            Ay ay;
            ay = new Ay(yil,kullaniciid,"0","0","0","0","0","0"
                    ,"0","0","0","0","0","0","0");

            veritabani.KitapHedefiEkle(ay);
        }

        okunanKitapBilgileri = veritabani.YillikKitapHedefiRaporBilgileri();

        if(!okunanKitapBilgileri.isEmpty()){
            for(int i = 0; i<okunanKitapBilgileri.size();i++){
                String[] str = okunanKitapBilgileri.get(i).getEklenmeTarihi().split("-");
                if(str[2].equals(yil+"")){
                    toplamSayfa += Integer.parseInt(okunanKitapBilgileri.get(0).getSayfaSayisi());
                }
            }
            okunanKitap = okunanKitapBilgileri.size();
        }
        else {
            toplamSayfa = 0;
            okunanKitap = 0;
        }


        txtokundu.setText(okunanKitap+"");
        txtsayfa1.setText(toplamSayfa+" sf.");
        float sayfa = (float)toplamSayfa/(float)gunsayisi;
        txtsayfa2.setText(String.format("%.1f", sayfa)+" sf.");
        float dakika = (float) (toplamSayfa*1.7);
        txtdakika1.setText(String.format("%.1f", dakika)+" dk.");
        float dakika2 = (float) (sayfa*1.7);
        txtdakika2.setText(String.format("%.1f", dakika2)+" dk.");

    }

    public void Kaydet(View view){
        KullaniciGirisi kullaniciGirisi = new KullaniciGirisi();
        int kullaniciid = kullaniciGirisi.getKullaniciid();
        Ay ay = new Ay(yil,kullaniciid,edthedef.getText().toString());
        veritabani.YillikKitapHedefiGuncelleme(ay);
        Toast.makeText(this,"Yıllık hedefiniz güncellendi",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(),YillikHedef.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}