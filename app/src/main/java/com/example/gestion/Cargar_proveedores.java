package com.example.gestion;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;

public class Cargar_proveedores extends AppCompatActivity {
private EditText numerotel,nombreprov,direccionprov;
private Button boton5,importar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargar_proveedores);

        numerotel = findViewById(R.id.numerotel);
        nombreprov = findViewById(R.id.nombreprov);
        direccionprov = findViewById(R.id.direccionprov);
        boton5 = findViewById(R.id.boton5);
        importar = findViewById(R.id.importar);


        importar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(intent,1);


            }
        });



        boton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String numero =numerotel.getText().toString();
                String nombre = nombreprov.getText().toString();
                String direccion = direccionprov.getText().toString();


                if (!numero.isEmpty() ) {
                    SQLiteService admin = new SQLiteService(getApplicationContext());
                    SQLiteDatabase db = admin.getReadableDatabase();

                    Cursor d = db.rawQuery("SELECT * FROM proveedores WHERE numero='"+numero+"'", null);

                    if (d.getCount() == 0) {

                        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

                        ContentValues registro = new ContentValues();

                        registro.put("numero", numero);
                        registro.put("nombre", nombre);
                        registro.put("direccion", direccion);


                        BaseDeDatos.insert("proveedores", null, registro);

                        BaseDeDatos.close();
                        numerotel.setText("");
                        nombreprov.setText("");
                        direccionprov.setText("");



                        Intent cambio = new Intent(getApplicationContext(), MainProveedores.class);
                        startActivity(cambio);
                        BaseDeDatos.close();


                    } else {

                        Toast.makeText(getApplicationContext(), "Ya se encuentra registrado", Toast.LENGTH_SHORT).show();
                    }

                    db.close();
                    d.close();

                }else{Toast.makeText(getApplicationContext(), "Debe llenar como minimo el campo del telefono", Toast.LENGTH_SHORT).show(); }
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK){

            Uri uri =data.getData();
            Cursor c =getContentResolver().query(uri,null,null,null,null);
            if (c.moveToFirst()) {

                int indice_nombre=c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                int indice_numero= c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

                String nom=c.getString(indice_nombre);
                String num=c.getString(indice_numero);

                num = num.replace(" ","").replace("-","");

                numerotel.setText(num);
                nombreprov.setText(nom);

            }


        }
    }
}