package Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.lenovo.ekrili.R;

import java.util.List;

import Beans.Avis;

/**
 * Created by LENOVO on 10/05/2018.
 */

public class AvisAdapter extends RecyclerView.Adapter<AvisAdapter.HolderAvis> {


    private Context context;
    private List<Avis> avisList ;

    public AvisAdapter(Context context, List<Avis> avisList) {
        this.context = context;
        this.avisList = avisList;
    }

    @Override
    public HolderAvis onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.avis_row , parent , false);

        return new HolderAvis(view);
    }

    @Override
    public void onBindViewHolder(HolderAvis holder, int position) {

        String complet_name = avisList.get(position).getClient().getNom()+" "+avisList.get(position).getClient().getPrenom();

        String image = avisList.get(position).getClient().getNom().toUpperCase().charAt(0)+""+avisList.get(position).getClient().getPrenom().toUpperCase().charAt(0);

        float raiting = avisList.get(position).getRaiting();

        String date = avisList.get(position).getDate();

        String comment = avisList.get(position).getComment();

        holder.image.setText(image);
        holder.complet_name.setText(complet_name);
        holder.note.setRating(raiting);
        holder.date.setText(date);
        holder.comment.setText(comment);

    }

    @Override
    public int getItemCount() {
        return avisList.size();
    }

    public class HolderAvis extends RecyclerView.ViewHolder{

        TextView image , complet_name , date , comment;
        RatingBar note;

        public HolderAvis(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_avis);
            complet_name = itemView.findViewById(R.id.name_avis);
            date = itemView.findViewById(R.id.date_avis);
            comment = itemView.findViewById(R.id.comment_avis);
            note = itemView.findViewById(R.id.note_avis);

        }
    }
}
