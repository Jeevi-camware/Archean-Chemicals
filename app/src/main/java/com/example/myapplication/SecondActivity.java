package com.example.myapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);


        // Set click listeners for PS1, PS1A, and PS5 buttons
        Button ps1Button = findViewById(R.id.PS1);
        ps1Button.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {
                navigateToThirdActivity("PS1");
            }
        });

        Button ps1aButton = findViewById(R.id.PS1A);
        ps1aButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToFourthActivity("PS1A");
            }
        });

        Button ps5Button = findViewById(R.id.PS5);
        ps5Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToFifthActivity("PS5");
            }
        });
    }

    private void navigateToThirdActivity(String psLabel) {
        Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
        intent.putExtra("ps_label", psLabel);
        startActivity(intent);
    }


    private void navigateToFourthActivity(String psLabel) {
        Intent intent = new Intent(SecondActivity.this, fourthActivity.class);
        intent.putExtra("ps_label", psLabel);
        startActivity(intent);
    }

    private void navigateToFifthActivity(String psLabel) {
        Intent intent = new Intent(SecondActivity.this, fifthActivity.class);
        intent.putExtra("ps_label", psLabel);
        startActivity(intent);
    }

}