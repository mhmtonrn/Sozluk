package com.softengine.monarch.sozluk;

/**
 * Created by mehmet on 10.1.2017.
 */

public class Model {
    private String sira;
    private String kategori;
    private String tur;
    private String eng;

    public Model(String sira, String kategori, String tur, String eng) {
        this.sira = sira;
        this.kategori = kategori;
        this.tur = tur;
        this.eng = eng;
    }

    public Model() {
    }

    public String getSira() {
        return sira;
    }

    public void setSira(String sira) {
        this.sira = sira;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getTur() {
        return tur;
    }

    public void setTur(String tur) {
        this.tur = tur;
    }

    public String getEng() {
        return eng;
    }

    public void setEng(String eng) {
        this.eng = eng;
    }
}
