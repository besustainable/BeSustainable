package com.example.pc_gaming.besustainable.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.pc_gaming.besustainable.R;


public class ScreenSlidePageFragment1 extends Fragment {

    private ImageView iv_Principle1;
    static ImageView iv_Un_environment;
    static ImageView iv_BeSustainable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page_1, container, false);

        iv_Principle1 = rootView.findViewById(R.id.ivPrinciple_1);
        iv_Un_environment = rootView.findViewById(R.id.iv_Un_environment);
        iv_BeSustainable = rootView.findViewById(R.id.iv_BeSustainable);


        iv_Un_environment.setImageResource(R.drawable.un_environment_icon);
        iv_BeSustainable.setImageResource(R.drawable.header);


        iv_Principle1.setImageResource(R.drawable.principle1_icon);

        return rootView;
    }
}