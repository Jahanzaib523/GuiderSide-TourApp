package com.semester_project.smd_project.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.semester_project.smd_project.R;

import Models.User;
import de.hdodenhof.circleimageview.CircleImageView;

public class Create_UserProfile extends AppCompatActivity
{
    private FirebaseAuth signupMe;
    private ProgressBar progress_bar;
    private static final int PICK_IMG = 1;
    private Uri photo = null;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private static final String USER = "GUIDER";
    Button bck, submitbtn;
    String coverimage_;
    CircleImageView profileimage;
    LinearLayout coverImage;
    Bitmap xyz;
    EditText name, email, phone, address, country, age_, experience_years, budget_;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__user_profile);

        profileimage = (CircleImageView) findViewById(R.id.profilepic);
        coverImage = findViewById(R.id.l1);
        submitbtn = findViewById(R.id.saveprofilebtn);
        name = findViewById(R.id.username);
        age_ = findViewById(R.id.age);
        experience_years = findViewById(R.id.experienceyears);
        budget_ = findViewById(R.id.budget);
        email = findViewById(R.id.useremail);
        phone = findViewById(R.id.userphone);
        address = findViewById(R.id.useraddress);
        country = findViewById(R.id.usercountry);
        progress_bar = findViewById(R.id.progressbar2);
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference(USER);
        signupMe = FirebaseAuth.getInstance();
        bck = findViewById(R.id.back);

        final String user_email = getIntent().getStringExtra("email");
        final String password1 = getIntent().getStringExtra("password1");
        final String username_ =getIntent().getStringExtra("username");

        name.setText(username_);
        email.setText(user_email);

        Upload_profilePicture();
        Upload_CoverPicture();

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitbtn.setVisibility(View.GONE);
                bck.setVisibility(View.GONE);
                progress_bar.setVisibility(View.VISIBLE);
                String number = phone.getText().toString();
                String address_ = address.getText().toString();
                String country_ = country.getText().toString();
                String guider_budget = budget_.getText().toString();
                String experience = experience_years.getText().toString();
                String guider_age = age_.getText().toString();

                Upload(username_, user_email, password1, number, address_, country_, guider_budget, guider_age, experience);
            }
        });

        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (getApplicationContext(), SignUp.class);
                startActivity(i);
                finish();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMG && resultCode == RESULT_OK)
        {
            photo  = data.getData();
            try
            {
                ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), photo);
                xyz = ImageDecoder.decodeBitmap(source);
                profileimage.setImageBitmap(xyz);
            }
            catch(Exception e)
            {
                e.getStackTrace();
            }
        }
    }

    protected void Upload_profilePicture()
    {
        profileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery, "Picture"), PICK_IMG);
            }
        });
    }

    protected void Upload_CoverPicture()
    {
        coverImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery, "Picture"), PICK_IMG);
            }
        });
    }

    public void RegisterUser(String user_email, String password1)
    {
        signupMe.createUserWithEmailAndPassword(user_email, password1).addOnCompleteListener(Create_UserProfile.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(!task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(), "User already exists with this email", Toast.LENGTH_SHORT).show();
                    progress_bar.setVisibility(View.GONE);
                }
                else
                {
                    FirebaseUser user = signupMe.getCurrentUser();
                    UpdateUI(user);
                    progress_bar.setVisibility(View.GONE);
                }
            }
        });
    }

    public void UpdateUI(FirebaseUser User)
    {
        String key = mDatabase.push().getKey();
        mDatabase.child(key).setValue(user);
        Intent signupbtnclicked = new Intent(Create_UserProfile.this, SignIn.class);
        startActivity(signupbtnclicked);
    }

    public void Upload(final String username_, final String user_email, final String password1, final String number, final String address_, final String country_, final String budget, final String guider_age, final String guider_experience)
    {
        if(photo != null)
        {
            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
            StorageReference storageReference = firebaseStorage.getReference();
            storageReference = storageReference.child("Images/" + phone);
            storageReference.putFile(photo)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();
                            task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri)
                                {
                                    String img = uri.toString();
                                    user = new User(username_, user_email, password1, img, coverimage_, number, address_, country_, budget, guider_age, guider_experience);
                                    RegisterUser(user_email, password1);
                                }
                            })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                             //submitbtn.setEnabled(true);
                                             //bck.setEnabled(true);
                                            submitbtn.setVisibility(View.VISIBLE);
                                            bck.setVisibility(View.VISIBLE);
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            submitbtn.setVisibility(View.VISIBLE);
                            bck.setVisibility(View.VISIBLE);
                        }
                    });
        }
        else {
            Toast.makeText(getApplicationContext(), "Upload All Images", Toast.LENGTH_SHORT).show();
        }
    }
}