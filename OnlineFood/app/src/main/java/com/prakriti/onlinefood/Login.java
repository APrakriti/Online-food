package com.prakriti.onlinefood;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Login extends AppCompatActivity {
    EditText name, password;
    Button login ;
    TextView createuser;
    CheckBox checkBox;
    ProgressDialog mprogressDialog;
    String sName, sPassword;
    JSONObject jsonObject;
    int flag;

    TextInputLayout inputLayoutemail, inputLayoutpassword, inputLayoutbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name = (EditText) findViewById(R.id.login_name);
        password = (EditText) findViewById(R.id.login_password);
        checkBox = (CheckBox) findViewById(R.id.login_checkbox);
        createuser = (TextView) findViewById(R.id.CreateUser);
        login = (Button) findViewById(R.id.btnlogin);
        inputLayoutemail = (TextInputLayout) findViewById(R.id.inputlayout_login_username);
        inputLayoutpassword = (TextInputLayout) findViewById(R.id.inputlayout_login_password);
        inputLayoutbtn = (TextInputLayout) findViewById(R.id.inputlayout_login_btn);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        createuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, SignIn.class);
                startActivity(i);
            }});


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = name.getText().toString();
                sPassword= password.getText().toString();



                if (sName.equals("") || sPassword.equals("")) {

                    Toast.makeText(getApplicationContext(), "Field Vaccant",
                            Toast.LENGTH_LONG).show();

                }



                else {
                    new loginAsyncTask().execute();
                }}
        });
    }


    class loginAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mprogressDialog = new ProgressDialog(Login.this);
            mprogressDialog.setMessage("please wait");
            mprogressDialog.setCancelable(false);
            mprogressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> loginHashMap = new HashMap<>();
            loginHashMap.put("email", sName);
            loginHashMap.put("password", sPassword);
            JsonParser jsonParser = new JsonParser();
            jsonObject = jsonParser.performPostCI("http://kinbech.6te.net/ResturantFoods/api/login", loginHashMap);

            try {
                if (jsonObject == null) {
                    flag = 1;
                } else if (jsonObject.getString("status").equals("success")) {
                    flag = 2;

                } else {
                    flag = 3;

                }
            } catch (JSONException e) {

            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mprogressDialog.dismiss();


            if (flag == 1) {
                Toast.makeText(Login.this, "Server/Network issue", Toast.LENGTH_SHORT).show();

            } else if (flag == 2) {



                Toast.makeText(Login.this, "Success" , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);

            } else {
                Toast.makeText(Login.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }


        }
    }
}