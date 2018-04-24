package com.example.pc_gaming.besustainable.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc_gaming.besustainable.R;


public class ScreenSlidePageFragment10 extends Fragment {

    private ImageView iv_Principle;
    private ImageView iv_Un_environment;
    private ImageView iv_BeSustainable;
    private SeekBar sbPrinciple;
    private TextView tvAnswer;
    private TextView tvPercentScroll;
    static int resultAnswer10;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page_10, container, false);

        // Assignation's
        iv_Principle = rootView.findViewById(R.id.ivPrinciple);
        iv_Un_environment = rootView.findViewById(R.id.iv_Un_environment);
        iv_BeSustainable = rootView.findViewById(R.id.iv_BeSustainable);
        sbPrinciple = rootView.findViewById(R.id.sbPinciple);
        tvAnswer = rootView.findViewById(R.id.tvAnswer);
        tvPercentScroll = rootView.findViewById(R.id.tvPercentScroll);


        // Sets

        iv_Un_environment.setImageResource(R.drawable.un_environment_icon);
        iv_BeSustainable.setImageResource(R.drawable.header);
        iv_Principle.setImageResource(R.drawable.principle10_icon);


        //SeekBar - Methods
        sbPrinciple.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                // Display the Answer
                String result = (seekBar.getProgress() > 50) ? getString(R.string.AnswerP10_2) : getString(R.string.AnswerP10_1);
                tvAnswer.setText(result);

                // Output the Percentage
                tvPercentScroll.setText(String.valueOf(seekBar.getProgress()) + "%");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {


            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                // Set static value
                resultAnswer10 = seekBar.getProgress();
            }
        });



        return rootView;
    }
}