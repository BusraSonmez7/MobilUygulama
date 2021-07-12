package com.busrasonmez.kitapgunlugu;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

public class KutuphaneListview {
    private int kitapId;
    private String kitapAdi;
    private String yazari;
    private String turutxt;
    private String kdurumutxt;
    private String okumatarihi;
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    byte[] resim = byteArrayOutputStream.toByteArray();

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


    public KutuphaneListview(int kitapId, String kitapAdi, String yazari, String turu, String kdurumu, String okumatarihi, byte[] resim) {
        this.kitapId = kitapId;
        this.kitapAdi = kitapAdi;
        this.yazari = yazari;
        this.turutxt = turu;
        this.kdurumutxt = kdurumu;
        this.okumatarihi = okumatarihi;
        this.resim = resim;
    }
    public KutuphaneListview(int kitapId, String kitapAdi, String yazari, String turu, String kdurumu, byte[] resim) {
        this.kitapId = kitapId;
        this.kitapAdi = kitapAdi;
        this.yazari = yazari;
        this.turutxt = turu;
        this.kdurumutxt = kdurumu;
        this.resim = resim;
    }

    public String getOkumatarihi() {
        return okumatarihi;
    }

    public void setOkumatarihi(String okumatarihi) {
        this.okumatarihi = okumatarihi;
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

    public String getTuru() {
        return turutxt;
    }

    public void setTuru(String turu) {
        this.turutxt = turu;
    }

    public String getKdurumu() {
        return kdurumutxt;
    }

    public void setKdurumu(String kdurumu) {
        this.kdurumutxt = kdurumu;
    }

    public byte[] getResim() {
        return resim;
    }

    public void setResim(byte[] resim) {
        this.resim = resim;
    }
}
