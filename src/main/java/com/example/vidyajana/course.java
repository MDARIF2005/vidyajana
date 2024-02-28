package com.example.vidyajana;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class course extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        ImageButton mainButton2=(ImageButton) findViewById(R.id.imageButton2);
        mainButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openhomepage();

            }
        });



    }
    public void openhomepage(){
        Intent intent =new Intent(this, homepage.class);
        startActivity(intent);
    }
}