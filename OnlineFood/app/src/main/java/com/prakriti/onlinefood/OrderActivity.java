package com.prakriti.onlinefood;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.prakriti.onlinefood.Pojo.FoodDetails;

import static android.R.attr.name;
import static android.R.layout.simple_spinner_dropdown_item;

public class OrderActivity extends AppCompatActivity {

    EditText ordername, orderaddress, orderphone;
    Button orderbtnmsg,orderok, orderbtnemail;
    Spinner spinner_quantity;
    String foodname;



    String quantity[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        final FoodDetails foodDetails = (FoodDetails) getIntent().getSerializableExtra("data");
        foodname = foodDetails.getName();



        final String[] emailaddress = {"prakritiadhikari2@gmail.com"};

        orderaddress = (EditText) findViewById(R.id.activity_order_address);
        ordername = (EditText) findViewById(R.id.activity_order_name);
        orderok = (Button) findViewById(R.id.btnorder_ok);
        orderphone = (EditText) findViewById(R.id.activity_order_phone);
        orderbtnmsg= (Button) findViewById(R.id.activity_order_btnmsg);
        orderbtnemail= (Button) findViewById(R.id.activity_order_btnemail);

        spinner_quantity = (Spinner) findViewById(R.id.spinner_quantity);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, quantity);
        arrayAdapter.setDropDownViewResource(simple_spinner_dropdown_item);
        spinner_quantity.setAdapter(arrayAdapter);

        orderok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OrderActivity.this, "Order recevied sucessfully", Toast.LENGTH_SHORT).show();
            }
        });

      /*  orderbtnemail.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        orderbtnemail.setBackgroundColor(Color.BLUE);
                        break;
                    case MotionEvent.ACTION_UP:
                        orderbtnemail.setBackgroundColor(Color.CYAN);
                        break;
                }
                return true;
            }

        });*/
        orderbtnmsg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name =ordername.getText().toString();
                String address=orderaddress.getText().toString();
                String phone=orderphone.getText().toString();
                String quantity=spinner_quantity.getSelectedItem().toString();

                if    (name.length()<=0 || address.length()<=0 || phone.length()<=0 )
                {
                    Toast.makeText(OrderActivity.this, "Please, fill all the fields! ", Toast.LENGTH_SHORT).show();
                }

                else if (!isValidContact(phone)){
                    orderphone.setError("Please enter your valid number");
                }

                else{
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + 1234567890));
                    intent.putExtra("sms_body", "I want to order"+foodname +name +address +quantity);

                    startActivity(intent);

                }
            }
        });
               // Intent sendIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("smsto", phoneNumber, null));
                //sendIntent.putExtra("sms_body", "text message");
                //startActivity(sendIntent);
               // sendSMS("9849398786", name + address + phone +quantity);
                //Toast.makeText(OrderActivity.this, "Ur messege is sent", Toast .LENGTH_SHORT).show();
           /*here i can send message to emulator 5556. In Real device
            *you can change number*/




        orderbtnemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                composeEmail(emailaddress, "Order");
            }
        });
    }


    public void composeEmail(String[] addresses, String subject) {
        String name =ordername.getText().toString();
        String address=orderaddress.getText().toString();
        String quantity=spinner_quantity.getSelectedItem().toString();

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, name +" wants to order " +foodname + " of quantity: " +quantity + " at " + address);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private boolean isValidContact(String sphone) {
        if (sphone != null && sphone.length() == 10){
            return true;
        }
        return false;
    }
}