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

import com.bumptech.glide.Glide;
import com.example.lenovo.ekrili.R;
import com.example.lenovo.ekrili.ValiderBienActivity;

import java.util.List;

import Beans.Location;
import generateclass.Config;

/**
 * Created by LENOVO on 02/06/2018.
 */

public class ValiderLocationAdapter extends RecyclerView.Adapter<ValiderLocationAdapter.ValiderHolder> {

    private Context context ;

    private List<Location> list;

    public ValiderLocationAdapter(Context context, List<Location> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ValiderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.valider_location_row , parent , false);

        return new ValiderHolder(view);
    }

    @Override
    public void onBindViewHolder(ValiderHolder holder, final int position) {
      String tittre ;
      final String image = list.get(position).getBien().getImage();
      final String date_debut = list.get(position).getDate_debut();
      final String date_fin = list.get(position).getDate_fin();
      final String montantTotal = list.get(position).getMontantTotal();
      final String telephone = list.get(position).getClient().getTelephone();

        Glide.with(context).
                load(image).
                override(100 , com.bumptech.glide.request.target.Target.SIZE_ORIGINAL).
                into(holder.imageView);

        holder.date_debut.setText(date_debut);
      holder.date_fin.setText(date_fin);
      holder.montant_total.setText(montantTotal+" DA");

      holder.telephone.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

              Intent cell = new Intent(Intent.ACTION_DIAL);
              cell.setData(Uri.parse("tel:" +telephone));
              context.startActivity(cell);
          }
      });

      holder.cardView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              String idLocation = Integer.toString(list.get(position).getIdLocation());
              String idBien = Integer.toString(list.get(position).getBien().getIdBien());
              String prix = Double.toString(list.get(position).getBien().getPrix());
              String adresse = list.get(position).getBien().getAdresse();
              String wilaya = list.get(position).getBien().getVille();
              String surface = Double.toString(list.get(position).getBien().getSurface());
              String nom = list.get(position).getClient().getNom();
              String prenom = list.get(position).getClient().getPrenom();
              Bundle bundle = new Bundle();
              bundle.putString(Config.ID_LOCATION , idLocation);
              bundle.putString(Config.ID_BIEN , idBien);
              bundle.putString(Config.IMAGE_PRINCIPALE , image);
              bundle.putString(Config.KEY_PRIX , prix);
              bundle.putString(Config.KEY_ADRESSE , adresse);
              bundle.putString(Config.KEY_WILAYA , wilaya);
              bundle.putString(Config.KEY_SURFACE  , surface);
              bundle.putString(Config.KEY_NAME , nom);
              bundle.putString(Config.KEY_PRENOM , prenom);
              bundle.putString(Config.KEY_DATE_DEBUT , date_debut);
              bundle.putString(Config.KEY_DATE_FIN , date_fin);
              bundle.putString(Config.KEY_MONTANT_TOTAL , montantTotal);
              Intent intent = new Intent(context , ValiderBienActivity.class);
              intent.putExtras(bundle);
              context.startActivity(intent);

          }
      });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ValiderHolder extends RecyclerView.ViewHolder{

        CardView cardView ;
        ImageView imageView  , telephone ;
        TextView date_debut , date_fin , montant_total , titre ;

        public ValiderHolder(View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.card_valider);
            imageView = itemView.findViewById(R.id.image_valider);
            telephone = itemView.findViewById(R.id.cell_locataire_valider);
            date_debut = itemView.findViewById(R.id.date_debut_valider);
            date_fin = itemView.findViewById(R.id.date_fin_valider);
            montant_total = itemView.findViewById(R.id.montant_total_valider);
            titre = itemView.findViewById(R.id.valider_titrre);
        }
    }
}
