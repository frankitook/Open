package com.example.gestion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity4 extends AppCompatActivity {

    RecyclerView listaProductos;
    ArrayList<ProductoStock> listaArrayProductos;
    ArrayList<ProductoStock> lista1;
    private TextView nohay6,colid,coldes,colpre,colstk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        nohay6 = findViewById(R.id.nohay6);
        colid = findViewById(R.id.colid);
        coldes = findViewById(R.id.coldes);
        colpre = findViewById(R.id.colpre);
        colstk = findViewById(R.id.colstk);

        listaProductos = findViewById(R.id.listaProductos);
        listaProductos.setLayoutManager(new LinearLayoutManager(this));
        listaProductos.setHasFixedSize(true);

        listaArrayProductos = new ArrayList<>();
        lista1=mostrarContactos();
        Adaptador adapter = new Adaptador(mostrarContactos());

        if (lista1.size()!=0){
            colid.setVisibility(View.VISIBLE);
            coldes.setVisibility(View.VISIBLE);
            colpre.setVisibility(View.VISIBLE);
            colstk.setVisibility(View.VISIBLE);
            listaProductos.setVisibility(View.VISIBLE);

            nohay6.setVisibility(View.INVISIBLE);
        }else {
            colid.setVisibility(View.INVISIBLE);
            coldes.setVisibility(View.INVISIBLE);
            colpre.setVisibility(View.INVISIBLE);
            listaProductos.setVisibility(View.INVISIBLE);
            colstk.setVisibility(View.INVISIBLE);
            nohay6.setText("No hay articulos cargados");

        }





        listaProductos.setAdapter(adapter);

    }

    public ArrayList<ProductoStock> mostrarContactos() {


        SQLiteService admin = new SQLiteService(this);
        SQLiteDatabase db = admin.getReadableDatabase();

        ArrayList<ProductoStock> listaContactos1 = new ArrayList<>();
        ProductoStock producto;
        Cursor c;

        c = db.rawQuery("SELECT * FROM productos", null);

        if (c.moveToFirst()) {
            do {
                producto = new ProductoStock();

                producto.setId(c.getString(0));
                producto.setDescripcion(c.getString(1));
                producto.setPrecio(c.getFloat(2));
                producto.setStock(c.getInt(3));
                listaContactos1.add(producto);
            } while (c.moveToNext());
        }

        c.close();
        db.close();

        return listaContactos1;
    }



}