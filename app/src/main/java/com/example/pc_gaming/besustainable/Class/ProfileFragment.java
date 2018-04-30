package com.example.pc_gaming.besustainable.Class;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pc_gaming.besustainable.R;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_profile, container, false);

        // Inflate the layout for this fragment
        return rootView;
    }
}
