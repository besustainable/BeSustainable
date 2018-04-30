package com.example.pc_gaming.besustainable.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc_gaming.besustainable.R;
import com.warkiz.widget.IndicatorSeekBar;


public class ScreenSlidePageFragment9 extends Fragment {

    private ImageView iv_Principle;
    private ImageView iv_Un_environment;
    private ImageView iv_BeSustainable;
    private IndicatorSeekBar indicatorSeekBar;
    public static int resultAnswer9 = 50;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page_9, container, false);

        // Assignation's
        iv_Principle = rootView.findViewById(R.id.ivPrinciple);
        iv_Un_environment = rootView.findViewById(R.id.iv_Un_environment);
        iv_BeSustainable = rootView.findViewById(R.id.iv_BeSustainable);
        indicatorSeekBar = rootView.findViewById(R.id.indicatorseekBar);



        // Sets

        iv_Un_environment.setImageResource(R.drawable.un_environment_icon);
        iv_BeSustainable.setImageResource(R.drawable.header);
        iv_Principle.setImageResource(R.drawable.principle9_icon);



        indicatorSeekBar.setOnSeekChangeListener(new IndicatorSeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(IndicatorSeekBar seekBar, int progress, float progressFloat, boolean fromUserTouch) {

            }

            @Override
            public void onSectionChanged(IndicatorSeekBar seekBar, int thumbPosOnTick, String textBelowTick, boolean fromUserTouch) {

            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar, int thumbPosOnTick) {

                if(seekBar.getProgress() < 50)
                    seekBar.getBuilder().setIndicatorCustomTopContentLayout(R.layout.custom_seekbar_text_9_1).apply();
                else
                    seekBar.getBuilder().setIndicatorCustomTopContentLayout(R.layout.custom_seekbar_text_9_2).apply();

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

                if(seekBar.getProgress() < 50)
                    seekBar.getBuilder().setIndicatorCustomTopContentLayout(R.layout.custom_seekbar_text_9_1).apply();
                else
                    seekBar.getBuilder().setIndicatorCustomTopContentLayout(R.layout.custom_seekbar_text_9_2).apply();

                resultAnswer9 = seekBar.getProgress();

            }
        });

        return rootView;
    }


}