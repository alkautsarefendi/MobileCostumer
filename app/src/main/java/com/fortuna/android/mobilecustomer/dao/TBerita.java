package com.fortuna.android.mobilecustomer.dao;

import java.util.List;

import android.R.string;
import android.content.Context;

import com.fortuna.android.mobilecustomer.annotation.Column;
import com.fortuna.android.mobilecustomer.annotation.PrimaryKey;
import com.fortuna.android.mobilecustomer.annotation.Table;

@Table(name="tberita")
public class TBerita extends BaseDAO {

    public TBerita(Context context) {
        super(context);
    }

    @Column
    @PrimaryKey
    public string KODE_BERITA;

    @Column
    public string TANGGAL;

    @Column
    public string BERITA;



    public string getKODE_BERITA() {
        return KODE_BERITA;
    }

    public void setKODE_BERITA(string kODE_BERITA) {
        KODE_BERITA = kODE_BERITA;
    }

    public string getTANGGAL() {
        return TANGGAL;
    }

    public void setTANGGAL(string tANGGAL) {
        TANGGAL = tANGGAL;
    }

    public string getBERITA() {
        return BERITA;
    }

    public void setBERITA(string bERITA) {
        BERITA = bERITA;
    }



    @Override
    public List<?> retrieveAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object retrieveByID() {
        // TODO Auto-generated method stub
        return null;
    }

}
