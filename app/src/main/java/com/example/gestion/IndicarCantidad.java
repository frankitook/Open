package com.example.gestion;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class IndicarCantidad extends AppCompatActivity {

    private TextView mna , mpa,tvs;
    private EditText ca;
    private Button carrito;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicar_cantidad);

        mna=findViewById(R.id.mna);
        mpa=findViewById(R.id.mpa);
        tvs=findViewById(R.id.tvs);

        ca=findViewById(R.id.ca);

        carrito=findViewById(R.id.carrito);

        mna.setText(getIntent().getStringExtra("desc123"));
        mpa.setText("Precio: $"+getIntent().getStringExtra("prec123"));

        SQLiteService admin = new SQLiteService(this);
        SQLiteDatabase db = admin.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM productos WHERE id='"+ getIntent().getStringExtra("id123")+"'", null);
        c.moveToFirst();
        tvs.setText("Cantidad en stock: "+ c.getInt(3));
        db.close();
        c.close();

        carrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteService admin = new SQLiteService(IndicarCantidad.this);
                SQLiteDatabase db = admin.getReadableDatabase();
                Cursor c = db.rawQuery("SELECT * FROM productos WHERE id='" + getIntent().getStringExtra("id123") + "'", null);
                c.moveToFirst();
                if (!(ca.getText().toString()).isEmpty()){
                    if (c.getInt(3) >= Integer.parseInt(ca.getText().toString())) {

                       if(Integer.parseInt(ca.getText().toString())!=0) {
                           agrega(getIntent().getStringExtra("id123"), getIntent().getStringExtra("desc123"), Float.parseFloat(getIntent().getStringExtra("prec123")));
                       }else { ca.setError("Ingrese cantidad mayor a 0");}
                    } else {

                        ca.setError("No hay stock suficiente");
                    }
            }else{ca.setError("Indicar cantidad");}
                db.close();
                c.close();
            }
        });





    }

    public void agrega(String id , String desc , Float prec){

        SQLiteService admin = new SQLiteService(this);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        if (id.length()!=0) {
            ContentValues registro = new ContentValues();

            registro.put("idart",id );
            registro.put("descart", desc);
            registro.put("precart", prec);
            registro.put("cantart",Integer.parseInt(ca.getText().toString()));

            BaseDeDatos.insert("listacompra", null, registro);

            BaseDeDatos.close();

            Intent cambio = new Intent(this, MainActivity8.class);
            startActivity(cambio);

        } else {

            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }

        BaseDeDatos.close();


    }



}