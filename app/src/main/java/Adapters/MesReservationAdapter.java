package Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import Beans.Location;
import generateclass.Config;

/**
 * Created by LENOVO on 07/06/2018.
 */

public class MesReservationAdapter extends RecyclerView.Adapter<MesReservationAdapter.MesReservationHolder> {

    private List<Location> list ;

    private Context context;

    private String type ;

    public MesReservationAdapter(List<Location> list, Context context , String type) {
        this.list = list;
        this.context = context;
        this.type = type;
    }

    @NonNull
    @Override
    public MesReservationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.mes_reservation_row , parent , false);

        return new MesReservationHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MesReservationHolder holder, final int position) {

       String image = list.get(position).getBien().getImage();
       String titre = list.get(position).getBien().getTitre();
       String nomP = list.get(position).getClient().getNom();
       String prenomP = list.get(position).getClient().getPrenom();
       String dateD = list.get(position).getDate_debut();
       String dateF = list.get(position).getDate_fin();
       String nbrJours = list.get(position).getNbrRestant();
       final String telephone = list.get(position).getClient().getTelephone();

        Glide.with(context).load(image).override(100 , 210).into(holder.imageView);
        holder.titre.setText(titre);
        holder.nomPro.setText(nomP);
        holder.prenomPro.setText(prenomP);
        holder.date_debut.setText(dateD);
        holder.date_fin.setText(dateF);

        if(!type.equals("A") && !type.equals("C")){

            holder.nbrJours.setVisibility(View.GONE);
            holder.textnbrJours.setVisibility(View.GONE);
        }
        else{
            if(type.equals("A")){

                holder.textnbrJours.setText("jours restants pour débuter ");
                holder.nbrJours.setText(nbrJours);

            }

            if(type.equals("C")){

                holder.textnbrJours.setText("jours restants pour la fin  ");
                holder.nbrJours.setText(nbrJours);
            }
        }

       holder.telephone.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               Intent i = new Intent(Intent.ACTION_DIAL);
               i.setData(Uri.parse("tel:"+telephone));
               context.startActivity(i);
           }
       });
       holder.cardView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               String prix = Double.toString( list.get(position).getBien().getPrix());
               String adresse = list.get(position).getBien().getAdresse();
               String surace = Double.toString(list.get(position).getBien().getSurface());
               String description = list.get(position).getBien().getDescription();
               String idB = Integer.toString(list.get(position).getBien().getIdBien());
               String email = list.get(position).getClient().getEmail();
               String statut = list.get(position).getBien().getStatut();
               Bundle bundle = new Bundle();
               bundle.putString(Config.KEY_PRIX , prix);
               bundle.putString(Config.KEY_ADRESSE , adresse);
               bundle.putString(Config.KEY_SURFACE , surace);
               bundle.putString(Config.KEY_DESCRIPTION , description);
               bundle.putString(Config.ID_BIEN , idB);
               bundle.putString(Config.KEY_EMAIL_CLIENT ,email);
               bundle.putString(Config.STATUT_KEY , statut);
               Intent intent = new Intent(context , BienDetailleActivity.class);
               intent.putExtras(bundle);
               context.startActivity(intent);
           }
       });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MesReservationHolder extends RecyclerView.ViewHolder{

        CardView cardView ;

        ImageView imageView ,telephone ;

        TextView titre , date_debut , date_fin , nomPro , prenomPro , textnbrJours , nbrJours ;


        public MesReservationHolder(View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.card_mesi_reservation);
            imageView = itemView.findViewById(R.id.image_mes_reservatio);
            telephone = itemView.findViewById(R.id.telephone_mes_reservation);
            titre = itemView.findViewById(R.id.titre_mes_reservation);
            date_debut = itemView.findViewById(R.id.date_debut_mes_reservation);
            date_fin = itemView.findViewById(R.id.date_fin_mes_reservation);
            nomPro = itemView.findViewById(R.id.nom_mes_reservation);
            prenomPro = itemView.findViewById(R.id.prenom_mes_reservation);
            textnbrJours = itemView.findViewById(R.id.text_nombre_jour);
            nbrJours = itemView.findViewById(R.id.nbr_jours_reservation);

        }
    }
}
