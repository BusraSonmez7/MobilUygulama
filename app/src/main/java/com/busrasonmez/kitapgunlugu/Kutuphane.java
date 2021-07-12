package com.busrasonmez.kitapgunlugu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class Kutuphane {
    private int kitapId;
    private int kullaniciID;
    private String kitapAdi;
    private String yazari;
    private int turu;
    private String sayfaSayisi;
    private int kdurumu;
    private int puan;
    private String ozet;
    private String eklenmeTarihi;
    private String turutxt;
    private String kdurumutxt;
    private String puantxt;
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    byte[] resim = byteArrayOutputStream.toByteArray();

    public Kutuphane(){

    }

    public Kutuphane(String sayfaSayisi, int kdurumu, String eklenmeTarihi, String kdurumutxt){

        this.sayfaSayisi = sayfaSayisi;
        this.kdurumu = kdurumu;
        this.eklenmeTarihi=eklenmeTarihi;
        this.kdurumutxt=kdurumutxt;

    }

    public Kutuphane(int kullaniciID, String kitapAdi, String yazari, int turu, String sayfaSayisi, int kdurumu,
                     int puan, String ozet, String eklenmeTarihi, String turutxt, String kdurumutxt, String puantxt,
                     byte[] resim) {
        this.kullaniciID = kullaniciID;
        this.kitapAdi = kitapAdi;
        this.yazari = yazari;
        this.turu = turu;
        this.sayfaSayisi = sayfaSayisi;
        this.kdurumu = kdurumu;
        this.puan = puan;
        this.ozet = ozet;
        this.eklenmeTarihi = eklenmeTarihi;
        this.turutxt = turutxt;
        this.kdurumutxt = kdurumutxt;
        this.resim = resim;
        this.puantxt = puantxt ;
    }

    public Kutuphane(int kullaniciID, String kitapAdi, String yazari, int turu, String sayfaSayisi,
                     int kdurumu, int puan, String ozet, byte[] resim, String eklenmeTarihi) {
        this.kullaniciID = kullaniciID;
        this.kitapAdi = kitapAdi;
        this.yazari = yazari;
        this.turu = turu;
        this.sayfaSayisi = sayfaSayisi;
        this.kdurumu = kdurumu;
        this.puan = puan;
        this.ozet = ozet;
        this.resim=resim;
        this.eklenmeTarihi=eklenmeTarihi;
    }

    public int getKitapId() {
        return kitapId;
    }

    public void setKitapId(int kitapId) {
        this.kitapId = kitapId;
    }

    public String getKitapAdi() {
        return kitapAdi;
    }

    public void setKitapAdi(String kitapAdi) {
        this.kitapAdi = kitapAdi;
    }

    public String getYazari() {
        return yazari;
    }

    public void setYazari(String yazari) {
        this.yazari = yazari;
    }

    public int getTuru() {
        return turu;
    }

    public void setTuru(int turu) {
        this.turu = turu;
    }

    public String getSayfaSayisi() {
        return sayfaSayisi;
    }

    public void setSayfaSayisi(String sayfaSayisi) {
        this.sayfaSayisi = sayfaSayisi;
    }

    public int getKdurumu() {
        return kdurumu;
    }

    public void setKdurumu(int kdurumu) {
        this.kdurumu = kdurumu;
    }

    public int getPuan() {
        return puan;
    }

    public void setPuan(int puan) {
        this.puan = puan;
    }

    public String getOzet() {
        return ozet;
    }

    public void setOzet(String ozet) {
        this.ozet = ozet;
    }

    public byte[] getResim() {
        return resim;
    }

    public void setResim(byte[] resim) {
        this.resim = resim;
    }

    public String getEklenmeTarihi() {
        return eklenmeTarihi;
    }

    public void setEklenmeTarihi(String eklenmeTarihi) {
        this.eklenmeTarihi = eklenmeTarihi;
    }

    public int getKullaniciID() {
        return kullaniciID;
    }

    public void setKullaniciID(int kullaniciID) {
        this.kullaniciID = kullaniciID;
    }

    public String getTurutxt() {
        return turutxt;
    }

    public void setTurutxt(String turutxt) {
        this.turutxt = turutxt;
    }

    public String getKdurumutxt() {
        return kdurumutxt;
    }

    public void setKdurumutxt(String kdurumutxt) {
        this.kdurumutxt = kdurumutxt;
    }

    public String getPuantxt() {
        return puantxt;
    }

    public void setPuantxt(String puantxt) {
        this.puantxt = puantxt;
    }
}
