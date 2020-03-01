package com.example.lenovo.ekrili;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import generateclass.Config;

public class ValiderBienActivity extends AppCompatActivity implements View.OnClickListener {

    private Bundle bundle ;

    private ImageView image_principale ;

    private TextView nom , prenom ,
                     wilaya , adresse , surface ,prix ,
                     date_debut , date_fin , montant_totale ;

    private Button accepterLocation , refuserLocation ;

    private String Nom , Prenom ,
                   Wilaya , Adresse , Surface , Prix ,Image ,
                   Date_debut , Date_fin , Montant_totale , CHOIX ,
                   idLocation , idBien  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valider_bien);

        bundle = getIntent().getExtras();

        if(bundle==null){
            Toast.makeText(this, "erreur  !!", Toast.LENGTH_SHORT).show();
        }

        else{

            initViews();
            accepterLocation.setOnClickListener(this);
            refuserLocation.setOnClickListener(this);
            fillInformations();

        }


    }

    private void initViews(){

        nom = findViewById(R.id.nom_valider_location);
        prenom = findViewById(R.id.prenom_valider_location);

        image_principale = findViewById(R.id.image_principale);
        wilaya = findViewById(R.id.wilaya_valider_location);
        adresse = findViewById(R.id.adresse_valider_location);
        surface = findViewById(R.id.surface_valider_location);
        prix = findViewById(R.id.prix_valider_location);

        date_debut = findViewById(R.id.date_debut_valider_location);
        date_fin = findViewById(R.id.date_fin_valider_location);
        montant_totale = findViewById(R.id.montant__valider_location);

        accepterLocation = findViewById(R.id.accepter_valider_location);
        refuserLocation = findViewById(R.id.refuser_valider_location);
    }

    private void fillInformations(){

        idLocation = bundle.getString(Config.ID_LOCATION);
        idBien = bundle.getString(Config.ID_BIEN);

        Nom = bundle.getString(Config.KEY_NAME);
        Prenom = bundle.getString(Config.KEY_PRENOM);

        Image = bundle.getString(Config.IMAGE_PRINCIPALE);
        Wilaya = bundle.getString(Config.KEY_WILAYA);
        Adresse = bundle.getString(Config.KEY_ADRESSE);
        Surface = bundle.getString(Config.KEY_SURFACE);
        Prix = bundle.getString(Config.KEY_PRIX);

        Date_debut = bundle.getString(Config.KEY_DATE_DEBUT);
        Date_fin = bundle.getString(Config.KEY_DATE_FIN);
        Montant_totale = bundle.getString(Config.KEY_MONTANT_TOTAL);

        nom.setText(Nom);
        prenom.setText(Prenom);

        Glide.with(this).load(Image).override(Target.SIZE_ORIGINAL , 180).into(image_principale);
        wilaya.setText(Wilaya);
        adresse.setText(Adresse);
        surface.setText(Surface+ " M²");
        prix.setText(Prix + " DA / MOIS");

        date_debut.setText(Date_debut);
        date_fin.setText(Date_fin);
        montant_totale.setText(Montant_totale + " DA");



    }

    @Override
    public void onClick(View view) {

        if(view==accepterLocation){

            CHOIX = "accepter";
            MakeChoice();
            Log.e("idBien :" , idBien);

        }

        else if(view==refuserLocation){

            CHOIX = "refuser";
            MakeChoice();
        }
    }

    private void  MakeChoice(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.CHOISIR_VALIDER_LOCATION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                    boolean resp = Boolean.parseBoolean(response);


                if(resp){

                    accepterLocation.setVisibility(View.GONE);
                    refuserLocation.setVisibility(View.GONE);

                    if(CHOIX.equals("accepter")){

                        Toast.makeText(ValiderBienActivity.this, "bien accepté avec succès", Toast.LENGTH_SHORT).show();

                    }else if(CHOIX.equals("refuser")){

                        Toast.makeText(ValiderBienActivity.this, "bien réfusé avec succès", Toast.LENGTH_SHORT).show();

                    }
                }

                else{
                    Toast.makeText(ValiderBienActivity.this, "erreur lors de la validation", Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ValiderBienActivity.this, "Erreur !!", Toast.LENGTH_SHORT).show();
                Log.e("erreur from server :" , error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> params = new HashMap<>();
                params.put("idLocation" , idLocation);
                params.put("choix" , CHOIX);
                params.put("idBien" ,idBien);
                params.put("date_debut" , Date_debut);
                params.put("date_fin" , Date_fin);
                return  params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
