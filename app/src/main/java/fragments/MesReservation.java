package fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.ekrili.R;

import Adapters.MesBiensFragmentAdapter;
import Adapters.MesReservationFragmentAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MesReservation extends Fragment {

    private View view ;
    private TabLayout tab;
    private ViewPager viewPager ;
    private MesReservationFragmentAdapter adapter ;


    public MesReservation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_mes_reservation, container, false);
        initViews();
        return view;
    }

    private void initViews(){

        tab= view.findViewById(R.id.tab_mes_reservation);
        viewPager = view.findViewById(R.id.view_pager_mes_reservation);
        adapter = new MesReservationFragmentAdapter(getChildFragmentManager());
        tab.setupWithViewPager(viewPager);
        viewPager.setAdapter(adapter);

    }

}
