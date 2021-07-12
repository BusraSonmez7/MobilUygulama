package com.busrasonmez.kitapgunlugu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class AylikHedef extends AppCompatActivity {

    Toolbar toolbar;
    EditText ocak, subat, mart, nisan, mayis, haziran, temmuz, agustos, eylul, ekim, kasim, aralik, edtaylikhedef;
    TextView ocak2, subat2, mart2, nisan2, mayis2, haziran2, temmuz2, agustos2, eylul2, ekim2, kasim2, aralik2;
    Spinner yilspinner;
    String txtyil;
    String secilenyil = "2021";
    int say=-1;

    Veritabani veritabani = new Veritabani(this);

    ArrayList<Ay> hedefListesi = new ArrayList<>();
    ArrayList<KutuphaneListview> kitaplar = new ArrayList<>();


    private ArrayAdapter<String> adapter2;
    private ArrayList<String> yillarArray = new ArrayList<>();

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aylik_hedef);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("AYLIK HEDEFİM");

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = preferences.edit();

        if (preferences.getInt("uyarigirisi",0) == 3){
        }
        else if(preferences.getInt("uyarigirisi",0) != 3){
            AlertDialog.Builder alert= new AlertDialog.Builder(AylikHedef.this);
            alert.setTitle("HATIRLATMA");
            alert.setMessage("Aylık hedefinizi girdikten sonra klavyeden \"İLERİ\" tuşuna basarak tüm aylar için aynı sayıda hedef belirleyebilirsiniz. " +
                    "Ayrıca her ay için özel kitap okuma hedefi de belirleyebilirsiniz.");
            alert.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    editor.putInt("uyarigirisi",3);
                    editor.commit();
                }
            });
            alert.show();
        }
        else {
            editor.putInt("uygulamagirisi",3).apply();
        }


        NesneDahilEtme();

        SpinnerDoldur();

        yilspinner.setSelection(say,true);

        SpinnerTiklandi();

        HedefDegerleri();

    }

    public void NesneDahilEtme(){

        ocak = findViewById(R.id.s22);
        subat = findViewById(R.id.s32);
        mart = findViewById(R.id.s42);
        nisan = findViewById(R.id.s52);
        mayis = findViewById(R.id.s62);
        haziran = findViewById(R.id.s72);
        temmuz = findViewById(R.id.s82);
        agustos = findViewById(R.id.s92);
        eylul = findViewById(R.id.s102);
        ekim = findViewById(R.id.s112);
        kasim = findViewById(R.id.s122);
        aralik = findViewById(R.id.s132);

        edtaylikhedef = findViewById(R.id.edtayhedef);

        ocak2 = findViewById(R.id.s23);
        subat2 = findViewById(R.id.s33);
        mart2 = findViewById(R.id.s43);
        nisan2 = findViewById(R.id.s53);
        mayis2 = findViewById(R.id.s63);
        haziran2 = findViewById(R.id.s73);
        temmuz2 = findViewById(R.id.s83);
        agustos2 = findViewById(R.id.s93);
        eylul2 = findViewById(R.id.s103);
        ekim2 = findViewById(R.id.s113);
        kasim2 = findViewById(R.id.s123);
        aralik2 = findViewById(R.id.s133);

        yilspinner = findViewById(R.id.spinneryil);

    }

    public void Vazgec(View view){
        Intent intent = new Intent(getApplicationContext(),AylikHedef.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void Kaydet(View view){

        EditText[] editTexts = new EditText[]{ocak,subat,mart,nisan,mayis,haziran,temmuz,agustos,eylul,ekim,kasim,aralik};

        ArrayList<Ay> hedefListesi = new ArrayList<>();
        int yil = Integer.parseInt(yilspinner.getSelectedItem().toString());

        hedefListesi = veritabani.HedefListele(yil);

        KullaniciGirisi kullaniciGirisi = new KullaniciGirisi();
        int kullaniciid = kullaniciGirisi.getKullaniciid();


        String[] h1 = new String[12];
        for(int i=0;i<12;i++){
            h1[i] = editTexts[i].getText().toString();
        }
        Ay ay2;

        if(!hedefListesi.isEmpty()){
            ay2 = new Ay(yil,kullaniciid,h1[0],h1[1],h1[2],h1[3],h1[4],h1[5],h1[6],h1[7],h1[8],h1[9],h1[10],h1[11]);
            veritabani.AylikKitapHedefiGuncelleme(ay2);

        }

        else {
            ay2 = new Ay(yil,kullaniciid,h1[0],h1[1],h1[2],h1[3],h1[4],h1[5],h1[6],h1[7],h1[8],h1[9],h1[10],h1[11],"0");
            veritabani.KitapHedefiEkle(ay2);
        }

        Toast.makeText(AylikHedef.this,"Hedefler kaydedildi",Toast.LENGTH_LONG).show();

    }

    public void HedefDegerleri(){

        hedefListesi = veritabani.HedefListele(Integer.parseInt(secilenyil));

        if(!hedefListesi.isEmpty()){

                ocak.setText(hedefListesi.get(0).getOcak());
                subat.setText(hedefListesi.get(0).getSubat());
                mart.setText(hedefListesi.get(0).getMart());
                nisan.setText(hedefListesi.get(0).getNisan());
                mayis.setText(hedefListesi.get(0).getMayis());
                haziran.setText(hedefListesi.get(0).getHaziran());
                temmuz.setText(hedefListesi.get(0).getTemmuz());
                agustos.setText(hedefListesi.get(0).getAgustos());
                eylul.setText(hedefListesi.get(0).getEylul());
                ekim.setText(hedefListesi.get(0).getEkim());
                kasim.setText(hedefListesi.get(0).getKasim());
                aralik.setText(hedefListesi.get(0).getAralik());
        }
        else {
            ocak.setText("0");
            subat.setText("0");
            mart.setText("0");
            nisan.setText("0");
            mayis.setText("0");
            haziran.setText("0");
            temmuz.setText("0");
            agustos.setText("0");
            eylul.setText("0");
            ekim.setText("0");
            kasim.setText("0");
            aralik.setText("0");
        }

        kitaplar = veritabani.OkunanOkunacakKitaplariListele(1);

        ArrayList<String> okunmaTarihleri = new ArrayList<>();
        ArrayList<String> aylar = new ArrayList<>();

        TextView[] views = new TextView[]{ocak2,subat2,mart2,nisan2,mayis2,haziran2,temmuz2,agustos2,eylul2,ekim2,
                kasim2,aralik2};

        if(!kitaplar.isEmpty()){
            for (int i = 0;i<kitaplar.size();i++){
                okunmaTarihleri.add(kitaplar.get(i).getOkumatarihi());
                String[] str = okunmaTarihleri.get(i).split("-");
                if(yilspinner.getSelectedItem().toString().equals(str[2])){
                    aylar.add(str[1]);
                }

            }

            for (int i =0;i<12;i++){
                int okunan = 0;
                if(!aylar.isEmpty()){
                    for(int j = 0;j<aylar.size();j++){
                        if(aylar.get(j).equals(i+1+"")){
                            okunan++;
                        }
                    }
                }

                views[i].setText(String.valueOf(okunan));

                okunan=0;
            }

        }
    }

    public void EdittextDegerGir(View view){
        EditText[] vieww = new EditText[]{ocak,subat,mart,nisan,mayis,haziran,temmuz,agustos,eylul,ekim,kasim,aralik};
        for (int i = 0; i<12;i++)
        if(!edtaylikhedef.getText().toString().equals("")){
            vieww[i].setText(edtaylikhedef.getText().toString());
        }
    }
    public void  SpinnerDoldur(){

        Calendar calendar = Calendar.getInstance();
        int yil = calendar.get(Calendar.YEAR);

        for(int i = 1980;i<yil+1;i++){

            this.yillarArray.add(""+i);
            say++;
        }

        adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1,yillarArray);
        yilspinner.setAdapter(adapter2);



    }

    public void SpinnerTiklandi(){
        yilspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                secilenyil = yilspinner.getSelectedItem().toString();
                HedefDegerleri();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.bilgilendirme_mesaji,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.bilgilendirme:
                AlertDialog.Builder alert= new AlertDialog.Builder(AylikHedef.this);
                alert.setTitle("HATIRLATMA");
                alert.setMessage("Aylık hedefinizi girdikten sonra klavyeden \"İLERİ\" tuşuna basarak tüm aylar için aynı sayıda hedef belirleyebilirsiniz. " +
                        "Ayrıca her ay için özel kitap okuma hedefi de belirleyebilirsiniz.");
                alert.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editor.putInt("uyarigirisi",3);
                        editor.commit();
                    }
                });
                alert.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}