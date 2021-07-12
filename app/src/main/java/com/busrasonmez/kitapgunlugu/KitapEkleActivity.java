package com.busrasonmez.kitapgunlugu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.Toolbar;

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

public class KitapEkleActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText edtkitap_adi, edt_yazar, edt_ozet, edt_sayfa;
    Spinner spinner_tur, spinner_durum, spinner_puan;
    Bitmap secilen_resim;
    ImageView resimekle, takvim;
    TextView textpuan,textozet, txttakvim,txttakvimMetni;
    Button btnekle1, btnekle2, btnvazgec1, btnvazgec2;

    private static int turPosition, durumPosition, puanPosition;
    private String turtxt,durumtxt,puantxt;

    private ArrayList<String> kitapturu;
    private ArrayList<String> kitapdurumu;
    private ArrayList<String> kitappuani;
    private ArrayAdapter<String> adapter2;

    Veritabani veritabani = new Veritabani(this);

    Uri resimUri;
    Bitmap resimSecimi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitap_ekle);



        NesneDahilEtme();

        registerForContextMenu(resimekle);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("KİTAP EKLE");

        SpinnerVeriEkleme("tur");
        SpinnerVeriEkleme("puan");
        SpinnerVeriEkleme("durum");

    }

    public void NesneDahilEtme(){

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        edtkitap_adi = findViewById(R.id.edtkadi);
        edt_yazar = findViewById(R.id.edtyazar);
        edt_ozet = findViewById(R.id.edtozett);
        spinner_tur = findViewById(R.id.spinnerTuru);
        edt_sayfa = findViewById(R.id.edtsayfa);
        spinner_durum = findViewById(R.id.spinnerdurumu);
        spinner_puan = findViewById(R.id.spinnerpuan);
        resimekle=findViewById(R.id.resimekle2);
        textpuan=findViewById(R.id.txtpuan);
        textozet = findViewById(R.id.txtozet);
        txttakvim = findViewById(R.id.txtokumatarihi2);
        txttakvimMetni = findViewById(R.id.txtokumatarihi);
        takvim = findViewById(R.id.takvim);

        btnekle1 = findViewById(R.id.btnekle);
        btnekle2 = findViewById(R.id.btnekle2);
        btnvazgec1 = findViewById(R.id.btnevazgec);
        btnvazgec2 = findViewById(R.id.btnevazgec2);

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

                        if(durumPosition == 1){
                            btnekle2.setVisibility(View.INVISIBLE);
                            btnekle1.setVisibility(View.VISIBLE);
                            btnvazgec1.setVisibility(View.VISIBLE);
                            btnvazgec2.setVisibility(View.INVISIBLE);
                            txttakvimMetni.setVisibility(View.VISIBLE);
                            txttakvim.setVisibility(View.VISIBLE);
                            txttakvimMetni.setVisibility(View.VISIBLE);
                            takvim.setVisibility(View.VISIBLE);

                            textozet.setVisibility(View.VISIBLE);
                            textpuan.setVisibility(View.VISIBLE);
                            edt_ozet.setVisibility(View.VISIBLE);
                            spinner_puan.setVisibility(View.VISIBLE);
                        }
                        else {
                            btnekle1.setVisibility(View.INVISIBLE);
                            btnekle2.setVisibility(View.VISIBLE);
                            btnvazgec2.setVisibility(View.VISIBLE);
                            btnvazgec1.setVisibility(View.INVISIBLE);
                            txttakvim.setVisibility(View.INVISIBLE);
                            txttakvimMetni.setVisibility(View.INVISIBLE);
                            takvim.setVisibility(View.INVISIBLE);

                            textozet.setVisibility(View.INVISIBLE);
                            textpuan.setVisibility(View.INVISIBLE);
                            edt_ozet.setVisibility(View.INVISIBLE);
                            spinner_puan.setVisibility(View.INVISIBLE);
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

    public void KitapEkle(View view){
        String kitapAdi = edtkitap_adi.getText().toString();
        String yazar = edt_yazar.getText().toString();
        int turu = turPosition;
        String sfsayisi = edt_sayfa.getText().toString();
        int kdurum = durumPosition;
        int puan = puanPosition;
        String ozet = edt_ozet.getText().toString();
        String turutxt = turtxt;
        String kdurumtxt = durumtxt;
        String puantxt = this.puantxt;
        String okumaTarihi = txttakvim.getText().toString();

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

            if(resimekle.getDrawable().getConstantState()==getResources().getDrawable(R.drawable.resim_ekleme_iconu).getConstantState()){
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.kitap_resim_yok);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,50, outputStream);
                byte[] byteArray = outputStream.toByteArray();
                Kutuphane kutuphane = new Kutuphane(kullaniciid,kitapAdi,yazar,turu,sfsayisi,kdurum,puan,ozet,okumaTarihi,turutxt,kdurumtxt,puantxt,byteArray);
                veritabani.KitapEkle(kutuphane);

            }

            else if(resimekle.getDrawable().getConstantState()!=getResources().getDrawable(R.drawable.resim_ekleme_iconu).getConstantState()){
                Bitmap kucukresim = ResimKucultme(resimSecimi,500);

                ByteArrayOutputStream outputStream2 = new ByteArrayOutputStream();
                kucukresim.compress(Bitmap.CompressFormat.PNG,50,outputStream2);
                byte[] byteArray2 = outputStream2.toByteArray();
                Kutuphane kutuphane2 = new Kutuphane(kullaniciid,kitapAdi,yazar,turu,sfsayisi,kdurum,puan,ozet,okumaTarihi,turutxt,kdurumtxt,puantxt,byteArray2);
                veritabani.KitapEkle(kutuphane2);
            }

            Toast.makeText(KitapEkleActivity.this,"Kitap kaydedildi..",
                    Toast.LENGTH_LONG).show();
            Intent intent = new Intent(KitapEkleActivity.this,KutuphaneActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else {
            HataMesaji(2);
        }

    }

    public void Vazgec(View view){
        Intent intent = new Intent(KitapEkleActivity.this,KutuphaneActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
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
                   resimekle.setImageBitmap(resimSecimi);
               }
               else{
                   resimSecimi = MediaStore.Images.Media.getBitmap(this.getContentResolver(),resimUri);
                   resimekle.setImageBitmap(resimSecimi);
               }

           } catch (IOException e) {
               e.printStackTrace();
           }
       }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void KutuphaneyeGit(View view){
        Intent intent = new Intent(this, KutuphaneActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
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
                resimekle.setImageResource(R.drawable.resim_ekleme_iconu);
                return true;
            default:
                return super.onContextItemSelected(item);

        }
    }
    public void HataMesaji(int id){
        AlertDialog.Builder alert;
        switch (id){
            case 1:
                alert= new AlertDialog.Builder(KitapEkleActivity.this);
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
                alert= new AlertDialog.Builder(KitapEkleActivity.this);
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