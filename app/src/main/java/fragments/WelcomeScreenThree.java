package fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.LoginActivity;
import com.example.lenovo.ekrili.R;
import com.RegisterActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeScreenThree extends Fragment {

    private Button login_Btn ;

    private Button  register_btn ;




    public WelcomeScreenThree() {


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_welcome_screen_three, container, false);

        login_Btn = view.findViewById(R.id.login);

        register_btn = view.findViewById(R.id.register);

        login_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext() , LoginActivity.class);
                intent.putExtra("FROM_WHERE" , "FROM_HOMME_SCREEN");
                startActivity(intent);
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext() , RegisterActivity.class);
                intent.putExtra("FROM_WHERE_Register" , "FROM_HOMME_SCREEN");
                startActivity(intent);
            }
        });

        return view ;
    }

}
