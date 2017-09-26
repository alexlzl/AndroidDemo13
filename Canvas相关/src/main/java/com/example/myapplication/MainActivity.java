package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void drawArc(View view) {
        Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, DrawArcActivity.class);
        startActivity(intent);
    }

    public void drawBitmap(View view) {
        Intent intent = new Intent(this, DrawBitmapActivity.class);
        startActivity(intent);
    }

    public void save(View view){
        Intent intent = new Intent(this, SaveActivity.class);
        startActivity(intent);
    }
    public void Region(View view){
        Intent intent = new Intent(this, RegionActivity.class);
        startActivity(intent);
    }

    public void Clip(View view){
        Intent intent = new Intent(this, ClipActivity.class);
        startActivity(intent);
    }
}
