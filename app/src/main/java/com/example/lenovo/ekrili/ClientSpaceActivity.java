package com.example.lenovo.ekrili;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.MainActivity;

import fragments.EditerProfile;
import fragments.MesBien;
import fragments.MesReservation;
import fragments.ProposerBien;
import fragments.ValiderLocation;
import generateclass.SessionManager;

public class ClientSpaceActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_space);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initViews();
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View view = navigationView.getHeaderView(0);
        TextView client_name = view.findViewById(R.id.espace_client_name);

        String complet_name = sessionManager.getUserDetaille().get(SessionManager.KEY_NAME)+" "+sessionManager.getUserDetaille().get(SessionManager.KEY_PRENOM);
        client_name.setText(complet_name);
        getSupportFragmentManager().beginTransaction().replace(R.id.espace_client_container , new MesReservation()).commit();

        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initViews(){

        sessionManager = new SessionManager(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.client_space, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_recherche) {
            Intent intent = new Intent(this , MainActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment;
        switch (id){

            case R.id.editer_profile :
                fragment = new EditerProfile();
                getSupportFragmentManager().beginTransaction().replace(R.id.espace_client_container , fragment).commit();
                break;

            case R.id.mes_reservation :
                fragment = new MesReservation();
                getSupportFragmentManager().beginTransaction().replace(R.id.espace_client_container , fragment).commit();
                break;

            case R.id.deconnecter :
               sessionManager.Deconnecter();
               finish();
                break;
            case R.id.mes_biens :
                fragment = new MesBien();
                getSupportFragmentManager().beginTransaction().replace(R.id.espace_client_container , fragment).commit();
                break;
            case R.id.proposer_bien :
                fragment = new ProposerBien();
                getSupportFragmentManager().beginTransaction().replace(R.id.espace_client_container , fragment).commit();
                break;

            case R.id.valider_bien :
                fragment = new ValiderLocation();
                getSupportFragmentManager().beginTransaction().replace(R.id.espace_client_container , fragment).commit();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
