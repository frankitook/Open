package com.example.gestion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActualizarContacto extends AppCompatActivity {

    private  EditText uptel ,upnom ,updir;
    private Button upboton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_contacto);

        uptel=findViewById(R.id.uptel);
        upnom=findViewById(R.id.upnom);
        updir=findViewById(R.id.updir);

        upboton=findViewById(R.id.upboton);

        uptel.setText(getIntent().getStringExtra("numero1"));
        upnom.setText(getIntent().getStringExtra("nombre1"));
        updir.setText(getIntent().getStringExtra("direccion1"));

     upboton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             if((uptel.getText().toString()).equals(getIntent().getStringExtra("numero1"))) {

                 SQLiteService admin = new SQLiteService(ActualizarContacto.this);
                 SQLiteDatabase db = admin.getWritableDatabase();

                 db.execSQL("UPDATE proveedores SET nombre='" + upnom.getText().toString() + "' , direccion='" + updir.getText().toString() + "'  WHERE numero='" + uptel.getText().toString() + "'");

                 db.close();


                 Intent intent = new Intent(ActualizarContacto.this, MainProveedores.class);
                 uptel.setText("");
                 updir.setText("");
                 upnom.setText("");
                 startActivity(intent);


             }else{

                 if((uptel.getText().toString()).isEmpty()){

                     uptel.setError("Debe llenar este campo");

                 }else{
                     SQLiteService admin = new SQLiteService(getApplicationContext());
                     SQLiteDatabase db = admin.getWritableDatabase();

                     db.execSQL("DELETE FROM proveedores WHERE numero='"+getIntent().getStringExtra("numero1")+"'");





                     ContentValues registro = new ContentValues();

                     registro.put("numero", uptel.getText().toString());
                     registro.put("nombre", upnom.getText().toString());
                     registro.put("direccion", updir.getText().toString());


                     db.insert("proveedores", null, registro);


                     uptel.setText("");
                     updir.setText("");
                     upnom.setText("");



                     Intent cambio = new Intent(getApplicationContext(), MainActivity.class);
                     startActivity(cambio);

                     db.close();

                     Intent intent = new Intent(ActualizarContacto.this, MainProveedores.class);
                     startActivity(intent);

                 }

             }
         }
     });




    }




}