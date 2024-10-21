package com.example.gestion;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity7 extends AppCompatActivity {

   private TextView nombre1, telefono1, direccion1;
   private ImageButton wsp,llamada;
   private Button editar,basura;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);

        nombre1=findViewById(R.id.nombre1);
        telefono1=findViewById(R.id.telefono1);
        direccion1=findViewById(R.id.direccion1);

        wsp=findViewById(R.id.wsp);
        llamada=findViewById(R.id.llamada);

        editar=findViewById(R.id.editar);
        basura=findViewById(R.id.basura);

        nombre1.setText(getIntent().getStringExtra("nombre"));
        telefono1.setText("Telefono: "+getIntent().getStringExtra("numero"));
        direccion1.setText("Direccion: "+getIntent().getStringExtra("direccion"));


        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity7.this, ActualizarContacto.class);
                   intent.putExtra("numero1",getIntent().getStringExtra("numero"));
                   intent.putExtra("nombre1",getIntent().getStringExtra("nombre"));
                   intent.putExtra("direccion1",getIntent().getStringExtra("direccion"));
                startActivity(intent);

            }
        });


        wsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((getIntent().getStringExtra("numero")).isEmpty()){

                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT ,"");
                    sendIntent.setType("text/plain");
                    sendIntent.setPackage("com.whatsapp");

                }else {

                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_VIEW);
                    String uri = "whatsapp://send?phone="+getIntent().getStringExtra("numero")+"&text="+"";
                    sendIntent.setData(Uri.parse(uri));
                    startActivity(sendIntent);

                }
            }
        });

llamada.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent =new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("tel:"+getIntent().getStringExtra("numero")));
        startActivity(intent);
    }
});

basura.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity7.this);
        builder.setTitle("Advertencia");
        builder.setMessage("¿Esta seguro que desea eliminar este contacto?");
        builder.setNegativeButton("Cancelar", null);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                SQLiteService admin = new SQLiteService(getApplicationContext());
                SQLiteDatabase db = admin.getWritableDatabase();

                db.execSQL("DELETE FROM proveedores WHERE numero='"+getIntent().getStringExtra("numero")+"'");


                db.close();

                Intent intent = new Intent(MainActivity7.this, MainProveedores.class);
                startActivity(intent);

            }

        });
        builder.show();
    }
});


    }




}