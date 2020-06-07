package com.cannamaster.cannamastergrowassistant.ui.main;

import com.cannamaster.cannamastergrowassistant.R;

import java.util.Random;

/**********************************************************
 * Randomizing Code for Viewpager Header Image
 **********************************************************/
public class HeaderImage {

    private static final Random RANDOM = new Random();

    public static int getRandomHeaderImage() {
        switch (RANDOM.nextInt(5)) {
            default:
            case 0:
                return R.drawable.main_header_1_;
            case 1:
                return R.drawable.main_header_2;
            case 2:
                return R.drawable.main_header_3;
            case 3:
                return R.drawable.main_header_4;
            case 4:
                return R.drawable.main_header_5;
            case 5:
                return R.drawable.main_header_6;
        }
    }
}
