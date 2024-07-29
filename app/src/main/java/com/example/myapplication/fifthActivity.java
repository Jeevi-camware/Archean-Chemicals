
package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;


public class fifthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth);

        setupMotorButton(R.id.motor1, FortysixActivity.class);
        setupMotorButton(R.id.motor2, FortysevenActivity.class);
        setupMotorButton(R.id.motor3, FortyeightActivity.class);
        setupMotorButton(R.id.motor4, FortynineActivity.class);
        setupMotorButton(R.id.motor5, FiftyActivity.class);
        setupMotorButton(R.id.motor6, FiftyoneActivity.class);
        setupMotorButton(R.id.motor7, FiftytwoActivity.class);
        setupMotorButton(R.id.motor8, FiftythreeActivity.class);
        setupMotorButton(R.id.motor9, FiftyfourActivity.class);
        setupMotorButton(R.id.motor10, FiftyfiveActivity.class);
        setupMotorButton(R.id.motor11, FiftysixActivity.class);
        setupMotorButton(R.id.motor12, FiftysevenActivity.class);
        setupMotorButton(R.id.motor13, FiftyeightActivity.class);
        setupMotorButton(R.id.motor14, FiftynineActivity.class);
        setupMotorButton(R.id.motor15, SixtyActivity.class);
        setupMotorButton(R.id.motor16, SixtyoneActivity.class);
        setupMotorButton(R.id.motor17, SixtytwoActivity.class);
        setupMotorButton(R.id.motor18, SixtythreeActivity.class);
        setupMotorButton(R.id.motor19, SixtyfourActivity.class);
        setupMotorButton(R.id.motor20, SixtyfiveActivity.class);

    }

    private void setupMotorButton(int buttonId, Class<?> activityClass) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(fifthActivity.this, activityClass);
                startActivity(intent);
            }
        });
    }
}
