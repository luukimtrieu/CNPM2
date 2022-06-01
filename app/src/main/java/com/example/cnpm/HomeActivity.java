package com.example.cnpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.cnpm.Database.AppDataBase;
import com.example.cnpm.Database.Employee;
import com.example.cnpm.Database.HR;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerLayout);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigaton_drawer_open, R.string.navigaton_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigationview);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home:
                    {
                        Navigation.findNavController(HomeActivity.this, R.id.fragmentContainerView).navigate(R.id.action_global_homeFragment);
                        break;
                    }
                    case R.id.profile:
                    {
                        Navigation.findNavController(HomeActivity.this, R.id.fragmentContainerView).navigate(R.id.action_global_myProfileFragment);
                        break;
                    }
                    case R.id.logout:
                    {
                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                return true;
            }
        });

        AppDataBase dataBase = new AppDataBase(this, "database5.db", null, 1);

    }



}