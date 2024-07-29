package com.example.myapplication;



import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        TextView Login = findViewById(R.id.Login);
        TextView EmployeeCode = findViewById(R.id.EmployeeCode);
      Button  okButton = findViewById(R.id.okButton);

    //

        // Set OK button click listener
        okButton.setOnClickListener(view -> {
            String employeeCode = EmployeeCode.getText().toString();

            // Handle the OK button click
            // Login successful
            if (employeeCode.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter employee code", Toast.LENGTH_SHORT).show();

            } else {
                if (employeeCode.equals("12345")) {
                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    //Navigate to second page
                    Intent intent= new Intent(MainActivity.this,SecondActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }
    });
}
}
