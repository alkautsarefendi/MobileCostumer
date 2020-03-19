package com.fortuna.android.mobilecustomer.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fortuna.android.mobilecustomer.annotation.Column;
import com.fortuna.android.mobilecustomer.annotation.PrimaryKey;
import com.fortuna.android.mobilecustomer.annotation.Table;
import com.fortuna.android.mobilecustomer.util.ConnectivityDB;

@Table(name="tpendaftaran")
public class TPendaftaran extends BaseDAO{

    public TPendaftaran(Context context) {
        super(context);
    }

    @Column
    @PrimaryKey
    public String kdDaftar;

    @Column
    public String pemohon;

    @Column
    public String almtPasang;

    @Column
    public String crtTgl;

    @Column
    public String ketStatus;

    @Column
    public String typeDaftar;

    @Column
    public String sDaftar;



    public String getKdDaftar() {
        return kdDaftar;
    }

    public void setKdDaftar(String kdDaftar) {
        this.kdDaftar = kdDaftar;
    }

    public String getPemohon() {
        return pemohon;
    }

    public void setPemohon(String pemohon) {
        this.pemohon = pemohon;
    }

    public String getAlmtPasang() {
        return almtPasang;
    }

    public void setAlmtPasang(String almtPasang) {
        this.almtPasang = almtPasang;
    }

    public String getCrtTgl() {
        return crtTgl;
    }

    public void setCrtTgl(String crtTgl) {
        this.crtTgl = crtTgl;
    }

    public String getKetStatus() {
        return ketStatus;
    }

    public void setKetStatus(String ketStatus) {
        this.ketStatus = ketStatus;
    }

    public String getTypeDaftar() {
        return typeDaftar;
    }

    public void setTypeDaftar(String typeDaftar) {
        this.typeDaftar = typeDaftar;
    }

    public String getsDaftar() {
        return sDaftar;
    }

    public void setsDaftar(String sDaftar) {
        this.sDaftar = sDaftar;
    }



    @Override
    public List<?> retrieveAll() {
        List<TPendaftaran> j = new ArrayList<TPendaftaran>();

        ConnectivityDB db = new ConnectivityDB(context);
        Cursor cursor = db.getAllData(this);
        if(cursor.moveToFirst()){
            do{
                TPendaftaran mjal = new TPendaftaran(context);
                String[] columnNames = cursor.getColumnNames();
                for (String string : columnNames) {
                    try {
                        Field declaredField = this.getClass().getDeclaredField(string);
                        declaredField.set(mjal, cursor.getString(cursor.getColumnIndex(string)));
                    } catch (NoSuchFieldException e) {
                    } catch (IllegalArgumentException e) {
                    } catch (IllegalAccessException e) {
                    }
                }
                j.add(mjal);
            }while(cursor.moveToNext());
        }
        db.close();
        return j;
    }

    @Override
    public Object retrieveByID() {
        ConnectivityDB db = new ConnectivityDB(context);
        Cursor cursor = db.getDataByPrimaryKey(this);
        if(cursor.getCount() > 0){
            if(cursor != null) cursor.moveToFirst();
            String[] columnNames = cursor.getColumnNames();
            for (String string : columnNames) {
                try {
                    Field declaredField = this.getClass().getDeclaredField(string);
                    declaredField.set(this, cursor.getString(cursor.getColumnIndex(string)));
                } catch (NoSuchFieldException e) {
                    // TODO Auto-generated catch block
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                }

            }
            return this;
        }
        db.close();
        return null;
    }

    /* Retrieve Data */
    public TPendaftaran retrieveForData(String kdDaftar) {
        ConnectivityDB db = new ConnectivityDB(context);
        SQLiteDatabase readableDatabase = db.getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery("select * from tpendaftaran where kdDaftar=?",
                new String[]{kdDaftar}
        );
        if(cursor.getCount() > 0 ){
            if(cursor != null) cursor.moveToFirst();
            String[] columnNames = cursor.getColumnNames();
            for (String string : columnNames) {
                try {
                    System.out.println("test "+string+"="+cursor.getString(cursor.getColumnIndex(string)));
                    Field declaredField = this.getClass().getDeclaredField(string);
                    declaredField.set(this, cursor.getString(cursor.getColumnIndex(string)));
                } catch (NoSuchFieldException e) {
                } catch (IllegalArgumentException e) {
                } catch (IllegalAccessException e) {
                }

            }
            return this;
        }
        readableDatabase.close();
        return null;
    }

}
