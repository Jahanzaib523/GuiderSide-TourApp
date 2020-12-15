package com.semester_project.smd_project;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import android.os.Bundle;

public class Navigation extends AppCompatActivity
{
    private DrawerLayout draw;
    private ActionBarDrawerToggle actionbartoggle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        draw =  (DrawerLayout) findViewById(R.id.drawr);
        actionbartoggle = new ActionBarDrawerToggle(this, draw, R.string.open, R.string.close);
        draw.addDrawerListener(actionbartoggle);
        actionbartoggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}