package Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import fragments.AvisBien;
import fragments.BienAccepter;
import fragments.BienEnAttente;
import fragments.BienEnLocation;
import fragments.BienRefuser;
import fragments.DetailleBien;

/**
 * Created by LENOVO on 27/05/2018.
 */

public class MesBiensFragmentAdapter extends FragmentPagerAdapter {


    public MesBiensFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        if(position==0){
            return new BienEnAttente();
        }
        else
            if(position==3){
            return new BienRefuser();
        }

        else if(position==1){

                return  new BienAccepter();
            }

            else

                return new BienEnLocation();


    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        if(position==0){
         return "Bien En attente";
        }

        else if(position==1){

            return "Bien Accepté";
        }

        else if(position==2){

            return "En Location";
        }
        else{

            return " Bien réfusé";
        }
    }
}
