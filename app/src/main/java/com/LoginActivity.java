package com;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.ekrili.ClientSpaceActivity;
import com.example.lenovo.ekrili.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import Beans.Client;
import generateclass.AlertDialogManager;
import generateclass.Config;
import generateclass.SessionManager;

public class LoginActivity extends AppCompatActivity {


    private TextView ForgotPass ;

    private AlertDialogManager alertManeger ;

    private EditText username , password;

    private String Username , Password ;

    private AlertDialogManager dialogManager ;

    private SessionManager sessionManager ;

    private Intent whichIntent;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        whichIntent = getIntent();
        ForgotPass = findViewById(R.id.forgot_password);
        username = findViewById(R.id.username_login);
        password = findViewById(R.id.password_login);

        dialogManager = new AlertDialogManager(this , this);
        sessionManager = new SessionManager(this);
        alertManeger = new AlertDialogManager(this , this );

        ForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertManeger.ShowForgotPassword();
            }
        });
    }

    public void LetLogin(View view) {

        Username = username.getText().toString().trim();
        Password = password.getText().toString().trim();

        if(Username.isEmpty() || Password.isEmpty()){

            alertManeger.ShowAlertDialoge("Il faut remplir tout les champs");
        }

        else{

            DotheLogin();
        }
    }

    private void DotheLogin() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.LoginURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String code = jsonObject.getString("code");
                    if(!code.equals("complet")){

                        alertManeger.ShowAlertDialoge("Erreur !! information invalide");
                        password.setText("");
                        username.setText("");
                    }

                    else{

                        String pseudo = jsonObject.getString("pseudo");
                        String nom = jsonObject.getString("nom");
                        String prenom = jsonObject.getString("prenom");
                        String dateNaissance = jsonObject.getString("dateNaissance");
                        String adresse = jsonObject.getString("adresse");
                        String email = jsonObject.getString("email");
                        String telephone = jsonObject.getString("telephone");

                        Client client = new Client(pseudo , nom ,  prenom , dateNaissance , adresse , email  , telephone);
                        sessionManager.createLoginSession(client);

                        Login();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                alertManeger.ShowAlertDialoge(error.toString());
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String , String> params = new HashMap<>();

                params.put("user_name" , Username);
                params.put("user_pass" , Password);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void Login(){

        Intent intent = new Intent(this , ClientSpaceActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        String startAct = whichIntent.getExtras().getString("FROM_WHERE");

       if(startAct.equals("FROM_HOMME_SCREEN")){
            HommeScreen.HommeScreenAct.finish();

        }


        if(startAct.equals("FROM_MAIN")){
            MainActivity.MainACTIV.finish();

        }

        finish();

    }
}
