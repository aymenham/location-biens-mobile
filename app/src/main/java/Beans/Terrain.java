package Beans;

import java.io.Serializable;

/**
 * Created by LENOVO on 05/06/2018.
 */

public class Terrain extends Bien implements Serializable {

    private String typeTerrain ;

    public Terrain (String ville, String adresse,
                       String actePro, String description, Client client,
                       double prix, double surface, int idBien , String image , String typeTerrain , String titre , String statut
    ) {

        super(ville, adresse, actePro, description, client, prix, surface, idBien , image , titre , statut);

        this.typeTerrain = typeTerrain;
    }

    public String getTypeTerrain() {
        return typeTerrain;
    }

    public void setTypeTerrain(String typeTerrain) {
        this.typeTerrain = typeTerrain;
    }
}
