package com.example.mandiapp;
import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;


public class crop extends AppCompatActivity {

    private String getCrop,getFarmer,getPhone,getAddress,getSelectDate;
    private RecyclerView recyclerView;
    private TextView TotalWeight;
    private EditText weight,removeNo;
    private Button next,createPdf;
    ArrayList<cropModel> itemList = new ArrayList<>();
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        getCrop = getIntent().getExtras().getString("keyname1");
        getFarmer = getIntent().getExtras().getString("keyname2");
        getPhone= getIntent().getExtras().getString("keyname3");
        getAddress = getIntent().getExtras().getString("keyname4");
        getSelectDate = getIntent().getExtras().getString("keyname5");

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        recyclerView = findViewById(R.id.recyclerview1);
        TotalWeight = findViewById(R.id.TotalWeight);
        weight = findViewById(R.id.weight);
        next = findViewById(R.id.next);
        createPdf = findViewById(R.id.createPdf);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new CustomAdapter(itemList);
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull  RecyclerView recyclerView, @NonNull  RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull  RecyclerView.ViewHolder viewHolder, int direction) {
                itemList.remove(viewHolder.getAdapterPosition());
                adapter.notifyDataSetChanged();


                for (int j=0;j<itemList.size();j++){
                    cropModel a = itemList.get(j);
                    String serial=String.valueOf(1+j);
                    a.setSerial(serial);
                  //  Toast.makeText(crop.this,""+a.getSerial() +" " + a.getString(), Toast.LENGTH_SHORT).show();
                }


                addSum();
            }
        }).attachToRecyclerView(recyclerView);




        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s =weight.getText().toString();

                if (s.isEmpty())
                {
                    Toast.makeText(crop.this,"Enter the weight",Toast.LENGTH_SHORT).show();
                }
                else{
                    try {
                        addSerial();

                    }catch (NumberFormatException e){

                    }
                 addSum();
                }

            }
        });
        createPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ActivityCompat.requestPermissions(crop.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
                try {
                    createPdf2();
                } catch (FileNotFoundException e){
                    e.printStackTrace();
                }


            }
        });
    }

    private void createPdf2() throws FileNotFoundException {

        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(pdfPath,getFarmer+getPhone+".pdf");
        OutputStream outputStream = new FileOutputStream(file);

        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);


        Text text1 = new Text("Date :-"+" "+getSelectDate+"\n");
        Text text2 = new Text("Crop :-"+" "+getCrop+"\n");
        Text text3 = new Text("Name :-"+" "+getFarmer+"\n");
        Text text4 = new Text("Address :-"+" "+getAddress+"\n");
        Text text5 = new Text("Phone :-"+" "+getPhone);

        Paragraph paragraph = new Paragraph();
        paragraph.add(text1)
                .add(text2)
                .add(text3)
                .add(text4)
                .add(text5);
        document.add(paragraph);

        float columnWidth[] = {200f,200f};
        Table table2= new Table(columnWidth);
        Table table= new Table(columnWidth);
        for (int i=0;i<itemList.size();i++)
        {
            cropModel cropModel= itemList.get(i);
            String weight2= cropModel.getString();
            String SerialNo= cropModel.getSerial();

            table2.addCell(SerialNo);
            table2.addCell(weight2).setFontSize(15);
        }
        document.add(table2);
        double b=0,c=0;
        for (int i=0;i<itemList.size();i++)
        {
            cropModel cropModel= itemList.get(i);
            String weight3= cropModel.getString();
            double a = Double.parseDouble(weight3);
            c=b+a;
            b=c;
        }
        String sum = String.valueOf(c);

        table.addCell("TOTAL").setBold().setFontSize(15);
        table.addCell(sum).setBold().setFontSize(15);
        document.add(table);
        document.close();
        Toast.makeText(this,"Save " +pdfPath,Toast.LENGTH_LONG).show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage("Do you want to exit this record?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {

                        finish();
                        //close();


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();


        return super.onOptionsItemSelected(item);
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey();

            //moveTaskToBack(false);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void exitByBackKey() {

        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage("Do you want to exit this record?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {

                        finish();
                        //close();


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();

    }

    public void addSerial()
    {

        String weight1 = weight.getText().toString();
        String serial=String.valueOf(1+adapter.getItemCount());
        cropModel cpModel = new cropModel(serial,weight1);
        itemList.add(cpModel);
        weight.setText("");

        adapter.notifyItemInserted(itemList.size()-1);


    }

    public void addSum()
    {
        double b=0.0,c=0.0;
        for (int i=0;i<itemList.size();i++)
        {
            cropModel cropModel= itemList.get(i);
            String weight3= cropModel.getString();
            double a = Double.parseDouble(weight3);
            c=b+a;
            b=c;
        }
        double sum = c ;
        double number2 = (int)(Math.round(sum * 100))/100.0;
        TotalWeight.setText(new DecimalFormat("##.##").format(number2));
    }

}