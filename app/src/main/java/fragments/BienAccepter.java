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

import Adapters.BienARadapter;
import Beans.Bien;
import generateclass.Config;
import generateclass.SessionManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class BienAccepter extends Fragment {

    private View view ;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter ;
    private List<Bien> bienList ;
    private SessionManager sessionManager ;
    private TextView listVide ;


    public BienAccepter() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bien_accepter, container, false);
        initViews();
        getAllInforations();
        return view;
    }

    private void initViews(){

        recyclerView = view.findViewById(R.id.recyler_bien_accepter);
        listVide = view.findViewById(R.id.list_vide_bien_accepter);
        bienList = new ArrayList<>();
        sessionManager = new SessionManager(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        adapter = new BienARadapter(bienList , getActivity() , "C");
        recyclerView.setAdapter(adapter);

    }

    private void getAllInforations(){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.BIEN_ACCEPTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length() ; i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Bien bien = new Bien();
                        int idbien = jsonObject.getInt("idBien");
                        double prix = jsonObject.getDouble("prix");
                        String wilaya = jsonObject.getString("wilaya");
                        String adresse = jsonObject.getString("adresse");
                        double surface = jsonObject.getDouble("surface");
                        String description = jsonObject.getString("description");
                        String image = jsonObject.getString("image_principale");
                        String titre = jsonObject.getString("titre");
                        bien.setTitre(titre);
                        bien.setIdBien(idbien);
                        bien.setPrix(prix);
                        bien.setSurface(surface);
                        bien.setVille(wilaya);
                        bien.setAdresse(adresse);
                        bien.setDescription(description);
                        bien.setImage(image);
                        bienList.add(bien);
                        adapter.notifyDataSetChanged();

                    }

                    if(adapter.getItemCount()==0){
                        recyclerView.setVisibility(View.GONE);
                        listVide.setText("PAS DE BIEN ACCEPTE POUR LE MOMENT");
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

                String pseudo = sessionManager.getUserDetaille().get(SessionManager.KEY_PSEUDO);
                Map<String , String> params = new HashMap<>();
                params.put("pseudo" , pseudo);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

}
