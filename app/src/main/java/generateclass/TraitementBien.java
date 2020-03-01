package generateclass;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.ekrili.R;

/**
 * Created by LENOVO on 27/04/2018.
 */

public class TraitementBien {

    private Activity activity ;

    private LinearLayout container;

    private int NbrChambre=1 , NbrEtage =1, NbrChambreMaison , NbrEtageMaison;

    private String JARDIN ="" , PISCINE="" , GARAGE="" ;

    private String TYPE_TERRAIN = "";

    public TraitementBien(Activity activity , LinearLayout container){


        NbrChambreMaison =1 ; NbrEtageMaison = 1;

        NbrChambreMaison = 1;
        NbrEtageMaison = 1;
        this.activity = activity ;
        this.container = container;
    }



    public void TraitementAppartement(){

        NbrChambre = 1 ; NbrEtage = 1;
        container.removeAllViews();
        View appar = activity.getLayoutInflater().inflate(R.layout.cherche_appartement , null , false);
        container.addView(appar);

        ImageView plus_chambre = appar.findViewById(R.id.plus_chambre);
        ImageView moins_chambre = appar.findViewById(R.id.moins_chambre);
        ImageView plus_etage = appar.findViewById(R.id.plus_etage);
        ImageView moins_etage = appar.findViewById(R.id.moins_etage);
        final TextView chambre_text = appar.findViewById(R.id.chambre_text);
        final TextView etage_text = appar.findViewById(R.id.etage_text);



        plus_chambre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NbrChambre++;
                chambre_text.setText(NbrChambre +" chambre(s) ");
                setNbrChambre(NbrChambre);

            }
        });

        moins_chambre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (NbrChambre != 1) {

                    NbrChambre--;

                    chambre_text.setText(NbrChambre + " chambre(s) ");
                    setNbrChambre(NbrChambre);

                }
            }
        });

        plus_etage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NbrEtage++;
                if(NbrEtage ==1){

                    etage_text.setText(NbrEtage +" ére étage ");


                }

                else{

                    etage_text.setText(NbrEtage +" éme étage ");
                }

            }
        });

        moins_etage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(NbrEtage!=1){

                    NbrEtage--;
                    if(NbrEtage ==1){

                        etage_text.setText(NbrEtage +" ére étage ");
                    }

                    else{

                        etage_text.setText(NbrEtage +" éme étage ");
                    }
                }

            }
        });
    }

    public void TraitementVilla(){
        NbrChambreMaison = 1 ; NbrEtageMaison = 1;
        container.removeAllViews();
        View maison = activity.getLayoutInflater().inflate(R.layout.cherche_maison , null , false);
        container.addView(maison);


        ImageView plus_chambre = maison.findViewById(R.id.plus_chambre_maison);
        ImageView moins_chambre = maison.findViewById(R.id.moins_chambre_maison);
        ImageView plus_etage = maison.findViewById(R.id.plus_etage_maison);
        ImageView moins_etage = maison.findViewById(R.id.moins_etage_maison);
        final TextView chambre_text = maison.findViewById(R.id.chambre_text_maison);
        final TextView etage_text = maison.findViewById(R.id.etage_text_maison);
        CheckBox jardin = maison.findViewById(R.id.jardin_recherche);
        CheckBox piscine = maison.findViewById(R.id.piscine_recherche);
         CheckBox garage = maison.findViewById(R.id.garage_recherche);

        plus_chambre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NbrChambreMaison++;

                chambre_text.setText(NbrChambreMaison +" chambre(s) ");

            }
        });

        moins_chambre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (NbrChambreMaison != 1) {

                    NbrChambreMaison--;

                    chambre_text.setText(NbrChambreMaison + " chambre(s) ");

                }
            }
        });


        plus_etage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NbrEtageMaison++;
                if(NbrEtageMaison ==1){

                    etage_text.setText(NbrEtageMaison +" ére étage ");
                }

                else{

                    etage_text.setText(NbrEtageMaison +" éme étage ");
                }

            }
        });

        moins_etage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(NbrEtageMaison!=1){

                    NbrEtageMaison--;
                    if(NbrEtageMaison ==1){

                        etage_text.setText(NbrEtageMaison +" ére étage ");
                    }

                    else{

                        etage_text.setText(NbrEtageMaison +" éme étage ");
                    }
                }

            }
        });

        jardin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {

                if(checked){
                    JARDIN="1";
                }

                else{
                    JARDIN="";
                }

            }
        });

        piscine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {

                if(checked){
                    PISCINE="1";
                }

                else{
                    PISCINE="";
                }

            }
        });

        garage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {

                if(checked){
                    GARAGE="1";
                }

                else{
                    GARAGE="";
                }

            }
        });


    }

    public void TraitementTerrain(){

        container.removeAllViews();

        View terrain = activity.getLayoutInflater().inflate(R.layout.cherche_terrain , null);

        Spinner spinner = terrain.findViewById(R.id.spinner_type_terrain);

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(activity , R.array.type_terrain  , android.R.layout.simple_spinner_item);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                TYPE_TERRAIN  = adapterView.getItemAtPosition(i).toString();
                setTYPE_TERRAIN(TYPE_TERRAIN);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                TYPE_TERRAIN = "";
                setTYPE_TERRAIN(TYPE_TERRAIN);
            }
        });

        container.addView(terrain);

    }

    public int getNbrChambre() {
        return NbrChambre;
    }

    public int getNbrEtage() {
        return NbrEtage;
    }

    public void setNbrChambre(int nbrChambre) {
        NbrChambre = nbrChambre;
    }

    public void setNbrEtage(int nbrEtage) {
        NbrEtage = nbrEtage;
    }

    public int getNbrChambreMaison() {
        return NbrChambreMaison;
    }

    public void setNbrChambreMaison(int nbrChambreMaison) {
        NbrChambreMaison = nbrChambreMaison;
    }

    public int getNbrEtageMaison() {
        return NbrEtageMaison;
    }

    public void setNbrEtageMaison(int nbrEtageMaison) {
        NbrEtageMaison = nbrEtageMaison;
    }

    public String getJARDIN() {
        return JARDIN;
    }

    public void setJARDIN(String JARDIN) {
        this.JARDIN = JARDIN;
    }

    public String getPISCINE() {
        return PISCINE;
    }

    public void setPISCINE(String PISCINE) {
        this.PISCINE = PISCINE;
    }

    public String getGARAGE() {
        return GARAGE;
    }

    public void setGARAGE(String GARAGE) {
        this.GARAGE = GARAGE;
    }

    public String getTYPE_TERRAIN() {
        return TYPE_TERRAIN;
    }

    public void setTYPE_TERRAIN(String TYPE_TERRAIN) {
        this.TYPE_TERRAIN = TYPE_TERRAIN;
    }
}
