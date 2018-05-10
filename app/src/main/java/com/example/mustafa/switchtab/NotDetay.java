package com.example.mustafa.switchtab;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.jar.*;

public class NotDetay extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MapView mapView;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private LatLng anlikKonum,sonKoordinat, isaretliKoordinat;
    private Location sonKonum, hedefKonum;
    private Geocoder adresBilgisi;
    private String isaretliAdresBilgisi;
    private List<Address> adresSorgu;
    private int notIndex;
    private LatLng notTemp;
    TextView notBaslik,notDetay,notHedef,notTarih,notSaat;
    private ImageView resim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_detay);

        anlikKonum=null;
        adresBilgisi=null;
        isaretliKoordinat=null;

        mapView = (MapView) findViewById(R.id.notDetay_mvHarita);
        notIndex = getIntent().getExtras().getInt("notIndex");
        notTemp = FirstActivity.kullanici.notuBul(notIndex).getAdresKoordinat();

        notBaslik = (TextView) findViewById(R.id.notDetay_tvBaslik);
        notDetay = (TextView) findViewById(R.id.notDetay_tvIcerik);
        notHedef = (TextView) findViewById(R.id.notDetay_tvKonum);
        notTarih = (TextView) findViewById(R.id.notDetay_tvTakvim);
        notSaat = (TextView) findViewById(R.id.notDetay_tvSaat);
        resim = (ImageView) findViewById(R.id.notDetay_ivResim);

        if(FirstActivity.kullanici.notuBul(notIndex).getNotResmi().equals("Eklenmedi")){
            resim.setVisibility(View.INVISIBLE);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, 0);
            resim.setLayoutParams(layoutParams);
        }
        else{
            Picasso.get().load(FirstActivity.kullanici.notuBul(notIndex).getNotResmi()).into(resim);
        }

        notBaslik.setText(FirstActivity.kullanici.notuBul(notIndex).getNotBaslik());
        notDetay.setText(FirstActivity.kullanici.notuBul(notIndex).getNotIcerik());

        String tarihFormati=FirstActivity.kullanici.notuBul(notIndex).getGun()+"."+FirstActivity.kullanici.notuBul(notIndex).getAy()+"."+FirstActivity.kullanici.notuBul(notIndex).getYil();
        notTarih.setText(tarihFormati);

        String saatFormati = FirstActivity.kullanici.notuBul(notIndex).getSaat()+":"+FirstActivity.kullanici.notuBul(notIndex).getDakika();
        notSaat.setText(saatFormati);

        String konumString = "Bulunamadı";
        LatLng latLng=FirstActivity.kullanici.notuBul(notIndex).getAdresKoordinat();
        if(latLng.longitude!=0 && latLng.latitude!=0){
            adresBilgisi = new Geocoder(getApplicationContext(), Locale.getDefault());
            try {
                adresSorgu = adresBilgisi.getFromLocation(latLng.latitude,latLng.longitude,1);
                konumString = adresSorgu.get(0).getSubAdminArea()+","+adresSorgu.get(0).getAdminArea();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            konumString="Konum Eklenmedi";
        }
        notHedef.setText(konumString);

        if(mapView!=null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getApplicationContext());
        mMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mMap.clear();

                notKonumunuAl();

                anlikKonum = new LatLng(location.getLatitude(),location.getLongitude());
                Location anlikYer = location;
                hedefKonum = new Location("Hedef Konum");
                hedefKonum.setLatitude(FirstActivity.kullanici.notuBul(notIndex).getAdresKoordinat().latitude);
                hedefKonum.setLongitude(FirstActivity.kullanici.notuBul(notIndex).getAdresKoordinat().longitude);
                int fark = (int) anlikYer.distanceTo(hedefKonum);
                if(fark<50){
                    //Toast.makeText(getApplicationContext(),fark+"m Mesafe var!",Toast.LENGTH_SHORT).show();
                }
                mMap.addMarker(new MarkerOptions()
                        .position(anlikKonum)
                        .title("Buradasınız")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                isaretliKonumGoster();
                //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(anlikKonum,16));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        // İzin Verilmemişse, İzin İstiyor
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,1,locationListener);
            sonKonum = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(sonKonum!=null){
                sonKoordinat = new LatLng(sonKonum.getLatitude(),sonKonum.getLongitude());
                mMap.addMarker(new MarkerOptions()
                        .position(sonKoordinat)
                        .title("Buradasınız")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sonKoordinat,16));

                notKonumunuAl();
            }
        }
    }

    public void konumaDon(View v){
        konumuGoster();
        /*Intent intent = new Intent(this,KonumKontrolServisi.class);
        KonumKontrolServisi.LAT = FirstActivity.kullanici.notuBul(notIndex).getAdresKoordinat().latitude;
        KonumKontrolServisi.LNG = FirstActivity.kullanici.notuBul(notIndex).getAdresKoordinat().longitude;
        //KonumKontrolServisi.context=this;
        startService(intent);
        Toast.makeText(getApplicationContext(),"Servis Başlatıldı!",Toast.LENGTH_SHORT).show();*/
    }

    public void haritaSifirla(View v){
        haritayiYenile();
        konumuGoster();
        notKonumunuAl();
    }

    public void hedefeGit(View v){
        hedefiGoster();
        /*Intent intent = new Intent(this,KonumKontrolServisi.class);
        stopService(intent);
        Toast.makeText(getApplicationContext(),"Servis Durduruldu!",Toast.LENGTH_SHORT).show();*/
    }

    public void geriDon(View v){
        this.finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(grantResults.length>0){
            if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,1,locationListener);
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void haritayiYenile(){
        mMap.clear();

        if(anlikKonum != null){
            mMap.addMarker(new MarkerOptions()
                    .position(anlikKonum)
                    .title("Buradasınız")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        }
        else {
            mMap.addMarker(new MarkerOptions()
                    .position(sonKoordinat)
                    .title("Buradasınız")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        }
        //isaretliKoordinat=null;
        FirstActivity.kullanici.notuBul(notIndex).setAdresKoordinat(notTemp.latitude,notTemp.longitude);
    }
    private void konumuGoster(){
        if(anlikKonum != null){
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(anlikKonum,16));
        }
        else if (sonKoordinat!=null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sonKoordinat,16));
        }
        else{
            Toast.makeText(getApplicationContext(),"Konum Verinize Ulaşamıyoruz",Toast.LENGTH_SHORT).show();
        }
    }
    private void isaretliKonumGoster(){
        if(isaretliKoordinat!=null && isaretliKoordinat.latitude!=0 && isaretliKoordinat.longitude!=0){
            mMap.addMarker(new MarkerOptions().position(isaretliKoordinat).title(isaretliAdresBilgisi));
        }
        else{
            Toast.makeText(this,"Hedef Eklenmedi!",Toast.LENGTH_SHORT).show();
        }
    }
    private void hedefiGoster(){
        if(isaretliKoordinat!=null){
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(isaretliKoordinat,16));
        }
        else{
            Toast.makeText(getApplicationContext(),"Bir Hedef Belirtmediniz",Toast.LENGTH_SHORT).show();
        }
    }
    private void notKonumunuAl(){
        //haritayıYenile();
        adresBilgisi= new Geocoder(getApplicationContext(),Locale.getDefault());
        isaretliAdresBilgisi="";
        isaretliKoordinat=FirstActivity.kullanici.notuBul(notIndex).getAdresKoordinat();
        if(isaretliKoordinat.longitude!=0 && isaretliKoordinat.latitude!=0){
            notTemp=FirstActivity.kullanici.notuBul(notIndex).getAdresKoordinat();
            String konumFormati = "Bulunamadı";

            try {
                adresSorgu = adresBilgisi.getFromLocation(isaretliKoordinat.latitude,isaretliKoordinat.longitude,1);
                if(adresSorgu!=null && adresSorgu.size()>0){
                    isaretliAdresBilgisi= adresSorgu.get(0).getAddressLine(0);
                    konumFormati = adresSorgu.get(0).getSubAdminArea()+","+adresSorgu.get(0).getAdminArea();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            mMap.addMarker(new MarkerOptions().position(isaretliKoordinat).title(isaretliAdresBilgisi));
            notHedef.setText(konumFormati);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
