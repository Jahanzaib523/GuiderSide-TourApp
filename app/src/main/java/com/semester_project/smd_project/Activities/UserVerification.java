package com.semester_project.smd_project.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.semester_project.smd_project.R;

public class UserVerification extends AppCompatActivity {
    private Button gobacktosignup, verifyuserclick;
    private TextView resendcodetoverify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_verification);

        verifyuserclick = findViewById(R.id.verifyme);
        gobacktosignup = findViewById(R.id.backtosignup);
        resendcodetoverify = findViewById(R.id.resendcode);

        verifyuserclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent verifyuserintent = new Intent(UserVerification.this, SignIn.class);
                startActivity(verifyuserintent);
            }
        });

        gobacktosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gobacktosignupintent = new Intent(UserVerification.this, SignUp.class);
                startActivity(gobacktosignupintent);
            }
        });

        resendcodetoverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
            }
        });
    }
}