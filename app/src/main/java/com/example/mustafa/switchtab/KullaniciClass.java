package com.example.mustafa.switchtab;

import java.util.ArrayList;


public class KullaniciClass {

    private String adSoyad;
    private String kullaniciAdi;
    private String email;
    private String sifre;
    private String profilFotografi;
    private ArrayList<NotClass> notlar;
    private int notIndis;
    private ArrayList<KullaniciClass> arkadasListesi;
    private int arkadasIndis;

    KullaniciClass(){
        adSoyad=null;
        kullaniciAdi=null;
        email=null;
        sifre=null;
        profilFotografi = "Eklenmedi";
        notlar= new ArrayList<>();
        notIndis=-1;
        arkadasListesi = new ArrayList<>();
        arkadasIndis=-1;
    }

    public void setAdSoyad(String adSoyad){
        this.adSoyad=adSoyad;
    }
    public String getAdSoyad(){
        return adSoyad;
    }

    public void setKullaniciAdi(String kullaniciAdi){
        this.kullaniciAdi = kullaniciAdi;
    }
    public String getKullaniciAdi(){
        return kullaniciAdi;
    }

    public void setEmail(String email){
        this.email=email;
    }
    public String getEmail(){
        return email;
    }

    public void setSifre(String sifre){
        this.sifre=sifre;
    }
    public String getSifre(){
        return sifre;
    }

    public void notEkle(NotClass not){
        notlar.add(not);
    }
    public boolean notIndisBul (NotClass not){
        for (int i=0; i<notlar.size();i++){
            if(not==notlar.get(i)){
                notIndis=i;
                return true;
            }
        }
        notIndis=-1;
        return false;
    }
    public boolean notDuzenle(NotClass not){
        if(notIndisBul(not)){
            notlar.set(notIndis,not);
            return true;
        }
        else {
            return false;
        }
    }
    public boolean notSil(NotClass not){
        if(notIndisBul(not)){
            notlar.remove(notIndis);
            return true;
        }
        else{
            return false;
        }
    }
    public int notSayisi(){
        return notlar.size();
    }
    public void notlariTemizle(){
        notlar.clear();
    }
    public ArrayList<NotClass> notlariAl(){
        return notlar;
    }
    public NotClass notuBul(int index){
        return notlar.get(index);
    }

    public void arkadasEkle(KullaniciClass arkadas){
        arkadasListesi.add(arkadas);
    }
    public boolean arkadasIndisBul(KullaniciClass arkadas){
        for(int i=0; i<arkadasListesi.size();i++){
            if(arkadas == arkadasListesi.get(i)){
                arkadasIndis=i;
                return true;
            }
        }
        arkadasIndis=-1;
        return false;
    }
    public void arkadasiSil(int index){
        arkadasListesi.remove(index);
    }
    public void arkadaslariTemizle(){
        arkadasListesi.clear();
    }
    public KullaniciClass arkadasiBul(int index){
        return arkadasListesi.get(index);
    }
    public int arkadasSayisi(){
        return arkadasListesi.size();
    }
    public boolean arkadasMi(String kullaniciAdi){
        for(int i=0;i<arkadasListesi.size();i++){
            if(arkadasListesi.get(i).getKullaniciAdi().equals(kullaniciAdi)){
                return true;
            }
        }
        return false;
    }

    public void setProfilFotografi(String profilFotografi){
        this.profilFotografi = profilFotografi;
    }
    public String getProfilFotografi(){
        return profilFotografi;
    }
}
