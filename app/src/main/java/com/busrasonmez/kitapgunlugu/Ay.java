package com.busrasonmez.kitapgunlugu;

public class Ay {

    private int yil;
    private int kullaniciId;
    private String ocak;
    private String subat;
    private String mart;
    private String nisan;
    private String mayis;
    private String haziran;
    private String temmuz;
    private String agustos;
    private String eylul;
    private String ekim;
    private String kasim;
    private String aralik;
    private String yillikHedef;

    public Ay(int yil, int kullaniciId, String yillikHedef) {
        this.yil = yil;
        this.kullaniciId = kullaniciId;
        this.yillikHedef = yillikHedef;
    }

    public Ay(String ocak, String subat, String mart, String nisan, String mayis, String haziran,
              String temmuz, String agustos, String eylul, String ekim, String kasim, String aralik, String yillikHedef) {
        this.ocak = ocak;
        this.subat = subat;
        this.mart = mart;
        this.nisan = nisan;
        this.mayis = mayis;
        this.haziran = haziran;
        this.temmuz = temmuz;
        this.agustos = agustos;
        this.eylul = eylul;
        this.ekim = ekim;
        this.kasim = kasim;
        this.aralik = aralik;
        this.yillikHedef = yillikHedef;
    }

    public Ay(int yil, int kullaniciId, String ocak, String subat, String mart, String nisan,
              String mayis, String haziran, String temmuz, String agustos, String eylul,
              String ekim, String kasim, String aralik) {
        this.yil = yil;
        this.kullaniciId = kullaniciId;
        this.ocak = ocak;
        this.subat = subat;
        this.mart = mart;
        this.nisan = nisan;
        this.mayis = mayis;
        this.haziran = haziran;
        this.temmuz = temmuz;
        this.agustos = agustos;
        this.eylul = eylul;
        this.ekim = ekim;
        this.kasim = kasim;
        this.aralik = aralik;
    }

    public Ay(int yil, int kullaniciId, String ocak, String subat, String mart, String nisan, String mayis, String haziran,
              String temmuz, String agustos, String eylul, String ekim, String kasim, String aralik, String yillikHedef) {
        this.yil = yil;
        this.kullaniciId = kullaniciId;
        this.ocak = ocak;
        this.subat = subat;
        this.mart = mart;
        this.nisan = nisan;
        this.mayis = mayis;
        this.haziran = haziran;
        this.temmuz = temmuz;
        this.agustos = agustos;
        this.eylul = eylul;
        this.ekim = ekim;
        this.kasim = kasim;
        this.aralik = aralik;
        this.yillikHedef = yillikHedef;
    }

    public int getYil() {
        return yil;
    }

    public void setYil(int yil) {
        this.yil = yil;
    }

    public int getKullaniciId() {
        return kullaniciId;
    }

    public void setKullaniciId(int kullaniciId) {
        this.kullaniciId = kullaniciId;
    }

    public String getOcak() {
        return ocak;
    }

    public void setOcak(String ocak) {
        this.ocak = ocak;
    }

    public String getSubat() {
        return subat;
    }

    public void setSubat(String subat) {
        this.subat = subat;
    }

    public String getMart() {
        return mart;
    }

    public void setMart(String mart) {
        this.mart = mart;
    }

    public String getNisan() {
        return nisan;
    }

    public void setNisan(String nisan) {
        this.nisan = nisan;
    }

    public String getMayis() {
        return mayis;
    }

    public void setMayis(String mayis) {
        this.mayis = mayis;
    }

    public String getHaziran() {
        return haziran;
    }

    public void setHaziran(String haziran) {
        this.haziran = haziran;
    }

    public String getTemmuz() {
        return temmuz;
    }

    public void setTemmuz(String temmuz) {
        this.temmuz = temmuz;
    }

    public String getAgustos() {
        return agustos;
    }

    public void setAgustos(String agustos) {
        this.agustos = agustos;
    }

    public String getEylul() {
        return eylul;
    }

    public void setEylul(String eylul) {
        this.eylul = eylul;
    }

    public String getEkim() {
        return ekim;
    }

    public void setEkim(String ekim) {
        this.ekim = ekim;
    }

    public String getKasim() {
        return kasim;
    }

    public void setKasim(String kasim) {
        this.kasim = kasim;
    }

    public String getAralik() {
        return aralik;
    }

    public void setAralik(String aralik) {
        this.aralik = aralik;
    }

    public String getYillikHedef() {
        return yillikHedef;
    }

    public void setYillikHedef(String yillikHedef) {
        this.yillikHedef = yillikHedef;
    }
}
