package Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.BienDetailleActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.lenovo.ekrili.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Beans.Bien;
import generateclass.Config;

/**
 * Created by LENOVO on 28/05/2018.
 */

public class BienARadapter extends RecyclerView.Adapter<BienARadapter.EnAttent> {

    private List<Bien> bienList;
    private Context context;
    private String type ;
    public BienARadapter(List<Bien> bienList, Context context ,String type ) {
        this.bienList = bienList;
        this.context = context;
        this.type = type;
    }

    @Override
    public EnAttent onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.consulter_bien_row , parent , false);

        return new EnAttent(view);
    }

    @Override
    public void onBindViewHolder(EnAttent holder, final int position) {

        final String image , ville , surace , prix , adresse , titre ;

        final int idBien = bienList.get(position).getIdBien();

        image = bienList.get(position).getImage();
        titre = bienList.get(position).getTitre();
        ville = bienList.get(position).getVille();
        surace = Double.toString(bienList.get(position).getSurface());
        prix = Double.toString(bienList.get(position).getPrix());
        adresse = bienList.get(position).getAdresse();
        Glide.with(context)
                .load(image)
                .override(100, 180)
                .into(holder.imageView);
        holder.titre.setText(titre);
        holder.ville.setText(ville);
        holder.surface.setText(surace+" M²");
        holder.prix.setText(prix+" DA/MOIS");
        if(type.equals("R")){

            holder.modifierPrix.setVisibility(View.GONE);
        }
        holder.deleteBien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Supprimer un bien");
                builder.setMessage("veuillez vous  vraiment supprimer le bien \n " +titre);

                builder.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String idbien = Integer.toString(idBien);
                        if(!type.equals("C")){
                            deleteBien(idbien , position);
                        }

                        else{

                                UpdateDelete(idbien , position);
                        }

                    }
                });

                builder.setNegativeButton("NON", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        holder.modifierPrix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View ModifiePrix = LayoutInflater.from(context).inflate(R.layout.modifier_prix_dialogue , null);
                final EditText editprix = ModifiePrix.findViewById(R.id.prix_modifier);
                editprix.setText(prix);
                builder.setView(ModifiePrix);


                builder.setPositiveButton("VALIDER", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                       double prixChanger = Double.parseDouble(editprix.getText().toString());

                       if(prixChanger<10000 || prixChanger>100000){
                           Toast.makeText(context, "le prix doit être comme les régles", Toast.LENGTH_SHORT).show();
                       }

                       else{
                           String idb = Integer.toString(idBien);
                           String prixModifier = editprix.getText().toString();
                           updatePrixBien(idb , prixModifier , position);
                       }
                    }
                });

                builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString(Config.KEY_PRIX , prix);
                bundle.putString(Config.KEY_ADRESSE , adresse);
                bundle.putString(Config.KEY_SURFACE , surace);
                bundle.putString(Config.KEY_DESCRIPTION , bienList.get(position).getDescription());
                bundle.putString(Config.IMAGE_PRINCIPALE , image);
                bundle.putString(Config.ID_BIEN , Integer.toString(bienList.get(position).getIdBien()));
                Intent intent = new Intent(context , BienDetailleActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return bienList.size();
    }

    public class EnAttent extends RecyclerView.ViewHolder{
        CardView cardView ;
        ImageView imageView , deleteBien , modifierPrix;
        TextView ville , surface , prix , titre ;

        public EnAttent(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.consulter_bien_card);
            imageView = itemView.findViewById(R.id.consulter_bien_image);
            ville = itemView.findViewById(R.id.consulter_bien_wilaya);
            surface = itemView.findViewById(R.id.consulter_bien_surface);
            prix = itemView.findViewById(R.id.consulter_bien_prix);
            deleteBien = itemView.findViewById(R.id.supp_mon_bien);
            modifierPrix = itemView.findViewById(R.id.modifier_prix_bien);
            titre = itemView.findViewById(R.id.titre_mes_bien);

        }


    }

    //remove item from list
    private void RemoveItem(int position){

        bienList.remove(position);
        notifyItemRemoved(position);
    }
    //update item in the list
    private void UpdateItem(int position , String prix ){
        double p = Double.parseDouble(prix);
        bienList.get(position).setPrix(p);
        notifyItemChanged(position);
    }

    //delete bien from database

    private void deleteBien(final String idBien , final int position){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.DELETE_BIEN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                if(response.equals("bien supprimé avec succès")){
                    RemoveItem(position);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> params = new HashMap<>();

                params.put("idBien" ,idBien);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    // update le prix

    private void updatePrixBien(final String idBien , final String prix , final int position){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.UPDATE_PRIX, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();

                if(response.equals("prix modifié avec succès")){

                    UpdateItem(position , prix);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> params = new HashMap<>();

                params.put("idBien" ,idBien);
                params.put("prix" , prix);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    // supprimer le bien en changant juste le statut pour les biens accepter

    private void UpdateDelete(final String idBien , final int position){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.UPDATE_DELETE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();

                if(response.equals("bien supprimé avec succès")){

                    RemoveItem(position);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> params = new HashMap<>();

                params.put("idBien" ,idBien);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}
