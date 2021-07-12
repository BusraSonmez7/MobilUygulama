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
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

public class ProfilActivity extends AppCompatActivity {

    Toolbar toolbar;
    private EditText ad, soyad, meslek, websitesi, eposta, telefon, sifre,sifretekrar;
    private Spinner sehir, cinsiyet, egitim;
    private ImageView takvim;
    private TextView txttakvim;
    private ImageView profilresmi, profilresmi2;
    public int sehirPosition=0, cinsiyetPosition=0, egitimPosition=0;
    public String sehirtxt, cinsiyettxt, egitimtxt;
    public Button vazgec, guncelle;

    Veritabani veritabani = new Veritabani(this);

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    ArrayList<Kullanici> kullaniciGuncelleme = new ArrayList<>();
    ArrayList<Kullanici> kullaniciOzellikleri = new ArrayList<>();

    private ArrayList<String> sehirarray;
    private ArrayList<String> cinsiyetarray;
    private ArrayList<String> egitimarray;
    private ArrayAdapter<String> adapter;

    boolean dogru=false;
    int dogrusay;
    String[] sehirler;

    Uri resimUri;
    Bitmap resimSecimi;
    int farkliResim = 0;
    boolean duzenle = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        NesneDahilEtme();

        SpinnerVeriEkleme("sehir");
        SpinnerVeriEkleme("cinsiyet");
        SpinnerVeriEkleme("egitim");

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("PROFİL");

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = preferences.edit();

        registerForContextMenu(profilresmi);

        ProfilOzellikleriListele();
    }

    public void NesneDahilEtme(){

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        ad = findViewById(R.id.edtad);
        soyad = findViewById(R.id.edtsoyad);
        meslek = findViewById(R.id.edtmeslek);
        websitesi = findViewById(R.id.edtweb);
        eposta = findViewById(R.id.edteposta);
        telefon = findViewById(R.id.edttelefon);
        sifre = findViewById(R.id.edtsifre);
        sifretekrar=findViewById(R.id.edtsifre2);
        sehir = findViewById(R.id.spinnersehir);
        cinsiyet = findViewById(R.id.spinnercinsiyet);
        egitim = findViewById(R.id.spinneregitim);
        takvim = findViewById(R.id.takvim);
        txttakvim = findViewById(R.id.txttakvim);
        profilresmi = findViewById(R.id.profilResmi);
        profilresmi2 = findViewById(R.id.profilResmi2);
        vazgec = findViewById(R.id.vazgec);
        guncelle = findViewById(R.id.guncelle);
        sehirarray = new ArrayList<>();
        cinsiyetarray = new ArrayList<>();
        egitimarray = new ArrayList<>();
    }

    public void SpinnerVeriEkleme(String key){

        switch (key){
            case "sehir":

                sehirler = SehirEkle();

                for(int i =0; i< sehirler.length;i++){
                    sehirarray.add(sehirler[i]);
                }
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1,sehirarray);
                sehir.setAdapter(adapter);

                sehir.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        sehirPosition = position;
                        sehirtxt = sehir.getSelectedItem().toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                break;
            case "cinsiyet":
                cinsiyetarray.add("Kadın");
                cinsiyetarray.add("Erkek");
                cinsiyetarray.add("Belirtmek istemiyorum");
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1,cinsiyetarray);
                cinsiyet.setAdapter(adapter);

                cinsiyet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        cinsiyetPosition = position;
                        cinsiyettxt = cinsiyet.getSelectedItem().toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                break;
            case "egitim":
                egitimarray.add("-yok-");
                egitimarray.add("İlköğretim");
                egitimarray.add("Ortaöğretim");
                egitimarray.add("Lise");
                egitimarray.add("Üniversite");
                egitimarray.add("Yüksek Lisans");
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1,egitimarray);
                egitim.setAdapter(adapter);

                egitim.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        egitimPosition = position;
                        egitimtxt = egitim.getSelectedItem().toString();
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


    public void ProfilOzellikleriListele(){
        this.kullaniciOzellikleri = (ArrayList<Kullanici>)veritabani.ProfilListele();
        ad.setText(kullaniciOzellikleri.get(0).getAd());
        soyad.setText(kullaniciOzellikleri.get(0).getSoyad());
        txttakvim.setText(kullaniciOzellikleri.get(0).getDtarihi());
        sehir.setSelection(kullaniciOzellikleri.get(0).getSehir(),true);
        cinsiyet.setSelection(kullaniciOzellikleri.get(0).getCinsiyet());
        meslek.setText(kullaniciOzellikleri.get(0).getMeslek());
        egitim.setSelection(kullaniciOzellikleri.get(0).getEgitim_durumu());
        websitesi.setText(kullaniciOzellikleri.get(0).getWeb_adres());
        telefon.setText(kullaniciOzellikleri.get(0).getTelefon());
        byte[] resimbyte = kullaniciOzellikleri.get(0).getProfilResmi();
        Bitmap bitmap = BitmapFactory.decodeByteArray(resimbyte,0,resimbyte.length);
        profilresmi.setImageBitmap(bitmap);
        profilresmi2.setImageBitmap(bitmap);
    }

    public void AktifPasifYapma(int key){
        switch (key){
            case 1:
                profilresmi.setVisibility(View.VISIBLE);
                profilresmi2.setVisibility(View.INVISIBLE);
                ad.setEnabled(true);
                soyad.setEnabled(true);
                takvim.setVisibility(View.VISIBLE);
                meslek.setEnabled(true);
                websitesi.setEnabled(true);
                telefon.setEnabled(true);
                vazgec.setVisibility(View.VISIBLE);
                guncelle.setVisibility(View.VISIBLE);
                break;

            case 2:
                profilresmi.setVisibility(View.INVISIBLE);
                profilresmi2.setVisibility(View.VISIBLE);
                ad.setEnabled(false);
                soyad.setEnabled(false);
                takvim.setEnabled(false);
                meslek.setEnabled(false);
                websitesi.setEnabled(false);
                telefon.setEnabled(false);
                vazgec.setVisibility(View.INVISIBLE);
                guncelle.setVisibility(View.INVISIBLE);
                break;

        }
    }
    public void SifreDegistir(View view){
        editor.putInt("epostasifre",200).apply();

        Intent intent = new Intent(getApplicationContext(),EpostaSifreDegistirme.class);
        intent.putExtra("eposta",kullaniciOzellikleri.get(0).getEposta());
        intent.putExtra("sifre",kullaniciOzellikleri.get(0).getSifre());
        startActivity(intent);
    }

    public void EpostaDegistir(View view){
        editor.putInt("epostasifre",100).apply();
        Intent intent = new Intent(getApplicationContext(),EpostaSifreDegistirme.class);
        intent.putExtra("eposta",kullaniciOzellikleri.get(0).getEposta());
        intent.putExtra("sifre",kullaniciOzellikleri.get(0).getSifre());
        startActivity(intent);
    }

    public void Guncelle(View view){

        AktifPasifYapma(2);

        String ad1 = ad.getText().toString();
        String soyad1 = soyad.getText().toString();
        String dtarihi1 = txttakvim.getText().toString();
        String telefon1 = telefon.getText().toString();
        String meslek1 = meslek.getText().toString();
        String webadres1 = websitesi.getText().toString();
        int sehir1 = sehirPosition;
        int cinsiyet1 = cinsiyetPosition;
        int egitim1 = egitimPosition;
        String sehirtxt1 = sehirtxt;
        String cinsiyettxt1 = cinsiyettxt;
        String egitimtxt1 = egitimtxt;
        String soz = kullaniciOzellikleri.get(0).getSoz();

        boolean adDogru, soyadDogru, epostaDogru, sifreDogru;

        if(!ad1.matches("") && !soyad1.matches("") && !dtarihi1.matches("")){

            adDogru = AdSoyadKontrol(ad1, true);
            soyadDogru = AdSoyadKontrol(soyad1,adDogru);


            if(adDogru && soyadDogru){
                if(farkliResim == 0){

                    if(profilresmi.getDrawable().getConstantState()==getResources().getDrawable(R.drawable.profilekle8).getConstantState()){

                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.profilyok8);
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG,50, outputStream);
                        byte[] byteArray = outputStream.toByteArray();
                        Kullanici kullanici2 = new Kullanici(ad1,soyad1,dtarihi1,meslek1,webadres1,kullaniciOzellikleri.get(0).getEposta(),telefon1,kullaniciOzellikleri.get(0).getSifre(),
                                cinsiyet1,sehir1,egitim1,sehirtxt1,cinsiyettxt1,egitimtxt1,byteArray,soz);
                        veritabani.KullaniciGuncelle(kullanici2);

                    }
                    else {
                        byte[] byteArray = kullaniciOzellikleri.get(0).getProfilResmi();
                        Kullanici kullanici2 = new Kullanici(ad1,soyad1,dtarihi1,meslek1,webadres1,kullaniciOzellikleri.get(0).getEposta(),telefon1,
                                kullaniciOzellikleri.get(0).getSifre(),cinsiyet1,sehir1,egitim1,sehirtxt1,cinsiyettxt1,egitimtxt1,byteArray,soz);
                        veritabani.KullaniciGuncelle(kullanici2);
                    }
                }

                else{
                    Bitmap kucukresim = ResimKucultme(resimSecimi,500);

                    ByteArrayOutputStream outputStream2 = new ByteArrayOutputStream();
                    kucukresim.compress(Bitmap.CompressFormat.PNG,50,outputStream2);
                    byte[] byteArray2 = outputStream2.toByteArray();
                    Kullanici kullanici2 = new Kullanici(ad1,soyad1,dtarihi1,meslek1,webadres1,kullaniciOzellikleri.get(0).getEposta(),telefon1,
                            kullaniciOzellikleri.get(0).getSifre(),cinsiyet1,sehir1,egitim1,sehirtxt1,cinsiyettxt1,egitimtxt1,byteArray2,soz);
                    veritabani.KullaniciGuncelle(kullanici2);
                }

                Toast.makeText(ProfilActivity.this,"Kullanıcı bilgileriniz güncellendi.",
                        Toast.LENGTH_LONG).show();

                Intent intent = new Intent(ProfilActivity.this,ProfilActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
        }
        else{
            HataMesaji(6);
        }

    }
    }

    public void Vazgec(View view){

        Intent intent = new Intent(getApplicationContext(),ProfilActivity.class);
        startActivity(intent);
    }


    public boolean AdSoyadKontrol(String text, boolean dogru){
        if(dogru){
            String karakterler = "QWERTYUIOPĞÜİŞLKJHGFDSAZXCVBNMÖÇığüişçöqwertyuopasdfghjklizxcvbnm";

            int say = 0;

            for(int i=0;i<text.length();i++){
                int index = karakterler.indexOf(text.charAt(i));
                if(index<0){

                }
                else{
                    say++;
                }
            }

            if(say == text.length()){
                dogrusay++;
                return true;
            }
            else{
                HataMesaji(5);
                dogrusay++;
                return false;
            }
        }
        else {
            return false;
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


    public void ProfilResmiSec(View view){
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
                    profilresmi.setImageBitmap(resimSecimi);
                    farkliResim++;
                }
                else{
                    resimSecimi = MediaStore.Images.Media.getBitmap(this.getContentResolver(),resimUri);
                    profilresmi.setImageBitmap(resimSecimi);
                    farkliResim++;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.profil_toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.duzenle:
                    sehir.setSelection(kullaniciOzellikleri.get(0).getSehir(),true);
                cinsiyet.setSelection(kullaniciOzellikleri.get(0).getCinsiyet());
                egitim.setSelection(kullaniciOzellikleri.get(0).getEgitim_durumu());
                AktifPasifYapma(1);

                return true;
            case R.id.sil:

                CikisYap(1);

                return true;
            case R.id.cikis:
                CikisYap(2);
                return true;
            case R.id.anasayfa:
                intent = new Intent(ProfilActivity.this,Anasayfa.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void CikisYap(int key){

        if(key == 1){
            AlertDialog.Builder alert2;
            alert2= new AlertDialog.Builder(ProfilActivity.this);
            alert2.setTitle("Hesap Sil");
            alert2.setMessage("Hesabınızı silmek istediğinize emin misiniz?");
            alert2.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    KullaniciGirisi kullaniciGirisi = new KullaniciGirisi();
                    int kullaniciid = kullaniciGirisi.getKullaniciid();

                    veritabani.KullaniciSil(kullaniciid);

                    editor.putInt("giris",1);
                    editor.commit();

                    editor.putInt("uyarigirisi",4);
                    editor.commit();
                    Intent intent = new Intent(getApplicationContext(),KullaniciGirisi.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    Toast.makeText(ProfilActivity.this,"Hesabınız silindi..",Toast.LENGTH_LONG).show();

                }
            });

            alert2.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alert2.show();
        }

        if(key == 2){
            AlertDialog.Builder alert2;
            alert2= new AlertDialog.Builder(ProfilActivity.this);
            alert2.setTitle("Çıkış");
            alert2.setMessage("Hesabınızdan çıkış yapmak istediğinize emin misiniz?");
            alert2.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    editor.putInt("giris",1);
                    editor.commit();

                    editor.putInt("uyarigirisi",4);
                    editor.commit();
                    Intent intent = new Intent(getApplicationContext(),KullaniciGirisi.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            });

            alert2.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alert2.show();
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
                profilresmi.setImageResource(R.drawable.profilekle8);
                profilresmi2.setImageResource(R.drawable.profilekle8);
                return true;
            default:
                return super.onContextItemSelected(item);

        }
    }

    public void HataMesaji(int id){
        AlertDialog.Builder alert;
        switch (id){
            case 1:
                alert= new AlertDialog.Builder(ProfilActivity.this);
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
                alert= new AlertDialog.Builder(ProfilActivity.this);
                alert.setTitle("Kayıt Tamamlanamadı");
                alert.setMessage("E-posta adresi sadece sayı, harf veya '_' içermelidir.");
                alert.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();
                break;
            case 3:
                alert= new AlertDialog.Builder(ProfilActivity.this);
                alert.setTitle("Kayıt Tamamlanamadı");
                alert.setMessage("Şifreler uyuşmuyor!");
                alert.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();
                break;
            case 4:
                alert= new AlertDialog.Builder(ProfilActivity.this);
                alert.setTitle("Kayıt Tamamlanamadı");
                alert.setMessage("Şifre en az 8 karakterden oluşmalıdır!");
                alert.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();
                break;
            case 5:
                alert= new AlertDialog.Builder(ProfilActivity.this);
                alert.setTitle("Kayıt Tamamlanamadı");
                alert.setMessage("İsim ve Soyisim sadece harf içermelidir!");
                alert.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();
                break;
            case 6:
                alert= new AlertDialog.Builder(ProfilActivity.this);
                alert.setTitle("Kayıt Tamamlanamadı");
                alert.setMessage("Lütfen * ile belirtilen alanları doldurunuz!");
                alert.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();
                break;
            case 7:
                alert= new AlertDialog.Builder(ProfilActivity.this);
                alert.setTitle("Kayıt Tamamlanamadı");
                alert.setMessage("Bu e-posta adresine ait bir hesabınız olduğunu fark ettik.\n" +
                        "Hesabınıza giriş yapabilir veya hatırlamıyorsanız şifrenizi yenileyebilirsiniz.");
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
    public String[] SehirEkle(){
        String[] sehirler ={"Adana","Adıyaman", "Afyon", "Ağrı", "Amasya", "Ankara", "Antalya", "Artvin",
                "Aydın", "Balıkesir","Bilecik", "Bingöl", "Bitlis", "Bolu", "Burdur", "Bursa", "Çanakkale",
                "Çankırı", "Çorum","Denizli","Diyarbakır", "Edirne", "Elazığ", "Erzincan", "Erzurum ", "Eskişehir",
                "Gaziantep", "Giresun","Gümüşhane", "Hakkari", "Hatay", "Isparta", "Mersin", "İstanbul", "İzmir",
                "Kars", "Kastamonu", "Kayseri","Kırklareli", "Kırşehir", "Kocaeli", "Konya", "Kütahya ", "Malatya",
                "Manisa", "Kahramanmaraş", "Mardin", "Muğla", "Muş", "Nevşehir", "Niğde", "Ordu", "Rize", "Sakarya",
                "Samsun", "Siirt", "Sinop", "Sivas", "Tekirdağ", "Tokat", "Trabzon  ", "Tunceli", "Şanlıurfa", "Uşak",
                "Van", "Yozgat", "Zonguldak", "Aksaray", "Bayburt ", "Karaman", "Kırıkkale", "Batman", "Şırnak",
                "Bartın", "Ardahan", "Iğdır", "Yalova", "Karabük ", "Kilis", "Osmaniye ", "Düzce"};

        return sehirler;
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