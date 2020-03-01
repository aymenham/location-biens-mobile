package generateclass;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.circulardialog.CDialog;
import com.example.circulardialog.extras.CDConstants;
import com.HommeScreen;
import com.MainActivity;
import com.example.lenovo.ekrili.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import sendemail.EnvoyerEmail;

/**
 * Created by LENOVO on 24/04/2018.
 */

public class AlertDialogManager {

    private Activity activity ;

    private Context context ;

    public AlertDialogManager(Activity activity , Context context) {
        this.activity = activity;
        this.context = context ;
    }

    public void ShowForgotPassword(){

        final AlertDialog.Builder builder = new AlertDialog.Builder(context) ;

        LayoutInflater layoutInflater = activity.getLayoutInflater();

        final View dialogView = layoutInflater.inflate(R.layout.custom_forgot_password_dialog , null) ;
        final EditText editText = dialogView.findViewById(R.id.email_forgot_password);

        builder.setView(dialogView) ;

        builder.setPositiveButton("VALIDER", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                String email = editText.getText().toString().trim();
                if (email.isEmpty()){
                    Toast.makeText(activity, "ecrire un email", Toast.LENGTH_SHORT).show();
                }

                else{
                    SendPassword(email);
                }


            }
        });
        builder.setNegativeButton("ANNULER", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create() ;

        alertDialog.show();

    }

    private void SendPassword(final String email) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.FORGOT_PASSWORD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    boolean exists = jsonObject.getString("code").equals("1");
                    String message = jsonObject.getString("message");

                    if(exists){

                        final String password = jsonObject.getString("pass");
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                EnvoyerEmail.Envoyer(email , "votre mot de passe oublié" , password);
                            }
                        }).start();

                        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                    }

                    else {
                        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, "erreur , verifier votre connexion", Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> params = new HashMap<>();
                params.put("email" , email);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        requestQueue.add(stringRequest);
    }

    public void ShowMerciMessage( String message , Intent intent){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context) ;

        LayoutInflater layoutInflater = activity.getLayoutInflater();

        final View dialogView = layoutInflater.inflate(R.layout.custom_merci_dialog , null) ;

        TextView textView = dialogView.findViewById(R.id.merci_text);

        builder.setView(dialogView) ;

        textView.setText(message);

        builder.setPositiveButton("MERCI", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                Intent intent = new Intent(activity, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                String startAct = activity.getIntent().getExtras().getString("FROM_WHERE_Register");

                if(startAct.equals("FROM_HOMME_SCREEN")){
                    HommeScreen.HommeScreenAct.finish();

                }


                if(startAct.equals("FROM_MAIN")){
                    MainActivity.MainACTIV.finish();

                }
                activity.finish();
            }
        });


        AlertDialog alertDialog = builder.create() ;

        alertDialog.show();

    }

    public  void  ShowAlertDialoge(String message){

        new CDialog(context).createAlert(message,
                CDConstants.ERROR,   // Type of dialog
                CDConstants.LARGE)    //  size of dialog
                .setAnimation(CDConstants.SCALE_FROM_BOTTOM_TO_TOP)     //  Animation for enter/exit
                .setDuration(2000)   // in milliseconds
                .setTextSize(CDConstants.LARGE_TEXT_SIZE)  // CDConstants.LARGE_TEXT_SIZE, CDConstants.NORMAL_TEXT_SIZE
                .show();

    }
}
