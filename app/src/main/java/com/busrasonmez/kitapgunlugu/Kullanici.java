package com.busrasonmez.kitapgunlugu;

import java.io.ByteArrayOutputStream;

public class Kullanici {

    private int kullaniciID;
    private String ad;
    private String soyad;
    private String dtarihi;
    private String meslek;
    private String web_adres;
    private String eposta;
    private String telefon;
    private String sifre;
    private int sehir;
    private int cinsiyet;
    private int egitim_durumu;
    private String sehirtxt;
    private String cinsiyettxt;
    private String egitimtxt;
    private String soz;
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    byte[] profilResmi = byteArrayOutputStream.toByteArray();

    public Kullanici(){

    }

    public Kullanici(String soz, int key){

        if(key == 8){
            this.soz = soz;
        }
        else if(key == 5){
            this.eposta = soz;
        }
        else {
            this.sifre = soz;
        }
    }

    public Kullanici(String ad, String soyad, String dtarihi, String meslek, String web_adres, String eposta, String telefon, String sifre,
                     int cinsiyet, int sehir, int egitim_durumu, String sehirtxt, String cinsiyettxt, String egitimtxt, byte[] profilResmi, String soz) {
        this.ad = ad;
        this.soyad = soyad;
        this.dtarihi = dtarihi;
        this.meslek = meslek;
        this.web_adres = web_adres;
        this.eposta = eposta;
        this.telefon = telefon;
        this.sifre = sifre;
        this.sehir = sehir;
        this.cinsiyet = cinsiyet;
        this.egitim_durumu = egitim_durumu;
        this.sehirtxt = sehirtxt;
        this.cinsiyettxt = cinsiyettxt;
        this.egitimtxt = egitimtxt;
        this.profilResmi = profilResmi;
        this.soz = soz;
    }

    public Kullanici(int kullaniciID, String eposta, String sifre){
        this.kullaniciID = kullaniciID;
        this.eposta = eposta;
        this.sifre = sifre;

    }

    public int getKullaniciId() {
        return kullaniciID;
    }

    public void setKullaniciId(int kullaniciId) {
        this.kullaniciID = kullaniciId;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getDtarihi() {
        return dtarihi;
    }

    public void setDtarihi(String dtarihi) {
        this.dtarihi = dtarihi;
    }

    public String getMeslek() {
        return meslek;
    }

    public void setMeslek(String meslek) {
        this.meslek = meslek;
    }

    public String getWeb_adres() {
        return web_adres;
    }

    public void setWeb_adres(String web_adres) {
        this.web_adres = web_adres;
    }

    public String getEposta() {
        return eposta;
    }

    public void setEposta(String eposta) {
        this.eposta = eposta;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public int getSehir() {
        return sehir;
    }

    public void setSehir(int sehir) {
        this.sehir = sehir;
    }

    public int getCinsiyet() {
        return cinsiyet;
    }

    public void setCinsiyet(int cinsiyet) {
        this.cinsiyet = cinsiyet;
    }

    public int getEgitim_durumu() {
        return egitim_durumu;
    }

    public void setEgitim_durumu(int egitim_durumu) {
        this.egitim_durumu = egitim_durumu;
    }

    public String getSehirtxt() {
        return sehirtxt;
    }

    public void setSehirtxt(String sehirtxt) {
        this.sehirtxt = sehirtxt;
    }

    public String getCinsiyettxt() {
        return cinsiyettxt;
    }

    public void setCinsiyettxt(String cinsiyettxt) {
        this.cinsiyettxt = cinsiyettxt;
    }

    public String getEgitimtxt() {
        return egitimtxt;
    }

    public void setEgitimtxt(String egitimtxt) {
        this.egitimtxt = egitimtxt;
    }

    public byte[] getProfilResmi() {
        return profilResmi;
    }

    public void setProfilResmi(byte[] profilResmi) {
        this.profilResmi = profilResmi;
    }

    public String getSoz() {
        return soz;
    }

    public void setSoz(String soz) {
        this.soz = soz;
    }

}
