package com.example.gestion;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EliminarProducto extends AppCompatActivity {
private TextView ena,eca,epa,esa;
private Button elimina;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_producto);


        ena=findViewById(R.id.ena);
        epa=findViewById(R.id.epa);
        eca=findViewById(R.id.eca);
        esa=findViewById(R.id.esa);
        elimina=findViewById(R.id.elimina);

        ena.setText(getIntent().getStringExtra("dp"));
        eca.setText("COD.ART:"+getIntent().getStringExtra("idp"));
        epa.setText("Precio: $"+getIntent().getStringExtra("pp"));
        esa.setText("Stock: "+getIntent().getStringExtra("sp"));

        elimina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar();
            }
        });


    }

    public void eliminar(){
        SQLiteService admin = new SQLiteService(getApplicationContext());
        SQLiteDatabase db = admin.getWritableDatabase();

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Advertencia");
                builder.setMessage("¿Desea eliminar este producto?");

                builder.setNegativeButton("Cancelar", null);


                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        SQLiteService admin = new SQLiteService(getApplicationContext());
                        SQLiteDatabase db = admin.getWritableDatabase();

                        db.execSQL("DELETE FROM productos WHERE id='"+getIntent().getStringExtra("idp")+"'");


                        Intent k = new Intent(EliminarProducto.this, Prueba.class);
                        startActivity(k);


                    }

                });
                builder.show();

        db.close();



    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        Intent intent = new Intent(EliminarProducto.this,ActivityOne.class);
        startActivity(intent);
        return super.onKeyUp(keyCode, event);
    }


}