package com.fortuna.android.mobilecustomer.dao;

import android.content.Context;
import android.database.SQLException;

import com.fortuna.android.mobilecustomer.util.ConnectivityDB;

import java.util.List;

public abstract class BaseDAO {

	protected Context context;
	protected ConnectivityDB db;

	public BaseDAO(Context context) {
		this.context = context;
	}

	public void save(){
		db = new ConnectivityDB(context);
		db.save(this);
	}

	public void update(){
		db = new ConnectivityDB(context);
		db.update(this);
	}

	public void delete(){
		db = new ConnectivityDB(context);
		db.delete(this);
	}
	
	public void open() throws SQLException {
		db = new ConnectivityDB(context);
	}

	public void close() {
		db.close();
	}
	

	public abstract List<?> retrieveAll();

	public abstract Object retrieveByID();


}