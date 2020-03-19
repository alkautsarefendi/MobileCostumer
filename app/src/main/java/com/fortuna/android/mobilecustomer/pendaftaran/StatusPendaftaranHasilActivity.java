package com.fortuna.android.mobilecustomer.pendaftaran;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.fortuna.android.mobilecustomer.R;
import com.fortuna.android.mobilecustomer.adapter.StatusPendaftaraHasilAdapter;
import com.fortuna.android.mobilecustomer.dao.TPendaftaran;

import java.util.List;

/**
 * Created by Stuarez on 11/30/2016.
 */
public class StatusPendaftaranHasilActivity extends AppCompatActivity {

    List<TPendaftaran> listPendaftaran;
    ListView listDaftar;

    private TextView txtNoDaftar, txtNama, txtAlamat;
    String noDaftarPref, namaPref, alamatpref;

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

        setContentView(R.layout.activity_status_pendaftaran_hasil);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        iniComponent();
        getDataFromCheck();
    }


    private void iniComponent() {

        listDaftar = findViewById(R.id.listDaftar);
        txtNoDaftar = findViewById(R.id.txtNoDaftar);
        txtNama = findViewById(R.id.txtNama);
        txtAlamat = findViewById(R.id.txtAlamat);

        listPendaftaran = (List<TPendaftaran>) new TPendaftaran(StatusPendaftaranHasilActivity.this).retrieveAll();
        StatusPendaftaraHasilAdapter pendaftaranadapter = new StatusPendaftaraHasilAdapter(this, listPendaftaran);
        listDaftar.setAdapter(pendaftaranadapter);

    }


    private void getDataFromCheck() {

        SharedPreferences sharedpreferences = getSharedPreferences(StatusPendaftaraHasilAdapter.DAFTARPREFERENCES, Context.MODE_PRIVATE);
        noDaftarPref = sharedpreferences.getString("noDaftarKey", "");
        namaPref = sharedpreferences.getString("NamaPelKey", "");
        alamatpref = sharedpreferences.getString("alamatPelKey", "");

        txtNoDaftar.setText(noDaftarPref);
        txtNama.setText(namaPref);
        txtAlamat.setText(alamatpref);

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.page_slide_left, R.anim.page_slide_right);
    }


    /* Menu */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menuback, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                overridePendingTransition(R.anim.page_slide_left, R.anim.page_slide_right);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
