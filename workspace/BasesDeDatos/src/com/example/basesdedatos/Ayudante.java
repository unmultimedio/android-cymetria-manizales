package com.example.basesdedatos;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.basesdedatos.DatabaseContract.Users;

public class Ayudante extends SQLiteOpenHelper {

	
	public static final String DATABASE_NAME = "manizales.db";
	public static final int DATABASE_VERSION = 1;
	
	public static final String SQL_CREATE_USERS =
			"CREATE TABLE "+ Users.TABLE_NAME +
			" ("+ Users._ID +" INTEGER PRIMARY KEY, "
				+ Users.COLUMN_NAME_NAME +" TEXT, "
				+ Users.COLUMN_NAME_DRINK +" TEXT, "
				+ Users.COLUMN_NAME_SPORT +" TEXT)";
	
	public static final String SQL_DELETE_USERS ="DROP TABLE IF EXISTS " + Users.TABLE_NAME;
	
	// Otras Sentencias ...
	
	// Las bases de datos, una para leer, una para escribir datos
	
	SQLiteDatabase escritor;
	SQLiteDatabase lector;
	
	public Ayudante(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public void prepareDataBases(){
		escritor = this.getWritableDatabase();
		lector = this.getReadableDatabase();
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// Called when the database is created for the first time. This is where the creation of tables and the initial population of the tables should happen.
		db.execSQL(SQL_CREATE_USERS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}
	
	public void clearUsers(SQLiteDatabase db){
		db.execSQL(SQL_DELETE_USERS);
	}


	public boolean insertarUsuario(Usuario nuevo) {
		
		ContentValues values = new ContentValues();
		
		values.put(Users.COLUMN_NAME_NAME, nuevo.name);
		values.put(Users.COLUMN_NAME_DRINK, nuevo.drink);
		values.put(Users.COLUMN_NAME_SPORT, nuevo.sport);
		
		long inserted = escritor.insert(
				Users.TABLE_NAME,
				Users.COLUMN_NAME_NAME,
				values);
		
		if(inserted == -1) return false;
		return true;
		
	}

	public List<Usuario> consultarUsuarios(){
		
		String[] columns = {Users._ID,
							Users.COLUMN_NAME_NAME,
							Users.COLUMN_NAME_DRINK,
							Users.COLUMN_NAME_SPORT};
		
		String selection = null; //Users.COLUMN_NAME_NAME + " like ?";
		String selectionArgs[] = null; //{"%a%"};
		String groupBy = null; //Users.COLUMN_NAME_SPORT;
		String having = null; //condicion aritmética
		String orderBy = null; //Users._ID;
		String limit = null; //"10";
		
		Cursor results = lector.query(Users.TABLE_NAME, 
										columns,
										selection,
										selectionArgs,
										groupBy, 
										having, 
										orderBy, 
										limit);
		
		if(results.getCount()>0){
			List<Usuario> users = new ArrayList<Usuario>();
			results.moveToFirst();
			int colId = results.getColumnIndex(Users._ID);
			int colName = results.getColumnIndex(Users.COLUMN_NAME_NAME);
			int colDrink = results.getColumnIndex(Users.COLUMN_NAME_DRINK);
			int colSport = results.getColumnIndex(Users.COLUMN_NAME_SPORT);
			String name, drink, sport;
			long id;
			while(!results.isAfterLast()){
				id = results.getLong(colId);
				name = results.getString(colName);
				drink = results.getString(colDrink);
				sport = results.getString(colSport);
				try {
					users.add(new Usuario(id, name, drink, sport));
					Log.e("db-mzl", String.format("Creado usuario con datos n=%s, d=%s, s=%s.", name, drink, sport));
				} catch (Exception e) {
					Log.e("db-mzl", String.format("Error creando usuario con datos n=%s, d=%s, s=%s. Usuario no incluído en los resultados.", name, drink, sport));
				}
				results.moveToNext();
			}
			return users;
		}
		
		return null;
	}
	
	public void limpiarTablaUsuarios(){
		//Borrar los registros de la tabla usuarios
		escritor.delete(Users.TABLE_NAME, null, null);
	}
}













