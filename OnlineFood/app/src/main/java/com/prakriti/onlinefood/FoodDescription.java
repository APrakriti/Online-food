package com.prakriti.onlinefood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.prakriti.onlinefood.Pojo.FoodDetails;

public class FoodDescription extends AppCompatActivity {
    ImageView imageViewdesc;
    Button smsbtn, phonebtn;
    TextView pricedesc, materialdesc;
    String foodname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_description);
        imageViewdesc = (ImageView) findViewById(R.id.image_description);
        smsbtn = (Button) findViewById(R.id.btn_order_sms);
        phonebtn = (Button) findViewById(R.id.btn_order_phn);
        pricedesc = (TextView) findViewById(R.id.price_desc);
        materialdesc = (TextView) findViewById(R.id.material_desc);
        FoodDetails foodDetails = (FoodDetails) getIntent().getSerializableExtra("data");


        Glide.with(FoodDescription.this).load(foodDetails.getImage()).into(imageViewdesc);
        pricedesc.setText("Rs."+ " " +foodDetails.getPrice());
        materialdesc.setText("Material used" + " " +foodDetails.getMaterial());

        smsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FoodDescription.this, OrderActivity.class);
                i.putExtra("data", foodname );
                startActivity(i);
            }
        });
    }
}
