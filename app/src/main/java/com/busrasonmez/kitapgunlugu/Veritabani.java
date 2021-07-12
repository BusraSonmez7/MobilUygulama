package com.busrasonmez.kitapgunlugu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Veritabani extends SQLiteOpenHelper {

    Kullanici kullanici = new Kullanici();

    public Veritabani(@Nullable Context context) {
        super(context, "Kitaplik", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS kullanici(kullaniciId INTEGER PRIMARY KEY, ad VARCHAR, soyad VARCHAR, dtarihi VARCHAR, meslek VARCHAR, webAdres VARCHAR, " +
                "egitimDurumu INTEGER, eposta VARCHAR, telefon VARCHAR, sifre VARCHAR, cinsiyet INTEGER, sehir INTEGER, sehirtxt VARCHAR, cinsiyettxt VARCHAR, egitimtxt VARCHAR, " +
                "profilResmi BLOB, soz VARCHAR)");

        db.execSQL("CREATE TABLE IF NOT EXISTS kutuphane(kitapId INTEGER PRIMARY KEY, kullaniciId INTEGER, kitapAdi VARCHAR, yazari VARCHAR, turu INTEGER, sayfaSayisi VARCHAR," +
                "kDurumu INTEGER, puan INTEGER, ozet TEXT, resim BLOB, eklenmeTarihi VARCHAR, turutxt VARCHAR, kdurumutxt VARCHAR,puantxt VARCHAR)");

        db.execSQL("CREATE TABLE IF NOT EXISTS aylikhedef(yil INTEGER PRIMARY KEY, kullaniciId INTEGER, ocak VARCHAR, subat VARCHAR, mart VARCHAR, nisan VARCHAR, " +
                "mayis VARCHAR, haziran VARCHAR, temmuz VARCHAR, agustos VARCHAR, eylul VARCHAR, ekim VARCHAR, kasim VARCHAR, aralik VARCHAR, yillikHedef VARCHAR)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS kullanici");
        this.onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS kutuphane");
        this.onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS aylikhedef");
        this.onCreate(db);

    }

    public void KullaniciEkle(Kullanici kullanici){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ad",kullanici.getAd());
        contentValues.put("soyad",kullanici.getSoyad());
        contentValues.put("dtarihi",kullanici.getDtarihi());
        contentValues.put("meslek",kullanici.getMeslek());
        contentValues.put("webAdres",kullanici.getWeb_adres());
        contentValues.put("egitimDurumu",kullanici.getEgitim_durumu());
        contentValues.put("eposta",kullanici.getEposta());
        contentValues.put("telefon",kullanici.getTelefon());
        contentValues.put("sifre",kullanici.getSifre());
        contentValues.put("cinsiyet",kullanici.getCinsiyet());
        contentValues.put("sehir",kullanici.getSehir());
        contentValues.put("sehirtxt",kullanici.getSehirtxt());
        contentValues.put("cinsiyettxt",kullanici.getCinsiyettxt());
        contentValues.put("egitimtxt",kullanici.getEgitimtxt());
        contentValues.put("profilResmi",kullanici.getProfilResmi());
        contentValues.put("soz",kullanici.getSoz());
        database.insert("kullanici",null,contentValues);
        database.close();
    }

    public void KitapEkle(Kutuphane kutuphane){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("kullaniciId",kutuphane.getKullaniciID());
        contentValues.put("kitapAdi",kutuphane.getKitapAdi());
        contentValues.put("yazari",kutuphane.getYazari());
        contentValues.put("turu",kutuphane.getTuru());
        contentValues.put("sayfaSayisi",kutuphane.getSayfaSayisi());
        contentValues.put("kDurumu",kutuphane.getKdurumu());
        contentValues.put("puan",kutuphane.getPuan());
        contentValues.put("ozet",kutuphane.getOzet());
        contentValues.put("resim",kutuphane.getResim());
        contentValues.put("turutxt",kutuphane.getTurutxt());
        contentValues.put("kdurumutxt",kutuphane.getKdurumutxt());
        contentValues.put("puantxt",kutuphane.getPuantxt());
        contentValues.put("eklenmeTarihi",kutuphane.getEklenmeTarihi());

        database.insert("kutuphane",null,contentValues);
        database.close();
    }

    public void KitapHedefiEkle(Ay ay){

        KullaniciGirisi kullaniciGirisi = new KullaniciGirisi();
        int kullaniciid = kullaniciGirisi.getKullaniciid();

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("kullaniciId",kullaniciid);
        contentValues.put("yil",ay.getYil());
        contentValues.put("ocak",ay.getOcak());
        contentValues.put("subat",ay.getSubat());
        contentValues.put("mart",ay.getMart());
        contentValues.put("nisan",ay.getNisan());
        contentValues.put("mayis",ay.getMayis());
        contentValues.put("haziran",ay.getHaziran());
        contentValues.put("temmuz",ay.getTemmuz());
        contentValues.put("agustos",ay.getAgustos());
        contentValues.put("eylul",ay.getEylul());
        contentValues.put("ekim",ay.getEkim());
        contentValues.put("kasim",ay.getKasim());
        contentValues.put("aralik",ay.getAralik());
        contentValues.put("yillikHedef",ay.getYillikHedef());

        database.insert("aylikhedef",null,contentValues);
        database.close();
    }

    public ArrayList<Kullanici> KullaniciId(){
        ArrayList<Kullanici> kullanicilar = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM kullanici",null);

        int kullaniciIDIx = cursor.getColumnIndex("kullaniciId");
        int emailIx = cursor.getColumnIndex("eposta");
        int sifreIx = cursor.getColumnIndex("sifre");

        if(cursor.moveToFirst()){
            do{
                int kullaniciid = cursor.getInt(kullaniciIDIx);
                String eposta = cursor.getString(emailIx);
                String sifre = cursor.getString(sifreIx);

                kullanicilar.add(new Kullanici(kullaniciid, eposta, sifre));
            }while(cursor.moveToNext());

        }
        return kullanicilar;
    }

    public ArrayList<Ay> HedefListele(int yil) {
        ArrayList<Ay> hedefListesi = new ArrayList<>();
        KullaniciGirisi kullaniciGirisi = new KullaniciGirisi();
        int kullaniciid = kullaniciGirisi.getKullaniciid();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM aylikhedef WHERE kullaniciId = ? AND yil = ?",new String[]{String.valueOf(kullaniciid), String.valueOf(yil)});

        int ocakIx = cursor.getColumnIndex("ocak");
        int subatIx = cursor.getColumnIndex("subat");
        int martIx = cursor.getColumnIndex("mart");
        int nisanIx = cursor.getColumnIndex("nisan");
        int mayisIx = cursor.getColumnIndex("mayis");
        int haziranIx = cursor.getColumnIndex("haziran");
        int temmuzIx = cursor.getColumnIndex("temmuz");
        int agustosIx = cursor.getColumnIndex("agustos");
        int eylulIx = cursor.getColumnIndex("eylul");
        int ekimIx = cursor.getColumnIndex("ekim");
        int kasimIx = cursor.getColumnIndex("kasim");
        int aralikIx = cursor.getColumnIndex("aralik");
        int yillikHedefIx = cursor.getColumnIndex("yillikHedef");

        if (cursor.moveToFirst()) {
            do {
                String ocak = cursor.getString(ocakIx);
                String subat = cursor.getString(subatIx);
                String mart = cursor.getString(martIx);
                String nisan = cursor.getString(nisanIx);
                String mayis = cursor.getString(mayisIx);
                String haziran = cursor.getString(haziranIx);
                String temmuz = cursor.getString(temmuzIx);
                String agustos = cursor.getString(agustosIx);
                String eylul = cursor.getString(eylulIx);
                String ekim = cursor.getString(ekimIx);
                String kasim = cursor.getString(kasimIx);
                String aralik = cursor.getString(aralikIx);
                String yillikHedef = cursor.getString(yillikHedefIx);
                hedefListesi.add(new Ay(ocak,subat,mart,nisan,mayis,haziran,temmuz,agustos,eylul,ekim,kasim,aralik, yillikHedef));
            } while (cursor.moveToNext());
        }
        return hedefListesi;
    }

    public ArrayList<KutuphaneListview> KutuphaneListele(){
        ArrayList<KutuphaneListview> kitaplar = new ArrayList<>();
        KullaniciGirisi kullaniciGirisi = new KullaniciGirisi();

        int kullaniciid = kullaniciGirisi.getKullaniciid();

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM kutuphane WHERE kullaniciId = ?",new String[]{String.valueOf(kullaniciid)});

        int kitapIdIx = cursor.getColumnIndex("kitapId");
        int kitapAdiIx = cursor.getColumnIndex("kitapAdi");
        int yazariIx = cursor.getColumnIndex("yazari");
        int turuIx = cursor.getColumnIndex("turutxt");
        int kdurumuIx = cursor.getColumnIndex("kdurumutxt");
        int resimIx = cursor.getColumnIndex("resim");

        if(cursor.moveToFirst()){
            do {
                int kitapId = cursor.getInt(kitapIdIx);
                String kitapAdi = cursor.getString(kitapAdiIx);
                String yazari = cursor.getString(yazariIx);
                String turu = cursor.getString(turuIx);
                String kdurumu = cursor.getString(kdurumuIx);
                byte[] resim = cursor.getBlob(resimIx);

                kitaplar.add(new KutuphaneListview(kitapId,kitapAdi,yazari,turu,kdurumu,resim));

            }while(cursor.moveToNext());
        }
        return kitaplar;
    }

    public ArrayList<KutuphaneListview> OkunanOkunacakKitaplariListele(int key){
        ArrayList<KutuphaneListview> kitaplar = new ArrayList<>();
        KullaniciGirisi kullaniciGirisi = new KullaniciGirisi();

        int kullaniciid = kullaniciGirisi.getKullaniciid();

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor;
        if(key == 1){
            cursor = database.rawQuery("SELECT * FROM kutuphane WHERE kullaniciId = ? AND kdurumutxt = ?",new String[]{String.valueOf(kullaniciid),"Okundu"});

        }
        else{
            cursor = database.rawQuery("SELECT * FROM kutuphane WHERE kullaniciId = ? AND kdurumutxt = ?",new String[]{String.valueOf(kullaniciid),"Okunmadı"});

        }
        int kitapIdIx = cursor.getColumnIndex("kitapId");
        int kitapAdiIx = cursor.getColumnIndex("kitapAdi");
        int yazariIx = cursor.getColumnIndex("yazari");
        int turuIx = cursor.getColumnIndex("turutxt");
        int kdurumuIx = cursor.getColumnIndex("kdurumutxt");
        int resimIx = cursor.getColumnIndex("resim");
        int okunmaTarihiIx = cursor.getColumnIndex("eklenmeTarihi");

        if(cursor.moveToFirst()){
            do {
                int kitapId = cursor.getInt(kitapIdIx);
                String kitapAdi = cursor.getString(kitapAdiIx);
                String yazari = cursor.getString(yazariIx);
                String turu = cursor.getString(turuIx);
                String kdurumu = cursor.getString(kdurumuIx);
                String okunmaTarihi = cursor.getString(okunmaTarihiIx);
                byte[] resim = cursor.getBlob(resimIx);

                kitaplar.add(new KutuphaneListview(kitapId,kitapAdi,yazari,turu,kdurumu, okunmaTarihi, resim));

            }while(cursor.moveToNext());
        }
        return kitaplar;
    }

    public ArrayList<Kutuphane> KitapOzellikleriListesi(int kitapid){
        ArrayList<Kutuphane> kitapOzellikleri = new ArrayList<>();
        KullaniciGirisi kullaniciGirisi = new KullaniciGirisi();

        int kullaniciid = kullaniciGirisi.getKullaniciid();

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM kutuphane WHERE kullaniciId = ? AND kitapId = ?",new String[]{String.valueOf(kullaniciid),String.valueOf(kitapid)});

        int kitapAdiIx = cursor.getColumnIndex("kitapAdi");
        int yazariIx = cursor.getColumnIndex("yazari");
        int turuIx = cursor.getColumnIndex("turu");
        int sfyayisiIx = cursor.getColumnIndex("sayfaSayisi");
        int kdurumuIx = cursor.getColumnIndex("kDurumu");
        int resimIx = cursor.getColumnIndex("resim");
        int eklenmeTarihiIx = cursor.getColumnIndex("eklenmeTarihi");
        int puanIx = cursor.getColumnIndex("puan");
        int ozetIx = cursor.getColumnIndex("ozet");
        int txtturuIx = cursor.getColumnIndex("turutxt");
        int txtdurumuIx = cursor.getColumnIndex("kdurumutxt");
        int txtpuanIx = cursor.getColumnIndex("puantxt");

        if(cursor.moveToFirst()){
            do {
                String kitapAdi = cursor.getString(kitapAdiIx);
                String yazari = cursor.getString(yazariIx);
                int turu = cursor.getInt(turuIx);
                String sfsayisi = cursor.getString(sfyayisiIx);
                int kdurumu = cursor.getInt(kdurumuIx);
                int puan = cursor.getInt(puanIx);
                String tarih = cursor.getString(eklenmeTarihiIx);
                String ozet = cursor.getString(ozetIx);
                String txtturu = cursor.getString(txtturuIx);
                String txtdurumu = cursor.getString(txtdurumuIx);
                String txtpuan = cursor.getString(txtpuanIx);
                byte[] resim = cursor.getBlob(resimIx);
                kitapOzellikleri.add(new Kutuphane(kullaniciid,kitapAdi,yazari,turu,sfsayisi,kdurumu,puan,ozet,tarih,txtturu,txtdurumu,txtpuan,resim));

            }while(cursor.moveToNext());
        }
        return kitapOzellikleri;
    }

    public ArrayList<Kutuphane> YillikKitapHedefiRaporBilgileri(){
        ArrayList<Kutuphane> kitaplar = new ArrayList<>();
        KullaniciGirisi kullaniciGirisi = new KullaniciGirisi();

        int kullaniciid = kullaniciGirisi.getKullaniciid();

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM kutuphane WHERE kullaniciId = ? AND kdurumutxt = ?",new String[]{String.valueOf(kullaniciid),"Okundu"});

        int sfsayisiIx = cursor.getColumnIndex("sayfaSayisi");
        int kdurumuIx = cursor.getColumnIndex("kDurumu");
        int eklenmeTarihiIx = cursor.getColumnIndex("eklenmeTarihi");
        int txtdurumuIx = cursor.getColumnIndex("kdurumutxt");

        if(cursor.moveToFirst()){
            do {
                String sfsayisi = cursor.getString(sfsayisiIx);
                int kdurumu = cursor.getInt(kdurumuIx);
                String tarih = cursor.getString(eklenmeTarihiIx);
                String txtdurumu = cursor.getString(txtdurumuIx);

                kitaplar.add(new Kutuphane(sfsayisi,kdurumu,tarih,txtdurumu));

            }while(cursor.moveToNext());
        }

        return kitaplar;
    }

    public ArrayList<Kullanici> ProfilListele(){
        ArrayList<Kullanici> kullaniciBilgileri = new ArrayList<>();
        KullaniciGirisi kullaniciGirisi = new KullaniciGirisi();
        int kullaniciid = kullaniciGirisi.getKullaniciid();

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM kullanici WHERE kullaniciId=?", new String[]{String.valueOf(kullaniciid)});

        int adIx = cursor.getColumnIndex("ad");
        int soyadIx = cursor.getColumnIndex("soyad");
        int dtarihiIx = cursor.getColumnIndex("dtarihi");
        int meslekIx = cursor.getColumnIndex("meslek");
        int webIx = cursor.getColumnIndex("webAdres");
        int epostaIx = cursor.getColumnIndex("eposta");
        int telefonIx = cursor.getColumnIndex("telefon");
        int sifreIx = cursor.getColumnIndex("sifre");
        int sehirIx = cursor.getColumnIndex("sehir");
        int cinsiyetIx = cursor.getColumnIndex("cinsiyet");
        int egitimIx = cursor.getColumnIndex("egitimDurumu");
        int sehirtxtIx = cursor.getColumnIndex("sehirtxt");
        int cinsiyettxtIx = cursor.getColumnIndex("cinsiyettxt");
        int egitimtxtIx = cursor.getColumnIndex("egitimtxt");
        int resimIx = cursor.getColumnIndex("profilResmi");
        int sozIx = cursor.getColumnIndex("soz");

        if (cursor.moveToFirst()){
            do {
                String ad = cursor.getString(adIx);
                String soyad = cursor.getString(soyadIx);
                String dtarihi = cursor.getString(dtarihiIx);
                String meslek = cursor.getString(meslekIx);
                String web = cursor.getString(webIx);
                String eposta = cursor.getString(epostaIx);
                String telefon = cursor.getString(telefonIx);
                String sifre = cursor.getString(sifreIx);
                int sehir = cursor.getInt(sehirIx);
                int cinsiyet = cursor.getInt(cinsiyetIx);
                int egitim = cursor.getInt(egitimIx);
                String egitimtxt = cursor.getString(egitimtxtIx);
                String cinsiyettxt = cursor.getString(cinsiyettxtIx);
                String sehirtxt = cursor.getString(sehirtxtIx);
                byte[] resim = cursor.getBlob(resimIx);
                String soz = cursor.getString(sozIx);
                kullaniciBilgileri.add(new Kullanici(ad,soyad,dtarihi,meslek,web,eposta,telefon,sifre,cinsiyet,sehir,egitim,sehirtxt,cinsiyettxt,egitimtxt,resim,soz));
            }while (cursor.moveToNext());
        }
        return kullaniciBilgileri;
    }

    public void KitapSil(int kitapId){

        KullaniciGirisi kullaniciGirisi = new KullaniciGirisi();
        int kullaniciid = kullaniciGirisi.getKullaniciid();

        SQLiteDatabase database = this.getWritableDatabase();
        database.delete("kutuphane","kitapId"+" = ? "+"AND "+"kullaniciID"+" = ?",
                new String[]{String.valueOf(kitapId),String.valueOf(kullaniciid)});
        database.close();
    }

    public void KitaplariSil(){

        KullaniciGirisi kullaniciGirisi = new KullaniciGirisi();
        int kullaniciid = kullaniciGirisi.getKullaniciid();

        SQLiteDatabase database = this.getWritableDatabase();
        database.delete("kutuphane","kullaniciID"+" = ?",
                new String[]{String.valueOf(kullaniciid)});
        database.close();
    }

    public void OkunanOkunacakKitaplariSil(int key){

        KullaniciGirisi kullaniciGirisi = new KullaniciGirisi();
        int kullaniciid = kullaniciGirisi.getKullaniciid();

        SQLiteDatabase database = this.getWritableDatabase();

        if(key == 1){
            database.delete("kutuphane","kdurumutxt"+" = ?"+" AND "+"kullaniciId"+" = ?",
                    new String[]{"Okundu",String.valueOf(kullaniciid)});
        }
        else {
            database.delete("kutuphane","kdurumutxt"+" = ?"+" AND "+"kullaniciId"+" = ?",
                    new String[]{"Okunmadı",String.valueOf(kullaniciid)});
        }

        database.close();
    }

    public int KitapGuncelle(int kitapId, Kutuphane kutuphane){
        KullaniciGirisi kullaniciGirisi = new KullaniciGirisi();
        int kullaniciid = kullaniciGirisi.getKullaniciid();

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("kitapAdi",kutuphane.getKitapAdi());
        contentValues.put("yazari",kutuphane.getYazari());
        contentValues.put("turu",kutuphane.getTuru());
        contentValues.put("sayfaSayisi",kutuphane.getSayfaSayisi());
        contentValues.put("kDurumu",kutuphane.getKdurumu());
        contentValues.put("puan",kutuphane.getPuan());
        contentValues.put("ozet",kutuphane.getOzet());
        contentValues.put("resim",kutuphane.getResim());
        contentValues.put("eklenmeTarihi",kutuphane.getEklenmeTarihi());

        contentValues.put("turutxt",kutuphane.getTurutxt());
        contentValues.put("kdurumutxt",kutuphane.getKdurumutxt());
        contentValues.put("puantxt",kutuphane.getPuantxt());

        int guncellendi = database.update("kutuphane",contentValues,"kitapId"+" = ?"+" AND "+"kullaniciId"+" = ?",
                new String[]{String.valueOf(kitapId),String.valueOf(kullaniciid)});
        database.close();

        return guncellendi;
    }

    public void KullaniciSil(int kullaniciid){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete("kutuphane","kullaniciId"+" = ?",
                new String[]{String.valueOf(kullaniciid)});
        database.delete("kullanici","kullaniciId"+" = ?",
                new String[]{String.valueOf(kullaniciid)});
        database.close();
    }

    public int KullaniciGuncelle(Kullanici kullanici){
        KullaniciGirisi kullaniciGirisi = new KullaniciGirisi();
        int kullaniciid = kullaniciGirisi.getKullaniciid();

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("ad",kullanici.getAd());
        contentValues.put("soyad",kullanici.getSoyad());
        contentValues.put("dtarihi",kullanici.getDtarihi());
        contentValues.put("sehir",kullanici.getSehir());
        contentValues.put("cinsiyet",kullanici.getCinsiyet());
        contentValues.put("egitimDurumu",kullanici.getEgitim_durumu());
        contentValues.put("webAdres",kullanici.getWeb_adres());
        contentValues.put("eposta",kullanici.getEposta());
        contentValues.put("telefon",kullanici.getTelefon());
        contentValues.put("sifre",kullanici.getSifre());
        contentValues.put("sehirtxt",kullanici.getSehirtxt());
        contentValues.put("cinsiyettxt",kullanici.getCinsiyettxt());
        contentValues.put("egitimtxt",kullanici.getEgitimtxt());
        contentValues.put("profilResmi",kullanici.getProfilResmi());

        int guncellendi = database.update("kullanici",contentValues,"kullaniciId"+" = ?",
                new String[]{String.valueOf(kullaniciid)});
        database.close();
        return guncellendi;
    }

    public int SozGuncelle(Kullanici kullanici){
        KullaniciGirisi kullaniciGirisi = new KullaniciGirisi();
        int kullaniciid = kullaniciGirisi.getKullaniciid();

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("soz",kullanici.getSoz());

        int guncellendi = database.update("kullanici",contentValues,"kullaniciId"+" = ?",
                new String[]{String.valueOf(kullaniciid)});
        database.close();
        return guncellendi;
    }

    public ArrayList<String> KayitliSoz(){
        ArrayList<String> sozler = new ArrayList<>();
        KullaniciGirisi kullaniciGirisi = new KullaniciGirisi();
        int kullaniciid = kullaniciGirisi.getKullaniciid();

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM kullanici WHERE kullaniciId=?", new String[]{String.valueOf(kullaniciid)});

        int sozIx = cursor.getColumnIndex("soz");

        if (cursor.moveToFirst()){
            do {
                String soztxt = cursor.getString(sozIx);
                sozler.add(soztxt);
            }while (cursor.moveToNext());
        }
        return sozler;
    }

    public int AylikKitapHedefiGuncelleme(Ay ay){

        KullaniciGirisi kullaniciGirisi = new KullaniciGirisi();
        int kullaniciid = kullaniciGirisi.getKullaniciid();

        int yil = ay.getYil();

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("ocak",ay.getOcak());
        contentValues.put("subat",ay.getSubat());
        contentValues.put("mart",ay.getMart());
        contentValues.put("nisan",ay.getNisan());
        contentValues.put("mayis",ay.getMayis());
        contentValues.put("haziran",ay.getHaziran());
        contentValues.put("temmuz",ay.getTemmuz());
        contentValues.put("agustos",ay.getAgustos());
        contentValues.put("eylul",ay.getEylul());
        contentValues.put("ekim",ay.getEkim());
        contentValues.put("kasim",ay.getKasim());
        contentValues.put("aralik", ay.getAralik());

        int guncellendi = database.update("aylikhedef",contentValues,"kullaniciId"+" = ?"+" AND "+"yil"+" = ?",
                new String[]{String.valueOf(kullaniciid),String.valueOf(yil)});
        database.close();
        return guncellendi;
    }

    public int YillikKitapHedefiGuncelleme(Ay ay){

        KullaniciGirisi kullaniciGirisi = new KullaniciGirisi();
        int kullaniciid = kullaniciGirisi.getKullaniciid();

        int yil = ay.getYil();

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("yillikHedef",ay.getYillikHedef());

        int guncellendi = database.update("aylikhedef",contentValues,"kullaniciId"+" = ?"+" AND "+"yil"+" = ?",
                new String[]{String.valueOf(kullaniciid),String.valueOf(yil)});
        database.close();
        return guncellendi;
    }

    public int EpostaSifreDegistirme(String metin,int key){

        KullaniciGirisi kullaniciGirisi = new KullaniciGirisi();
        int kullaniciid = kullaniciGirisi.getKullaniciid();

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        int guncellendi;
        if(key == 1){
            contentValues.put("eposta",metin);
            guncellendi = database.update("kullanici",contentValues,"kullaniciId "+" = ?",
                    new String[]{String.valueOf(kullaniciid)});
        }
        else {
            contentValues.put("sifre",metin);
            guncellendi = database.update("kullanici",contentValues,"kullaniciId"+" = ?",
                    new String[]{String.valueOf(kullaniciid)});
        }

        database.close();
        return guncellendi;
    }

}
