package Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import java.lang.annotation.Target;
import java.net.URI;
import java.util.List;

import Beans.Location;
import generateclass.Config;

/**
 * Created by LENOVO on 29/05/2018.
 */

public class BienEnlocationAdapter extends RecyclerView.Adapter<BienEnlocationAdapter.EnLocation> {

    private Context context ;

    private List<Location> locationList ;

    public BienEnlocationAdapter(Context context, List<Location> locationList) {
        this.context = context;
        this.locationList = locationList;
    }

    @Override
    public EnLocation onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.en_location_row , parent , false);

       return  new EnLocation(view);
    }

    @Override
    public void onBindViewHolder(EnLocation holder, final int position) {

        final String image = locationList.get(position).getBien().getImage();
        final String telephone = locationList.get(position).getClient().getTelephone();
        String nom = locationList.get(position).getClient().getNom();
        String prenom = locationList.get(position).getClient().getPrenom();
        String dateD = locationList.get(position).getDate_debut();
        String dateF = locationList.get(position).getDate_fin();
        String nbrJours = locationList.get(position).getNbrRestant();
        String titre = locationList.get(position).getBien().getTitre();
        // pour détails bien

       final  String prix =Double.toString(locationList.get(position).getBien().getPrix());

       final String adresse = locationList.get(position).getBien().getAdresse();

       final String surace = Double.toString(locationList.get(position).getBien().getSurface());

       final String description = locationList.get(position).getBien().getDescription();

       final String idB =Integer.toString(locationList.get(position).getBien().getIdBien());


        Glide.with(context).load(image).override(100 , com.bumptech.glide.request.target.Target.SIZE_ORIGINAL).into(holder.image_bien);
        holder.nom_locataire.setText(nom);
        holder.prenom_locataire.setText(prenom);
        holder.date_debut.setText(dateD);
        holder.date_fin.setText(dateF);
        holder.nbrJours.setText(nbrJours);
        holder.titre.setText(titre);
        holder.cell_locataire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+telephone));
                context.startActivity(intent);
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString(Config.KEY_PRIX , prix);
                bundle.putString(Config.KEY_ADRESSE , adresse);
                bundle.putString(Config.KEY_SURFACE , surace);
                bundle.putString(Config.KEY_DESCRIPTION , description);
                bundle.putString(Config.IMAGE_PRINCIPALE , image);
                bundle.putString(Config.ID_BIEN , idB);

                Intent intent = new Intent(context , BienDetailleActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    public class EnLocation extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView image_bien , cell_locataire ;
        TextView nom_locataire , prenom_locataire , date_debut , date_fin , nbrJours , titre ;

        public EnLocation(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_en_location);
            image_bien = itemView.findViewById(R.id.image_en_location);
            cell_locataire = itemView.findViewById(R.id.cell_locataire_en_location);
            nom_locataire = itemView.findViewById(R.id.nom_locataire);
            prenom_locataire = itemView.findViewById(R.id.prenom_locataire);
            date_debut = itemView.findViewById(R.id.date_debut_en_location);
            date_fin = itemView.findViewById(R.id.date_fin_en_location);
            nbrJours = itemView.findViewById(R.id.nombre_restant_en_location);
            titre = itemView.findViewById(R.id.titre_en_location);
        }
    }
}
