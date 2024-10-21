package com.example.gestion;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.github.mikephil.charting.utils.ColorTemplate;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import harmony.java.awt.Color;
import repack.org.bouncycastle.jce.provider.symmetric.ARC4;

public class FinalizarPedido extends AppCompatActivity {

    private Button finventa;
    private final static String NOMBRE_DIRECTORIO = "GestorDeVenta";
    private final static String ETIQUETA_ERROR = "ERROR";
    private ArrayList<ProductoStock> dt= new ArrayList<>();
    private  File f;
    private EditText nomCli, telCli , dirCli , mailCli, razonsocial, cuit,telEmp,descuentoporcentaje;
    private RadioButton r1,r2,r3,radioButton,radioButton2,radioButton3,radioButton4;
    private CheckBox recordar;
    private RadioGroup rg;
    float totalTotal = 0;
        String path;

    String nombreCli;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizar_pedido);


        finventa=findViewById(R.id.finventa);
        nomCli=findViewById(R.id.nomCli);
        telCli=findViewById(R.id.telCli);
        dirCli=findViewById(R.id.dirCli);
        mailCli=findViewById(R.id.mailCli);
        rg =findViewById(R.id.rg);
        descuentoporcentaje=findViewById(R.id.descuentoporcentaje);

        razonsocial=findViewById(R.id.razonsocial);
        cuit=findViewById(R.id.cuit);
        telEmp=findViewById(R.id.telEmp);

        recordar=findViewById(R.id.recordar);

        r1=findViewById(R.id.r1);
        r2=findViewById(R.id.r2);
        r3=findViewById(R.id.r3);

        radioButton=findViewById(R.id.radioButton);
        radioButton2=findViewById(R.id.radioButton2);

        radioButton3=findViewById(R.id.radioButton3);
        radioButton4=findViewById(R.id.radioButton4);

        r1.setChecked(true);
        radioButton2.setChecked(true);
        descuentoporcentaje.setEnabled(false);
        radioButton3.setChecked(true);



        if(radioButton.isChecked() && radioButton3.isChecked()){

            totalTotal=Float.parseFloat( getIntent().getStringExtra("impcad")) + (((Float.parseFloat( getIntent().getStringExtra("impcad")))* 21)/100) - (((Float.parseFloat( getIntent().getStringExtra("impcad")))* (Integer.parseInt(descuentoporcentaje.getText().toString())))/100) ;

        }

        if(!radioButton.isChecked() && radioButton3.isChecked()){

            totalTotal=Float.parseFloat( getIntent().getStringExtra("impcad")) + (((Float.parseFloat( getIntent().getStringExtra("impcad")))* 21)/100)  ;

        }

        if(!radioButton.isChecked() && !radioButton3.isChecked()){

            totalTotal=Float.parseFloat( getIntent().getStringExtra("impcad")) ;

        }

        if(radioButton.isChecked() && !radioButton3.isChecked()){

            totalTotal=Float.parseFloat( getIntent().getStringExtra("impcad"))  - (((Float.parseFloat( getIntent().getStringExtra("impcad")))* (Integer.parseInt(descuentoporcentaje.getText().toString())))/100) ;

        }

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId==R.id.radioButton)
                {
                    changeEditTextAvailability(descuentoporcentaje, true);
                }

                if(checkedId==R.id.radioButton2)
                {
                    changeEditTextAvailability(descuentoporcentaje, false);
                }


            }
        });


        cargarPreferencias();


        finventa.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



                if(!nomCli.getText().toString().isEmpty()){ if (recordar.isChecked()){

                    guardarPreferencias();
                }else {guardarPreferenciasVacias();}

                if(!descuentoporcentaje.getText().toString().isEmpty() && radioButton.isChecked()) {

                    generarPdf();

                    guardarHistorial();
                    finalizar();

                }

                if(descuentoporcentaje.getText().toString().isEmpty() && radioButton.isChecked()){

                    descuentoporcentaje.setError("Ingrese %");
                }

                if (radioButton2.isChecked()){
                    generarPdf();
                    guardarHistorial();
                    finalizar();

                }}else {

                    nomCli.setError("Ingrese nombre del cliente");
                }

            }


        });

    }


    public void guardarHistorial(){


        int dia,mes,anio,hora,minuto;
        Calendar fecha = Calendar.getInstance();
        dia=fecha.get(Calendar.DAY_OF_MONTH);
        mes=fecha.get(Calendar.MONTH)+1;
        anio=fecha.get(Calendar.YEAR);
        hora=fecha.get(Calendar.HOUR_OF_DAY);
        minuto=fecha.get(Calendar.MINUTE);

        String minuto1;
        String hora12;

        if(minuto>=0 && minuto<=9){

            minuto1="0"+minuto;
        }else {

            minuto1=""+minuto;
        }

        if(hora>=0 && hora<=9){

            hora12="0"+hora;
        }else {

            hora12=""+hora;
        }


        String fecha1=dia+"/"+mes+"/"+anio;
        String hora1=hora12+":"+minuto1;

        SQLiteService admin = new SQLiteService(getApplicationContext());
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();


        ContentValues registro = new ContentValues();

            registro.put("fecha", fecha1);
            registro.put("hora", hora1);
            registro.put("total", Float.toString(Float.parseFloat(String.format("%.2f",totalTotal))));
            registro.put("nombCliente", nomCli.getText().toString());
            registro.put("cantidadProductos",Integer.parseInt(getIntent().getStringExtra("cantArt")));

            BaseDeDatos.insert("historial", null, registro);

            BaseDeDatos.close();


    }

private   void cargarPreferencias(){

    SharedPreferences preferences = getSharedPreferences("datosEmpresa", Context.MODE_PRIVATE);
    String rs = preferences.getString("rs","");
    String cuitemp = preferences.getString("cuitemp","");
    String telEmpresa = preferences.getString("telEmpresa","");
    boolean estado=preferences.getBoolean("estado",false);

    razonsocial.setText(rs);
    cuit.setText(cuitemp);
    telEmp.setText(telEmpresa);
    recordar.setChecked(estado);

}

private void guardarPreferencias(){
    SharedPreferences preferences = getSharedPreferences("datosEmpresa", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor=preferences.edit();
    String rs = razonsocial.getText().toString();
    String cuitemp =cuit.getText().toString();
    String telEmpresa =telEmp.getText().toString();

    editor.putString("rs",rs);
    editor.putString("cuitemp",cuitemp);
    editor.putString("telEmpresa",telEmpresa);
    editor.putBoolean("estado",true);
    editor.commit();

}

private void guardarPreferenciasVacias(){

    SharedPreferences preferences = getSharedPreferences("datosEmpresa", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor=preferences.edit();
    String rs = "";
    String cuitemp ="";
    String telEmpresa ="";

    editor.putString("rs",rs);
    editor.putString("cuitemp",cuitemp);
    editor.putString("telEmpresa",telEmpresa);
    editor.putBoolean("estado",false);
    editor.commit();

}

    public void finalizar(){

        SQLiteService admin = new SQLiteService(this);
        SQLiteDatabase db = admin.getReadableDatabase();
        SQLiteDatabase db1 = admin.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM listacompra ", null);

        if (c.moveToFirst()) {
            do {
                c.getString(0);
                 c.getInt(3);

                Cursor j = db.rawQuery("SELECT * FROM productos WHERE id='"+c.getString(0) +"'", null);
                j.moveToFirst();
                if(j.getCount()!=0) {
                    j.getInt(3);
                    int p=(j.getInt(3)) - (c.getInt(3));

                    db1.execSQL("UPDATE productos SET cant=" + p + " WHERE id='" + c.getString(0) + "'");
                }
            } while (c.moveToNext());
        }

        c.close();

        db.close();
        db1.close();


        Intent intent = new Intent(FinalizarPedido.this, MostrarPDF.class);
        intent.putExtra("nombrearchivo",path);
        startActivity(intent);



    }





    public void generarPdf() {

        dt=datosTabla();

        // Creamos el documento.
        Document documento = new Document();
        int dia,mes,anio,hora,minuto,segundo;
        Calendar fecha = Calendar.getInstance();
        dia=fecha.get(Calendar.DAY_OF_MONTH);
        mes=fecha.get(Calendar.MONTH)+1;
        anio=fecha.get(Calendar.YEAR);
        hora=fecha.get(Calendar.HOUR_OF_DAY);
        minuto=fecha.get(Calendar.MINUTE);
        segundo=fecha.get(Calendar.SECOND);

        path=nomCli.getText().toString().replace(" ","")+"-"+dia+mes+anio+"-"+hora+minuto+segundo+".pdf";

        String minuto1;
        String hora12;
        String segundo1;
        if(minuto>=0 && minuto<=9){

            minuto1="0"+minuto;
        }else {

            minuto1=""+minuto;
        }

        if(hora>=0 && hora<=9){

            hora12="0"+hora;
        }else {

            hora12=""+hora;
        }

        if(segundo>=0 && segundo<=9){

            segundo1="0"+segundo;
        }else {

            segundo1=""+segundo;
        }

        if(radioButton.isChecked() && radioButton3.isChecked()){

            totalTotal=Float.parseFloat( getIntent().getStringExtra("impcad")) + (((Float.parseFloat( getIntent().getStringExtra("impcad")))* 21)/100) - (((Float.parseFloat( getIntent().getStringExtra("impcad")))* (Integer.parseInt(descuentoporcentaje.getText().toString())))/100) ;

        }

        if(!radioButton.isChecked() && radioButton3.isChecked()){

            totalTotal=Float.parseFloat( getIntent().getStringExtra("impcad")) + (((Float.parseFloat( getIntent().getStringExtra("impcad")))* 21)/100)  ;

        }

        if(!radioButton.isChecked() && !radioButton3.isChecked()){

            totalTotal=Float.parseFloat( getIntent().getStringExtra("impcad")) ;

        }

        if(radioButton.isChecked() && !radioButton3.isChecked()){

            totalTotal=Float.parseFloat( getIntent().getStringExtra("impcad"))  - (((Float.parseFloat( getIntent().getStringExtra("impcad")))* (Integer.parseInt(descuentoporcentaje.getText().toString())))/100) ;

        }

        String formatoFecha =dia+"/"+mes+"/"+anio;

        String seleccion="";

        String telefonoCli;
        String direccionCli;
        String mailCli1;
        String rs;
        String cuit12;
        String telEmp1;
        String aplicacionIva ="";
        String aplicacionDesc ="";

        if(r1.isChecked()){ seleccion += "A";
        r2.setChecked(false);
        r3.setChecked(false);
        }
        if(r2.isChecked()){ seleccion += "B";
            r1.setChecked(false);
            r3.setChecked(false);
        }
        if(r3.isChecked()){ seleccion += "C";
            r2.setChecked(false);
            r1.setChecked(false);
        }



        if(radioButton.isChecked()){ radioButton2.setChecked(false);
            aplicacionDesc=descuentoporcentaje.getText().toString()+"%";
        }
        if(radioButton2.isChecked()){ radioButton.setChecked(false);
            aplicacionDesc="No Aplicado";
        }

        if(radioButton3.isChecked()){ radioButton4.setChecked(false);
        aplicacionIva="Aplicado";
        }
        if(radioButton4.isChecked()){ radioButton3.setChecked(false);
            aplicacionIva="No Aplicado";
        }





        if(!(nomCli.getText().toString()).isEmpty()){ nombreCli=nomCli.getText().toString();}else{nombreCli=" ";}
        if(!(telCli.getText().toString()).isEmpty()){ telefonoCli=telCli.getText().toString();}else{telefonoCli=" ";}
        if(!(dirCli.getText().toString()).isEmpty()){ direccionCli=dirCli.getText().toString();}else{direccionCli=" ";}
        if(!(mailCli.getText().toString()).isEmpty()){ mailCli1=mailCli.getText().toString();}else{mailCli1=" ";}

        if(!(razonsocial.getText().toString()).isEmpty()){ rs=razonsocial.getText().toString();}else{rs=" ";}
        if(!(cuit.getText().toString()).isEmpty()){ cuit12=cuit.getText().toString();}else{cuit12=" ";}
        if(!(telEmp.getText().toString()).isEmpty()){ telEmp1=telEmp.getText().toString();}else{telEmp1=" ";}


        try {

             f= crearFichero(path);

            // Creamos el flujo de datos de salida para el fichero donde
            // guardaremos el pdf.
            FileOutputStream ficheroPdf = new FileOutputStream(f.getAbsolutePath());

            // Asociamos el flujo que acabamos de crear al documento.
            PdfWriter writer = PdfWriter.getInstance(documento, ficheroPdf);


            // Abrimos el documento.
            documento.open();


            Font font = FontFactory.getFont(FontFactory.HELVETICA, 28, Font.NORMAL, Color.BLACK);
            Font font1 = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD, Color.WHITE);
            Font font2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, Font.BOLD, Color.BLACK);
            Font font3 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD, Color.BLACK);
            Font font4 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 5, Font.BOLD, Color.BLACK);
            Font font5 = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD, Color.BLACK);
            Font font6 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD, Color.BLACK);

            documento.add(new Paragraph("Factura", font));

            documento.add(new Paragraph(" ",font4));
            documento.add(new Paragraph("Fecha de factura: "+formatoFecha, font3));
            documento.add(new Paragraph("Hora de factura: "+hora12+":"+minuto1+":"+segundo1, font3));
            documento.add(new Paragraph("Tipo de factura: "+seleccion, font3));
            documento.add(new Paragraph(" ",font4));

            PdfPTable tabla10 = new PdfPTable(2);
            tabla10.setWidthPercentage(100);
            PdfPCell celdainfo = new PdfPCell(new Phrase("Datos del cliente:",font2));
            celdainfo.setBorder(Rectangle.BOTTOM);
            tabla10.addCell(celdainfo);


            PdfPCell celdainfo10 = new PdfPCell(new Phrase("Datos de la empresa:",font2));
            celdainfo10.setBorder(Rectangle.BOTTOM);
            tabla10.addCell(celdainfo10);
            documento.add(tabla10);

            PdfPTable tabla11 = new PdfPTable(2);
            tabla11.setWidthPercentage(100);
            PdfPCell celdainfo1 = new PdfPCell(new Phrase("Nombre del Cliente: "+nombreCli,font3));
            celdainfo1.setBorder(Rectangle.NO_BORDER);
            tabla11.addCell(celdainfo1);
            PdfPCell celdainfo2 = new PdfPCell(new Phrase("Razon social: "+rs,font3));
            celdainfo2.setBorder(Rectangle.NO_BORDER);
            tabla11.addCell(celdainfo2);
            documento.add(tabla11);


            PdfPTable tabla12 = new PdfPTable(2);
            tabla12.setWidthPercentage(100);
            PdfPCell celdainfo3 = new PdfPCell(new Phrase("Telefono: "+telefonoCli,font3));
            celdainfo3.setBorder(Rectangle.NO_BORDER);
            tabla12.addCell(celdainfo3);
            PdfPCell celdainfo4 = new PdfPCell(new Phrase("CUIT: "+cuit12,font3));
            celdainfo4.setBorder(Rectangle.NO_BORDER);
            tabla12.addCell(celdainfo4);
            documento.add(tabla12);


            PdfPTable tabla13 = new PdfPTable(2);
            tabla13.setWidthPercentage(100);
            PdfPCell celdainfo5 = new PdfPCell(new Phrase("Direccion: "+direccionCli,font3));
            celdainfo5.setBorder(Rectangle.NO_BORDER);
            tabla13.addCell(celdainfo5);
            PdfPCell celdainfo6 = new PdfPCell(new Phrase("Telefono: "+telEmp1,font3));
            celdainfo6.setBorder(Rectangle.NO_BORDER);
            tabla13.addCell(celdainfo6);
            documento.add(tabla13);

            PdfPTable tabla14 = new PdfPTable(2);
            tabla14.setWidthPercentage(100);
            PdfPCell celdainfo7 = new PdfPCell(new Phrase("Email: "+mailCli1,font3));
            celdainfo7.setBorder(Rectangle.NO_BORDER);
            tabla14.addCell(celdainfo7);
            PdfPCell celdainfo8 = new PdfPCell(new Phrase("",font3));
            celdainfo8.setBorder(Rectangle.NO_BORDER);
            tabla14.addCell(celdainfo8);
            documento.add(tabla14);

            documento.add(new Paragraph(" "));


            PdfPTable tabla1 = new PdfPTable(4);
            tabla1.setWidthPercentage(100);
            PdfPCell cell21 = new PdfPCell(new Phrase("Cod.Art"));
            cell21.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell21.setVerticalAlignment(Element.ALIGN_CENTER);
            cell21.setBackgroundColor(Color.YELLOW);
            tabla1.addCell(cell21);

            PdfPCell cell22 = new PdfPCell(new Phrase("Descripcion"));
            cell22.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell22.setVerticalAlignment(Element.ALIGN_CENTER);
            cell22.setBackgroundColor(Color.YELLOW);
            tabla1.addCell(cell22);

            PdfPCell cell23 = new PdfPCell(new Phrase("Cantidad"));
            cell23.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell23.setVerticalAlignment(Element.ALIGN_CENTER);
            cell23.setBackgroundColor(Color.YELLOW);
            tabla1.addCell(cell23);

            PdfPCell cell24 = new PdfPCell(new Phrase("Precio Unitario"));
            cell24.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell24.setVerticalAlignment(Element.ALIGN_CENTER);
            cell24.setBackgroundColor(Color.YELLOW);
            tabla1.addCell(cell24);


            documento.add(tabla1);


            // Insertamos una tabla.
            PdfPTable tabla = new PdfPTable(4);
            tabla.setWidthPercentage(100);
            for (int i = 0; i < dt.size(); i++) {

                PdfPCell celda1 = new PdfPCell(new Phrase(dt.get(i).getId()));
                celda1.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabla.addCell(celda1);

                PdfPCell celda2 = new PdfPCell(new Phrase(dt.get(i).getDescripcion()));
                celda2.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabla.addCell(celda2);

                PdfPCell celda3 = new PdfPCell(new Phrase(Integer.toString(dt.get(i).getStock())));
                celda3.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabla.addCell(celda3);

                PdfPCell celda4 = new PdfPCell(new Phrase("$"+ dt.get(i).getPrecio()));
                celda4.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabla.addCell(celda4);


            }
            documento.add(tabla);


            PdfPTable table8 = new PdfPTable(4);

            table8.setWidthPercentage(100);
            PdfPCell cell5 = new PdfPCell(new Phrase(""));


            cell5.setBorder(Rectangle.NO_BORDER);
            table8.addCell(cell5);

            PdfPCell cell6 = new PdfPCell(new Phrase(""));
            cell6.setBorder(Rectangle.NO_BORDER);
            table8.addCell(cell6);



            PdfPCell cell7 = new PdfPCell(new Phrase("SubTotal:",font5));
            cell7.setBorder(Rectangle.NO_BORDER);
            cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
            table8.addCell(cell7);

            PdfPCell cell8 = new PdfPCell(new Phrase("$"+String.format("%.2f",Float.parseFloat(getIntent().getStringExtra("impcad"))),font5));
            cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell8.setBorder(Rectangle.NO_BORDER);
            table8.addCell(cell8);
            documento.add(table8);

//----------------------------------------------------------------------------------------------------

            PdfPTable table99 = new PdfPTable(4);
            table99.setWidthPercentage(100);

            PdfPCell cell9 = new PdfPCell(new Phrase(""));
            cell9.setBorder(Rectangle.NO_BORDER);
            table99.addCell(cell9);

            PdfPCell cell10 = new PdfPCell(new Phrase(""));
            cell10.setBorder(Rectangle.NO_BORDER);
            table99.addCell(cell10);



            PdfPCell cell11 = new PdfPCell(new Phrase("Descuento: ",font5));

            cell11.setBorder(Rectangle.NO_BORDER);
            cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
            table99.addCell(cell11);

            PdfPCell cell12 = new PdfPCell(new Phrase(aplicacionDesc,font5));

            cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell12.setBorder(Rectangle.NO_BORDER);
            table99.addCell(cell12);
            documento.add(table99);

//--------------------------------------------------------------------------------------------------

//----------------------------------------------------------------------------------------------------

            PdfPTable table98 = new PdfPTable(4);
            table98.setWidthPercentage(100);

            PdfPCell cell13 = new PdfPCell(new Phrase(""));
            cell13.setBorder(Rectangle.NO_BORDER);
            table98.addCell(cell13);

            PdfPCell cell14 = new PdfPCell(new Phrase(""));
            cell14.setBorder(Rectangle.NO_BORDER);
            table98.addCell(cell14);



            PdfPCell cell15 = new PdfPCell(new Phrase("IVA (21%): ",font5));

            cell15.setBorder(Rectangle.NO_BORDER);
            cell15.setHorizontalAlignment(Element.ALIGN_CENTER);
            table98.addCell(cell15);

            PdfPCell cell16 = new PdfPCell(new Phrase(aplicacionIva,font5));

            cell16.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell16.setBorder(Rectangle.NO_BORDER);
            table98.addCell(cell16);
            documento.add(table98);

//--------------------------------------------------------------------------------------------------

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);

            PdfPCell cell1 = new PdfPCell(new Phrase(""));
            cell1.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell1);

            PdfPCell cell2 = new PdfPCell(new Phrase(""));
            cell2.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell2);


            PdfPCell cell3 = new PdfPCell(new Phrase("Total:",font1));
            cell3.setBackgroundColor(Color.BLACK);
            cell3.setBorder(Rectangle.NO_BORDER);
            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell3);

            PdfPCell cell4 = new PdfPCell(new Phrase("$"+ String.format("%.2f",totalTotal),font1));
            cell4.setBackgroundColor(Color.BLACK);
            cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell4.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell4);
            documento.add(table);



        } catch (DocumentException e) {

            Log.e(ETIQUETA_ERROR, e.getMessage());

        } catch (IOException e) {

            Log.e(ETIQUETA_ERROR, e.getMessage());

        } finally {
            // Cerramos el documento.
            documento.close();
        }
    }


    public static File crearFichero(String nombreFichero) throws IOException {
        File ruta = getRuta();
        File fichero = null;
        if (ruta != null)
            fichero = new File(ruta, nombreFichero);
        return fichero;
    }


    public static File getRuta() {

        // El fichero sera almacenado en un directorio dentro del directorio
        // Descargas
        File ruta = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            ruta = new File(Environment.getExternalStorageDirectory().toString() + "/Download");

            if (ruta != null) {
                if (!ruta.mkdirs()) {
                    if (!ruta.exists()) {
                        return null;
                    }
                }
            }
        } else {
        }

        return ruta;
    }

    public ArrayList<ProductoStock> datosTabla() {

        SQLiteService admin = new SQLiteService(this);
        SQLiteDatabase db = admin.getReadableDatabase();

        ArrayList<ProductoStock> listaContactos10 = new ArrayList<>();
        ProductoStock producto;
        Cursor  c = db.rawQuery("SELECT * FROM listacompra ", null);

        if (c.moveToFirst()) {
            do {
                producto = new ProductoStock();
                producto.setId(c.getString(0));
                producto.setDescripcion(c.getString(1));
                producto.setPrecio(c.getFloat(2));
                producto.setStock(c.getInt(3));
                listaContactos10.add(producto);
            } while (c.moveToNext());
        }

        c.close();
        db.close();

        return listaContactos10;

    }

    private void changeEditTextAvailability(EditText editText, boolean status) {
        editText.setFocusable(status);
        editText.setEnabled(status);
        editText.setCursorVisible(status);
        editText.setFocusableInTouchMode(status);
    }


}