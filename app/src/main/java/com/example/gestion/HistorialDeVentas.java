package com.example.gestion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class HistorialDeVentas extends AppCompatActivity {

    private RecyclerView historialVentas;
    private ArrayList<Historial> arrayList;
    private TextView nohayhis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_de_ventas);
        nohayhis=findViewById(R.id.nohayhis);

        historialVentas=findViewById(R.id.historialVentas);
        arrayList=mostrarHistorial();
        if(arrayList.size()!=0) {
            historialVentas.setVisibility(View.VISIBLE);

            historialVentas.setLayoutManager(new LinearLayoutManager(this));
            historialVentas.setHasFixedSize(true);
            AdaptadorHistorial adapter129 = new AdaptadorHistorial(mostrarHistorial());
            historialVentas.setAdapter(adapter129);
        }else {

            historialVentas.setVisibility(View.INVISIBLE);
            nohayhis.setText("No hay ventas realizadas");

        }

    }


    public ArrayList<Historial> mostrarHistorial() {

        SQLiteService admin = new SQLiteService(this);
        SQLiteDatabase db = admin.getReadableDatabase();

        ArrayList<Historial> listaContactos10 = new ArrayList<>();
        Historial historial;
        Cursor c = db.rawQuery("SELECT * FROM historial", null);

        if (c.moveToFirst()) {
            do {
                historial = new Historial();

                historial.setId(c.getInt(0));
                historial.setFecha(c.getString(1));
                historial.setHora(c.getString(2));
                historial.setTotal(c.getString(3));
                historial.setNombCliente(c.getString(4));
                historial.setCantidadProductos(c.getInt(5));
                listaContactos10.add(historial);
            } while (c.moveToNext());
        }

        c.close();
        db.close();

        return listaContactos10;

    }


}