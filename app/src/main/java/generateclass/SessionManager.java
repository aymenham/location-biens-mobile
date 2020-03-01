package generateclass;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.HommeScreen;
import com.MainActivity;

import java.util.HashMap;

import Beans.Client;

/**
 * Created by LENOVO on 22/04/2018.
 */

public class SessionManager {

    private SharedPreferences sharedPreferences ;

    private SharedPreferences.Editor editor ;

    private Context context ;

    private final  int  PRIVATE_MODE =0;

    private static String Pref_Name = "com.example.lenovo.ekrili" ;

    private static String KEY_IS_First_Time_Lunch  = "isFirstTimeLunch" ;
    private static String KEY_IS_LOGIN;

    public static String KEY_PSEUDO = "pseudo";
    public static String KEY_NAME = "nom";
    public static String KEY_PRENOM = "prenom";
    public static String KEY_DATENAISSANCE= "dateNaissance";
    public static String KEY_ADRESSE= "adresse";
    public static String KEY_EMAIL= "email";
    public static String KEY_TELEPHONE= "telephone";


    public SessionManager(Context context){

        this.context = context;

        sharedPreferences = context.getSharedPreferences(Pref_Name , PRIVATE_MODE);

        editor = sharedPreferences.edit();
    }


    public void createLoginSession(Client client){
        setKEY_IS_First_Time_Lunch();
        editor.putBoolean(KEY_IS_LOGIN , true);
        editor.putString(KEY_PSEUDO , client.getPseudo());
        editor.putString(KEY_NAME , client.getNom());
        editor.putString(KEY_PRENOM , client.getPrenom());
        editor.putString(KEY_DATENAISSANCE, client.getDateNaissance());
        editor.putString(KEY_ADRESSE , client.getAdress());
        editor.putString(KEY_EMAIL , client.getEmail());
        editor.putString(KEY_TELEPHONE , client.getTelephone());

        editor.commit();

    }

    public void setKEY_IS_First_Time_Lunch(){

        editor.putBoolean(KEY_IS_First_Time_Lunch , false);
        editor.commit();
    }

    public boolean IsFirstTimeLunch(){

        return sharedPreferences.getBoolean(KEY_IS_First_Time_Lunch , true);
    }

    public boolean IsLogin(){

        return sharedPreferences.getBoolean(KEY_IS_LOGIN , false);
    }

    public HashMap<String , String> getUserDetaille(){

        HashMap<String , String> user = new HashMap<>();

        user.put(KEY_PSEUDO , sharedPreferences.getString(KEY_PSEUDO , null));
        user.put(KEY_NAME , sharedPreferences.getString(KEY_NAME , null));
        user.put(KEY_PRENOM , sharedPreferences.getString(KEY_PRENOM , null));
        user.put(KEY_DATENAISSANCE , sharedPreferences.getString(KEY_DATENAISSANCE , null));
        user.put(KEY_ADRESSE , sharedPreferences.getString(KEY_ADRESSE , null));
        user.put(KEY_EMAIL , sharedPreferences.getString(KEY_EMAIL, null));
        user.put(KEY_TELEPHONE , sharedPreferences.getString(KEY_TELEPHONE, null));

        return user;

    }


    public void Deconnecter(){

        editor.clear();
        editor.commit();

        Intent i = new Intent(context, HommeScreen.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Mainactivity Activity
        context.startActivity(i);
    }







}
