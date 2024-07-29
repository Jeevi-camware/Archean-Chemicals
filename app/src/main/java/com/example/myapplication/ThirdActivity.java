
package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;


public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        setupMotorButton(R.id.motor1, SixthActivity.class);
        setupMotorButton(R.id.motor2, SeventhActivity.class);
        setupMotorButton(R.id.motor3, EigthActivity.class);
        setupMotorButton(R.id.motor4, NinethActivity.class);
        setupMotorButton(R.id.motor5, TenthActivity.class);
        setupMotorButton(R.id.motor6, ElevenActivity.class);
        setupMotorButton(R.id.motor7, TwelveActivity.class);
        setupMotorButton(R.id.motor8, ThirteenActivity.class);
        setupMotorButton(R.id.motor9, FourteenActivity.class);
        setupMotorButton(R.id.motor10, FifteenActivity.class);
        setupMotorButton(R.id.motor11, SixteenActivity.class);
        setupMotorButton(R.id.motor12, SeventeenActivity.class);
        setupMotorButton(R.id.motor13, EighteenActivity.class);
        setupMotorButton(R.id.motor14, NineteenActivity.class);
        setupMotorButton(R.id.motor15, TwentyActivity.class);
        setupMotorButton(R.id.motor16, TwentyoneActivity.class);
        setupMotorButton(R.id.motor17, TwentytwoActivity.class);
        setupMotorButton(R.id.motor18, TwentythreeActivity.class);
        setupMotorButton(R.id.motor19, TwentyfourActivity.class);
        setupMotorButton(R.id.motor20, TwentyfiveActivity.class);

    }

    private void setupMotorButton(int buttonId, Class<?> activityClass) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThirdActivity.this, activityClass);
                startActivity(intent);
            }
        });
    }
}
