package fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.daimajia.slider.library.SliderLayout;
import com.example.lenovo.ekrili.R;
import com.github.clans.fab.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import generateclass.Config;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailleBien extends Fragment {

    private View view;



    private TextView prix, adresse, surface, calculer_montant  , nbrChmbre ,   nbrEtage , NbrChambreM , NbrEtageM , typeTerrain ;

    private ImageView check_jardin , check_piscine , check_garage;

    private ReadMoreTextView description;

    private ImageView button_plus, button_moins;

    private FloatingActionButton cell_button;

    private LinearLayout type_container;

    private int NbrMois = 1;

    private double PrixCalculer;

    private Bundle bundle;

    private  String type ,idbien ;



    public DetailleBien() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_detaille_bien, container, false);
        bundle = getActivity().getIntent().getExtras();
        initViews();
        fillInformations();
        calculerMontant();
        ChoseType();

        return view;
    }

    private void initViews() {

        prix = view.findViewById(R.id.prix_detaille);
        adresse = view.findViewById(R.id.adresse_detaille);
        surface = view.findViewById(R.id.surface_detaille);
        description = view.findViewById(R.id.description_detaille);
        button_plus = view.findViewById(R.id.plus_detaille);
        calculer_montant = view.findViewById(R.id.montant_total_detaille);
        button_moins = view.findViewById(R.id.moins_detaille);
        type_container = view.findViewById(R.id.type_container_detaille);
        cell_button = view.findViewById(R.id.cell_button);


    }

    private void fillInformations() {
         idbien = bundle.getString(Config.ID_BIEN);
        String PRIX = bundle.getString(Config.KEY_PRIX);
        String ADRESSE = bundle.getString(Config.KEY_ADRESSE);
        String SURFACE = bundle.getString(Config.KEY_SURFACE);
        String DESCRIPTION = bundle.getString(Config.KEY_DESCRIPTION);
        PrixCalculer = Double.parseDouble(PRIX);
        String MONTANT = NbrMois + "MOIS = " + PrixCalculer * NbrMois + " DA/MOIS";
        prix.setText(PRIX + " DA/MOIS");
        adresse.setText(ADRESSE);
        surface.setText(SURFACE + " M²");
        description.setText(DESCRIPTION);
        calculer_montant.setText(MONTANT);


    }
    private void calculerMontant() {

        button_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NbrMois++;
                String MONTANT = NbrMois + "MOIS = " + PrixCalculer * NbrMois + " DA/MOIS";
                calculer_montant.setText(MONTANT);

            }
        });

        button_moins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NbrMois != 1) {
                    NbrMois--;
                    String MONTANT = NbrMois + "MOIS = " + PrixCalculer * NbrMois + " DA/MOIS";
                    calculer_montant.setText(MONTANT);
                }
            }
        });

    }

    private void ChoseType() {

         type = bundle.getString(Config.TYPE);

         if(type!=null) {
             switch (type) {

                 case "appartement":
                     fillAppartement();
                     break;

                 case "maison":
                     fillMaison();
                     break;

                 case "terrain":
                     fillTerrain();
                     break;
             }
         }

         else{

             GetDeailleBien();
         }
        
    }

    private void fillTerrain() {

        initLinearTerr();

        if(type!=null){

            typeTerrain.setText(bundle.getString(Config.TYPE_TERRAIN));
        }

        else{
            GetDeailleBien();
        }


    }

    private void fillMaison() {

            InitLinearMaison();

        if(type!=null){
            int ChambreM = bundle.getInt(Config.KEY_CHAMBRE_);
            int EtageM = bundle.getInt(Config.KEY_ETAGE_ );
            boolean jardin = bundle.getBoolean(Config.KEY_JARDIN_MAISON);
            boolean piscine = bundle.getBoolean(Config.KEY_PISCINE_MAISON);
            boolean garage = bundle.getBoolean(Config.KEY_GARAGE_MAISON);
            NbrChambreM.setText(Integer.toString(ChambreM));
            NbrEtageM.setText(Integer.toString(EtageM));

            if(jardin){

                check_jardin.setImageResource(R.drawable.bien_detaille_yes);
            }
            else{

                check_jardin.setImageResource(R.drawable.bien_detaille_maison_no);
            }

            if(piscine){

                check_piscine.setImageResource(R.drawable.bien_detaille_yes);
            }
            else{

                check_piscine.setImageResource(R.drawable.bien_detaille_maison_no);
            }

            if(garage){

                check_garage.setImageResource(R.drawable.bien_detaille_yes);
            }
            else{

                check_garage.setImageResource(R.drawable.bien_detaille_maison_no);
            }
        }

        else{

            GetDeailleBien();
        }
    }

    private void fillAppartement( ) {

        InitLinearApp();
        if(type!=null){
            
            //from search 
            String nombreChambre = Integer.toString(bundle.getInt(Config.KEY_CHAMBRE_));
            String nombreEtage = Integer.toString(bundle.getInt(Config.KEY_ETAGE_));
            nbrChmbre.setText(nombreChambre);
            nbrEtage.setText(nombreEtage);
        }
        
        else{
            
            GetDeailleBien();
            
        }
     

      
    }

    private void InitLinearApp(){
        type_container.removeAllViews();
        View appartement = getLayoutInflater().inflate(R.layout.detaille_appartement, null);
        type_container.addView(appartement);

        nbrChmbre = appartement.findViewById(R.id.chambre_app_detille);
        nbrEtage = appartement.findViewById(R.id.etage_app_detille);

    }

    private void initLinearTerr(){
        type_container.removeAllViews();
        View terrain = getLayoutInflater().inflate(R.layout.detaille_terrain , null );
        type_container.addView(terrain);
        typeTerrain = terrain.findViewById(R.id.detaille_type_terrain);
    }

    private void InitLinearMaison(){

        type_container.removeAllViews();
        View maison = getLayoutInflater().inflate(R.layout.detaille_maison, null);
        type_container.addView(maison);
        NbrChambreM = maison.findViewById(R.id.chambre_maison_detille);
        NbrEtageM = maison.findViewById(R.id.etage_maison_detille);
        check_jardin = maison.findViewById(R.id.jardin_detaille_masion);
        check_piscine = maison.findViewById(R.id.piscine_detaille_masion);
        check_garage = maison.findViewById(R.id.garage_detaille_masion);
    }

    private void GetDeailleBien() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.GET_DETAILLE_BIEN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String code = jsonObject.getString("code");
                    switch(code){

                        case "appartement":
                            String chambre = jsonObject.getString("chambre");
                            String etage = jsonObject.getString("etage");
                            InitLinearApp();
                            nbrChmbre.setText(chambre);
                            nbrEtage.setText(etage);
                            break;

                        case "maison":
                            String chambreM = jsonObject.getString("chambre");
                            String etageM = jsonObject.getString("etage");
                            boolean jardin = jsonObject.getString("jardin").equals("1");
                            boolean piscine =jsonObject.getString("piscine").equals("1");
                            boolean garage = jsonObject.getString("garage").equals("1");
                            InitLinearMaison();
                            NbrChambreM.setText(chambreM);
                            NbrEtageM.setText(etageM);

                            if(jardin){

                                check_jardin.setImageResource(R.drawable.bien_detaille_yes);
                            }
                            else{

                                check_jardin.setImageResource(R.drawable.bien_detaille_maison_no);
                            }

                            if(piscine){

                                check_piscine.setImageResource(R.drawable.bien_detaille_yes);
                            }
                            else{

                                check_piscine.setImageResource(R.drawable.bien_detaille_maison_no);
                            }

                            if(garage){

                                check_garage.setImageResource(R.drawable.bien_detaille_yes);
                            }
                            else{

                                check_garage.setImageResource(R.drawable.bien_detaille_maison_no);
                            }
                            break;

                        case "terrain" :
                            initLinearTerr();
                            String typeT = jsonObject.getString("type_terrain");
                            typeTerrain.setText(typeT);
                            break ;

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String , String> params = new HashMap<>();

                params.put("idbien" , idbien);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        requestQueue.add(stringRequest);

    }

}
