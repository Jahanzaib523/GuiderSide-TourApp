package com.semester_project.smd_project.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.semester_project.smd_project.R;

import org.jetbrains.annotations.NotNull;

public class SignIn extends AppCompatActivity
{
    private Button signinbtnclicked;
    private TextView forgotpassclick, createnewaccount;
    private EditText user_email, user_password;
    private FirebaseAuth SigninMe;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        signinbtnclicked = findViewById(R.id.signbtn);
        forgotpassclick = findViewById(R.id.forgotpassword);
        createnewaccount = findViewById(R.id.createaccount);
        user_email = findViewById(R.id.useremail);
        user_password = findViewById(R.id.userpassword);
        progressBar = findViewById(R.id.progressbar1);
        SigninMe = FirebaseAuth.getInstance();
        ShowPassowrd(user_password);

        signinbtnclicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailId = user_email.getText().toString();
                String password = user_password.getText().toString();
                HideKeyboard(getApplicationContext());
                if(emailId.isEmpty()) { user_email.setError("Please, Enter Email ID"); user_email.requestFocus(); }
                else if (password.isEmpty()) {user_password.setError("Please, Enter Password"); user_password.requestFocus(); }
                else if (password.isEmpty() && emailId.isEmpty()) { Toast.makeText(getApplicationContext(), "Please, fill all the fields!", Toast.LENGTH_LONG).show(); }
                else if (!password.isEmpty() && !emailId.isEmpty())
                {
                    progressBar.setVisibility(View.VISIBLE);
                    SigninMe.signInWithEmailAndPassword(emailId, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Intent intohom = new Intent (SignIn.this, MainActivity.class);
                                intohom.putExtra("UID", emailId);
                                startActivity(intohom);
                                progressBar.setVisibility(View.GONE);

                                finish();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Not Signed In !", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            }
        });

        forgotpassclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgotpassclickedintent = new Intent (SignIn.this, PasswordRecovery.class);
                startActivity(forgotpassclickedintent);
                finish();
            }
        });

        createnewaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createaccountclickedintent = new Intent (SignIn.this, SignUp.class);
                startActivity(createaccountclickedintent);
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    public void ShowPassowrd(@NotNull final EditText pass)
    {
        pass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    if(event.getX() >= (pass.getRight() - pass.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width()))
                    {
                        pass.setCompoundDrawables(ContextCompat.getDrawable(getApplicationContext(), R.drawable.password), null, ContextCompat.getDrawable(getApplicationContext(), R.drawable.invisible), null);
                        pass.setInputType(1);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    public void HideKeyboard(Context c)
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view == null) {
            view = new View(c);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}