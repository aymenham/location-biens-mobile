package Beans;

/**
 * Created by LENOVO on 10/05/2018.
 */

public class Avis {

    private int idAvis ;

    private Client client ;

    private String date , comment ;

    private float raiting;

    public Avis(int idAvis ,Client client, String date, String comment, float raiting) {
        this.client = client;
        this.date = date;
        this.comment = comment;
        this.raiting = raiting;
        this.idAvis = idAvis ;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public float getRaiting() {
        return raiting;
    }

    public void setRaiting(float raiting) {
        this.raiting = raiting;
    }
}
