package Beans;

/**
 * Created by LENOVO on 18/05/2018.
 */

public class Location {

    private int idLocation;

    private Client client ;

    private Bien bien ;

    private String date_debut , date_fin , statut , NbrRestant , montantTotal ;

    public Location(){


    }


    public Location(Client client, Bien bien, String date_debut, String date_fin, String statut , String NbrRestant) {
        this.client = client;
        this.bien = bien;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.statut = statut;
        this.NbrRestant = NbrRestant;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Bien getBien() {
        return bien;
    }

    public void setBien(Bien bien) {
        this.bien = bien;
    }

    public String getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(String date_debut) {
        this.date_debut = date_debut;
    }

    public String getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(String date_fin) {
        this.date_fin = date_fin;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getNbrRestant() {
        return NbrRestant;
    }

    public void setNbrRestant(String nbrRestant) {
        NbrRestant = nbrRestant;
    }

    public String getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(String montantTotal) {
        this.montantTotal = montantTotal;
    }

    public int getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(int idLocation) {
        this.idLocation = idLocation;
    }
}
