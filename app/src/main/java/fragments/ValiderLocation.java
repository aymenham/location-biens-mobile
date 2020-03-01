package fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import Adapters.ValiderLocationAdapter;
import Beans.Bien;
import Beans.Client;
import Beans.Location;
import generateclass.Config;
import generateclass.SessionManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class ValiderLocation extends Fragment {

    private View view ;

    private RecyclerView recyclerView ;

    private RecyclerView.Adapter adapter ;

    private List<Location> list ;

    private SessionManager sessionManager ;

    private TextView listVide ;



    public ValiderLocation() {
        setHasOptionsMenu(false);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_valider_location, container, false);
        initViews();

        return view;
    }

    private void initViews(){

        recyclerView = view.findViewById(R.id.recycler_accepte_location);
        listVide = view.findViewById(R.id.list_valider_location);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        list = new ArrayList<>();
        sessionManager = new SessionManager(getActivity());



    }

    private void GetAllInformations(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.VALIDER_LOCATION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i <jsonArray.length() ; i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int Idbien = jsonObject.getInt("idBien");
                        double prixMensuelle = jsonObject.getDouble("prix");
                        String adress = jsonObject.getString("adresse");
                        String wilaya = jsonObject.getString("wilaya");
                        double surface = jsonObject.getDouble("surface");
                        String image_principale = jsonObject.getString("image_principale");
                        Bien bien = new Bien();
                        bien.setIdBien(Idbien);
                        bien.setPrix(prixMensuelle);
                        bien.setAdresse(adress);
                        bien.setSurface(surface);
                        bien.setImage(image_principale);
                        bien.setVille(wilaya);
                        String telephone_client = jsonObject.getString("telephone_client");
                        String nom = jsonObject.getString("nom");
                        String prenom = jsonObject.getString("prenom");
                        Client client = new Client();
                        client.setNom(nom);
                        client.setPrenom(prenom);
                        client.setTelephone(telephone_client);
                        String date_debut = jsonObject.getString("date_debut");
                        String date_fin = jsonObject.getString("date_fin");
                        String montant = jsonObject.getString("montant");
                        int idLocation = jsonObject.getInt("idLocation");
                        Location location = new Location(client, bien, date_debut, date_fin, null, null);
                        location.setIdLocation(idLocation);
                        location.setMontantTotal(montant);
                        list.add(location);

                        adapter.notifyDataSetChanged();

                    }

                    if(adapter.getItemCount()==0){
                        recyclerView.setVisibility(View.GONE);
                        listVide.setText("PAS DE DEMANDE DE LOCATION");
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

                Toast.makeText(getContext(), "erreur lors de chargment de la page", Toast.LENGTH_SHORT).show();
                Log.e("volley error : " , error.toString());
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String pseudo = sessionManager.getUserDetaille().get(SessionManager.KEY_PSEUDO);
                Map<String , String> params = new HashMap<>();
                params.put("pseudo" ,pseudo );
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onStart() {
        list.clear();
        adapter = new ValiderLocationAdapter(getContext() , list);
        recyclerView.setAdapter(adapter);
        GetAllInformations();
        super.onStart();
    }
}
