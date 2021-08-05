package com.example.egarantiuygulamasi;

public class Yuklenenler {
    private String yukleUrunKod;
    private String yukleUrunAd;
    private String yukleUrunFiyat;
    private String yukleUrunTarih;
    private String yukleUrunGaranti;
    private  String yukleMail;

    public Yuklenenler(){

    }

    public Yuklenenler(String yukleUrunKod,String yukleUrunAd,String yukleUrunFiyat,String yukleUrunTarih,String yukleUrunGaranti,String yukleMail){
        this.yukleUrunKod = yukleUrunKod;
        this.yukleUrunAd = yukleUrunAd;
        this.yukleUrunFiyat = yukleUrunFiyat;
        this.yukleUrunTarih = yukleUrunTarih;
        this.yukleUrunGaranti = yukleUrunGaranti;
        this.yukleMail=yukleMail;

    }

    public String getYukleUrunKod() {
        return yukleUrunKod;
    }

    public void setYukleUrunKod(String yukleUrunKod) {
        this.yukleUrunKod = yukleUrunKod;
    }

    public String getYukleUrunAd() {
        return yukleUrunAd;
    }

    public void setYukleUrunAd(String yukleUrunAd) {
        this.yukleUrunAd = yukleUrunAd;
    }

    public String getYukleUrunFiyat() {
        return yukleUrunFiyat;
    }

    public void setYukleUrunFiyat(String yukleUrunFiyat) {
        this.yukleUrunFiyat = yukleUrunFiyat;
    }

    public String getYukleUrunTarih() {
        return yukleUrunTarih;
    }

    public void setYukleUrunTarih(String yukleUrunTarih) {
        this.yukleUrunTarih = yukleUrunTarih;
    }

    public String getYukleUrunGaranti() {
        return yukleUrunGaranti;
    }

    public void setYukleUrunGaranti(String yukleUrunGaranti) {
        this.yukleUrunGaranti = yukleUrunGaranti;
    }

    public String getYukleMail() {
        return yukleMail;
    }

    public void setYukleMail(String yukleMail) {
        this.yukleMail = yukleMail;
    }
}
