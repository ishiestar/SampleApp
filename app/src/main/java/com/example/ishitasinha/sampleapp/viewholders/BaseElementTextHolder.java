package com.example.ishitasinha.sampleapp.viewholders;

import android.graphics.Color;
import android.view.View;

import com.quintype.coreui.TextLoader;

public abstract class BaseElementTextHolder extends BaseElementHolder {

    //TextLoader to process and load html text.
    static TextLoader textLoader = TextLoader.create()
            .bulletColor(Color.BLACK)
            .bulletRadius(6)
            .linkColor(Color.BLUE)
            .linkUnderline(true);

    public BaseElementTextHolder(View itemView) {
        super(itemView);
    }
}
