package com;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.ekrili.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import generateclass.AlertDialogManager;
import generateclass.Config;
import generateclass.SessionManager;


public class RegisterActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private Button finish_register ;

    private EditText nom , prenom , pseudo , dateNaissance , adress , email , telephone , motdp , Cmotdp ;

    private String Nom , Prenom , Pseudo , Adress ,DateNaissance , Email , Telephone , Motdp , CMotdp ;

        private Intent intent;

    private AlertDialogManager dialogManager;

    private SessionManager sessionManager ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        intent = getIntent();
        this.InitializeViews();

        finish_register = findViewById(R.id.finish_register);

        finish_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



              Register();

            }


        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(!isValidEmail(email.getText().toString().trim())){

                    email.setError("Email non valide ");
                }


            }
        });

        dateNaissance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Calendar calendar = Calendar.getInstance();
                DatePickerDialog calendrierDispo = DatePickerDialog.newInstance(
                        RegisterActivity.this,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );

                calendrierDispo.setAccentColor("#fc4628");
                calendrierDispo.show(getFragmentManager(), "Datepickerdialog");
            }
        });


    }

    private void Register() {

        Nom = nom.getText().toString().trim() ;
        Prenom = prenom.getText().toString().trim() ;
        Pseudo = pseudo.getText().toString().trim() ;
        Adress = adress.getText().toString().trim() ;
        DateNaissance = dateNaissance.getText().toString().trim() ;
        Email = email.getText().toString().trim() ;
        Telephone = telephone.getText().toString().trim() ;
        Motdp = motdp.getText().toString().trim() ;
        CMotdp = Cmotdp.getText().toString().trim() ;

        if(     Nom.isEmpty() || Prenom.isEmpty() || Pseudo.isEmpty() ||
                Adress.isEmpty() || DateNaissance.isEmpty() || Email.isEmpty() ||
                Telephone.isEmpty() || Motdp.isEmpty() || CMotdp.isEmpty()
                ){

                dialogManager.ShowAlertDialoge("Il faut remplir tout les champs ");


        }

        else{

                if (!isValidEmail(Email)){

                    dialogManager.ShowAlertDialoge("Email non valide");
                }

                if(!Motdp.equals(CMotdp)){

                    dialogManager.ShowAlertDialoge("Confirme bien votre mot de passe");
                }

                else{

                    DoTheInscription();
                }

        }


    }

    private void InitializeViews(){

        nom = findViewById(R.id.nom_inscrption);
        prenom = findViewById(R.id.prenom_inscrption);
        pseudo = findViewById(R.id.pseudo_inscrption);
        dateNaissance = findViewById(R.id.date_inscrption);
        adress = findViewById(R.id.adress_inscrption);
        email = findViewById(R.id.email_inscrption);
        telephone = findViewById(R.id.telephone_inscrption);
        motdp = findViewById(R.id.mdp_inscrption);
        Cmotdp = findViewById(R.id.cmdp_inscrption);
        dialogManager = new AlertDialogManager(this, this);
        sessionManager = new SessionManager(this);
    }

    private final boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }



    private void DoTheInscription(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.RegisterURl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String code = jsonObject.getString("code");
                    String message = jsonObject.getString("message");
                    if (!code.equals("complet")){

                        dialogManager.ShowAlertDialoge(message);
                        pseudo.setError("pseudo existe dèja");
                        email.setError("email existe dèja");

                    }

                    else{
                        sessionManager.setKEY_IS_First_Time_Lunch();
                        dialogManager.ShowMerciMessage(message , intent);

                    }

                } catch (JSONException e) {
                    Toast.makeText(RegisterActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                dialogManager.ShowAlertDialoge("Erreur Lors de l'inscription vérifie votre connexion");
                Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> params = new HashMap<>();
                params.put("pseudo" , Pseudo);
                params.put("nom" , Nom);
                params.put("prenom" , Prenom);
                params.put("date_naissance" , DateNaissance);
                params.put("adresse" , Adress);
                params.put("email" , Email);
                params.put("mdp" , Motdp);
                params.put("telephone" , Telephone);
                return  params;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        String day = Integer.toString(dayOfMonth);
        String month = Integer.toString(monthOfYear+1);
        if(dayOfMonth<10){

            day = "0"+day;
        }

        if(monthOfYear+1<10){
            month = "0"+month;
        }

        String dateComplet = year+"-"+month+"-"+day;

        dateNaissance.setText(dateComplet);

    }
}
