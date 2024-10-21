package com.example.gestion;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity5 extends AppCompatActivity {

private EditText descripcionactualizar,precioactualizar,stockactualizar;
private Button b123;
private TextView textView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        textView=findViewById(R.id.textView4);
        descripcionactualizar=findViewById(R.id.descripcionactualizar);
        precioactualizar=findViewById(R.id.precioactualizar);
        stockactualizar=findViewById(R.id.stockactualizar);

        b123=findViewById(R.id.botonactualizar);

        textView.setText("COD.ART: " + getIntent().getStringExtra("intentID"));
        descripcionactualizar.setText(getIntent().getStringExtra("intentDesc"));
        precioactualizar.setText(getIntent().getStringExtra("intentPrecio"));
        stockactualizar.setText(getIntent().getStringExtra("intentStock"));


    }


    public void  actualizar(View view){

        String id1= getIntent().getStringExtra("intentID");
        String descripcion1 = descripcionactualizar.getText().toString();

        if(!id1.isEmpty() && !descripcion1.isEmpty() && !(precioactualizar.getText().toString()).isEmpty()&& !(stockactualizar.getText().toString()).isEmpty()) {
            float precio1 = Float.parseFloat(precioactualizar.getText().toString());
            int cantidad1 = Integer.parseInt(stockactualizar.getText().toString());

            SQLiteService admin = new SQLiteService(this);
            SQLiteDatabase db = admin.getWritableDatabase();

            db.execSQL("UPDATE productos SET descripcion='" + descripcion1 + "' , precio=" + precio1 + " , cant=" + cantidad1 + " WHERE id='" + id1 + "'");

            db.close();


            descripcionactualizar.setText("");
            precioactualizar.setText("");
            stockactualizar.setText("");

            Toast.makeText(this, "ACTUALIZADO", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(MainActivity5.this, Prueba.class);
            startActivity(intent);
        }else {


            if(descripcion1.isEmpty()){ descripcionactualizar.setError("Debe llenar este campo");}
            if((precioactualizar.getText().toString()).isEmpty()){ precioactualizar.setError("Debe llenar este campo");}
            if((stockactualizar.getText().toString()).isEmpty()){ stockactualizar.setError("Debe llenar este campo");}

        }


    }






}