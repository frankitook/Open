package com.example.gestion;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
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
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity3 extends AppCompatActivity {
private EditText ed1 ,ed2,ed3,ed4;
private Button btn4;

private ImageButton btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

       ed1=findViewById(R.id.idprod);
        ed2=findViewById(R.id.descprod);
        ed3=findViewById(R.id.precioprod);
        ed4=findViewById(R.id.cantidadprod);

        btn3=findViewById(R.id.button3);
        btn4=findViewById(R.id.button4);

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrador= new IntentIntegrator(MainActivity3.this);
                integrador.setDesiredBarcodeFormats(IntentIntegrator.EAN_13);
                integrador.setPrompt("Escanee el codigo del producto");
                integrador.setCameraId(0);
                integrador.setBeepEnabled(true);
                integrador.setBarcodeImageEnabled(true);
                integrador.initiateScan();

            }
        });



        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id= ed1.getText().toString();
                String descripcion = ed2.getText().toString();

                if(!id.isEmpty() && !descripcion.isEmpty() && !(ed3.getText().toString()).isEmpty()&& !(ed4.getText().toString()).isEmpty()){
                    float precio= Float.parseFloat(ed3.getText().toString());
                    int cantidad =Integer.parseInt( ed4.getText().toString());
                    SQLiteService admin = new SQLiteService(getApplicationContext());


                                SQLiteDatabase db = admin.getReadableDatabase();
                                Cursor d = db.rawQuery("SELECT * FROM productos WHERE id='"+id+"'", null);

                                if(d.getCount()==0){

                                    SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();


                                    ContentValues registro = new ContentValues();

                                    registro.put("id", id);
                                    registro.put("descripcion", descripcion);
                                    registro.put("precio", precio);
                                    registro.put("cant", cantidad);

                                    BaseDeDatos.insert("productos", null, registro);

                                    BaseDeDatos.close();
                                    ed1.setText("");
                                    ed2.setText("");
                                    ed3.setText("");
                                    ed4.setText("");

                                    Toast.makeText(getApplicationContext(), "Registro exitoso", Toast.LENGTH_SHORT).show();

                                    Intent cambio = new Intent(getApplicationContext(), Prueba.class);
                                    startActivity(cambio);
                                    BaseDeDatos.close();



                                }else {

                                    Toast.makeText(getApplicationContext(), "El producto se encuenta cargado", Toast.LENGTH_SHORT).show();
                                }

                                db.close();
                                d.close();

                }else {

                    if(id.isEmpty()){ ed1.setError("Debe llenar este campo");}
                    if(descripcion.isEmpty()){ ed2.setError("Debe llenar este campo");}
                    if((ed3.getText().toString()).isEmpty()){ ed3.setError("Debe llenar este campo");}
                    if((ed4.getText().toString()).isEmpty()){ ed4.setError("Debe llenar este campo");}

                }


            }
        });




    }

    protected void onActivityResult(int requestCode , int resultCode , Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result != null){
            if (result.getContents()== null){


            }else{
                String dalee=result.getContents();
                ed1.setText(dalee);

            }

        } else {

            super.onActivityResult(requestCode, resultCode, data);
        }


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        Intent intent = new Intent(MainActivity3.this,Prueba.class);
        startActivity(intent);
        return super.onKeyUp(keyCode, event);
    }


    }










