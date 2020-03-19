package com.fortuna.android.mobilecustomer.util;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fortuna.android.mobilecustomer.annotation.Column;
import com.fortuna.android.mobilecustomer.annotation.PrimaryKey;
import com.fortuna.android.mobilecustomer.dao.BaseDAO;
import com.fortuna.android.mobilecustomer.dao.TBerita;
import com.fortuna.android.mobilecustomer.dao.TLoket;
import com.fortuna.android.mobilecustomer.dao.TPendaftaran;
import com.fortuna.android.mobilecustomer.dao.TPengaduan;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressLint("NewApi")
public class ConnectivityDB extends SQLiteOpenHelper  {
	
	static Integer dbVersion = 1;
	private BaseDAO[] baseDao = new BaseDAO[]{
			new TPendaftaran(null),
			new TBerita(null),
			new TLoket(null),
			new TPengaduan(null)
	};
	
	public SQLiteDatabase getReadDB(){
		return this.getReadableDatabase();
	}
	
	public ConnectivityDB(Context context) {
		super(context, "PDAMCUSTOMER.db", null, dbVersion);
	}

	
	
	/* On Create Database */
	@Override
	public void onCreate(SQLiteDatabase db) {
		for (BaseDAO domain : baseDao) {
			String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +FortunaUtils.getTableName(domain) + " (" +FortunaUtils.getCloumns(domain)+ ")";
			db.execSQL(CREATE_TABLE);
		}
	}
	
	
	
	/* When Upgrading Database */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		for (BaseDAO domain : baseDao) {
			db.execSQL("DROP TABLE IF EXISTS " + FortunaUtils.getTableName(domain));
		}
		onCreate(db);
	}

	
	
	
	/* Save to Database */
	/**
	 * 
	 * @param
	 */
	public void save(BaseDAO domain) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    ContentValues values = new ContentValues();
	    
	    Field[] declaredFields = domain.getClass().getDeclaredFields();
	    for (Field field : declaredFields) {
	    	try{
	    		Annotation[] ann = field.getDeclaredAnnotations();
	    		for (Annotation annotation : ann) {
					if (annotation instanceof Column) {
						Column c = (Column) annotation;
						String name = c.name();
		    			if(name.equals("")) name = field.getName();
		    			
		    			Object value = field.get(domain);
						if (value instanceof String) {
							String new_name = (String) value;
							values.put(name, new_name);
						}else if (value instanceof Integer) {
							Integer new_name = (Integer) value;
							values.put(name, new_name);
						}else if (value instanceof Double) {
							Double new_name = (Double) value;
							values.put(name, new_name);
						}else if (value instanceof Long) {
							Long new_name = (Long) value;
							values.put(name, new_name);
						}else if (value instanceof Float) {
							Float new_name = (Float) value;
							values.put(name, new_name);
						}else if (value instanceof Byte) {
							Byte new_name = (Byte) value;
							values.put(name, new_name);
						}else if (value instanceof byte[]) {
							byte[] new_name = (byte[]) value;
							values.put(name, new_name);
						}else if (value instanceof Boolean) {
							Boolean new_name = (Boolean) value;
							values.put(name, new_name);
						}
						System.out.println(name+"="+value);
					}
				}
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
		}
	 
	    Set<String> keySet = values.keySet();
	    for (String string : keySet) {
			System.out.println(string+""+values.get(string));
		}
	    
	    // Inserting Row
	    db.insert(FortunaUtils.getTableName(domain), null, values);
	    db.close(); // Closing database connection
	}
	
	
	
	
	/* Update Database */
	public int update(BaseDAO domain) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    ContentValues values = new ContentValues();
	    String keyid = "";
	    List<String> params = new ArrayList<String>();
	    
	    Field[] declaredFields = domain.getClass().getDeclaredFields();
	    for (Field field : declaredFields) {
	    	try{
	    		Column c = field.getAnnotation(Column.class);
	    		if(c != null){
	    			String name = c.name();
	    			if(name.equals("")) name = field.getName();
	    			
	    			Object value = field.get(domain);
					if (value instanceof String) {
						String new_name = (String) value;
						values.put(name, new_name);
					}else if (value instanceof Integer) {
						Integer new_name = (Integer) value;
						values.put(name, new_name);
					}else if (value instanceof Double) {
						Double new_name = (Double) value;
						values.put(name, new_name);
					}else if (value instanceof Long) {
						Long new_name = (Long) value;
						values.put(name, new_name);
					}else if (value instanceof Float) {
						Float new_name = (Float) value;
						values.put(name, new_name);
					}else if (value instanceof Byte) {
						Byte new_name = (Byte) value;
						values.put(name, new_name);
					}else if (value instanceof byte[]) {
						byte[] new_name = (byte[]) value;
						values.put(name, new_name);
					}else if (value instanceof Boolean) {
						Boolean new_name = (Boolean) value;
						values.put(name, new_name);
					}
					System.out.println(name+"="+value);
	    			PrimaryKey p = field.getAnnotation(PrimaryKey.class);
	    			if(p != null){
	    				keyid = keyid+" "+name+"=? and";
	    				params.add(String.valueOf(field.get(domain)));
	    			}
	    		}
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
		}
	    keyid = keyid.substring(0, keyid.length()-3);
	    // updating row
	    int a = db.update(FortunaUtils.getTableName(domain), values, keyid,
	            params.toArray(new String[]{}));
	    db.close();
	    return a;
	}
	
	
	
	
	/* Delete Database */
	public int delete(BaseDAO domain) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    String keyid = "";
	    List<String> params = new ArrayList<String>();
	    
	    Field[] declaredFields = domain.getClass().getDeclaredFields();
	    for (Field field : declaredFields) {
	    	try{
	    		Column c = field.getAnnotation(Column.class);
	    		if(c != null){
	    			String name = c.name();
	    			if(name.equals("")) name = field.getName();
	    			PrimaryKey p = field.getAnnotation(PrimaryKey.class);
	    			if(p != null && field.get(domain) != null){
	    				keyid = keyid+" "+name+"=? and";
	    				params.add((String) field.get(domain));
	    			}
	    		}
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
		}
	    // updating row
	    int a = 0;
	    if(params.isEmpty()){
	    	a= db.delete(FortunaUtils.getTableName(domain), null, null);
	    }else{
	    	a= db.delete(FortunaUtils.getTableName(domain), keyid,
	    			params.toArray(new String[]{}));

	    }
	    db.close();
	    return a;
	}
	
	
	
	/* Get Primary Key */
	public Cursor getDataByPrimaryKey(BaseDAO domain){
		SQLiteDatabase db = this.getReadableDatabase();
		
		String keyid="";
		String tableName = FortunaUtils.getTableName(domain);
		List<String> columnNames = new ArrayList<String>();
		List<String> params = new ArrayList<String>();
		
		Field[] declaredFields = domain.getClass().getDeclaredFields();
	    for (Field field : declaredFields) {
	    	try{
	    		Column c = field.getAnnotation(Column.class);
	    		if(c != null){
	    			String name = c.name();
	    			if(name.equals("")) name = field.getName();
	    			columnNames.add(name);
	    			PrimaryKey p = field.getAnnotation(PrimaryKey.class);
	    			if(p != null){
	    				keyid = keyid+" "+name+"=? and";
	    				params.add(String.valueOf(field.get(domain)));
	    			}
	    		}
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
		}
		
	    keyid = keyid.substring(0, keyid.length()-3);
	    
	    Cursor c = db.query(tableName, columnNames.toArray(new String[]{}), keyid, params.toArray(new String[]{}), null, null, null);
	    return c;
	}
	
	

	/* Get All Data */
	public Cursor getAllData(BaseDAO domain) {
	    // Select All Query
	    String selectQuery = "SELECT "+FortunaUtils.getCloumns(domain)+" FROM " + FortunaUtils.getTableName(domain);
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	    return cursor;
	}
}