package com.semester_project.smd_project.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.semester_project.smd_project.R;

public class HomePage extends AppCompatActivity {
    private Button signup, signin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        signup = findViewById(R.id.signuphomepage);
        signin = findViewById(R.id.signinhomepage);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupintent = new Intent(HomePage.this, SignUp.class);
                startActivity(signupintent);
                finish();
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signinintent = new Intent(HomePage.this, SignIn.class);
                startActivity(signinintent);
                finish();
            }
        });
    }
}