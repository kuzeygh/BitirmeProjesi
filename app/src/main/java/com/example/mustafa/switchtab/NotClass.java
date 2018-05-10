package com.example.mustafa.switchtab;

import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class NotClass extends TakvimClass{
    private String notBaslik;
    private String notIcerik;
    private String notSahibi;
    private String notHedefi;
    private String notResmi;
    private int notSira;
    private List<Address> adresBilgisi;
    private LatLng adresKoordinat;

    NotClass(){
        notBaslik=null;
        notIcerik=null;
        notSahibi=FirstActivity.autoLogin.getString("username",null);
        notHedefi=null;
        notResmi="Eklenmedi";
        notSira=0;
        adresBilgisi=null;
        adresKoordinat= new LatLng(0,0);
    }

    NotClass(String notBaslik,String notIcerik, String notSahibi, String notHedefi, String notResmi, LatLng adresKoordinat){
        this.notBaslik=notBaslik;
        this.notIcerik=notIcerik;
        this.notSahibi=notSahibi;
        this.notHedefi=notHedefi;
        this.notResmi=notResmi;
        this.adresKoordinat=adresKoordinat;
    }

    public void setNotBaslik(String notBaslik){
        this.notBaslik=notBaslik;
    }
    public String getNotBaslik(){return notBaslik;}

    public void setNotIcerik(String notIcerik){
        this.notIcerik=notIcerik;
    }
    public String getNotIcerik(){return notIcerik;}

    public void setNotSahibi(String notSahibi){
        this.notSahibi=notSahibi;
    }
    public String getNotSahibi(){return notSahibi;}

    public void setNotHedefi(String notHedefi){
        this.notHedefi=notHedefi;
    }
    public String getNotHedefi(){return notHedefi;}

    public void setNotResmi(String notResmi){
        this.notResmi=notResmi;
    }
    public String getNotResmi(){return notResmi;}

    public void setNotSira(int notSira){
        this.notSira=notSira;
    }
    public int getNotSira(){
        return notSira;
    }

    public void setAdresBilgisi(List<Address> adresBilgisi){
        this.adresBilgisi=adresBilgisi;
    }
    public List<Address> getAdresBilgisi(){
        return adresBilgisi;
    }

    public void setAdresKoordinat(LatLng adresKoordinat){
        this.adresKoordinat=adresKoordinat;
    }
    public void setAdresKoordinat(double lat, double lng){
        LatLng latLng = new LatLng(lat,lng);
        adresKoordinat=latLng;
    }
    public LatLng getAdresKoordinat(){
        return adresKoordinat;
    }
    public void adresKoordinatSil(){
        this.adresKoordinat=null;
    }
}
