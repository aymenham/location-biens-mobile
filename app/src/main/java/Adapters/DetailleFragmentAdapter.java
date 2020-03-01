package Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import fragments.AvisBien;
import fragments.DetailleBien;

/**
 * Created by LENOVO on 10/05/2018.
 */

public class DetailleFragmentAdapter extends FragmentPagerAdapter {


    public DetailleFragmentAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {

            if(position==0){
                return  new DetailleBien();
            }
            else{
                return  new AvisBien();
            }


    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
       switch (position){
           case 0 : return "Detaille Bien";
           case 1 : return "Avis Bien";
           default: return null;
       }
    }
}
