package com;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.ekrili.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Beans.Appartement;
import Beans.Bien;
import Beans.Client;
import Beans.Maison;
import Beans.Terrain;
import generateclass.Config;
import generateclass.SessionManager;
import generateclass.TraitementBien;
import io.apptik.widget.MultiSlider;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener , AdapterView.OnItemSelectedListener{

    public static Activity MainACTIV;

    public static  String KEY_LIST = "keylist";
    public static  String KEY_TYPE = "keytype";



    private LinearLayout container ;

    private TraitementBien typeBien;

    private MultiSlider prix;

    private RadioButton appartement , maison , terrain , garage ;

    private TextView prixText ;

    private Spinner wilaya ;

    private Button chercheButton;

    private String WILAYA=""  , TYPE =""; //TOUT LES BIENS

    private double SURFACE= 0 , PRIXMIN= 5000 , PRIXMAX= 100000 ; // TOUT LES BIENS

    private int CHAMBRE_APP =1 , ETAGE_APP =1 ; //POUR LES APPARTEMENTS

    private int CHAMBRE_MAISON =1, ETAGE_MAISON=1;

    private String JARDIN = "" , PISCINE ="" , GARAGE ="";

    private String url="";

    private EditText surface ;

    private ArrayList<Appartement> appartementArrayList ;

    private ArrayList<Maison> maisonArrayList ;

    private ArrayList<Terrain> terrainArrayList ;

    private ArrayList<Bien> bienArrayList ;

    private SessionManager sessionManager ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainACTIV = this;
         InitializeViews();

         InitializeWilaya();

        InitializePriceBar();


        appartement.setOnClickListener(this);
        maison.setOnClickListener(this);
        terrain.setOnClickListener(this);
        garage.setOnClickListener(this);
        chercheButton.setOnClickListener(this);
        wilaya.setOnItemSelectedListener(this);

        prix.setOnThumbValueChangeListener(new MultiSlider.OnThumbValueChangeListener() {


            @Override
            public void onValueChanged(MultiSlider multiSlider, MultiSlider.Thumb thumb, int thumbIndex, int value) {

                int indexone = thumb.getMin() , indextwo = thumb.getMax() ;

                if(thumbIndex==0){
                    indexone = value;
                    prixText.setText("Prix entre "+indexone+" DA et "+multiSlider.getThumb(1).getValue()+" DA");
                    PRIXMIN = indexone;
                }

                else{
                    indextwo = value;
                    prixText.setText("Prix entre "+multiSlider.getThumb(0).getValue()+" DA et "+indextwo+" DA ");
                    PRIXMAX = indextwo;
                }




            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if(!sessionManager.IsLogin()){
            getMenuInflater().inflate(R.menu.mainactivity_menu , menu);
        }


        return true ;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.login_menu  :
                Intent intent = new Intent(this , LoginActivity.class);
                intent.putExtra("FROM_WHERE" , "FROM_MAIN");
                startActivity(intent);
                break;

            case R.id.register_menu :
                Intent intent1 = new Intent(this , RegisterActivity.class);
                intent1.putExtra("FROM_WHERE_Register" , "FROM_MAIN");
                startActivity(intent1);
                break;


        }

        return true;
    }


    @Override
    public void onClick(View view) {





        switch (view.getId()){

            case R.id.appartement :
                typeBien.TraitementAppartement();
                this.ChangeEtatButtonCherche();
                break;

            case R.id.maison :
                typeBien.TraitementVilla();
                this.ChangeEtatButtonCherche();
                break;

            case R.id.terrain :
                typeBien.TraitementTerrain();
                this.ChangeEtatButtonCherche();
                break;

            case R.id.garage :
                container.removeAllViews();
                this.ChangeEtatButtonCherche();
                break;

            case R.id.cherche_bien :
             MakeSearch();
                break;
                default:

                    break;



        }
    }

    private void InitializePriceBar(){

        prix.setMin(5000);

        prix.setMax(100000);

        prix.setStep(1000);

    }

    private void InitializeViews(){


        container = findViewById(R.id.recherche_container);
        prixText = findViewById(R.id.prix_text);
        prix = findViewById(R.id.prix_slider);
        wilaya = findViewById(R.id.wilaya);
        appartement = findViewById(R.id.appartement);
        maison = findViewById(R.id.maison);
        terrain = findViewById(R.id.terrain);
        garage = findViewById(R.id.garage);
        chercheButton = findViewById(R.id.cherche_bien);
        surface = findViewById(R.id.surface_cherche);
        typeBien = new TraitementBien(this , container);
        appartementArrayList = new ArrayList<>();
        maisonArrayList = new ArrayList<>();
        terrainArrayList = new ArrayList<>();
        bienArrayList = new ArrayList<>();
        sessionManager = new SessionManager(this);
    }

   private void  InitializeWilaya(){

       ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
               R.array.wilaya, android.R.layout.simple_spinner_item);

       adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

       wilaya.setAdapter(adapter);
   }

   private void ChangeEtatButtonCherche(){

       chercheButton.setEnabled(true);
       chercheButton.setText("CHERCHER");

   }

   private void MakeSearch(){

       TYPE  = getTypeSelected();

       if(!surface.getText().toString().trim().isEmpty())
            SURFACE = Double.parseDouble(surface.getText().toString().trim());



       switch(TYPE){

           case "appartement" :
               SearchAppartement();
               break;

           case "maison" :
               SearchMaison();
               break;

           case "terrain" :
               SearchTerrain();
               break;
           case "garage" :
               SearchGarage();
               break;
       }

   }

    private void SearchGarage() {

        url = Config.SEARCH_GARAGE_URL;




        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for(int i =0 ; i<jsonArray.length() ; i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int idBien = jsonObject.getInt("idBien");
                        double prix = jsonObject.getDouble("prix");
                        String wilaya = jsonObject.getString("wilaya");
                        String adresse = jsonObject.getString("adresse");
                        double surface = jsonObject.getDouble("surface");
                        String description = jsonObject.getString("description");
                        String titre = jsonObject.getString("titre");
                        String statut = jsonObject.getString("statut");
                        String image = jsonObject.getString("image_principale");
                        String telephone_client = jsonObject.getString("telephone_client");
                        String email_client = jsonObject.getString("email_client");
                        Client client = new Client();
                        client.setEmail(email_client);
                        client.setTelephone(telephone_client);

                        Bien bien = new Bien(wilaya , adresse , null , description , client , prix , surface , idBien , image , titre , statut);
                        bienArrayList.add(bien);




                    }

                    Intent startsearchactivity = new Intent(MainActivity.this , ResultSearchActivity.class);

                    startsearchactivity.putExtra(KEY_LIST ,  bienArrayList);
                    startsearchactivity.putExtra(KEY_TYPE , TYPE);
                    startActivity(startsearchactivity);

                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> paramas = new HashMap<>();
                paramas.put("wilaya" , WILAYA);
                paramas.put("surface" , Double.toString(SURFACE));
                paramas.put("prix_min" , Double.toString(PRIXMIN)) ;
                paramas.put("prix_max" , Double.toString(PRIXMAX));
                return paramas;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest) ;



    }

    private void SearchTerrain() {

        url = Config.SEARCH_TERRAIN_URL;




        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for(int i =0 ; i<jsonArray.length() ; i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int idBien = jsonObject.getInt("idBien");
                        double prix = jsonObject.getDouble("prix");
                        String wilaya = jsonObject.getString("wilaya");
                        String adresse = jsonObject.getString("adresse");
                        double surface = jsonObject.getDouble("surface");
                        String description = jsonObject.getString("description");
                        String titre = jsonObject.getString("titre");
                        String statut = jsonObject.getString("statut");
                        String typeT = jsonObject.getString("type_terrain");
                        String image = jsonObject.getString("image_principale");
                        String telephone_client = jsonObject.getString("telephone_client");
                        String email_client = jsonObject.getString("email_client");
                        Client client = new Client();
                        client.setEmail(email_client);
                        client.setTelephone(telephone_client);
                        Terrain terrain = new Terrain(wilaya , adresse , null , description , client , prix , surface , idBien , image , typeT , titre , statut);

                        terrainArrayList.add(terrain);




                    }

                    Intent startsearchactivity = new Intent(MainActivity.this , ResultSearchActivity.class);

                    startsearchactivity.putExtra(KEY_LIST ,  terrainArrayList);
                    startsearchactivity.putExtra(KEY_TYPE , TYPE);
                    startActivity(startsearchactivity);

                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> paramas = new HashMap<>();
                paramas.put("wilaya" , WILAYA);
                paramas.put("surface" , Double.toString(SURFACE));
                paramas.put("prix_min" , Double.toString(PRIXMIN)) ;
                paramas.put("prix_max" , Double.toString(PRIXMAX));
                paramas.put("typeT" , typeBien.getTYPE_TERRAIN());
                return paramas;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest) ;


    }



    public String  getTypeSelected(){

       if(appartement.isChecked()){

           TYPE = "appartement" ;
       }

       if(maison.isChecked()){

           TYPE = "maison" ;
       }

       if(terrain.isChecked()){

           TYPE = "terrain" ;
       }

       if(garage.isChecked()){

           TYPE = "garage" ;
       }

       return TYPE ;
   }

    private void SearchAppartement() {

        url = Config.SEARCH_APP_URL;

        CHAMBRE_APP  = typeBien.getNbrChambre();

        ETAGE_APP  = typeBien.getNbrEtage();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for(int i =0 ; i<jsonArray.length() ; i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int idBien = jsonObject.getInt("idBien");
                        double prix = jsonObject.getDouble("prix");
                        String wilaya = jsonObject.getString("wilaya");
                        String adresse = jsonObject.getString("adresse");
                        String acte = jsonObject.getString("acte");
                        double surface = jsonObject.getDouble("surface");
                        String description = jsonObject.getString("description");
                        String titre = jsonObject.getString("titre");
                        String statut = jsonObject.getString("statut");
                        int etage = jsonObject.getInt("etage");
                        int chambre = jsonObject.getInt("chambre");
                       // String client = jsonObject.getString("client");
                        String image = jsonObject.getString("image_principale");
                        String telephone_client = jsonObject.getString("telephone_client");
                        String email_client = jsonObject.getString("email_client");
                        Client client = new Client();
                        client.setEmail(email_client);
                        client.setTelephone(telephone_client);

                        Appartement appartement = new Appartement(wilaya , adresse , acte , description , client , prix ,surface , idBien , chambre ,etage , image , titre , statut);
                        appartementArrayList.add(appartement);



                    }

                    Intent startsearchactivity = new Intent(MainActivity.this , ResultSearchActivity.class);

                    startsearchactivity.putExtra(KEY_LIST ,  appartementArrayList);
                    startsearchactivity.putExtra(KEY_TYPE , TYPE);
                    startActivity(startsearchactivity);

                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> paramas = new HashMap<>();
                paramas.put("wilaya" , WILAYA);
                paramas.put("surface" , Double.toString(SURFACE));
                paramas.put("prix_min" , Double.toString(PRIXMIN)) ;
                paramas.put("prix_max" , Double.toString(PRIXMAX));
                paramas.put("chambre" , Integer.toString(CHAMBRE_APP));
                paramas.put("etage" , Integer.toString(ETAGE_APP));
                return paramas;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest) ;


    }

    private void SearchMaison(){

        url = Config.SEARCH_MAISON_URL;

        CHAMBRE_MAISON  = typeBien.getNbrChambreMaison();

        ETAGE_MAISON  = typeBien.getNbrEtageMaison();

        JARDIN = typeBien.getJARDIN();

        GARAGE = typeBien.getGARAGE();

        PISCINE = typeBien.getPISCINE();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for(int i =0 ; i<jsonArray.length() ; i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int idBien = jsonObject.getInt("idBien");
                        double prix = jsonObject.getDouble("prix");
                        String wilaya = jsonObject.getString("wilaya");
                        String adresse = jsonObject.getString("adresse");
                        String acte = jsonObject.getString("acte");
                        double surface = jsonObject.getDouble("surface");
                        String description = jsonObject.getString("description");
                        String titre = jsonObject.getString("titre");
                        String statut = jsonObject.getString("statut");
                        int etage = jsonObject.getInt("nombreEtage");
                        int chambre = jsonObject.getInt("chambre");

                        String telephone_client = jsonObject.getString("telephone_client");
                        String email_client = jsonObject.getString("email_client");
                        Client client = new Client();
                        client.setEmail(email_client);
                        client.setTelephone(telephone_client);

                        String image = jsonObject.getString("image_principale");
                        boolean jardin = jsonObject.getString("jardin").equals("1");
                        boolean garage = jsonObject.getString("garage").equals("1");
                        boolean piscine = jsonObject.getString("piscine").equals("1");
                       Maison maison = new Maison(wilaya , adresse , acte , description , client , prix , surface , idBien , image , chambre , etage , garage , piscine , jardin , titre , statut);
                        maisonArrayList.add(maison);



                    }

                    Intent startsearchactivity = new Intent(MainActivity.this , ResultSearchActivity.class);

                    startsearchactivity.putExtra(KEY_LIST ,  maisonArrayList);
                    startsearchactivity.putExtra(KEY_TYPE , TYPE);
                    startActivity(startsearchactivity);

                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> paramas = new HashMap<>();
                paramas.put("wilaya" , WILAYA);
                paramas.put("surface" , Double.toString(SURFACE));
                paramas.put("prix_min" , Double.toString(PRIXMIN)) ;
                paramas.put("prix_max" , Double.toString(PRIXMAX));
                paramas.put("chambre" , Integer.toString(CHAMBRE_MAISON));
                paramas.put("etage" , Integer.toString(ETAGE_MAISON));
                paramas.put("garage" , GARAGE);
                paramas.put("piscine" , PISCINE);
                paramas.put("jardin" , JARDIN);

                return paramas;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest) ;

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        WILAYA = adapterView.getItemAtPosition(i).toString();
        WILAYA = WILAYA.trim().replaceAll("[^A-Za-z]","");
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

        WILAYA ="";
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        appartementArrayList = new ArrayList<>();
        maisonArrayList = new ArrayList<>();
        terrainArrayList = new ArrayList<>();
        bienArrayList = new ArrayList<>();
    }


}
