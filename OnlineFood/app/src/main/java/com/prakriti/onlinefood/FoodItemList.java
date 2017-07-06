package com.prakriti.onlinefood;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.prakriti.onlinefood.Adapter.FoodItemListAdapter;
import com.prakriti.onlinefood.Pojo.FoodDetails;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FoodItemList extends AppCompatActivity {
    TabLayout tabLayout;
    ListView listView;
    CarouselView carouselview;
    Button map;
    List<FoodDetails> foodDetailslist = new ArrayList<>();
    int[] images = {R.drawable.fries, R.drawable.momo, R.drawable.murgha, R.drawable.pizza};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_item_list);
        map = (Button) findViewById(R.id.map);
        listView = (ListView) findViewById(R.id.fooditemlist);
        carouselview = (CarouselView) findViewById(R.id.carouselview);
        carouselview.setPageCount(images.length);
        carouselview.setImageListener(imagelistener);
        new FoodAsyncTask().execute();

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FoodItemList.this, MapsActivity.class);
                startActivity(i);
            }
        });

    }


    ImageListener imagelistener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(images[position]);
        }
    };




    class FoodAsyncTask extends AsyncTask<String, String, String>{
        ProgressDialog progressDialog;
        int flag = 0;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(FoodItemList.this);
            progressDialog.setMessage("Plz wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> loginhashmap = new HashMap<>();
            JsonParser jsonParser = new JsonParser();
            JSONObject jsonObject = jsonParser.performPostCI("http://kinbech.6te.net/ResturantFoods/api/showMenuList", loginhashmap);
            try {
                if (jsonObject == null){
                    flag =1;

                }
                else if (jsonObject.getString("status").equals("success")){
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i<jsonArray.length(); i++)
                    {
                        JSONObject dataobject = jsonArray.getJSONObject(i);
                        String id = dataobject.getString("id");
                        String name = dataobject.getString("name");
                        String price = dataobject.getString("price");
                        String description = dataobject.getString("details");
                        String image = dataobject.getString("image");
                        String material = dataobject.getString("materials");

                        FoodDetails foodDetails = new FoodDetails(id,name, price, description,image,material);
                        foodDetailslist.add(foodDetails);
                        flag = 2;


                    }

                }
                else {
                    flag = 3;
                }

        } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
            @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if (flag==1){
                Toast.makeText(FoodItemList.this, "Server error", Toast.LENGTH_SHORT).show();
            }
            else if(flag ==2) {
                Toast.makeText(FoodItemList.this, "Successfull", Toast.LENGTH_SHORT).show();
               FoodItemListAdapter foodItemListAdapter = new FoodItemListAdapter(FoodItemList.this,R.layout.foodlistadapter, foodDetailslist);
               listView.setAdapter(foodItemListAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        FoodDetails foodDetails = foodDetailslist.get(position);
                        Intent i = new Intent(FoodItemList.this, FoodDescription.class);
                        i.putExtra("data", foodDetails);
                        startActivity(i);
                    }
                });
            }
             else {
                Toast.makeText(FoodItemList.this, "Invalid Credentils", Toast.LENGTH_SHORT).show();
            }


        }
    }
}
