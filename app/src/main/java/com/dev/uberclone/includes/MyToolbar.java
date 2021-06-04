package com.dev.uberclone.includes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.ToolbarWidgetWrapper;

import com.dev.uberclone.R;

import java.util.Objects;

public class MyToolbar {

    public static void show(AppCompatActivity activity, int title, boolean upButton) {

        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        Objects.requireNonNull(activity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeButtonEnabled(upButton);
        activity.getSupportActionBar().setTitle(title);

    }
}
