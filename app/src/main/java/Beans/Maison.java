package Beans;

import java.io.Serializable;

/**
 * Created by LENOVO on 02/05/2018.
 */

public class Maison extends Bien implements Serializable {

    private int nbrChambre , nbrEtage ;

    private boolean garage , piscine , jardin ;

    public Maison(String ville, String adresse, String actePro, String description,
                  Client client, double prix, double surface, int idBien, String image ,
                  int nbrChambre , int nbrEtage , boolean garage , boolean piscine , boolean jardin , String titre , String statut
                  ) {
        super(ville, adresse, actePro, description, client, prix, surface, idBien, image , titre , statut);

        this.nbrChambre = nbrChambre;
        this.nbrEtage = nbrEtage;
        this.garage = garage;
        this.piscine = piscine;
        this.jardin = jardin;
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

    public boolean isGarage() {
        return garage;
    }

    public void setGarage(boolean garage) {
        this.garage = garage;
    }

    public boolean isPiscine() {
        return piscine;
    }

    public void setPiscine(boolean piscine) {
        this.piscine = piscine;
    }

    public boolean isJardin() {
        return jardin;
    }

    public void setJardin(boolean jardin) {
        this.jardin = jardin;
    }
}
