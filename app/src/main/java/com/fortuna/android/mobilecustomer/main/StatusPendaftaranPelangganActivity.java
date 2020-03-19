package com.fortuna.android.mobilecustomer.main;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fortuna.android.mobilecustomer.R;
import com.fortuna.android.mobilecustomer.dao.TPendaftaran;
import com.fortuna.android.mobilecustomer.pendaftaran.StatusPendaftaranHasilActivity;
import com.fortuna.android.mobilecustomer.util.ConnectivityWS;
import com.fortuna.android.mobilecustomer.util.FortunaUtils;
import com.fortuna.android.mobilecustomer.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Stuarez on 11/30/2016.
 */
public class StatusPendaftaranPelangganActivity extends AppCompatActivity {

    private EditText etStatusPendaftaran;
    private String sKodeDaftar;
    Button status;
    private ProgressDialog pDialog;
    String url_status;
    int tagih = 0;
    Bundle bundleAnimation;

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

        setContentView(R.layout.activity_status_pendaftaran_pelanggan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initComponent();
    }

    private void initComponent() {

        etStatusPendaftaran = findViewById(R.id.etStatusPendaftaran);
        status = findViewById(R.id.btnStatusDaftar);
        status.setOnClickListener(new ButtonStatusDaftarListener());

    }


    private class ButtonStatusDaftarListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            final ProgressDialog progress = ProgressDialog.show(StatusPendaftaranPelangganActivity.this,
                    "PDAM DEMO", "Menunggu Hasil...", true, false);

            new Thread(new Runnable() {
                public void run() {
                    loadDaftar();
                    progress.cancel();
                }
            }).start();
        }
    }

    private void loadDaftar() {

        if (etStatusPendaftaran.getText().toString().isEmpty()) {
            Util.showmsg(StatusPendaftaranPelangganActivity.this, "Informasi :", "Nomor Pendaftaran Kosong !");
        } else {
            sKodeDaftar = etStatusPendaftaran.getText().toString();
            url_status = "http://119.2.69.3:7112/pdamcustomerws/pdam/pdammobile?PDAMID=PDAMMobile&KD_DAFTAR=" + sKodeDaftar + "&WEBCMD=InquiryPendaftaran";
            doInquiryPendaftaran();
            Intent tagihan = new Intent(StatusPendaftaranPelangganActivity.this, StatusPendaftaranHasilActivity.class);
            bundleAnimation = ActivityOptions.makeCustomAnimation(StatusPendaftaranPelangganActivity.this, R.anim.page_animation, R.anim.page_animation1).toBundle();
            startActivity(tagihan, bundleAnimation);
            System.out.println("Kode ne" + etStatusPendaftaran);
        }
        /*new PostAsync().execute();*/
        System.out.println(url_status);

    }


    /*class PostAsync extends AsyncTask<String, String, String> {

        ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();

        String pelanggan = etStatusPendaftaran.getText().toString();
        String tes_kode = "0";

        Handler handler = new Handler();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(StatusPendaftaranPelangganActivity.this);
            pDialog.setMessage("Menunggu Hasil...");
            pDialog.setTitle("PDAM Tirta Kerta Raharja");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            handler.postDelayed(new Runnable() {
                public void run() {
                    pDialog.dismiss();
                }
            }, 3000);  // 1000 milliseconds

        }

        @Override
        protected String doInBackground(String... agrs) {

            try {

                doInquiryPendaftaran();

            } catch (Exception e) {

                e.printStackTrace();

            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }

    }*/

    private void doInquiryPendaftaran() {
        TPendaftaran tpendaftaran = new TPendaftaran(StatusPendaftaranPelangganActivity.this);
        tpendaftaran.setKdDaftar(sKodeDaftar);
        JSONObject object = ConnectivityWS.postToServer(tpendaftaran, url_status);
        try {
            if (object != null && object.has("DATA") || (object.has("CODE") && object.getString("CODE").equals("00"))) {
                new TPendaftaran(getApplicationContext()).delete();
                JSONArray jsonArray = object.getJSONArray("DATA");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject juser = jsonArray.getJSONObject(i);
                    Iterator keys = juser.keys();
                    while (keys.hasNext()) {
                        try {
                            String key = ((String) keys.next()).trim();
                            tpendaftaran.getClass().getDeclaredField(FortunaUtils.formatField(key)).set(tpendaftaran, String.valueOf(juser.get(key)));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("Kode Daftar = " + tpendaftaran.getKdDaftar() + " | Alamat Pasang = " + tpendaftaran.getAlmtPasang());
                    tpendaftaran.save();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                overridePendingTransition(R.anim.page_slide_left, R.anim.page_slide_right);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
