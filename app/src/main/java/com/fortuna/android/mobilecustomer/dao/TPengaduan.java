package com.fortuna.android.mobilecustomer.dao;

import java.util.List;

import android.R.string;
import android.content.Context;

import com.fortuna.android.mobilecustomer.annotation.Column;
import com.fortuna.android.mobilecustomer.annotation.PrimaryKey;
import com.fortuna.android.mobilecustomer.annotation.Table;

@Table(name="tpengaduan")
public class TPengaduan extends BaseDAO {

    public TPengaduan(Context context) {
        super(context);
    }

    @Column
    @PrimaryKey
    public string NO_TIKET;

    @Column
    public string NO_PELANGGAN;

    @Column
    public string NAMA;

    @Column
    public string ALAMAT;

    @Column
    public string GOL;

    @Column
    public string PENGADUAN;

    @Column
    public string TANGGAL;

    @Column
    public string STATUS;



    public string getNO_TIKET() {
        return NO_TIKET;
    }

    public void setNO_TIKET(string nO_TIKET) {
        NO_TIKET = nO_TIKET;
    }

    public string getNO_PELANGGAN() {
        return NO_PELANGGAN;
    }

    public void setNO_PELANGGAN(string nO_PELANGGAN) {
        NO_PELANGGAN = nO_PELANGGAN;
    }

    public string getNAMA() {
        return NAMA;
    }

    public void setNAMA(string nAMA) {
        NAMA = nAMA;
    }

    public string getALAMAT() {
        return ALAMAT;
    }

    public void setALAMAT(string aLAMAT) {
        ALAMAT = aLAMAT;
    }

    public string getGOL() {
        return GOL;
    }

    public void setGOL(string gOL) {
        GOL = gOL;
    }

    public string getPENGADUAN() {
        return PENGADUAN;
    }

    public void setPENGADUAN(string pENGADUAN) {
        PENGADUAN = pENGADUAN;
    }

    public string getTANGGAL() {
        return TANGGAL;
    }

    public void setTANGGAL(string tANGGAL) {
        TANGGAL = tANGGAL;
    }

    public string getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(string sTATUS) {
        STATUS = sTATUS;
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
