package com;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.lenovo.ekrili.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Adapters.DetailleFragmentAdapter;
import Beans.Avis;
import Beans.Client;
import fragments.AvisBien;
import generateclass.Config;
import generateclass.SessionManager;
import sendemail.EnvoyerEmail;

public class BienDetailleActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private SliderLayout mDemoSlider;

    private FloatingActionButton cell_button , comment_button , send_email_button , louer_button;
    private FloatingActionMenu floatingActionMenu ;

    private TabLayout tabLayout ;

    private ViewPager viewPager ;

    private String TelephoneClient, EmailClient;

    private SessionManager sessionManager;

    private Bundle bundle = null;


    // pour la location

    private Calendar calendar ;

    private EditText dateDebut ;

    private TextView nombreMois ;

    private ImageView calendarImage ;

    private DatePickerDialog calendrierDispo ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getIntent().getExtras();

        if (bundle == null) {

            setContentView(R.layout.bien_detaille_erreur);

        } else {
            setContentView(R.layout.activity_bien_detaille);
            initToolBar();
            initViews();
            getImagesBiens();
            fillInformations();
            cell_button.setOnClickListener(this);
            comment_button.setOnClickListener(this);
            send_email_button.setOnClickListener(this);
            louer_button.setOnClickListener(this);

            if(bundle.getString(Config.STATUT_KEY)!=null){


                if(bundle.getString(Config.STATUT_KEY).equals("Supprimer")){

                    louer_button.setVisibility(View.GONE);
                }
            }

        }


    }


    private void getImagesBiens() {



        // l'ajout des  images


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.URL_IMAGES_BIENS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String url = jsonObject.getString("url");
                        TextSliderView textSliderView = new TextSliderView(BienDetailleActivity.this);
                        textSliderView
                                .image(Config.IMAGES + url)
                                .setScaleType(BaseSliderView.ScaleType.CenterCrop);
                        mDemoSlider.addSlider(textSliderView);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(BienDetailleActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String idbien = bundle.getString(Config.ID_BIEN);

                Map<String, String> params = new HashMap<>();

                params.put("idbien", idbien);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(BienDetailleActivity.this);
        requestQueue.add(stringRequest);
    }


    private void initToolBar() {

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.anim_toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
    }



    private void fillInformations() {

        TelephoneClient = bundle.getString(Config.KEY_TELEPHONE_CLIENT);
        EmailClient = bundle.getString(Config.KEY_EMAIL_CLIENT);



        String email_session= sessionManager.getUserDetaille().get(SessionManager.KEY_EMAIL);

            if(EmailClient!=null){
                if(EmailClient.equals(email_session)){

                    floatingActionMenu.setVisibility(View.GONE);
                }
            }

            else{

                floatingActionMenu.setVisibility(View.GONE);
            }


    }



    private void initViews() {


        mDemoSlider = findViewById(R.id.slider_biens);
        cell_button = findViewById(R.id.cell_button);
        comment_button = findViewById(R.id.make_comment);
        send_email_button = findViewById(R.id.send_email_det);
        louer_button = findViewById(R.id.louer_bien);
        floatingActionMenu = findViewById(R.id.menu_detaille);
        sessionManager = new SessionManager(this);
        tabLayout = findViewById(R.id.tab_detaille);
        viewPager = findViewById(R.id.viewpager_detaille);
        DetailleFragmentAdapter adapter = new DetailleFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.cell_button:
                MakeCell();
                break;

            case R.id.make_comment:
                MakeComment();
                break;

            case R.id.send_email_det:
                SendEmail();
                break;

            case R.id.louer_bien:
                LouerBien();
                break;


        }
    }

    private void LouerBien() {

        if(sessionManager.IsLogin()){

            ShowFenetreLocation();

        }
        else{

            Toast.makeText(this, "il faut créer un compte pour pouvoir louer ce bien", Toast.LENGTH_SHORT).show();
        }
    }



    private void SendEmail() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View view = getLayoutInflater().inflate(R.layout.send_email_dialogue , null);

        final  EditText titre = view.findViewById(R.id.titre_email);
         final  EditText message = view.findViewById(R.id.message_email);

        builder.setPositiveButton("Envoyer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                final String to = bundle.getString(Config.KEY_EMAIL_CLIENT);

                final String subject = titre.getText().toString().trim();

                final String text = message.getText().toString().trim();

                if(subject.isEmpty() || text.isEmpty()){

                    Toast.makeText(BienDetailleActivity.this, "remplir les champs SVP", Toast.LENGTH_SHORT).show();
                }

                else {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {





                        }
                    }).start();
                    Toast.makeText(BienDetailleActivity.this, "message envoyé", Toast.LENGTH_SHORT).show();
                }




            }

        });

        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });

        builder.setView(view);

        AlertDialog alertDialog = builder.create();

        alertDialog.show();



    }

    private void MakeComment() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View view = getLayoutInflater().inflate(R.layout.avis_dialogue , null);

        final RatingBar ratingBar = view.findViewById(R.id.raiting_avis);

        final EditText comment_client = view.findViewById(R.id.comment);

        ratingBar.setRating(2.5f);

        if(sessionManager.IsLogin()){

            builder.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {


                    String pseudo =sessionManager.getUserDetaille().get(SessionManager.KEY_PSEUDO);
                    String nom = sessionManager.getUserDetaille().get(SessionManager.KEY_NAME);
                    String prenom = sessionManager.getUserDetaille().get(SessionManager.KEY_PRENOM);
                    String idbien = bundle.getString(Config.ID_BIEN);
                    float note = ratingBar.getRating();
                    String comment = comment_client.getText().toString().trim();
                    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

                    //ajouter a l'interface
                    Client client = new Client();
                    client.setNom(nom);
                    client.setPrenom(prenom);
                    boolean check = CheckExistPseudo(pseudo , client , date , comment , note);

                    if (!check){
                        Avis avis = new Avis(0,client , date , comment , note);
                        AvisBien.avisList.add(avis);
                        AvisBien.adapter.notifyDataSetChanged();
                    }



                    // ajouter a la base de donnée
                    AddAvis(pseudo , idbien , note , comment , date);



                }
            });

            builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    dialogInterface.dismiss();
                }
            });
            builder.setView(view);

            AlertDialog alertDialog = builder.create();



            alertDialog.show();

        }

        else{

            Toast.makeText(this, "il faut créer un compte pour pouvoir donné un avis ", Toast.LENGTH_SHORT).show();
        }





    }

    private void AddAvis(final String pseudo , final String idbien , final float note , final String comment , final String date) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.ADD_COMMENT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(BienDetailleActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(BienDetailleActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String , String> params = new HashMap<>();

                params.put( "pseudo", pseudo);
                params.put("idbien", idbien);
                params.put( "note", Float.toString(note));
                params.put("message" , comment);
                params.put("date" , date);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);



    }

    private void MakeCell() {



            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + TelephoneClient));
            startActivity(callIntent);


    }

    // tout les methode de location

    private void ShowFenetreLocation() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View view = getLayoutInflater().inflate(R.layout.location_dialogue , null);

        dateDebut = view.findViewById(R.id.date_debut_location);
        nombreMois = view.findViewById(R.id.nombre_mois_location);
        calendarImage = view.findViewById(R.id.calendar_location);
        calendarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowCalendar();
            }
        });
        builder.setView(view);

        builder.setPositiveButton("VALIDER", new DialogInterface.OnClickListener() {



            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                final String DD = dateDebut.getText().toString();
                String nbrMois = nombreMois.getText().toString();

                if(nbrMois.isEmpty() ){

                    Toast.makeText(BienDetailleActivity.this, "il faut remplir tout les champs", Toast.LENGTH_SHORT).show();
                }

                else {

                    final String futurDate = addMontToDate(DD , nbrMois);
                    int nbrM = Integer.parseInt(nbrMois);
                    double prixBien = Double.parseDouble(bundle.getString(Config.KEY_PRIX));
                    final String prixT = Integer.toString((int)(nbrM*prixBien));
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(BienDetailleActivity.this);

                    builder.setTitle("Accepter OU Réfuser la location");
                    builder1.setMessage("Cette location va couter "+prixT + "DA");
                    builder1.setPositiveButton("VAlider", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            InsertLocationToDataBase(DD , futurDate , prixT);
                        }
                    });

                    builder1.setNegativeButton("Refuser", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            dialogInterface.dismiss();
                        }
                    });

                    AlertDialog dialog = builder1.create();
                    dialog.show();



                }

            }
        });

        builder.setNegativeButton("ANNULER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }



    // calendrier de disponibilite

    private void ShowCalendar(){

         calendar = Calendar.getInstance();
        calendrierDispo = DatePickerDialog.newInstance(
                BienDetailleActivity.this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        calendrierDispo.setMinDate(calendar);
        calendrierDispo.setAccentColor("#fc4628");
        DisableAllDate();



    }


    // qui on choisi une date et on click OK
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

        dateDebut.setText(dateComplet);

    }

    private void DisableAllDate(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.JOUR_RESERVE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i <jsonArray.length() ; i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String DD = jsonObject.getString("dateD");
                        String DF = jsonObject.getString("dateF");

                        Log.e("dateD :" , DD);
                        disableDate(DD , DF);
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                calendrierDispo.show(getFragmentManager(), "Datepickerdialog");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BienDetailleActivity.this, "erreur !!", Toast.LENGTH_SHORT).show();
                Log.e("erreur from server :" , error.toString());
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String idbien = bundle.getString(Config.ID_BIEN);

                Map<String, String> params = new HashMap<>();

                params.put("idBien", idbien);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private Calendar dateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    private List<Date> getDatesBetween(
            Date startDate, Date endDate) {
        List<Date> datesInRange = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);

        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(endDate);

        while (calendar.before(endCalendar)) {
            Date result = calendar.getTime();
            datesInRange.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        return datesInRange;
    }

    // methode to disable date :

    public void disableDate(String DD , String DF ){





        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date dateD = null;
        Date dateF = null;
        try {
            dateD = sdf.parse(DD);
            dateF = sdf.parse(DF);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<Date> list = getDatesBetween(dateD ,dateF);
        list.add(dateF);

        java.util.Date date = null;

        for (Date d : list) {


            date = d;


            calendar = dateToCalendar(date);
            System.out.println(calendar.getTime());

            List<Calendar> dates = new ArrayList<>();
            dates.add(calendar);
            Calendar[] disabledDays1 = dates.toArray(new Calendar[dates.size()]);
            calendrierDispo.setDisabledDays(disabledDays1);




        }


    }

    private String addMontToDate(String DD , String nbrMois){

        int nbMois = Integer.parseInt(nbrMois);
        String DateDebut = DD ;
        final java.util.Calendar cal1 = GregorianCalendar.getInstance();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date dateDebut = null;
        try {
            dateDebut = format.parse(DateDebut);
            cal1.setTime(dateDebut);
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        cal1.add(GregorianCalendar.MONTH, nbMois);
        Date dateFin = cal1.getTime();

        return format.format(dateFin);
    }

    private void InsertLocationToDataBase(final String dd, final String futurDate , final String prixT) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.INSERER_LOCATION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(BienDetailleActivity.this) ;

                LayoutInflater layoutInflater = getLayoutInflater();

                final View dialogView = layoutInflater.inflate(R.layout.custom_merci_dialog , null) ;

                TextView textView = dialogView.findViewById(R.id.merci_text);

                builder.setView(dialogView) ;

                textView.setText(response);

                builder
                        .setPositiveButton("MERCI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });

                android.app.AlertDialog dialog = builder.create();
                dialog.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(BienDetailleActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> params = new HashMap<>();
                String idbien = bundle.getString(Config.ID_BIEN);
                String pseudo = sessionManager.getUserDetaille().get(SessionManager.KEY_PSEUDO);
                params.put("idBien" , idbien);
                params.put("date_debut" , dd);
                params.put("date_fin" , futurDate);
                params.put("pseudo" , pseudo);
                params.put("prixT" , prixT);

                return params ;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public  boolean CheckExistPseudo(String pseudo , Client client ,String date ,
                                           String comment , float note ){
        int i =0;
        for (Avis avis: AvisBien.avisList) {

            if(avis.getClient().getPseudo().equals(pseudo)){
                AvisBien.avisList.get(i).setClient(client);
                AvisBien.avisList.get(i).setDate(date);
                AvisBien.avisList.get(i).setComment(comment);
                AvisBien.avisList.get(i).setRaiting(note);
                AvisBien.adapter.notifyDataSetChanged();
                return true;
            }

            i++;

        }

        return false;
    }


}

