package com.semester_project.smd_project.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.onesignal.OneSignal;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.semester_project.smd_project.R;
import com.squareup.picasso.Picasso;

import Fragments.Fragment_CompletedOrders;
import Fragments.Fragment_Help;
import Fragments.Fragment_MyOrders;
import Fragments.Fragment_Setting;
import Fragments.Profile_Fragment;
import Fragments.Wallet_Fragment;
import Models.Orders;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
{
    private ImageView bars;
    public DrawerLayout drawyer;
    FirebaseAuth mlogout;
    String Email_ID;
    private FirebaseDatabase database;
    private DatabaseReference dbreference;
    private CircleImageView headerimage;
    private TextView headerusername;
    private TextView headeremail;
    private ProgressBar progress;
    private  FirebaseAuth.AuthStateListener mauthlistener;
    private static final String ONESIGNAL_APP_ID = "a7a343a0-776f-4206-b1e2-da8c3031aa17";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Email_ID = getIntent().getStringExtra("UID");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Email_ID = user.getEmail();

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);

        OneSignal.sendTag("USER_ID", Email_ID);
        if (savedInstanceState == null)
        {
            Bundle bundle = new Bundle();
            //bundle.putString("emailId", Email_ID);
            Fragment selectedfrag = new Fragment_MyOrders();
            selectedfrag.setArguments(bundle);

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    selectedfrag).commit();
        }

        bars = (ImageView) findViewById(R.id.barsid);
        drawyer = (DrawerLayout) findViewById(R.id.drwr);
        mlogout = FirebaseAuth.getInstance();

        NavigationView navi = findViewById(R.id.sidenavigation);
        View headerView = navi.getHeaderView(0);
        headerimage = headerView.findViewById(R.id.headerpciture);
        headeremail = headerView.findViewById(R.id.headeruseremail);
        headerusername = headerView.findViewById(R.id.headerusername);
        progress = headerView.findViewById(R.id.progressbar4);
        database = FirebaseDatabase.getInstance();
        dbreference = database.getReference("GUIDER");

        HideKeyboard(getApplicationContext());
        progress.setVisibility(View.VISIBLE);
        dbreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                  for(DataSnapshot d :snapshot.getChildren())
                  {
                      if(d.child("useremail").getValue().equals(Email_ID))
                      {
                          headerusername.setText(d.child("username").getValue(String.class));
                          headeremail.setText(d.child("useremail").getValue(String.class));
                          Picasso.get().load(d.child("profilepic").getValue(String.class)).into(headerimage);
                      }
                  }
                  progress.setVisibility(View.GONE);
            };

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progress.setVisibility(View.GONE);
            }
        });

        bars.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (drawyer.isDrawerOpen(Gravity.LEFT))
                {
                    bars.setImageDrawable(getResources().getDrawable(R.drawable.drawer_bars));
                    drawyer.closeDrawer(Gravity.LEFT);
                }
                else
                {
                    bars.setImageDrawable(getResources().getDrawable(R.drawable.back));
                    drawyer.openDrawer(Gravity.LEFT);
                }
            }
        });


        navi.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                Fragment selectedFragment = null;
                Bundle bundle__ = new Bundle();
                switch (item.getItemId())
                {
                    case R.id.dashboard:
                        //bundle__.putString("emailId", Email_ID);
                        selectedFragment = new Fragment_MyOrders();
                        selectedFragment.setArguments(bundle__);
                        break;

                    case R.id.profile:
                        //bundle__.putString("emailId", Email_ID);
                        selectedFragment = new Profile_Fragment();
                        selectedFragment.setArguments(bundle__);
                        break;

                    case R.id.completedorders:
                        //bundle__.putString("emailId", Email_ID);
                        selectedFragment = new Fragment_CompletedOrders();
                        selectedFragment.setArguments(bundle__);
                        break;

                    case R.id.wallet:
                        //bundle__.putString("emailId", Email_ID);
                        selectedFragment = new Wallet_Fragment();
                        selectedFragment.setArguments(bundle__);
                        break;

                    case R.id.setting:
                        //bundle__.putString("emailId", Email_ID);
                        selectedFragment = new Fragment_Setting();
                        selectedFragment.setArguments(bundle__);
                        break;

                    case R.id.help:
                        //bundle__.putString("emailId", Email_ID);
                        selectedFragment = new Fragment_Help();
                        selectedFragment.setArguments(bundle__);
                        break;

                    case R.id.logout:
                        mlogout.signOut();
                        Intent intent = new Intent(getApplicationContext(), SignIn.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
                        finish();
                        startActivity(intent);
                        break;
                }

                ReceiveOrders();
                HideKeyboard(getApplicationContext());
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                bars.setImageDrawable(getResources().getDrawable(R.drawable.drawer_bars));
                drawyer.closeDrawer(Gravity.LEFT);

                return true;
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drwr);
        if (drawer.isDrawerOpen(Gravity.LEFT))
        {
            drawer.closeDrawer(Gravity.LEFT);
        }
        else {
            super.onBackPressed();
        }
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

    private void ReceiveOrdersNotification(String guiderID)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel("n", "n", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getApplicationContext().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "n")
                .setContentText("Order Received")
                .setSmallIcon(R.drawable.orders)
                .setAutoCancel(true)
                .setContentText(guiderID + " just placed an order.");
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getApplicationContext());
        builder.setPriority(managerCompat.IMPORTANCE_MAX);
        managerCompat.notify((int) System. currentTimeMillis (), builder.build());
    }

    public void ReceiveOrders()
    {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference drefer = db.getReference("ORDERS");
        progress.setVisibility(View.VISIBLE);
        drefer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d:snapshot.getChildren())
                {
                    String key = d.getKey();
                    if(d.child("guiderID").getValue().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail()))
                    {
                        if(d.child("tripstatus").getValue().equals("In Progress"))
                        {
                            if(d.child("notification_status").getValue().equals("Sent"))
                            {
                                ReceiveOrdersNotification(d.child("uid").getValue(String.class));
                                drefer.getRef().child(key).child("notification_status").setValue("Received");
                            }
                        }
                    }
                    progress.setVisibility(View.GONE);
                }
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progress.setVisibility(View.GONE);
            }
        });
    }
}