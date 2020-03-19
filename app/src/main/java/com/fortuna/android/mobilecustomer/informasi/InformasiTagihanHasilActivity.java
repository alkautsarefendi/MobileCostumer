package com.fortuna.android.mobilecustomer.informasi;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fortuna.android.mobilecustomer.R;
import com.fortuna.android.mobilecustomer.lokasi.LokasiKantorHasilActivity;
import com.fortuna.android.mobilecustomer.main.InformasiTagihanPelangganActivity;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * Created by Stuarez on 11/30/2016.
 */
public class InformasiTagihanHasilActivity extends AppCompatActivity {

    TextView tvJudul, tvIdPelanggan, tvAlamat, tvAmount, tvPeriode, tvNamaPDAM, tvNama, tvTotalAmount, tvDenda, tvKodePDAM;
    LinearLayout llKodePDAM, llNamaPDAM, llIDPel, llNama, llAlamat, llPeriode, llAmount, llDenda, llTotalAmount, llButtonBayar;

    String idPelPref, almtPref, periodpref, amntPref, resNamaPDAMPref, nmPref, ttlAmntPref, dendaPref, kdPDAMPref;

    Button btnLiatLokasi;
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

        setContentView(R.layout.activity_informasi_tagihan_hasil);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initComponent();
        getDataFromInquiry();
    }


    private void initComponent() {

        tvJudul = findViewById(R.id.tvJudul);
        tvIdPelanggan = findViewById(R.id.TxtViewIdPelanggan);
        tvAlamat = findViewById(R.id.TxtViewAlamat);
        tvAmount = findViewById(R.id.TxtViewAmount);
        tvPeriode = findViewById(R.id.TxtPeriode);
        tvNamaPDAM = findViewById(R.id.TxtViewNamaPDAM);
        tvNama = findViewById(R.id.TxtViewNama);
        tvTotalAmount = findViewById(R.id.TxtViewTotalAmount);
        tvDenda = findViewById(R.id.TxtViewDenda);
        tvKodePDAM = findViewById(R.id.TxtViewKodePDAM);

        llKodePDAM = findViewById(R.id.llKodePDAM);
        llNamaPDAM = findViewById(R.id.llNamaPDAM);
        llIDPel = findViewById(R.id.llIDPel);
        llNama = findViewById(R.id.llNama);
        llAlamat = findViewById(R.id.llAlamat);
        llPeriode = findViewById(R.id.llPeriode);
        llAmount = findViewById(R.id.llAmount);
        llDenda = findViewById(R.id.llDenda);
        llTotalAmount = findViewById(R.id.llTotalAmount);
        //llButtonBayar = (LinearLayout) findViewById(R.id.llButtonBayar);

        btnLiatLokasi = findViewById(R.id.btnLiatLokasi);
        btnLiatLokasi.setOnClickListener(new ButtonLiatLokasiClickHandler());

    }


    private void getDataFromInquiry() {

        SharedPreferences sharedpreferences = getSharedPreferences(InformasiTagihanPelangganActivity.PDAMPREFERENCES, Context.MODE_PRIVATE);
        idPelPref = sharedpreferences.getString("idPelangganKey", "");
        almtPref = sharedpreferences.getString("alamatKey", "");
        periodpref = sharedpreferences.getString("periodeKey", "");
        amntPref = sharedpreferences.getString("amountKey", "");
        resNamaPDAMPref = sharedpreferences.getString("namaPDAMKey", "");
        nmPref = sharedpreferences.getString("namaKey", "");
        ttlAmntPref = sharedpreferences.getString("totalAmountKey", "");
        dendaPref = sharedpreferences.getString("dendaKey", "");
        kdPDAMPref = sharedpreferences.getString("kodePDAMKey", "");

        tvIdPelanggan.setText(idPelPref);
        tvAlamat.setText(almtPref);
        tvAmount.setText(amntPref);
        tvPeriode.setText(periodpref);
        tvAmount.setText(formatNumInd(Double.valueOf(amntPref)).replace(",00", ""));
        tvPeriode.setText(periodpref);
        tvNamaPDAM.setText(resNamaPDAMPref);
        tvNama.setText(nmPref);
        tvTotalAmount.setText(formatNumInd(Double.valueOf(ttlAmntPref)).replace(",00", ""));
        tvDenda.setText(formatNumInd(Double.valueOf(dendaPref)).replace(",00", ""));
        tvKodePDAM.setText(kdPDAMPref);

    }


    public String formatNumInd(double d) {

        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        kursIndonesia.setDecimalFormatSymbols(formatRp);

        return kursIndonesia.format(d);
    }


    private class ButtonLiatLokasiClickHandler implements View.OnClickListener {
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View v) {

            Intent blk = new Intent(InformasiTagihanHasilActivity.this, LokasiKantorHasilActivity.class);
            bundleAnimation = ActivityOptions.makeCustomAnimation(InformasiTagihanHasilActivity.this, R.anim.page_animation, R.anim.page_animation1).toBundle();
            startActivity(blk);

        }
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
