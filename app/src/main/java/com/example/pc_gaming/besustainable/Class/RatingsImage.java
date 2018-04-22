package com.example.pc_gaming.besustainable.Class;

import com.example.pc_gaming.besustainable.R;

/**
 * Created by PC_Gaming on 22/04/2018.
 */

public class RatingsImage {

    public RatingsImage(){}

    public int economyRate(int economyAVG)
    {
        switch (economyAVG){
            case 0:
                return R.drawable.economy;
            case 1:
                return R.drawable.economy_1;
            case 2:
                return R.drawable.economy_2;
            case 3:
                return R.drawable.economy_3;
            case 4:
                return R.drawable.economy_4;
            case 5:
                return R.drawable.economy_5;
        }

        // If distinct of 1...5 return empty economy
        return R.drawable.economy;
    }

    public int satisfactionRate(int satisfactionAVG){

        switch (satisfactionAVG){
            case 0:
                return R.drawable.satisfaction;
            case 1:
                return R.drawable.satisfaction_1;
            case 2:
                return R.drawable.satisfaction_2;
            case 3:
                return R.drawable.satisfaction_3;
            case 4:
                return R.drawable.satisfaction_4;
            case 5:
                return R.drawable.satisfaction_5;
        }

        // If distinct of 1...5 return empty satisfaction
        return R.drawable.satisfaction;
    }


}
