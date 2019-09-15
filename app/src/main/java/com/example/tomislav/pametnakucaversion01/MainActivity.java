package com.example.tomislav.pametnakucaversion01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void dnevni(View view) {
    }

    public void spavaca(View view) {
    }

    public void kupaona(View view) {
    }

    public void sva_svjetla(View view) {
    }

    public void kitchen(View view) {
        Intent kuh = new Intent(MainActivity.this, KitchenLightActivity.class);
        startActivity(kuh);
    }
}
