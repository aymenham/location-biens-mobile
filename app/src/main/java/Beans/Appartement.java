package Beans;

import java.io.Serializable;

/**
 * Created by LENOVO on 29/04/2018.
 */

public class Appartement extends Bien implements Serializable {

    private int nbrChambre , nbrEtage;


    public Appartement(String ville, String adresse,
                       String actePro, String description, Client client,
                        double prix, double surface, int idBien ,
                        int nbrChambre , int nbrEtage , String image , String titre , String statut
                       ) {

        super(ville, adresse, actePro, description, client, prix, surface, idBien , image , titre , statut);

        this.nbrChambre = nbrChambre;
        this.nbrEtage = nbrEtage;
    }

    public int getNbrChambre() {
        return nbrChambre;
    }

    public void setNbrChambre(int nbrChambre) {
        this.nbrChambre = nbrChambre;
    }

    public int getNbrEtage() {
        return nbrEtage;
    }

    public void setNbrEtage(int nbrEtage) {
        this.nbrEtage = nbrEtage;
    }
}
