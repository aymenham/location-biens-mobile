package Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.BienDetailleActivity;
import com.bumptech.glide.Glide;
import com.example.lenovo.ekrili.R;

import java.util.List;

import Beans.Terrain;
import generateclass.Config;

/**
 * Created by LENOVO on 05/06/2018.
 */

public class TerrainAdapter extends RecyclerView.Adapter<TerrainAdapter.TerrainHolder> {

    private List<Terrain> bienList ;

    private Context context ;

    public TerrainAdapter(List<Terrain> terrains, Context context) {
        this.bienList = terrains;
        this.context = context;
    }

    @Override
    public TerrainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recherche_row , parent , false);

        return  new TerrainHolder(view);
    }

    @Override
    public void onBindViewHolder(TerrainHolder holder, final int position) {

        final String image , ville , surace , prix , titre , statut ;

        image = bienList.get(position).getImage();
        titre = bienList.get(position).getTitre();
        statut = bienList.get(position).getStatut();
        ville = bienList.get(position).getVille();
        surace = Double.toString(bienList.get(position).getSurface());
        prix = Double.toString(bienList.get(position).getPrix());

        Glide.with(context)
                .load(image)
                .override(100, 100)
                .into(holder.Imagebien);
        holder.titre.setText(titre);

        holder.ville.setText(ville);
        holder.surface.setText(surace+" M²");
        holder.prix.setText(prix+" DA/MOIS");
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString(Config.TYPE , "terrain");
                bundle.putString(Config.STATUT_KEY , statut);
                bundle.putString(Config.KEY_PRIX , prix);
                bundle.putString(Config.KEY_ADRESSE , bienList.get(position).getAdresse());
                bundle.putString(Config.KEY_SURFACE , surace);
                bundle.putString(Config.KEY_DESCRIPTION , bienList.get(position).getDescription());
                bundle.putString(Config.ID_BIEN , Integer.toString(bienList.get(position).getIdBien()));
                bundle.putString(Config.TYPE_TERRAIN , bienList.get(position).getTypeTerrain());
                bundle.putString(Config.KEY_TELEPHONE_CLIENT , bienList.get(position).getClient().getTelephone());
                bundle.putString(Config.KEY_EMAIL_CLIENT , bienList.get(position).getClient().getEmail());
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


    public class TerrainHolder extends RecyclerView.ViewHolder{

        CardView cardView ;
        ImageView Imagebien ;
        TextView ville , surface , prix , titre;
        public TerrainHolder(View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.card_search);
            Imagebien = itemView.findViewById(R.id.image_bien);
            ville = itemView.findViewById(R.id.ville_search);
            surface = itemView.findViewById(R.id.surface_search);
            prix = itemView.findViewById(R.id.prix_search);
            titre = itemView.findViewById(R.id.titre_search);
        }
    }
}
