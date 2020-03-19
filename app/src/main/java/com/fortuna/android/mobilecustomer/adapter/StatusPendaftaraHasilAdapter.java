package com.fortuna.android.mobilecustomer.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fortuna.android.mobilecustomer.R;
import com.fortuna.android.mobilecustomer.dao.TPendaftaran;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Stuarez on 12/6/2016.
 */
public class StatusPendaftaraHasilAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private String noDaftar, namaPel, alamatPel;
    private List<TPendaftaran> listpendaftaran;
    private Date date;

    private SharedPreferences sharedpreferences;
    public static final String DAFTARPREFERENCES = "StatusDaftar";
    public static final String NoDaftar = "noDaftarKey";
    public static final String NamaPelDaftar = "NamaPelKey";
    public static final String AlamatPelDaftar = "alamatPelKey";

    public StatusPendaftaraHasilAdapter(Activity activity, List<TPendaftaran> listPendaftaran) {
        this.activity = activity;
        this.listpendaftaran = listPendaftaran;
        sharedpreferences = activity.getSharedPreferences(DAFTARPREFERENCES, Context.MODE_PRIVATE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.status_pendaftaran_hasil_adapter, parent, false);

        TextView tanggal = (TextView) convertView.findViewById(R.id.txtTanggal);
        TextView status = (TextView) convertView.findViewById(R.id.txtStatus);

        SharedPreferences.Editor editor = sharedpreferences.edit();

        TPendaftaran tp = listpendaftaran.get(position);
        editor.putString(NoDaftar, tp.getKdDaftar());
        editor.putString(NamaPelDaftar, tp.getPemohon());
        editor.putString(AlamatPelDaftar, tp.getAlmtPasang());

        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        try {
            date = inputFormat.parse(tp.getCrtTgl());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // Format date into output format
        DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String outputString = outputFormat.format(date);

        //SimpleDateFormat tanggalFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        tanggal.setText(outputString);
        status.setText(tp.getKetStatus());

        editor.apply();

        System.out.println("Tanggal " + outputString);

        return convertView;
    }

    @Override
    public int getCount() {
        return listpendaftaran.size();
    }

    @Override
    public Object getItem(int position) {
        return listpendaftaran.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}