package generateclass;

/**
 * Created by LENOVO on 28/04/2018.
 */

public class Config {


    // urls :


    // url principale
    public static String URL = "http://192.168.43.51/ekrili/";

    // url des scripte php

    public static  String LoginURL = URL+"login.php";

    public static String RegisterURl = URL+"register.php";

    public static String SEARCH_APP_URL=URL+"chercheappar.php";

    public static String SEARCH_MAISON_URL=URL+"cherchemaison.php";

    public static  final String SEARCH_TERRAIN_URL = URL + "chercheterrain.php";

    public static final String SEARCH_GARAGE_URL = URL + "cherchegarage.php";


    public static String URL_IMAGES_BIENS = URL+"getimages.php";

    public static String AVIS_URL = URL+"getavis.php";

    public static String ADD_COMMENT = URL+"addavis.php";

    public static  String CHECK_PSEUDO_EMAIL = URL+"checkPE.php";

    public static String EDIT_PROFILE = URL+"editprofile.php";

    public static String MES_RESERVATION =URL+"mesreservation.php";

    public static String GET_DETAILLE_BIEN = URL+"getdetaillebiens.php";

    public static  String BIEN_EN_ATTENTE = URL+"bienenattent.php";

    public static String DELETE_BIEN = URL+"deletebien.php";

    public static String UPDATE_PRIX = URL+"updateprix.php";

    public static String BIEN_REFUSER = URL+"bienrefuser.php";

    public static  String BIEN_ACCEPTER = URL+"bienaccepter.php";

    public static String UPDATE_DELETE = URL+"updatedelete.php";

    public static String BIEN_EN_LOCATION = URL+"bienenlocation.php";

    public static String PROPOSER_BIEN = URL+"proposerbien.php";

    public static final String VALIDER_LOCATION = URL+"validerlocation.php";

    public static final String CHOISIR_VALIDER_LOCATION = URL+"choixvalidation.php";

    public static final  String JOUR_RESERVE = URL + "joursreserve.php";

    public static final String INSERER_LOCATION = URL + "insertionlocation.php" ;

    public static final String FORGOT_PASSWORD = URL + "envoyermdp.php" ;



    // dossier des images
    public static String IMAGES  = URL;



    // les keys
    public static String TYPE = "type_";
    public static String KEY_PRIX = "prix_key" ;
    public static String KEY_ADRESSE = "adresse_key";
    public static String KEY_SURFACE = "surface_key";
    public static String KEY_DESCRIPTION = "description_key";
    public static String KEY_CHAMBRE_ = "chambre_app_key";
    public static String KEY_ETAGE_ = "etage_app_key";
    public static String TOUT_IMAGE = "tout_images";
    public static String IMAGE_PRINCIPALE = "image_principale";
    public static String ID_BIEN = "id_bien";
    public static String KEY_TELEPHONE_CLIENT="telephone_client";
    public static String KEY_EMAIL_CLIENT = "email_client";
    public static String KEY_JARDIN_MAISON = "key_jardin_maison";
    public static String KEY_PISCINE_MAISON = "key_piscine_maison";
    public static String KEY_GARAGE_MAISON = "key_garage_maison";
    public static final String KEY_WILAYA = "kay_wilya";
    public static  final String KEY_NAME = "key_name";
    public static final String KEY_PRENOM = "prenom_key";
    public static final String KEY_DATE_DEBUT = "debut_key";
    public static final String KEY_DATE_FIN = "fin_key";
    public static final String KEY_MONTANT_TOTAL = "key_montant";
    public  static final String ID_LOCATION = "key_id_location";
    public static final String TYPE_TERRAIN = "type-terrain";
    public static final String STATUT_KEY ="key_statut";
    // keys pour récupérer les images in strat activity for result

    public static final int REQCODE_ACTE = 100;

    public static final int REQCODE_IMAGES = 200;



}
