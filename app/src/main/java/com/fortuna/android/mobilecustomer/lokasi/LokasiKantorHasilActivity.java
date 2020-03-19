package com.fortuna.android.mobilecustomer.lokasi;

import android.content.pm.PackageManager;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fortuna.android.mobilecustomer.R;
import com.fortuna.android.mobilecustomer.util.Util;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stuarez on 12/2/2016.
 */
public class LokasiKantorHasilActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Spinner locationSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.title_view, null);

        ((TextView) v.findViewById(R.id.titleAB)).setText(this.getTitle());
        getSupportActionBar().setCustomView(v);

        setContentView(R.layout.activity_lokasi_kantor_hasil);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initComponent();
    }

    private void initComponent() {

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        addItemOnSpinner();
        addListenerOnSpinnerItemSelection();

    }


    private void addItemOnSpinner() {
        locationSearch = findViewById(R.id.spinner);
        List<String> list = new ArrayList<String>();
        list.add("Wilayah 1 (Kisamaun)");
        list.add("Wilayah 2 (Pasar Baru)");
        list.add("Wilayah 3 (Perum)");
        list.add("Serpong");
        list.add("Teluk Naga");
        list.add("Tiga Raksa");
        list.add("Rajeg");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSearch.setAdapter(dataAdapter);
    }

    public void addListenerOnSpinnerItemSelection() {
        locationSearch = findViewById(R.id.spinner);
        locationSearch.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    private class CustomOnItemSelectedListener implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            Toast.makeText(parent.getContext(),
                    "PDAM TKR Wilayah / Cabang / IKK : " + parent.getItemAtPosition(pos).toString(),
                    Toast.LENGTH_SHORT).show();
            Spinner locationSearch = findViewById(R.id.spinner);
            String location = locationSearch.getSelectedItem().toString();
            float zoomLevel = (float) 16.0;
            if (location.equals("Wilayah 1 (Kisamaun)")) {
                LatLng wil1 = new LatLng(-6.185986, 106.6313934);
                mMap.addMarker(new MarkerOptions().position(wil1).title("Pusat & Wilayah 1")).showInfoWindow();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(wil1, zoomLevel));
            } else if (location.equals("Wilayah 2 (Pasar Baru)")) {
                LatLng wil2 = new LatLng(-6.1702022, 106.621772);
                mMap.addMarker(new MarkerOptions().position(wil2).title("Wilayah 2")).showInfoWindow();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(wil2, zoomLevel));
            } else if (location.equals("Wilayah 3 (Perum)")) {
                LatLng wil3 = new LatLng(-6.2118074, 106.6152549);
                mMap.addMarker(new MarkerOptions().position(wil3).title("Wilayah 3")).showInfoWindow();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(wil3, zoomLevel));
            } else if (location.equals("Serpong")) {
                LatLng serp = new LatLng(-6.3221583, 106.6632167);
                mMap.addMarker(new MarkerOptions().position(serp).title("Cabang Serpong")).showInfoWindow();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(serp, zoomLevel));
            } else if (location.equals("Teluk Naga")) {
                LatLng teluk = new LatLng(-6.0767389, 106.6454);
                mMap.addMarker(new MarkerOptions().position(teluk).title("Cabang Teluk Naga")).showInfoWindow();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(teluk, zoomLevel));
            } else if (location.equals("Tiga Raksa")) {
                LatLng tiga = new LatLng(-6.2421786, 106.4265571);
                mMap.addMarker(new MarkerOptions().position(tiga).title("Cabang Tiga Raksa")).showInfoWindow();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tiga, zoomLevel));
            } else if (location.equals("Rajeg")) {
                LatLng rajeg = new LatLng(-6.128009, 106.4949277);
                mMap.addMarker(new MarkerOptions().position(rajeg).title("IKK Rajeg")).showInfoWindow();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(rajeg, zoomLevel));
            } else {
                Util.showmsg(LokasiKantorHasilActivity.this, "Informasi :", "Lokasi Tidak Ditemukan");
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }
    }


    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.setMyLocationEnabled(true);

        float zoomLevel = (float) 11.0;
        // Add a marker in PDAM Tirta Kerta Raharja and move the camera
        LatLng wil2 = new LatLng(-6.1702022, 106.621772);
        mMap.addMarker(new MarkerOptions().position(wil2).title("Wilayah 2")).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(wil2));

        LatLng wil3 = new LatLng(-6.2118074, 106.6152549);
        mMap.addMarker(new MarkerOptions().position(wil3).title("Wilayah 3")).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(wil3));

        LatLng serp = new LatLng(-6.3221583, 106.6632167);
        mMap.addMarker(new MarkerOptions().position(serp).title("Cabang Serpong")).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(serp));

        LatLng teluk = new LatLng(-6.0767389, 106.6454);
        mMap.addMarker(new MarkerOptions().position(teluk).title("Cabang Teluk Naga")).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(teluk));

        LatLng tiga = new LatLng(-6.2421786, 106.4265571);
        mMap.addMarker(new MarkerOptions().position(tiga).title("Cabang Tiga Raksa")).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(tiga));

        LatLng rajeg = new LatLng(-6.128009, 106.4949277);
        mMap.addMarker(new MarkerOptions().position(rajeg).title("IKK Rajeg")).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(rajeg));

        LatLng wil1 = new LatLng(-6.185986, 106.6313934);
        mMap.addMarker(new MarkerOptions().position(wil1).title("Pusat & Wilayah 1")).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(wil1, zoomLevel));

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.page_slide_left, R.anim.page_slide_right);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuback, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.page_slide_left, R.anim.page_slide_right);
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
