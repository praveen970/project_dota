package com.pkapps.dotainfo;

/**
 * Created by PraveenKumar on 3/25/2018.
 */

import android.graphics.drawable.Drawable;

public class People {

    public int image;
    public Drawable imageDrw;
    public String name;
    public String email;
    public boolean section = false;

    public People() {
    }

    public People(String name, boolean section) {
        this.name = name;
        this.section = section;
    }

}