package com.example.gestion;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class ActivityOne extends AppCompatActivity {

private EditText e1;
private Button b1;
private ImageButton b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);

        e1=findViewById(R.id.e1);

        b2=findViewById(R.id.b2);



        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrador= new IntentIntegrator(ActivityOne.this);
                integrador.setDesiredBarcodeFormats(IntentIntegrator.EAN_13);
                integrador.setPrompt("Escanee el codigo del producto");
                integrador.setCameraId(0);
                integrador.setBeepEnabled(false);
                integrador.setBarcodeImageEnabled(true);
                integrador.initiateScan();
            }
        });



    }

    protected void onActivityResult(int requestCode , int resultCode , Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result != null){
            if (result.getContents()== null){


            }else{

                String r = result.getContents();
                e1.setText(r);

            }

        } else {

            super.onActivityResult(requestCode, resultCode, data);
        }


    }

    public void am(View view){
        if(!(e1.getText().toString()).isEmpty()){
            SQLiteService admin = new SQLiteService(ActivityOne.this);
            SQLiteDatabase db = admin.getWritableDatabase();

            Cursor c = db.rawQuery("SELECT * FROM productos WHERE id='"+e1.getText().toString()+"'", null);
            c.moveToFirst();

            if(c.getCount()!=0){



                Intent k = new Intent(ActivityOne.this, EliminarProducto.class);
                k.putExtra("idp",c.getString(0));
                k.putExtra("dp",c.getString(1));
                k.putExtra("pp",Float.toString(c.getFloat(2)));
                k.putExtra("sp",Integer.toString(c.getInt(3)));
                startActivity(k);

            }else {
                Toast.makeText(ActivityOne.this,"No se encontro el producto",Toast.LENGTH_SHORT).show();
            }

            c.close();
            db.close();
        }else{
            e1.setError("Ingrese codigo");

        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        Intent intent = new Intent(ActivityOne.this,Prueba.class);
        startActivity(intent);
        return super.onKeyUp(keyCode, event);
    }


}