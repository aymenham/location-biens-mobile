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

/**
 * A simple {@link Fragment} subclass.
 */
public class MesBien extends Fragment {

    private View view ;
    private TabLayout tabLayout ;
    private ViewPager viewPager ;
    private MesBiensFragmentAdapter adapter ;

    public MesBien() {

        setHasOptionsMenu(false);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_mes_bien, container, false);
        initViews();
        return view;
    }

    private void initViews(){

        tabLayout = view.findViewById(R.id.tab_mes_bien);
        viewPager = view.findViewById(R.id.view_pager_bien);
        adapter = new MesBiensFragmentAdapter(getChildFragmentManager());
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(adapter);



    }

}
