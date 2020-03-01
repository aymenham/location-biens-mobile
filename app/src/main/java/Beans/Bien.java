package Beans;

import java.io.Serializable;
import java.util.ArrayList;

import generateclass.Config;


public class Bien implements Serializable {

    private int idBien ;

    private String  ville , adresse , actePro , description , image , titre , statut ;

    private double prix , surface ;

    private Client client;



public Bien(){


}

    public Bien(String ville, String adresse, String actePro, String description,
                Client client, double prix, double surface , int idBien , String image , String titre ,String statut ) {
        this.ville = ville;
        this.adresse = adresse;
        this.actePro = actePro;
        this.description = description;
        this.client = client;
        this.prix = prix;
        this.surface = surface;
        this.idBien = idBien;
        this.image = image ;
        this.statut = statut;
        this.titre = titre ;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getActePro() {
        return actePro;
    }

    public void setActePro(String actePro) {
        this.actePro = actePro;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public double getSurface() {
        return surface;
    }

    public void setSurface(double surface) {
        this.surface = surface;
    }

    public int getIdBien() {
        return idBien;
    }

    public void setIdBien(int idBien) {
        this.idBien = idBien;
    }

    public String getImage() {
        return Config.IMAGES+image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}
