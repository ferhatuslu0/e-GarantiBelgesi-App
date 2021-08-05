package com.example.egarantiuygulamasi;

public class Profil {
    private String email;
    private String sifre;
    private String tel;
    private String adSoyad;
    private int idArttir;

    public Profil(){

    }
    public Profil(int idArttir ,String tel,String adSoyad,String email, String sifre) {


        this.idArttir=idArttir;
        this.tel=tel;
        this.adSoyad=adSoyad;
        this.email = email;
        this.sifre = sifre;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getSifre() {
        return sifre;
    }
    public void setSifre(String sifre) {
        this.sifre = sifre;
    }
    public String getTel() {
        return tel;
    }
    public void setTel(String unvan) {
        this.tel = unvan;
    }
    public String getadSoyad() {
        return adSoyad;
    }
    public void setadSoyad(String adSoyad) {
        this.adSoyad = adSoyad;
    }

    public int getIdArttir() {
        return idArttir;
    }

    public void setIdArttir(int idArttir) {
        this.idArttir = idArttir;
    }


}