package com.example.gestion;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Application;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.util.Objects;

public class MostrarPDF extends AppCompatActivity {
private Button visualizar , backhome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_pdf);
        visualizar=findViewById(R.id.visualizar);
        backhome=findViewById(R.id.backhome);



        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MostrarPDF.this, MainActivity.class);
                startActivity(intent);
            }
        });

        visualizar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onClick(View v) {
                irPDF();
            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void irPDF() {



       /* StrictMode.VmPolicy.Builder builder =new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        String storage = Environment.getExternalStorageDirectory().toString() + "/Download/GestorDeVenta/"+getIntent().getStringExtra("nombrearchivo");
        File file = new File(storage);
        Uri uri = Uri.fromFile(file);
        Intent viewFile = new Intent(Intent.ACTION_VIEW);
        viewFile.setData(uri);
        startActivity(viewFile);

        String storage = "content:///storage/emulated/0/Download/GestorDeVenta/"+getIntent().getStringExtra("nombrearchivo");
        Uri uri = Uri.parse(storage);
        Intent viewFile = new Intent(Intent.ACTION_VIEW);
        viewFile.setDataAndType(uri,"application/pdf");
        startActivity(viewFile);*/


        String storage = Environment.getExternalStorageDirectory().toString() + "/Download/"+getIntent().getStringExtra("nombrearchivo");


        StrictMode.VmPolicy.Builder builder =new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();



        File pdfFile = new File(storage);

       Uri excelPath = FileProvider.getUriForFile(this, getApplicationContext().getPackageName()+ ".provider", pdfFile);

        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);


        pdfIntent.setDataAndType(excelPath, "application/pdf");

        pdfIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION |Intent.FLAG_GRANT_PREFIX_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION|Intent.FLAG_EXCLUDE_STOPPED_PACKAGES | Intent.FLAG_ACTIVITY_NO_HISTORY);

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MostrarPDF.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,}, 1000);
        } else {
        }


            startActivity(pdfIntent);






        }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Advertencia");
        builder.setMessage("¿Quieres volver al inicio?");

        builder.setNegativeButton("Cancelar", null);


        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent = new Intent(MostrarPDF.this,MainActivity.class);
                startActivity(intent);
            }

        });
        builder.show();

        return super.onKeyUp(keyCode, event);
    }



    }




