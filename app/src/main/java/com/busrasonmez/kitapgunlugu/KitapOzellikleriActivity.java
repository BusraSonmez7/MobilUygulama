package com.busrasonmez.kitapgunlugu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class KitapOzellikleriActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText edtkitap_adi, edt_yazar, edt_ozet, edt_sayfa;
    Spinner spinner_tur, spinner_durum, spinner_puan;
    TextView txttakvim, txttakvimMetni,txtozet,txtpuan;
    Button btnguncelle,btnguncelle2, btnvazgec,btnvazgec2;
    ImageView resimEkle, takvim, resimEkle2;

    int turPosition=0, durumPosition=0, puanPosition=0;
    String turtxt,durumtxt,puantxt;

    Bitmap resimSecimi;
    Uri resimUri;
    int farkliResim = 0;
    boolean duzenle = false;

    Veritabani veritabani;
    ArrayList<Kutuphane> kitapOzellikleri = new ArrayList<>();

    private ArrayList<String> kitapturu;
    private ArrayList<String> kitapdurumu;
    private ArrayList<String> kitappuani;
    private ArrayAdapter<String> adapter2;

    private static int kitapid;

    public static int getKitapid() {
        return kitapid;
    }

    public static void setKitapid(int kitapid) {
        KitapOzellikleriActivity.kitapid = kitapid;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitap_ozellikleri);

        NesneDahilEtme();

        registerForContextMenu(resimEkle);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("KİTAP ÖZELLİKLERİ");

        SpinnerVeriEkleme("tur");
        SpinnerVeriEkleme("puan");
        SpinnerVeriEkleme("durum");

        final Intent intent = getIntent();
        kitapid = intent.getIntExtra("kitapId",1);
        Kutuphane kutuphane = new Kutuphane();
        kutuphane.setKitapId(kitapid);

        veritabani = new Veritabani(this);
        KitapOzellikleriListesi();

    }

    public void NesneDahilEtme(){

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        edtkitap_adi = findViewById(R.id.edtkadi);
        edt_yazar = findViewById(R.id.edtyazar);
        edt_ozet = findViewById(R.id.edtozet2);
        spinner_tur = findViewById(R.id.spinnerTuru);
        edt_sayfa = findViewById(R.id.edtsayfa);
        spinner_durum = findViewById(R.id.spinnerdurumu);
        spinner_puan = findViewById(R.id.spinnerpuan);
        txttakvim = findViewById(R.id.txtokumatarihi2);
        txttakvimMetni = findViewById(R.id.txtokumatarihi);
        takvim = findViewById(R.id.takvim);
        resimEkle = findViewById(R.id.resimekle2);
        btnguncelle = findViewById(R.id.btnguncelle);
        btnguncelle2 = findViewById(R.id.btnguncelle2);
        btnvazgec = findViewById(R.id.btnvazgec);
        btnvazgec2 = findViewById(R.id.btnvazgec2);
        txtozet = findViewById(R.id.txtozet);
        txtpuan=findViewById(R.id.txtpuan);
        resimEkle2 = findViewById(R.id.resimekle3);

        kitapturu =new ArrayList<>();
        kitapdurumu=new ArrayList<>();
        kitappuani = new ArrayList<>();

    }

    public void SpinnerVeriEkleme(String key){

        switch (key){
            case "tur":
                kitapturu.add("Anı");
                kitapturu.add("Roman");
                kitapturu.add("Hikaye");
                kitapturu.add("Gezi");
                kitapturu.add("Şiir");
                kitapturu.add("Biyografi");
                kitapturu.add("Din");
                kitapturu.add("Bilim");
                kitapturu.add("Çocuk");
                adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1,kitapturu);
                spinner_tur.setAdapter(adapter2);

                spinner_tur.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        turPosition = position;
                        turtxt = spinner_tur.getSelectedItem().toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                break;
            case "puan":
                kitappuani.add("-");
                kitappuani.add("1");
                kitappuani.add("2");
                kitappuani.add("3");
                kitappuani.add("4");
                kitappuani.add("5");
                kitappuani.add("6");
                kitappuani.add("7");
                kitappuani.add("8");
                kitappuani.add("9");
                kitappuani.add("10");
                adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1,kitappuani);
                spinner_puan.setAdapter(adapter2);

                spinner_puan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        puanPosition = position;
                        puantxt = spinner_puan.getSelectedItem().toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                break;
            case "durum":
                kitapdurumu.add("Okunmadı");
                kitapdurumu.add("Okundu");
                adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1,kitapdurumu);
                spinner_durum.setAdapter(adapter2);

                spinner_durum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        durumPosition = position;
                        durumtxt = spinner_durum.getSelectedItem().toString();

                        if(durumPosition == 1 && duzenle){
                            AktifPasifYapma(2);
                            AktifPasifYapma(4);
                        }
                        else if(durumPosition == 0 && duzenle){
                            AktifPasifYapma(3);
                            AktifPasifYapma(5);
                        }
                        else {

                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                break;
            default:
                break;
        }
    }

    public void KitapOzellikleriListesi(){

        this.kitapOzellikleri = (ArrayList<Kutuphane>)veritabani.KitapOzellikleriListesi(kitapid);
        edtkitap_adi.setText(kitapOzellikleri.get(0).getKitapAdi());
        edt_yazar.setText(kitapOzellikleri.get(0).getYazari());
        edt_sayfa.setText(kitapOzellikleri.get(0).getSayfaSayisi());
        txttakvim.setText(kitapOzellikleri.get(0).getEklenmeTarihi());
        edt_ozet.setText(kitapOzellikleri.get(0).getOzet());
        spinner_tur.setSelection(kitapOzellikleri.get(0).getTuru(),true);
        spinner_durum.setSelection(kitapOzellikleri.get(0).getKdurumu(),true);
        spinner_puan.setSelection(kitapOzellikleri.get(0).getPuan(),true);

        byte[] resimbyte = kitapOzellikleri.get(0).getResim();
        Bitmap bitmap = BitmapFactory.decodeByteArray(resimbyte,0,resimbyte.length);
        resimEkle.setImageBitmap(bitmap);
        resimEkle2.setImageBitmap(bitmap);

        durumPosition = kitapOzellikleri.get(0).getKdurumu();
        if(durumPosition == 1){
            AktifPasifYapma(2);
        }
        else {
            AktifPasifYapma(3);

        }
    }

    public void AktifPasifYapma(int key){
        switch (key){
            case 1:
                edtkitap_adi.setEnabled(true);
                edt_yazar.setEnabled(true);
                spinner_tur.setEnabled(true);
                spinner_tur.setVisibility(View.VISIBLE);
                edt_sayfa.setEnabled(true);
                spinner_durum.setEnabled(true);
                spinner_durum.setVisibility(View.VISIBLE);
                takvim.setVisibility(View.VISIBLE);
                edt_ozet.setEnabled(true);
                resimEkle.setVisibility(View.VISIBLE);
                resimEkle2.setVisibility(View.INVISIBLE);

                if(durumPosition == 1){
                    AktifPasifYapma(4);
                }
                else {
                    AktifPasifYapma(5);
                }
                break;

            case 2:
                txtpuan.setVisibility(View.VISIBLE);
                spinner_puan.setVisibility(View.VISIBLE);
                edt_ozet.setVisibility(View.VISIBLE);
                txtozet.setVisibility(View.VISIBLE);
                break;

            case 3:
                txtpuan.setVisibility(View.INVISIBLE);
                edt_ozet.setVisibility(View.INVISIBLE);
                txtozet.setVisibility(View.INVISIBLE);
                spinner_puan.setVisibility(View.INVISIBLE);
                break;
            case 4:
                btnguncelle.setVisibility(View.VISIBLE);
                btnguncelle2.setVisibility(View.INVISIBLE);
                btnvazgec.setVisibility(View.VISIBLE);
                btnvazgec2.setVisibility(View.INVISIBLE);
                break;
            case 5:
                btnguncelle2.setVisibility(View.VISIBLE);
                btnguncelle.setVisibility(View.INVISIBLE);
                btnvazgec2.setVisibility(View.VISIBLE);
                btnvazgec.setVisibility(View.INVISIBLE);
                break;
            default:
                break;
        }

    }

    public void KitapGuncelle(View view){

        String kitapAdi = edtkitap_adi.getText().toString();
        String yazar = edt_yazar.getText().toString();
        int turu = turPosition;
        String txtturu = turtxt;
        String sfsayisi = edt_sayfa.getText().toString();
        int kdurum = durumPosition;
        String txtkdurumu = durumtxt;
        int puan = puanPosition;
        String okumaTarihi = txttakvim.getText().toString();
        String ozet = edt_ozet.getText().toString();

        Calendar calendar = Calendar.getInstance();
        int yil = calendar.get(Calendar.YEAR);
        int ay = calendar.get(Calendar.MONTH);
        int gun = calendar.get(Calendar.DATE);
        String eklemetarihi = gun+"-"+ay+"-"+yil;

        KullaniciGirisi kullaniciGirisi = new KullaniciGirisi();
        int kullaniciid = kullaniciGirisi.getKullaniciid();

        if(durumPosition == 0){
            puan = 0;
            puantxt = "-";
            ozet = "";
        }

        if(!kitapAdi.matches("") && !yazar.equals("") && !sfsayisi.matches("")){

            if(durumPosition == 1 && okumaTarihi.matches("")){
                okumaTarihi = eklemetarihi;
            }
            else{

            }
            if(farkliResim == 0){

                if(resimEkle.getDrawable().getConstantState()==getResources().getDrawable(R.drawable.resim_ekleme_iconu).getConstantState()){
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.kitap_resim_yok);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,50, outputStream);
                byte[] byteArray = outputStream.toByteArray();
                Kutuphane kutuphane = new Kutuphane(kullaniciid,kitapAdi,yazar,turu,sfsayisi,kdurum,puan,ozet,okumaTarihi,txtturu,txtkdurumu,puantxt,byteArray);
                veritabani.KitapGuncelle(kitapid,kutuphane);

                }
                else {
                    byte[] byteArray = kitapOzellikleri.get(0).getResim();
                    Kutuphane kutuphane = new Kutuphane(kullaniciid,kitapAdi,yazar,turu,sfsayisi,kdurum,puan,ozet,okumaTarihi,txtturu,txtkdurumu,puantxt,byteArray);
                    veritabani.KitapGuncelle(kitapid,kutuphane);
                }
            }

            else{
                Bitmap kucukresim = ResimKucultme(resimSecimi,300);

                ByteArrayOutputStream outputStream2 = new ByteArrayOutputStream();
                kucukresim.compress(Bitmap.CompressFormat.PNG,50,outputStream2);
                byte[] byteArray2 = outputStream2.toByteArray();
                Kutuphane kutuphane2 = new Kutuphane(kullaniciid,kitapAdi,yazar,turu,sfsayisi,kdurum,puan,ozet,okumaTarihi,txtturu,txtkdurumu,puantxt,byteArray2);
                veritabani.KitapGuncelle(kitapid,kutuphane2);
            }

            Toast.makeText(KitapOzellikleriActivity.this,"Kitap güncellendi..",
                    Toast.LENGTH_LONG).show();
            Intent intent = new Intent(KitapOzellikleriActivity.this,KutuphaneActivity.class);
            startActivity(intent);
        }
        else {
            HataMesaji(2);
        }
    }

    public void ResimEkle(View view){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }
        else {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent,2);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode==1){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent,2);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 2 && resultCode == RESULT_OK && data!=null){
            resimUri = data.getData();
            try {
                if(Build.VERSION.SDK_INT>=28){
                    ImageDecoder.Source source=ImageDecoder.createSource(this.getContentResolver(),resimUri);
                    resimSecimi=ImageDecoder.decodeBitmap(source);
                    resimEkle.setImageBitmap(resimSecimi);
                    farkliResim++;
                }
                else{
                    resimSecimi = MediaStore.Images.Media.getBitmap(this.getContentResolver(),resimUri);
                    resimEkle.setImageBitmap(resimSecimi);
                    farkliResim++;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void Vazgec(View view){
        Intent intent = new Intent(KitapOzellikleriActivity.this,KutuphaneActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.kitap_toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.duzenle:
                spinner_puan.setSelection(kitapOzellikleri.get(0).getPuan());
                spinner_durum.setSelection(kitapOzellikleri.get(0).getKdurumu());
                spinner_tur.setSelection(kitapOzellikleri.get(0).getTuru());
                duzenle=true;
                AktifPasifYapma(1);
                return true;
            case R.id.sil:
                AlertDialog.Builder alert;
                alert= new AlertDialog.Builder(KitapOzellikleriActivity.this);
                alert.setTitle("Sil");
                alert.setMessage("Kitabı kalıcı olarak silmek istediğinize emin misiniz?");
                alert.setPositiveButton("EVET", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        veritabani.KitapSil(kitapid);
                        Intent intent1 = new Intent(getApplicationContext(),KutuphaneActivity.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent1);

                        Toast.makeText(KitapOzellikleriActivity.this,"Kitap silindi",Toast.LENGTH_LONG).show();

                    }
                });

                alert.setNegativeButton("HAYIR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.liste_menu2,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        final  AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.sil2:
                resimEkle.setImageResource(R.drawable.resim_ekleme_iconu);
                return true;
            default:
                return super.onContextItemSelected(item);

        }
    }

    public void Takvim(View view){

        Calendar nowTime = Calendar.getInstance();
        int yil = nowTime.get(Calendar.YEAR);
        int ay = nowTime.get(Calendar.MONTH);
        int gun = nowTime.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if(year<=yil){

                    if(year==yil){
                        if(month<=ay){
                            if(ay==month && gun<dayOfMonth){
                                HataMesaji(1);
                            }
                            else{
                                txttakvim.setVisibility(View.VISIBLE);
                                txttakvim.setText(dayOfMonth+"-"+(month+1)+"-"+year); //txttakvim e seçilen değer basılacak
                            }
                        }
                        else{
                            HataMesaji(1);
                        }


                    }
                    else{
                        txttakvim.setVisibility(View.VISIBLE);
                        txttakvim.setText(dayOfMonth+"-"+(month+1)+"-"+year); //txttakvim e seçilen değer basılacak

                    }

                }
                else{
                    HataMesaji(1);
                }

            }
        },yil,ay,gun); // Takvim üzerinde başlangıçta ne gözükecekse onu girdik.

        datePickerDialog.setTitle("TAKVİM");
        datePickerDialog.show();
    }
    public void HataMesaji(int id){
        AlertDialog.Builder alert;
        switch (id){
            case 1:
                alert= new AlertDialog.Builder(KitapOzellikleriActivity.this);
                alert.setTitle("HATA");
                alert.setMessage("Geçersiz tarih seçimi!");
                alert.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();
                break;
            case 2:
                alert= new AlertDialog.Builder(KitapOzellikleriActivity.this);
                alert.setTitle("Kitap eklenemedi");
                alert.setMessage("Lütfen * ile belirtilen alanları doldurunuz.");
                alert.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();
                break;
            default:
                break;
        }
    }
    public Bitmap ResimKucultme(Bitmap resim, int maxsize){

        int width = resim.getWidth();
        int height = resim.getHeight();

        float bolme = (float)width/(float)height;

        if(bolme > 1){
            width = maxsize;
            height = (int)(width / bolme);
        }
        else {
            height = maxsize;
            width = (int)(height*bolme);
        }

        return Bitmap.createScaledBitmap(resim,width,height,true);

    }
}