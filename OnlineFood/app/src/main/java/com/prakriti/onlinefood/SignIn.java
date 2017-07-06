package com.prakriti.onlinefood;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SignIn extends AppCompatActivity {

    EditText name, username, password, contact, email, confirm;
    Button register;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String sname, susername, spassword, sconfirmpassword, semail, scontact, saddress;
    TextInputLayout inputLayoutname, inputLayoutusername, inputLayoutpassword,
            inputLayoutemail, inputLayoutcontact;
    TextInputLayout inputLayoutconfirm;
    ProgressDialog mprogressDialog;
    int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        name = (EditText) findViewById(R.id.signupname);
        username = (EditText) findViewById(R.id.signupUsername);
        password = (EditText) findViewById(R.id.singupPassword);
        confirm = (EditText) findViewById(R.id.singupconfirmPassword);
        contact = (EditText) findViewById(R.id.signupContact);
        email = (EditText) findViewById(R.id.signupemail);
        register = (Button) findViewById(R.id.btnRegister);
        inputLayoutname = (TextInputLayout) findViewById(R.id.inputlayout_name);
        inputLayoutusername = (TextInputLayout) findViewById(R.id.inputlayout_username);
        inputLayoutpassword = (TextInputLayout) findViewById(R.id.inputlayout_password);
        inputLayoutcontact = (TextInputLayout) findViewById(R.id.inputlayout_Contact);
        inputLayoutconfirm = (TextInputLayout) findViewById(R.id.inputlayout_confirmpassword);
        inputLayoutemail = (TextInputLayout) findViewById(R.id.inputlayout_email);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (name.length() < 0 || confirm.length() < 0 || email.length() < 0 || contact.length() < 0 || password.length() < 0) {
                    Toast.makeText(SignIn.this, "Please, fill all the fields! ", Toast.LENGTH_SHORT).show();
                } else {
                    sname = name.getText().toString();
                    sconfirmpassword = confirm.getText().toString();
                    semail = email.getText().toString();
                    spassword = password.getText().toString();
                    scontact = contact.getText().toString();
                    new registerAsyncTask().execute();
                }
            }
        });
    }



            /*    if (sname.equals("") || susername.equals("")
                        || spassword.equals("")|| sconfirmpassword.equals("")|| semail.equals("")|| scontact.equals("")) {

                    Toast.makeText(getApplicationContext(), "Field Vaccant",
                            Toast.LENGTH_LONG).show();

                }
                else if(!semail.matches(emailPattern)){
                    Toast.makeText(SignIn.this, "Invalid Password", Toast.LENGTH_SHORT).show();

                }
                else if(scontact.length() != 10){
                    Toast.makeText(SignIn.this, "Invalid Contact", Toast.LENGTH_SHORT).show();
                }

                else if(sname.length()<=4 && username.length() <=4){
                    Toast.makeText(SignIn.this, "Username and Name most contain more than " +
                            "4 letter ", Toast.LENGTH_SHORT).show();
                }
                else {

                 new registerAsyncTask().execute();
                }
*/


    class registerAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mprogressDialog = new ProgressDialog(SignIn.this);
            mprogressDialog.setMessage("loading");
            mprogressDialog.setCancelable(false);
            mprogressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> registerActivityHashMap = new HashMap<>();
            registerActivityHashMap.put("email", semail);
            registerActivityHashMap.put("password", spassword);
            registerActivityHashMap.put("name", sname);
            registerActivityHashMap.put("phone", scontact);
            registerActivityHashMap.put("address", sconfirmpassword);
            JsonParser jsonParser = new JsonParser();
            JSONObject jsonObject = jsonParser.performPostCI("http://kinbech.6te.net/ResturantFoods/api/register", registerActivityHashMap);

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
                Toast.makeText(SignIn.this, "Network issue", Toast.LENGTH_SHORT).show();

            } else if (flag == 2) {
                Toast.makeText(SignIn.this, "Registration success", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(SignIn.this, "Invalid user Try again ", Toast.LENGTH_SHORT).show();
            }

        }
    }


}