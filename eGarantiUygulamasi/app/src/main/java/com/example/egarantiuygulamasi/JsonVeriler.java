package com.example.egarantiuygulamasi;

public class JsonVeriler {

    private  String eMail;
    private String mImageUrl;
    private String magazaAd;
    private String urunKod;
    private String urunAd;
    private String urunFiyat;
    private String tarih;
    private String garantiSure;
    private String nakKart;
    private String garantiFoto;
    private String mTel;

    public JsonVeriler()
    {

    }

    public JsonVeriler(String mImageUrl,String magazaAd, String urunKod, String urunAd, String urunFiyat, String tarih,String garantiSure,String nakKart,String garantiFoto,String eMail,String mTel) {

        this.eMail =eMail;
        this.mImageUrl = mImageUrl;
        this.magazaAd = magazaAd;
        this.urunKod = urunKod;
        this.urunAd = urunAd;
        this.urunFiyat = urunFiyat;
        this.tarih = tarih;
        this.garantiSure = garantiSure;
        this.nakKart = nakKart;
        this.garantiFoto =garantiFoto;
        this.mTel = mTel;

    }

    public String getmTel() {
        return mTel;
    }

    public void setmTel(String mTel) {
        this.mTel = mTel;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getMagazaAd() {
        return magazaAd;
    }

    public void setMagazaAd(String magazaAd) {
        this.magazaAd = magazaAd;
    }


    public String getUrunKod() {
        return urunKod;
    }

    public void setUrunKod(String urunKod) {
        this.urunKod = urunKod;
    }

    public String getUrunAd() {
        return urunAd;
    }

    public void setUrunAd(String urunAd) {
        this.urunAd = urunAd;
    }

    public String getUrunFiyat() {
        return urunFiyat;
    }

    public void setUrunFiyat(String urunFiyat) {
        this.urunFiyat = urunFiyat;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public String getGarantiSure() {
        return garantiSure;
    }

    public void setGarantiSure(String garantiSure) {
        this.garantiSure = garantiSure;
    }

    public String getNakKart() {
        return nakKart;
    }

    public void setNakKart(String nakKart) {
        this.nakKart = nakKart;
    }

    public String getGarantiFoto() {
        return garantiFoto;
    }

    public void setGarantiFoto(String garantiFoto) {
        this.garantiFoto = garantiFoto;
    }
}
