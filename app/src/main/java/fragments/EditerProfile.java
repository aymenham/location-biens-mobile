package fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.lenovo.ekrili.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import Beans.Client;
import generateclass.AlertDialogManager;
import generateclass.Config;
import generateclass.SessionManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditerProfile extends Fragment {


    private View view;

    private EditText nom , prenom , pseudo , dateNaissance , adress , email , telephone , motdp , Nmotdp ;

    private String Nom , Prenom , Pseudo , Adress ,DateNaissance , Email , Telephone , Motdp , CMotdp ,  actuel_pseudo , actuel_email ;

    private Button finish_edit ;

    private SessionManager sessionManager;

    private AlertDialogManager dialogManager ;

    private boolean PseudoIsCheked = true  , EmailIscheked = true ;

    private Client client ;




    public EditerProfile() {

        setHasOptionsMenu(false);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_editer_profile, container, false);

        this.InitializeViews();
        this.fillEditText();

        finish_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Edit();
            }
        });



        return view;
    }

    private void CheckAndUpdate() {

        // check pseudo and email if they exist or not

        if (Pseudo.equals(actuel_pseudo) && Email.equals(actuel_email)) {

            PseudoIsCheked = true;
            EmailIscheked = true;
        } else {

            if(!Pseudo.equals(actuel_pseudo) && !Email.equals(actuel_email)){

                PseudoIsCheked = false;
                EmailIscheked = false;


            }

            if (!Pseudo.equals(actuel_pseudo) && Email.equals(actuel_email)) {

                Email = "";
                EmailIscheked = true;
                PseudoIsCheked = false;
            } if (Pseudo.equals(actuel_pseudo) && !Email.equals(actuel_email)) {


                Pseudo = "";
                PseudoIsCheked = true;
                EmailIscheked = false;

            }

        }

        if(PseudoIsCheked && EmailIscheked){

            DoTheEdit();
        }

        else{

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.CHECK_PSEUDO_EMAIL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {



                    if (!PseudoIsCheked || !EmailIscheked) {


                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject informations = jsonArray.getJSONObject(0);

                            PseudoIsCheked = informations.getBoolean("pseudo");
                            EmailIscheked = informations.getBoolean("email");



                            if (!PseudoIsCheked) {

                                pseudo.setError("pseudo existe dèja");

                            }

                            if (!EmailIscheked) {

                                email.setError("email existe dèja");

                            }

                             if(PseudoIsCheked && EmailIscheked){


                                DoTheEdit();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }






                }


            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                }
            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String , String> params = new HashMap<>();

                    params.put("user_name" , Pseudo);
                    params.put("user_email" , Email);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getContext());

            requestQueue.add(stringRequest);
        }
    }

    private void fillEditText() {

        String actuel_nom = sessionManager.getUserDetaille().get(SessionManager.KEY_NAME);
        String actuel_prenom  = sessionManager.getUserDetaille().get(SessionManager.KEY_PRENOM);
         actuel_pseudo = sessionManager.getUserDetaille().get(SessionManager.KEY_PSEUDO);
        String actuel_date_naiisance= sessionManager.getUserDetaille().get(SessionManager.KEY_DATENAISSANCE);
        String actuel_adresse = sessionManager.getUserDetaille().get(SessionManager.KEY_ADRESSE);
         actuel_email = sessionManager.getUserDetaille().get(SessionManager.KEY_EMAIL);
        String actuel_telephone= sessionManager.getUserDetaille().get(SessionManager.KEY_TELEPHONE);

        nom.setText(actuel_nom);
        prenom.setText(actuel_prenom);
        pseudo.setText(actuel_pseudo);
        dateNaissance.setText(actuel_date_naiisance);
        adress.setText(actuel_adresse);
        email.setText(actuel_email);
        telephone.setText(actuel_telephone);
        motdp.setText("");
        Nmotdp.setText("");


    }

    private void Edit() {

        Nom = nom.getText().toString().trim() ;
        Prenom = prenom.getText().toString().trim() ;
        Pseudo = pseudo.getText().toString().trim() ;
        Adress = adress.getText().toString().trim() ;
        DateNaissance = dateNaissance.getText().toString().trim() ;
        Email = email.getText().toString().trim() ;
        Telephone = telephone.getText().toString().trim() ;
        Motdp = motdp.getText().toString().trim() ;
        CMotdp = Nmotdp.getText().toString().trim() ;

        if ((Nom.isEmpty() || Prenom.isEmpty() || Pseudo.isEmpty() ||
                Adress.isEmpty() || DateNaissance.isEmpty() || Email.isEmpty() || Telephone.isEmpty() )
                || ( (Motdp.isEmpty() && !CMotdp.isEmpty()) ||( !Motdp.isEmpty() && CMotdp.isEmpty()) )
                ) {

            dialogManager.ShowAlertDialoge("Il faut remplir tout les champs ");


        } else {



            if (!isValidEmail(Email)) {

                dialogManager.ShowAlertDialoge("Email non valide");
            }

            if(!Motdp.equals(CMotdp)){

                dialogManager.ShowAlertDialoge("confirme bien votre mot de passe");
            }

            else{

                CheckAndUpdate();



            }



        }
    }


    private void InitializeViews() {

        nom = view.findViewById(R.id.edit_nom_inscrption);
        prenom = view.findViewById(R.id.edit_prenom_inscrption);
        pseudo = view.findViewById(R.id.edit_pseudo_inscrption);
        dateNaissance = view.findViewById(R.id.edit_date_inscrption);
        adress = view.findViewById(R.id.edit_adress_inscrption);
        email = view.findViewById(R.id.edit_email_inscrption);
        telephone = view.findViewById(R.id.edit_telephone_inscrption);
        motdp = view.findViewById(R.id.edit_mdp_inscrption);
        Nmotdp = view.findViewById(R.id.edit_cmdp_inscrption);
        finish_edit = view.findViewById(R.id.edit_finish);
        sessionManager = new SessionManager(getActivity());
        dialogManager = new AlertDialogManager(getActivity() , getContext());
    }

    private final boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    private void  DoTheEdit(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.EDIT_PROFILE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                boolean check_pdate = Boolean.parseBoolean(response);

                if(check_pdate){

                    EmailIscheked= true;
                    PseudoIsCheked = true;

                    final AlertDialog.Builder builder = new AlertDialog.Builder(getContext()) ;

                    LayoutInflater layoutInflater = getActivity().getLayoutInflater();

                    final View dialogView = layoutInflater.inflate(R.layout.custom_merci_dialog , null) ;

                    TextView textView = dialogView.findViewById(R.id.merci_text);

                    builder.setView(dialogView) ;

                    textView.setText("profile edité avec succès ");

                    builder.setPositiveButton("Merci", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(Pseudo.isEmpty()){
                                 client = new Client(actuel_pseudo , Nom , Prenom , DateNaissance , Adress , Email , Telephone);
                            }else if(Email.isEmpty()){
                                 client = new Client(Pseudo , Nom , Prenom , DateNaissance , Adress , actuel_email , Telephone);
                            }
                            else{
                               client = new Client(Pseudo , Nom , Prenom , DateNaissance , Adress , Email , Telephone);
                            }

                            sessionManager.createLoginSession(client);
                            fillEditText();
                            dialogInterface.dismiss();

                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                }

                else{

                    Toast.makeText(getContext(), "profile non edité", Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> params = new HashMap<>();

                    params.put("ancien_pseudo" , actuel_pseudo);

                if(Pseudo.isEmpty()){

                    params.put("user_pseudo" , actuel_pseudo);
                }
                else{
                    params.put("user_pseudo" , Pseudo);
                }

                params.put("user_nom" , Nom);
                params.put("user_prenom" , Prenom);
                params.put("user_date" , DateNaissance);
                params.put("user_adress" , Adress);
                if(Email.isEmpty()){
                    params.put("user_email" , actuel_email);
                }
                else{
                    params.put("user_email" , Email);
                }

                params.put("user_telephone" , Telephone);
                params.put("user_pass" , Motdp);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

}
