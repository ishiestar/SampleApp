package com.example.ishitasinha.sampleapp;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.TypedValue;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    TextView sampleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RadioGroup nightModeRadioGroup = (RadioGroup) findViewById(R.id.rg_night_mode);
        if (nightModeRadioGroup != null) {
            nightModeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                    switch (checkedId) {
                        case R.id.rb_always_day:
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                            break;
                        case R.id.rb_always_night:
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                            break;
                        case R.id.rb_auto_switch:
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
                            break;
                    }
                    onNavigateUp();
                }
            });
        }

        setUpTextResize();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences
                (context.getString(R.string.app_name), Context.MODE_PRIVATE);
    }

    /**
     * For setting font size
     *
     * @param context
     * @param fontSize
     */
    public void setFontSize(Context context, String fontSize) {
        getSharedPreferences(context).edit().putString("FONT_SIZE", fontSize)
                .apply();
    }

    /**
     * For getting font size
     *
     * @param context
     * @return
     */
    public String getFontSize(Context context) {
        return getSharedPreferences(context).getString("FONT_SIZE", "50");
    }

    private void setUpTextResize() {
        String fontSize = getFontSize(getApplicationContext());
        AppCompatSeekBar textResizeSeekbar = ((AppCompatSeekBar) findViewById(R.id.text_resize_seekbar));
        sampleText = ((TextView) findViewById(R.id.sample_text));
        textResizeSeekbar.setProgress((int) Float.parseFloat(fontSize));
        sampleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textResizeSeekbar.getProgress());
        textResizeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                sampleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                setFontSize(getApplicationContext(), String.valueOf(sampleText.getTextSize()));
            }
        });
    }

}
