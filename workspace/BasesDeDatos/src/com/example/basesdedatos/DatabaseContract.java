package com.example.basesdedatos;

import android.provider.BaseColumns;

public class DatabaseContract {

	//Tabla Usuarios
	public static class Users implements BaseColumns{
		public static final String TABLE_NAME="users";
		public static final String COLUMN_NAME_NAME = "name";
		public static final String COLUMN_NAME_DRINK = "drink";
		public static final String COLUMN_NAME_SPORT = "sport";
	}
	
	// Otras tablas, vistas...
}
