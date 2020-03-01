package com;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.lenovo.ekrili.R;

import java.util.ArrayList;

import Adapters.AppartementAdapter;
import Adapters.GarageAdapter;
import Adapters.MaisonAdapter;
import Adapters.TerrainAdapter;
import Beans.Appartement;
import Beans.Bien;
import Beans.Maison;
import Beans.Terrain;

public class ResultSearchActivity extends AppCompatActivity {

    private RecyclerView SearchRecyler ;

    private RecyclerView.Adapter adapter ;

    private RecyclerView.LayoutManager layoutManager ;

    private ArrayList<Appartement> appartements;

    private ArrayList<Maison> maisons ;

    private ArrayList<Terrain> terrains ;

    private ArrayList<Bien> biens ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     //   appartements = (ArrayList<Appartement>) getIntent().getSerializableExtra(MainActivity.KEY_LIST);
       // maisons = (ArrayList<Maison>) getIntent().getSerializableExtra(MainActivity.KEY_LIST);
        String type = getIntent().getStringExtra(MainActivity.KEY_TYPE);

        if(emptyResult(type)){

            setContentView(R.layout.emptysearch);

        }

        else{

            setContentView(R.layout.activity_result_search);

            SearchRecyler = findViewById(R.id.recycler_search);
            layoutManager = new LinearLayoutManager(this);
            SearchRecyler.setLayoutManager(layoutManager);
            SearchRecyler.setHasFixedSize(true);
            ChooseAdapter(type);
            SearchRecyler.setAdapter(adapter);

        }












    }

    private boolean emptyResult(String type){

        switch (type){

            case "appartement" :
                appartements = (ArrayList<Appartement>) getIntent().getSerializableExtra(MainActivity.KEY_LIST);
                return appartements.isEmpty();

            case "maison" :
                maisons = (ArrayList<Maison>) getIntent().getSerializableExtra(MainActivity.KEY_LIST);
                return maisons.isEmpty();
            case "terrain" :
                terrains = (ArrayList<Terrain>) getIntent().getSerializableExtra(MainActivity.KEY_LIST);
                return terrains.isEmpty();
            case "garage" :
                biens = (ArrayList<Bien>) getIntent().getSerializableExtra(MainActivity.KEY_LIST);
                return biens.isEmpty();

                default:return true;
        }

    }

    private void ChooseAdapter(String type){

        switch (type){

            case "appartement" :

                adapter = new AppartementAdapter(this , appartements);

                break;

            case "maison" :

                adapter = new MaisonAdapter(this , maisons);

                break ;

            case "terrain" :

                adapter = new TerrainAdapter(terrains , this);

                break ;

            case "garage" :

                adapter = new GarageAdapter(biens , this);

                break ;

        }
    }


}
