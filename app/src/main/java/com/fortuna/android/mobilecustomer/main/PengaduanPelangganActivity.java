package com.fortuna.android.mobilecustomer.main;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fortuna.android.mobilecustomer.R;
import com.fortuna.android.mobilecustomer.json.JSONParser;
import com.fortuna.android.mobilecustomer.util.Util;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Stuarez on 11/30/2016.
 */
public class PengaduanPelangganActivity extends AppCompatActivity {

    private EditText input_name, input_idpel, input_alamat, input_telepon, input_ponsel, input_message;
    Button btnPengaduan;

    private ProgressDialog pDialog;
    private JSONParser jsonParser = new JSONParser();
    private static final String PENGADUAN_URL = "http://119.2.69.3:1337/pengaduan/pengaduan.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

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

        setContentView(R.layout.activity_pengaduan_pelanggan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initComponent();
    }

    private void initComponent() {

        input_name = findViewById(R.id.input_name);
        input_idpel = findViewById(R.id.input_idpel);
        input_alamat = findViewById(R.id.input_alamat);
        input_telepon = findViewById(R.id.input_telepon);
        input_ponsel = findViewById(R.id.input_ponsel);
        input_message = findViewById(R.id.input_message);

        btnPengaduan = findViewById(R.id.btnPengaduan);
        btnPengaduan.setOnClickListener(new ButtonPengaduanClickHandler());
    }


    private class ButtonPengaduanClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            if (isOnline()) {

                new kirimPesan().execute();

            }

        }
    }


    class kirimPesan extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */

        String tNama = input_name.getText().toString();
        String tIdPel = input_idpel.getText().toString();
        String tAlamat = input_alamat.getText().toString();
        String tTelepon = input_telepon.getText().toString();
        String tPonsel = input_ponsel.getText().toString();
        String tPesan = input_message.getText().toString();
        @SuppressLint("SimpleDateFormat")
        String tTgl = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS").format(new Date());
        String tStatus = "0";

        int success;
        Handler handler = new Handler();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PengaduanPelangganActivity.this);
            pDialog.setMessage("Mengirim Pesan...");
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
        protected String doInBackground(String... args) {

            try {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("nama_pel", tNama));
                params.add(new BasicNameValuePair("id_pel", tIdPel));
                params.add(new BasicNameValuePair("alamat", tAlamat));
                params.add(new BasicNameValuePair("telepon", tTelepon));
                params.add(new BasicNameValuePair("ponsel", tPonsel));
                params.add(new BasicNameValuePair("tgl", tTgl));
                params.add(new BasicNameValuePair("pesan", tPesan));
                params.add(new BasicNameValuePair("status", tStatus));

                Log.d("request!", "starting");

                JSONObject json = jsonParser.makeHttpRequest(PENGADUAN_URL, "POST", params);
                Log.d("Pengaduan Pelanggan", json.toString());

                // json success element
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {

                    Log.d("Kirim Pengaduan !", json.toString());

                    Thread t = new Thread() {
                        public void run() {
                            handler.post(new Runnable() {
                                public void run() {
                                    Util.showmsg(PengaduanPelangganActivity.this, "Pengaduan :", "Pengiriman Pesan Berhasil !");
                                    clearData();
                                }
                            });
                        }
                    };
                    t.start();

                    //return json.getString(TAG_MESSAGE);

                } else {

                    Log.d("Failure!", json.getString(TAG_MESSAGE));

                    Thread t = new Thread() {
                        public void run() {
                            handler.post(new Runnable() {
                                public void run() {
                                    Util.showmsg(PengaduanPelangganActivity.this, "Pengaduan :", "Pengiriman Pesan Gagal !");
                                }
                            });
                        }
                    };
                    t.start();

                    //return json.getString(TAG_MESSAGE);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private void clearData() {

        input_name.setText("");
        input_idpel.setText("");
        input_alamat.setText("");
        input_telepon.setText("");
        input_ponsel.setText("");
        input_message.setText("");

    }


    @SuppressWarnings("static-access")
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfoMob = cm.getNetworkInfo(cm.TYPE_MOBILE);
        NetworkInfo netInfoWifi = cm.getNetworkInfo(cm.TYPE_WIFI);
        return (netInfoMob != null || netInfoWifi != null) && (netInfoMob.isConnectedOrConnecting() || netInfoWifi.isConnectedOrConnecting());
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
