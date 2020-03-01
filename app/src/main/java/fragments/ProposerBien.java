package fragments;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.ekrili.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import generateclass.AlertDialogManager;
import generateclass.Config;
import generateclass.TraitementBien;
import generateclass.SessionManager;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProposerBien extends Fragment implements AdapterView.OnItemSelectedListener , View.OnClickListener {

    private View view ;
    private Spinner typeBien , wilaya;
    private LinearLayout linearLayout ;
    private TraitementBien traitementBien;
    private AlertDialogManager dialogManager;
    private EditText titre , prix  , adresse , surface , description ;
    private Button   imageBien , valider ;
    private ImageView acteBien;
    private String Type="" ,  Titre , Prix , Wilaya="" , Adresse , Surface , Description , chambreA , etageA  ,
                    chambreM  , etageM , Piscine , Garage , Jardin , encodedImage ="" ,
                     typeT="";
    private JSONObject jsonObject;
    private SessionManager sessionManager ;
    private ProgressDialog progressDialog ;

    // pour les images :

    private ArrayList<Uri> imagesUriList;
    private ArrayList<String> encodedImageList;
    private String imageURI;
    private JSONArray jsonArray ;



    public ProposerBien() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_proposer_bien, container, false);
        initViews();
        typeBien.setOnItemSelectedListener(this);
        wilaya.setOnItemSelectedListener(this);
        imageBien.setOnClickListener(this);
        valider.setOnClickListener(this);
        acteBien.setOnClickListener(this);
        prix.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!prix.getText().toString().isEmpty()){
                    double price = Double.parseDouble(prix.getText().toString());
                    if(!checkPrice(price)){

                        prix.setError("le prix doit être pas moins de 5000 DA \n et pas plus de 100000 DA");
                    }
                }

            }
        });
        return view;
    }

    private void  initViews(){

        typeBien = view.findViewById(R.id.spinner_proposer);
        linearLayout = view.findViewById(R.id.proposer_bien_container);
        titre = view.findViewById(R.id.titre_bien_proposer);
        prix = view.findViewById(R.id.prix_bien_proposer);
        wilaya = view.findViewById(R.id.wilaya_bien_proposer);
        adresse = view.findViewById(R.id.adresse_bien_proposer);
        surface = view.findViewById(R.id.surface_bien_proposer);
        description = view.findViewById(R.id.description_bien_proposer);
        imageBien = view.findViewById(R.id.image_bien_proposer);
        valider = view.findViewById(R.id.valider_proposition);
        acteBien = view.findViewById(R.id.acte_bien_proposer);
        sessionManager = new SessionManager(getActivity());
        encodedImageList = new ArrayList<>();
        imagesUriList = new ArrayList<>();
        jsonObject = new JSONObject();
        traitementBien = new TraitementBien(getActivity() , linearLayout);
        initAppartement();
        initMaison();
        dialogManager = new AlertDialogManager(getActivity() , getContext());
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Insertion des informations ...");
        progressDialog.setCancelable(false);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.type_bien, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        typeBien.setAdapter(adapter);

        ArrayAdapter<CharSequence> wilayadapter = ArrayAdapter.createFromResource(getActivity() , R.array.wilaya , android.R.layout.simple_spinner_item);

        wilayadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        wilaya.setAdapter(wilayadapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        Spinner spinner = (Spinner) adapterView;

        if(spinner.getId()==R.id.spinner_proposer){

            Type = adapterView.getItemAtPosition(i).toString();

            switch(Type){
                case "" :
                    linearLayout.removeAllViews();
                    break;
                case "Appartement" :
                    traitementBien.TraitementAppartement();
                    break;
                case "Maison" :
                    traitementBien.TraitementVilla();
                    break;
                case "Terrain" :
                    traitementBien.TraitementTerrain();
                    break;
                case "Garage" :
                    linearLayout.removeAllViews();
                    break;

            }
        }

        if(spinner.getId()==R.id.wilaya_bien_proposer){

            Wilaya = adapterView.getItemAtPosition(i).toString();
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
            linearLayout.removeAllViews();
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){

            case R.id.acte_bien_proposer :
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, Config.REQCODE_ACTE);
                break;

            case R.id.image_bien_proposer :
                Intent intent1 = new Intent();
                intent1.setType("image/*");
                intent1.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent1.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent1, "Choose application"), Config.REQCODE_IMAGES);
                break;

            case R.id.valider_proposition :
                Valider();
                break;

        }
    }

    private void Valider() {
        //tout les biens
        Titre = titre.getText().toString().trim();
        Prix = prix.getText().toString().trim();
        Adresse = adresse.getText().toString().trim();
        Surface = surface.getText().toString().trim();
        Description = description.getText().toString().trim();
        //pour les appartment
        chambreA = Integer.toString(traitementBien.getNbrChambre());
        etageA = Integer.toString(traitementBien.getNbrEtage());
        //pour les maision
        chambreM = Integer.toString(traitementBien.getNbrChambreMaison());
        etageM = Integer.toString(traitementBien.getNbrEtageMaison());
        Jardin = traitementBien.getJARDIN();

        // pour les terrain
        typeT = traitementBien.getTYPE_TERRAIN();


        if(Jardin.equals("")){
            Jardin = "0";
        }
        Piscine = traitementBien.getPISCINE();
        if(Piscine.equals("")){
            Piscine = "0";
        }
        Garage = traitementBien.getGARAGE();
        if(Garage.equals("")){
            Garage = "0";
        }



        if(Type.isEmpty()){
            Toast.makeText(getContext(), "choisir un type du bien", Toast.LENGTH_SHORT).show();
        }
        else if(Titre.isEmpty() ||Prix.isEmpty() || Wilaya.isEmpty() || Adresse.isEmpty() ||
                Surface.isEmpty() || Description.isEmpty()){

            dialogManager.ShowAlertDialoge("Il faut remplir tout les champs ");

        }

        else if(!checkPrice(Double.parseDouble(Prix))){
            Toast.makeText(getContext(), "le prix doit être pas moins de 1000 DA \n et pas plus de 100000 DA ", Toast.LENGTH_SHORT).show();
        }

        else if(encodedImage.isEmpty()){

            Toast.makeText(getContext(), "il faut choisir un acte ", Toast.LENGTH_SHORT).show();
        }
        else if(encodedImageList.isEmpty()){

            Toast.makeText(getContext(), "il faut choisir au moins une image", Toast.LENGTH_SHORT).show();
        }
        else {

            progressDialog.show();
            try {
                jsonObject.put("type" ,Type);
                jsonObject.put("titre" ,Titre);
                jsonObject.put("prix" ,Prix);
                jsonObject.put("surface" ,Surface);
                Wilaya = Wilaya.trim().replaceAll("[^A-Za-z]","");
                jsonObject.put("wilaya" ,Wilaya);
                jsonObject.put("adresse" ,Adresse);
                jsonObject.put("description" ,Description);
                // pour les appartement
                jsonObject.put("pseudo" ,sessionManager.getUserDetaille().get(SessionManager.KEY_PSEUDO));
                jsonObject.put("chambreA" , chambreA);
                jsonObject.put("etageA" , etageA);

                //pour les maison

                jsonObject.put("chambreM" , chambreM);
                jsonObject.put("etageM" , etageM);
                jsonObject.put("jardin" , Jardin);
                jsonObject.put("piscine" , Piscine);
                jsonObject.put("garage" , Garage);

                // pour les terrain

                jsonObject.put("typeT" , typeT);

                // pour l'acte

                jsonObject.put("acte" , encodedImage);

                // pour les images

                jsonObject.put("imageList" , jsonArray);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            InsertToDataBase();



        }



    }

    private void InsertToDataBase() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Config.PROPOSER_BIEN, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.hide();
                try {
                    String message = response.getString("message");
                    finishUpload(message);
                    Log.e("message from server:" , message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Log.e("erreur from server :" , error.toString());
                Toast.makeText(getContext(), "erreur lors de la proposition du bien ", Toast.LENGTH_SHORT).show();
            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(200*30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);
    }

    private void initAppartement(){

        traitementBien.setNbrChambre(1);
        traitementBien.setNbrEtage(1);
    }

    private void initMaison(){

        traitementBien.setNbrChambreMaison(1);
        traitementBien.setNbrEtageMaison(1);
        traitementBien.setJARDIN("");
        traitementBien.setPISCINE("");
        traitementBien.setGARAGE("");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Config.REQCODE_ACTE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            acteBien.setImageURI(selectedImageUri);
            traitementActe();
        }
        try {
            // When an Image is picked
            if (requestCode == Config.REQCODE_IMAGES && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                imagesUriList = new ArrayList<Uri>();
                encodedImageList.clear();
                if(data.getData()!=null){

                    Uri mImageUri=data.getData();

                    // Get the cursor
                    Cursor cursor = getActivity().getContentResolver().query(mImageUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageURI  = cursor.getString(columnIndex);
                    cursor.close();

                }else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);
                            // Get the cursor
                            Cursor cursor = getActivity().getContentResolver().query(uri, filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageURI  = cursor.getString(columnIndex);
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                            String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                            encodedImageList.add(encodedImage);
                            cursor.close();

                        }

                        traitementImages();
                    }


                }
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }




    private void traitementActe(){

        Bitmap image = ((BitmapDrawable) acteBien.getDrawable()).getBitmap();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
         encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

    }

    private void traitementImages(){

         jsonArray = new JSONArray();


        for (String encoded: encodedImageList){
            jsonArray.put(encoded);
        }
    }


    // inistialisation du tout aprés l'insertion
    private void finishUpload(String message){

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext()) ;

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();

        final View dialogView = layoutInflater.inflate(R.layout.custom_merci_dialog , null) ;

        TextView textView = dialogView.findViewById(R.id.merci_text);

        builder.setView(dialogView) ;

        textView.setText(message);

        builder.setPositiveButton("MERCI", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                initAppartement();
                initMaison();
                typeBien.setSelection(0);
                titre.setText("");
                prix.setText("");
                wilaya.setSelection(0);
                adresse.setText("");
                surface.setText("");
                description.setText("");
                acteBien.setImageDrawable(null);
                linearLayout.removeAllViews();
                imagesUriList.clear();
                encodedImageList.clear();

            }
        });

        AlertDialog alertDialog = builder.create() ;

        alertDialog.show();

    }

    // vérifier les régles d'un bien

    private boolean checkPrice(double price){

        return price>=5000 && price<=100000;
    }



}
