package com.example.pc_gaming.besustainable.Class;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc_gaming.besustainable.R;

public class ProfileFragment extends Fragment {

    TextView tvDescription, tvEmail, tvNick;
    EditText etBirthday;
    ImageView ivProfile, ivGender;
    Switch switchNewsletter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_profile, container, false);

        tvDescription = rootView.findViewById(R.id.tvDescription);
        tvEmail = rootView.findViewById(R.id.tvEmail);
        tvNick = rootView.findViewById(R.id.tvNick);
        etBirthday = rootView.findViewById(R.id.etBirthday);
        ivProfile = rootView.findViewById(R.id.ivProfile);
        ivGender = rootView.findViewById(R.id.ivGender);
        switchNewsletter = rootView.findViewById(R.id.switchNewsletter);

        SharedPreferences mPrefs = getActivity().getPreferences(Context.MODE_PRIVATE);

        if(mPrefs.getString("User", "").equals("")){
            Intent i = new Intent(rootView.getContext(), LoginActivity.class);
            startActivity(i);
            return null;
            // Avoid the anterior Activity & finalize the App.
            //getActivity().finish();
        }




        // Inflate the layout for this fragment
        return rootView;
    }
}
