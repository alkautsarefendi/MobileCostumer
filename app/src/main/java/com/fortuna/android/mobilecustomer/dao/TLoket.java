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

@Table(name="tloket")
public class TLoket extends BaseDAO {

    public TLoket(Context context) {
        super(context);
    }

    @Column
    @PrimaryKey
    public String kdWilayah;

    @Column
    public String nmKantor;

    @Column
    public String almtKantor;

    @Column
    public String nmKepala;



    public String getKdWilayah() {
        return kdWilayah;
    }

    public void setKdWilayah(String kdWilayah) {
        this.kdWilayah = kdWilayah;
    }

    public String getNmKantor() {
        return nmKantor;
    }

    public void setNmKantor(String nmKantor) {
        this.nmKantor = nmKantor;
    }

    public String getAlmtKantor() {
        return almtKantor;
    }

    public void setAlmtKantor(String almtKantor) {
        this.almtKantor = almtKantor;
    }

    public String getNmKepala() {
        return nmKepala;
    }

    public void setNmKepala(String nmKepala) {
        this.nmKepala = nmKepala;
    }



    @Override
    public List<?> retrieveAll() {
        List<TLoket> j = new ArrayList<TLoket>();

        ConnectivityDB db = new ConnectivityDB(context);
        Cursor cursor = db.getAllData(this);
        if(cursor.moveToFirst()){
            do{
                TLoket mjal = new TLoket(context);
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
    public TLoket retrieveForData(String kdWilayah) {
        ConnectivityDB db = new ConnectivityDB(context);
        SQLiteDatabase readableDatabase = db.getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery("select * from tloket where kdWilayah=?",
                new String[]{kdWilayah}
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
