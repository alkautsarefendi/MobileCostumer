package com.fortuna.android.mobilecustomer.main;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
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
import android.widget.Toast;

import com.fortuna.android.mobilecustomer.R;
import com.fortuna.android.mobilecustomer.informasi.InformasiTagihanHasilActivity;
import com.fortuna.android.mobilecustomer.util.Util;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Stuarez on 11/30/2016.
 */
public class InformasiTagihanPelangganActivity extends AppCompatActivity {

    private EditText etNoPelanggan;
    Button btnCekTagihan;

    String res;
    private String alamatPel = "";
    String idPel, almt, period, amnt, resCd, nm, ttlAmnt, denda, kdPDAM, nmPDAM;
    Bundle bundleAnimation;
    private ProgressDialog pDialog;
    private Handler handler = new Handler();

    private SharedPreferences sharedpreferences;
    public static final String PDAMPREFERENCES = "PembayaranPDAM";
    public static final String KodePDAM = "kodePDAMKey";
    public static final String IDPelanggan = "idPelangganKey";
    public static final String Alamat = "alamatKey";
    public static final String Amount = "amountKey";
    public static final String Periode = "periodeKey";
    public static final String NamaPDAM = "namaPDAMKey";
    public static final String Nama = "namaKey";
    public static final String TotalAmount = "totalAmountKey";
    public static final String Denda = "dendaKey";

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

        setContentView(R.layout.activity_informasi_tagihan_pelanggan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initComponent();
    }

    private void initComponent() {

        etNoPelanggan = findViewById(R.id.etNoPelanggan);

        btnCekTagihan = findViewById(R.id.btnCekTagihan);
        btnCekTagihan.setOnClickListener(new ButtonCekTagihanClickHandler());

        sharedpreferences = getSharedPreferences(PDAMPREFERENCES, Context.MODE_PRIVATE);

    }

    private class ButtonCekTagihanClickHandler implements View.OnClickListener {
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View v) {

            new CekTagihan().execute();

        }
    }


    class CekTagihan extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */

        /*ProgressDialog pDialog = ProgressDialog.show(InformasiTagihanPelangganActivity.this, "PDAM Tirta Kerta Raharja", "Menunggu Hasil...");*/
        Handler handler = new Handler();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(InformasiTagihanPelangganActivity.this);
            pDialog.setMessage("Menunggu Hasil...");
            pDialog.setTitle("PDAM DEMO");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            handler.postDelayed(new Runnable() {
                public void run() {
                    pDialog.dismiss();
                }
            }, 3000);  // 1000 milliseconds

            /*pDialog.show();
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);*/
        }

        @Override
        protected String doInBackground(String... args) {

            try {

                Thread t = new Thread() {
                    public void run() {
                        handler.post(new Runnable() {
                            public void run() {
                                doProcess();
                            }
                        });
                    }
                };
                t.start();

            } catch (Exception e) {

                e.printStackTrace();

            }

            return null;

        }
    }


    private void doProcess() {

        if (isOnline() == true) {

            if (etNoPelanggan.getText().toString().isEmpty()) {

                etNoPelanggan.setError("Masukkan ID Pelanggan Anda !");

            } else {

                try {

                    InformasiTagihanPelangganActivity it = new InformasiTagihanPelangganActivity();

                    res = sendToRest("PAMINQ", etNoPelanggan.getText().toString(), "", "", "", "");
                    Log.i("DEBUG", "Response Inq -> " + res);

                    it = dataParsingResponInquiry(res);
                    Log.i("DEBUG", "RC -> " + resCd);


                    if (resCd == null) {

                        new AlertDialog.Builder(InformasiTagihanPelangganActivity.this)
                                .setMessage("Tidak dapat melakukan cek tagihan PDAM yang dipilih !")
                                .setCancelable(false)
                                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                })
                                .show();
                    } else {

                        if (resCd.equals("00")) {

                            Intent pdamHasil = new Intent(getApplicationContext(), InformasiTagihanHasilActivity.class);
                            bundleAnimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.page_animation, R.anim.page_animation1).toBundle();
                            startActivity(pdamHasil, bundleAnimation);

                        } else {

                            new AlertDialog.Builder(InformasiTagihanPelangganActivity.this)
                                    .setMessage(inResponseCode(resCd))
                                    .setCancelable(false)
                                    .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    }).show();

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            Toast.makeText(getApplicationContext(), "Silahkan cek koneksi anda", Toast.LENGTH_LONG).show();
        }
    }


    @SuppressWarnings("static-access")
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfoMob = cm.getNetworkInfo(cm.TYPE_MOBILE);
        NetworkInfo netInfoWifi = cm.getNetworkInfo(cm.TYPE_WIFI);
        return (netInfoMob != null || netInfoWifi != null) && (netInfoMob.isConnectedOrConnecting() || netInfoWifi.isConnectedOrConnecting());
    }


    public String sendToRest(String menu, String idPelanggan, String sKodePdam, String sAmount, String sDenda, String sTotalAmount) {

        String sHasil = "", output = null, output2 = null;
        int connectiontimeout = 10000; //10 second
        int sockettimeout = 10000;
        String sURL = "";

        if (menu.equals("PAMINQ")) {
            sURL = "http://119.2.66.36:8113/inquiryData?AgentID=5579001&AgentPIN=6682001&AgentTrxID=3148&AgentStoreID=FMT2&ProductID=1000&CustomerID=" + idPelanggan + "&DateTimeRequest=20150224163407&Signature=3d920c48b329c3b9802ed29fa7407e8941f1fab9";
            Log.i("DEBUG", "MENU INQUIRY CALL REST ");
            Log.i("DEBUG", "SURL INQ : " + sURL);

        } else if (menu.equals("PAMINQPROD")) {

            sURL = "http://10.10.21.49:8103/inquiryData?AgentID=5579001&AgentPIN=6682001&AgentTrxID=3148&AgentStoreID=K329&ProductID=" + sKodePdam + "&CustomerID=" + idPelanggan + "&DateTimeRequest=20150224163407&Signature=3d920c48b329c3b9802ed29fa7407e8941f1fab9";
            Log.i("DEBUG", "MENU PAYMENT CALL REST ");
            Log.i("DEBUG", "SURL PAY : " + sURL);

        }

        try {

            HttpParams httpparameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpparameters, connectiontimeout);
            HttpConnectionParams.setSoTimeout(httpparameters, sockettimeout);
            HttpClient httpClient = new DefaultHttpClient(httpparameters);
            HttpGet getRequest = new HttpGet(sURL);
            HttpResponse response = httpClient.execute(getRequest);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
            while ((output = br.readLine()) != null) {
                if (output != null)
                    output2 = output;
            }

            sHasil = output2;
            httpClient.getConnectionManager().shutdown();
        } catch (ClientProtocolException e) {
            Log.i("DEBUG", "Error Client Protocol Exception");
        } catch (IOException e) {
            Log.i("", "Error IO Exception");
        }

        return sHasil;

    }


    private InformasiTagihanPelangganActivity dataParsingResponInquiry(String dataInquiry) {

        int i = 0;
        int value = 0;
        int q = 0;
        InformasiTagihanPelangganActivity it = new InformasiTagihanPelangganActivity();
        String dataParsing = "";//dataInquiry[dataInquiry.length];
        String temp = "";
        SharedPreferences.Editor editor = sharedpreferences.edit();

        for (i = 0; i < dataInquiry.length(); i++) {
            temp = "";
            temp = dataInquiry.substring(value, value + 1);
            if (temp.equals("|")) {

                System.out.println(q);
                System.out.println("Data Parsing = " + dataInquiry);

                if (q == 4) idPel = dataParsing;
                editor.putString(IDPelanggan, idPel);
                if (q == 6) resCd = dataParsing;
                if (q == 9) period = dataParsing;
                editor.putString(Periode, period);
                if (q == 10) nm = dataParsing;
                editor.putString(Nama, nm);
                if (q == 11) alamatPel = dataParsing;
                if (alamatPel.contains("#")) {
                    almt = alamatPel.split("#")[3];
                    editor.putString(Alamat, almt);
                } else {

                }

                if (q == 13)
                    amnt = dataParsing;
                editor.putString(Amount, amnt);
                if (q == 14)
                    denda = dataParsing;
                editor.putString(Denda, denda);
                if (q == 15)
                    ttlAmnt = dataParsing;
                editor.putString(TotalAmount, ttlAmnt);

                nmPDAM = "PDAM TKR";
                editor.putString(NamaPDAM, nmPDAM);

                kdPDAM = "1000";
                editor.putString(KodePDAM, kdPDAM);

                editor.apply();

                temp = "";
                dataParsing = "";
                q++;
                value++;
            } else {
                dataParsing += temp;
                temp = "";
                value++;
            }

        }
        return it;
    }


    public String inResponseCode(String sRc) {
        String sHasil = "";

        if (sRc.equals("14")) {
            sHasil = "ID Pelanggan Tidak Dikenal";
        } else if (sRc.equals("41")) {
            sHasil = "Silakan Bayar Diloket PDAM";
        } else if (sRc.equals("88")) {
            sHasil = "Sudah Lunas";
        } else if (sRc.equals("50")) {
            sHasil = "Jumalah Bayar Salah";
        } else if (sRc.equals("34")) {
            sHasil = "Signature Salah";
        } else if (sRc.equals("68")) {
            sHasil = "TimeOut";
        } else if (sRc.equals("31")) {
            sHasil = "Agent ID Salah";
        } else if (sRc.equals("30")) {
            sHasil = "Database Bermasalah";
        } else if (sRc.equals("63")) {
            sHasil = "Transaksi Tidak Dapat Dilakukan";
        } else if (sRc.equals("97")) {
            sHasil = "Reversal Ditolak";
        } else if (sRc.equals("33")) {
            sHasil = "Product ID Salah";
        } else if (sRc.equals("89")) {
            sHasil = "Gangguan Jaringan";
        } else if (sRc.equals("94")) {
            sHasil = "Sudah Dilakukan Reversal";
        } else if (sRc.equals("05")) {
            sHasil = "Error Yang Lain";
        } else {
            sHasil = "Error Tak Terdefinisi";
        }
        return sHasil;

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
