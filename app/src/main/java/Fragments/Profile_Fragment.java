package Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.semester_project.smd_project.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import static android.app.Activity.RESULT_OK;

public class Profile_Fragment extends Fragment
{
    TextView editprofile_, cancelprofileeditbtn, mainid, mainname;
    Button savebtn;
    EditText user, email, phone, address, country, age_, experience_, budget_;
    CircleImageView coverphoto, profilepic, cicularprofilepicture;
    static final int PICK_IMG = 1, PICK_IMG1 = 2;
    ProgressBar progress;
    Uri photo;
    Bitmap xyz;
    String UID;
    LinearLayout coverpic;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    public Profile_Fragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.profile_fragment, container, false);

        mainid = root.findViewById(R.id.mainemailid);
        mainname = root.findViewById(R.id.mainusername);
        editprofile_ = root.findViewById(R.id.editprofile);
        cancelprofileeditbtn = root.findViewById(R.id.cancelprofileedit);
        savebtn = root.findViewById(R.id.saveprofilebtn);
        user = root.findViewById(R.id.user);
        email = root.findViewById(R.id.email);
        phone = root.findViewById(R.id.phonenum);
        address = root.findViewById(R.id.address);
        country = root.findViewById(R.id.country);
        coverphoto = root.findViewById(R.id.editcoverphoto);
        profilepic = root.findViewById(R.id.profilepictureicon);
        coverpic = root.findViewById(R.id.l1);
        cicularprofilepicture = root.findViewById(R.id.pic);
        age_ = root.findViewById(R.id.age1);
        budget_ = root.findViewById(R.id.budget1);
        experience_ = root.findViewById(R.id.experience1);
        progress = root.findViewById(R.id.progressbar3);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("GUIDER");

        FirebaseUser user_1  = FirebaseAuth.getInstance().getCurrentUser();
        UID = user_1.getEmail();
        progress.setVisibility(View.VISIBLE);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d : snapshot.getChildren())
                {
                  if(d.child("useremail").getValue().equals(UID))
                  {
                      user.setText(d.child("username").getValue(String.class));
                      email.setText(d.child("useremail").getValue(String.class));
                      phone.setText(d.child("phone").getValue(String.class));
                      address.setText(d.child("address").getValue(String.class));
                      country.setText(d.child("country").getValue(String.class));
                      mainid.setText(d.child("useremail").getValue(String.class));
                      mainname.setText(d.child("username").getValue(String.class));
                      experience_.setText(d.child("experience").getValue(String.class) + " Years");
                      budget_.setText(d.child("budget").getValue(String.class));
                      age_.setText(d.child("age").getValue(String.class) + " YO");
                      Picasso.get().load(d.child("profilepic").getValue(String.class)).into(cicularprofilepicture);
                  }
                }
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery, "Picture"), PICK_IMG1);
            }
        });

        coverphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery, "Picture"), PICK_IMG);
            }
        });

        editprofile_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editprofile_.getText().equals("Edit"))
                {
                    visibility_gone_save_cover_profile (savebtn, profilepic, coverphoto, View.VISIBLE);
                    set_edittext_disable(user, true);
                    set_edittext_disable(email, true);
                    set_edittext_disable(phone, true);
                    set_edittext_disable(address, true);
                    set_edittext_disable(country, true);
                    set_edittext_disable(age_, true);
                    set_edittext_disable(experience_, true);
                    set_edittext_disable(budget_,true );
                    editprofile_.setText("Cancel");
                    editprofile_.setTextSize(16);
                }
                else if(editprofile_.getText().equals("Cancel"))
                {
                    editprofile_.setText("Edit");
                    editprofile_.setTextSize(15);
                    visibility_gone_save_cover_profile (savebtn, profilepic, coverphoto, View.GONE);
                    set_edittext_disable(user, false);
                    set_edittext_disable(email, false);
                    set_edittext_disable(phone, false);
                    set_edittext_disable(address, false);
                    set_edittext_disable(country, false);
                    set_edittext_disable(age_, false);
                    set_edittext_disable(experience_, false);
                    set_edittext_disable(budget_,false);
                }
            }
        });

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editprofile_.setText("Edit");
                editprofile_.setTextSize(15);
                visibility_gone_save_cover_profile (savebtn, profilepic, coverphoto, View.GONE);
                set_edittext_disable(user, false);
                set_edittext_disable(email, false);
                set_edittext_disable(phone, false);
                set_edittext_disable(address, false);
                set_edittext_disable(country, false);
                set_edittext_disable(age_, false);
                set_edittext_disable(experience_, false);
                set_edittext_disable(budget_,false);
            }
        });

        cancelprofileeditbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editprofile_.setVisibility(View.VISIBLE);
                cancelprofileeditbtn.setVisibility(View.GONE);
                visibility_gone_save_cover_profile (savebtn, profilepic, coverphoto, View.GONE);
                set_edittext_disable(user, false);
                set_edittext_disable(email, false);
                set_edittext_disable(phone, false);
                set_edittext_disable(address, false);
                set_edittext_disable(country, false);
                set_edittext_disable(age_, false);
                set_edittext_disable(experience_, false);
                set_edittext_disable(budget_,false);
            }
        });
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMG1 && resultCode == RESULT_OK)
        {
            photo  = data.getData();
            try
            {
                ImageDecoder.Source source = ImageDecoder.createSource(getContext().getContentResolver(), photo);
                xyz = ImageDecoder.decodeBitmap(source);
                //BitmapDrawable background = new BitmapDrawable(xyz);
                cicularprofilepicture.setImageBitmap(xyz);
            }
            catch(Exception e)
            {
                e.getStackTrace();
            }
        }
        else if (requestCode == PICK_IMG && resultCode == RESULT_OK)
        {
            photo  = data.getData();
            try
            {
                ImageDecoder.Source source = ImageDecoder.createSource(getContext().getContentResolver(), photo);
                xyz = ImageDecoder.decodeBitmap(source);
                BitmapDrawable background = new BitmapDrawable(xyz);
                coverpic.setBackgroundDrawable(background);
            }
            catch(Exception e) {
                e.getStackTrace();
            }
        }
    }

    private void set_edittext_disable(EditText t, boolean bool)
    {
        t.setFocusable(bool);
        t.setEnabled(bool);
    }

    private  void visibility_gone_save_cover_profile (Button s, CircleImageView m1, CircleImageView m2, int v1)
    {
        s.setVisibility(v1);
        m1.setVisibility(v1);
        m2.setVisibility(v1);
    }
}
