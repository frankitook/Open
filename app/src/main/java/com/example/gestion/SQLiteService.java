package com.example.gestion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteService extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NOMBRE = "GestionVentas";


    public SQLiteService(Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table productos(id text primary key, descripcion text, precio float, cant int)");

        db.execSQL("create table listacompra(idart text primary key, descart text, precart float, cantart int)");

        db.execSQL("create table proveedores(numero text primary key, nombre text, direccion text)");

        db.execSQL("create table historial(id INTEGER PRIMARY KEY AUTOINCREMENT, fecha text, hora text, total text, nombCliente text , cantidadProductos int)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}