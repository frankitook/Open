package com.example.gestion;

import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity6 extends AppCompatActivity {
private Button botonBA1;
private EditText idBA;
private ImageButton imageBA;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        botonBA1=findViewById(R.id.botonBA);

        idBA=findViewById(R.id.idBA);

        imageBA=findViewById(R.id.imageBA);

        imageBA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrador= new IntentIntegrator(MainActivity6.this);
                integrador.setDesiredBarcodeFormats(IntentIntegrator.EAN_13);
                integrador.setPrompt("Escanee el codigo del producto");
                integrador.setCameraId(0);
                integrador.setBeepEnabled(true);
                integrador.setBarcodeImageEnabled(true);
                integrador.initiateScan();
            }
        });


    }

    protected void onActivityResult(int requestCode , int resultCode , Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result != null){
            if (result.getContents()== null){

                Toast.makeText(this,"Cancelado",Toast.LENGTH_SHORT).show();
            }else{

                String r = result.getContents();
                idBA.setText(r);

            }

        } else {

            super.onActivityResult(requestCode, resultCode, data);
        }


    }



    public void buscar1(View view){

        String id=idBA.getText().toString();
if(!id.isEmpty()){
        SQLiteService admin = new SQLiteService(this);
        SQLiteDatabase db = admin.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM productos WHERE id='"+ id+"'", null);


        if (c.getCount() == 1) {

            c.moveToFirst();
            String desc = c.getString(1);
            String prec=Float.toString(c.getFloat(2));
            String stock=Integer.toString(c.getInt(3));

            Intent intent = new Intent(MainActivity6.this, MainActivity5.class);
            intent.putExtra("intentID",id );
            intent.putExtra("intentDesc",desc);
            intent.putExtra("intentPrecio",prec );
            intent.putExtra("intentStock",stock );
            startActivity(intent);

        } else {

            Toast.makeText(this, "El producto no esta registrado", Toast.LENGTH_SHORT).show();
        }

        db.close();
        c.close();

    }else {

    idBA.setError("Ingresar codigo");


}

    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        Intent intent = new Intent(MainActivity6.this,Prueba.class);
        startActivity(intent);
        return super.onKeyUp(keyCode, event);
    }


}