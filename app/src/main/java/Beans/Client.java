package Beans;

import java.io.Serializable;

/**
 * Created by LENOVO on 28/04/2018.
 */

public class Client implements Serializable {

    private String pseudo ;

    private String nom ;

    private  String prenom;

    private String dateNaissance;

    private String adress;

    private String email;

    private String telephone;


    public Client(){


    }

    public Client(String pseudo, String nom, String prenom , String dateNaissance, String adress, String email, String telephone) {
        this.pseudo = pseudo;
        this.nom = nom;
        this.dateNaissance = dateNaissance;
        this.adress = adress;
        this.email = email;
        this.telephone = telephone;
        this.prenom = prenom;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getNom() {
        return nom;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public String getAdress() {
        return adress;
    }

    public String getEmail() {
        return email;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
