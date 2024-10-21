package com.example.gestion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class More extends AppCompatActivity {
    private FloatingActionButton b10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        b10=findViewById(R.id.botonir);
        eliminando();
        b10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent is = new Intent(More.this, MainActivity2.class);
                startActivity(is);

            }
        });

    }

    public void eliminando(){



        SQLiteService admin = new SQLiteService(this);
        SQLiteDatabase db = admin.getWritableDatabase();

        db.execSQL("DELETE FROM listacompra");


        db.close();


    }

}