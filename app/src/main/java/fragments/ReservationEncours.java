package fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import java.util.List;
import java.util.Map;

import Adapters.MesReservationAdapter;
import Beans.Bien;
import Beans.Client;
import Beans.Location;
import generateclass.Config;
import generateclass.SessionManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReservationEncours extends Fragment {

    private View view ;

    private RecyclerView recyclerView ;

    private RecyclerView.Adapter adapter ;

    private List<Location> list ;

    private SessionManager sessionManager ;

    private TextView listVide ;

    public ReservationEncours() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_reservation_encours, container, false);
        initViews();
        getAllInformations();
        return view;
    }

    private void initViews(){

        recyclerView = view.findViewById(R.id.recyler_reservation_en_cours);
        listVide = view.findViewById(R.id.list_vide_reservation_en_cours);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        sessionManager = new SessionManager(getActivity());
        list = new ArrayList<>();
        adapter = new MesReservationAdapter(list , getContext() , "C");
        recyclerView.setAdapter(adapter);

    }

    private void getAllInformations(){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.MES_RESERVATION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i <jsonArray.length() ; i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int Idbien = jsonObject.getInt("idBien");
                        double prixMensuelle = jsonObject.getDouble("prix");
                        String adress = jsonObject.getString("adresse");
                        double surface = jsonObject.getDouble("surface");
                        String description = jsonObject.getString("description");
                        String image_principale = jsonObject.getString("image_principale");
                        String titre = jsonObject.getString("titre");
                        String statut = jsonObject.getString("statut");
                        Bien bien = new Bien();
                        bien.setIdBien(Idbien);
                        bien.setTitre(titre);
                        bien.setPrix(prixMensuelle);
                        bien.setAdresse(adress);
                        bien.setSurface(surface);
                        bien.setDescription(description);
                        bien.setImage(image_principale);
                        bien.setStatut(statut);
                        String telephone_client = jsonObject.getString("telephone_client");
                        String email = jsonObject.getString("email_client");
                        String nom = jsonObject.getString("nom");
                        String prenom = jsonObject.getString("prenom");
                        Client client = new Client();
                        client.setNom(nom);
                        client.setPrenom(prenom);
                        client.setTelephone(telephone_client);
                        client.setEmail(email);
                        String date_debut = jsonObject.getString("date_debut");
                        String date_fin = jsonObject.getString("date_fin");
                        String nbrJours = jsonObject.getString("nbrjour");


                        Location location = new Location(client, bien, date_debut, date_fin, null, nbrJours);

                        list.add(location);
                        adapter.notifyDataSetChanged();

                    }
                    if(adapter.getItemCount()==0){
                        recyclerView.setVisibility(View.GONE);
                        listVide.setText("PAS DE  LOCATION EN COURS ");
                        listVide.setVisibility(View.VISIBLE);

                    }

                    else{

                        recyclerView.setVisibility(View.VISIBLE);
                        listVide.setVisibility(View.GONE);
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
                params.put("pseudo" , sessionManager.getUserDetaille().get(SessionManager.KEY_PSEUDO));
                params.put("stat" , "EnCours");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        requestQueue.add(stringRequest);
    }

}
