package Adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import fragments.ReservationAccepter;
import fragments.ReservationEncours;
import fragments.ReservationFini;
import fragments.ReservationEnAttent;
import fragments.ReservationRefuser;

/**
 * Created by LENOVO on 07/06/2018.
 */

public class MesReservationFragmentAdapter extends FragmentPagerAdapter {


    public MesReservationFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        if(position==3){

            return new ReservationFini();
        }

        else if(position==0){
            return new ReservationEnAttent();
        }

        else if(position==1){
            return new ReservationAccepter();
        }

        else if(position == 2){

            return new ReservationEncours();
        }

        else{

            return new ReservationRefuser();
        }

    }

    @Override
    public int getCount() {
        return 5;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){

            case 0 : return "En attente" ;

            case 1 : return "Accepter";

            case 2 : return "En cours";

            case 3 : return "FINI";

            case 4 : return "Réfuser";

            default:return "";
        }
    }
}
