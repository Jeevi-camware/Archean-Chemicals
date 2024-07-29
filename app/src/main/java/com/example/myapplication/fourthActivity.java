
package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;


public class fourthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        setupMotorButton(R.id.motor1, TwentysixActivity.class);
        setupMotorButton(R.id.motor2, TwentysevenActivity.class);
        setupMotorButton(R.id.motor3, TwentyeightActivity.class);
        setupMotorButton(R.id.motor4, TwentynineActivity.class);
        setupMotorButton(R.id.motor5, ThirtyActivity.class);
        setupMotorButton(R.id.motor6, ThirtyoneActivity.class);
        setupMotorButton(R.id.motor7, ThirtytwoActivity.class);
        setupMotorButton(R.id.motor8, ThirtythreeActivity.class);
        setupMotorButton(R.id.motor9, ThirtyfourActivity.class);
        setupMotorButton(R.id.motor10, ThirtyfiveActivity.class);
        setupMotorButton(R.id.motor11, ThirtysixActivity.class);
        setupMotorButton(R.id.motor12, ThirtysevenActivity.class);
        setupMotorButton(R.id.motor13, ThirtyeightActivity.class);
        setupMotorButton(R.id.motor14, ThirtynineActivity.class);
        setupMotorButton(R.id.motor15, FortyActivity.class);
        setupMotorButton(R.id.motor16, FortyoneActivity.class);
        setupMotorButton(R.id.motor17, FortytwoActivity.class);
        setupMotorButton(R.id.motor18, FortythreeActivity.class);
        setupMotorButton(R.id.motor19, FortyfourActivity.class);
        setupMotorButton(R.id.motor20, FortyfiveActivity.class);

    }

    private void setupMotorButton(int buttonId, Class<?> activityClass) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(fourthActivity.this, activityClass);
                startActivity(intent);
            }
        });
    }
}
