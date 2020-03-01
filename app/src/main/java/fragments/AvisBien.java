package fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import Adapters.AvisAdapter;
import Beans.Avis;
import Beans.Client;
import generateclass.Config;

/**
 * A simple {@link Fragment} subclass.
 */
public class AvisBien extends Fragment {

    private View view;

    private RecyclerView recycler_avis;

    public static RecyclerView.Adapter adapter ;

    public static  List<Avis> avisList;




    public AvisBien() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_avis_bien, container, false);

       initViews();
       getAllViews();

        return view;
    }

    private void initViews(){

        recycler_avis = view.findViewById(R.id.recycler_avis);

        recycler_avis.setLayoutManager(new LinearLayoutManager(getContext()));
        avisList = new ArrayList<>();
       adapter = new AvisAdapter(getContext() , avisList);
        recycler_avis.setAdapter(adapter);

    }

    private void getAllViews(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST,Config.AVIS_URL , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for(int i=0;i<jsonArray.length();i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = jsonObject.getString("nom");
                        String prenom = jsonObject.getString("prenom");
                        String date = jsonObject.getString("date");
                        String pseudo = jsonObject.getString("pseudo");
                        int idAvis = jsonObject.getInt("idAvis");
                        float note = Float.parseFloat(jsonObject.getString("note"));
                        String comment = jsonObject.getString("comment");

                        Client client = new Client();
                        client.setNom(name);
                        client.setPrenom(prenom);
                        client.setPseudo(pseudo);
                        Avis avis = new Avis(idAvis ,client , date , comment , note);

                        avisList.add(avis);

                        adapter.notifyDataSetChanged();
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

                String idbien = getActivity().getIntent().getExtras().getString(Config.ID_BIEN);

                Map<String, String> params = new HashMap<>();

                params.put("idbien", idbien);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }





}
