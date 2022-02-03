package com.example.mandiapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class FarmerDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText farmerName,phoneNo,address,cropDetails;
    private TextView selectDate;
    private Button submit;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_details);
        cropDetails = findViewById(R.id.spinnerCrop);
        farmerName = findViewById(R.id.farmerName);
        phoneNo = findViewById(R.id.phoneNo);
        address = findViewById(R.id.address);
        selectDate = findViewById(R.id.date);
        submit = findViewById(R.id.submit);

        dateDisplay();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String selectDate2 = selectDate.getText().toString();
                String crop= cropDetails.getText().toString();
                String farmer=farmerName.getText().toString();
                String phone=phoneNo.getText().toString();
                String address2=address.getText().toString();

                if (TextUtils.isEmpty(selectDate2))
                {
                    selectDate.setError("Date is Required");
                    return;
                }
                if (TextUtils.isEmpty(farmer))
                {
                    farmerName.setError("FarmerName is Required");
                    return;
                }
                if (TextUtils.isEmpty(phone))
                {
                    phoneNo.setError("PhoneNo. is Required");
                    return;
                }
                if (TextUtils.isEmpty(address2))
                {
                    address.setError("Address is Required");
                    return;
                }

                Intent intent =new Intent(FarmerDetails.this, crop.class);
                intent.putExtra("keyname1",crop);  //Selected Crop
                intent.putExtra("keyname2",farmer); //farmer name
                intent.putExtra("keyname3",phone);  //farmer phoneno.
                intent.putExtra("keyname4",address2); //farmer address
                intent.putExtra("keyname5",selectDate2);  // date
                startActivity(intent);




            }
        });


    }

    private void dateDisplay() {

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        FarmerDetails.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;


                selectDate.setText(date);
                //  Toast.makeText(teacherpanel.this, date, Toast.LENGTH_LONG).show();
            }
        };


    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {
      //     ((TextView) adapterView.getChildAt(0)).setTextColor(Color.GRAY);
      //     ((TextView) adapterView.getChildAt(0)).setTextSize(20);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}