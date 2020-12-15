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

public class SignUp extends AppCompatActivity {
    private Button signupclick;
    private TextView gotosigninpage;
    private EditText name, useremail, pass1, pass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signupclick = findViewById(R.id.singnupbtn);
        gotosigninpage = findViewById(R.id.gotosign);
        name = findViewById(R.id.username);
        useremail = findViewById(R.id.emailid);
        pass1 = findViewById(R.id.password1);
        pass2 = findViewById(R.id.password2);

        ShowPassowrd(pass1);
        ShowPassowrd(pass2);

        signupclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user_email = useremail.getText().toString();
                String password1 = pass1.getText().toString();
                String password2 = pass2.getText().toString();
                String username = name.getText().toString();
                HideKeyboard(getApplicationContext());
                if(user_email.isEmpty()) { useremail.setError("Please, Enter Email ID"); useremail.requestFocus(); }
                else if(username.isEmpty()) { name.setError("Please, Enter User Name"); name.requestFocus(); }
                else if (password1.isEmpty()) {pass1.setError("Please, Enter Password"); pass1.requestFocus(); }
                else if (password2.isEmpty()) {pass2.setError("Please, Enter Password"); pass2.requestFocus(); }
                else if (!password1.equals(password2)) { pass2.setError("Password Does not match"); pass2.requestFocus(); }
                else if(!user_email.isEmpty() && !username.isEmpty() && !password1.isEmpty() && !password2.isEmpty())
                {

                    Intent signupbtnclicked = new Intent(SignUp.this, Create_UserProfile.class);

                    signupbtnclicked.putExtra("email", user_email);
                    signupbtnclicked.putExtra("password1", password1);
                    signupbtnclicked.putExtra("username", username);

                    startActivity(signupbtnclicked);
                    finish();
                }
            }
        });

        gotosigninpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotosigninclickedintent = new Intent(SignUp.this, SignIn.class);
                startActivity(gotosigninclickedintent);
                finish();
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
                final int DRAWABLE_TOP = 1;
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

    @SuppressLint("ClickableViewAccessibility")
    public void HidePassowrd(@NotNull final EditText pass)
    {
        pass.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_RIGHT = 2;

                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    if(event.getX() >= (pass.getRight() - pass.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width()))
                    {
                        pass.setCompoundDrawables(ContextCompat.getDrawable(getApplicationContext(), R.drawable.password), null, ContextCompat.getDrawable(getApplicationContext(), R.drawable.eye), null);
                        pass.setInputType(0);
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