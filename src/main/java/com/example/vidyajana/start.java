package com.example.vidyajana;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class start extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ImageButton mainButton=(ImageButton) findViewById(R.id.imageButton);
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openTermsAndconditions();}
        });

    }
    public void openTermsAndconditions(){
        Intent intent =new Intent(this, TermsAndConditions.class);
        startActivity(intent);
    }

}