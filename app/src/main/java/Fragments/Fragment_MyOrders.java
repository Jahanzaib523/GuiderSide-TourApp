package Fragments;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.semester_project.smd_project.Guider_info;
import com.semester_project.smd_project.R;

import java.util.ArrayList;
import java.util.List;

import Models.Orders;
import MyRecycleView.EmptyAdapter;
import MyRecycleView.MyOrdersRecycleView;

public class Fragment_MyOrders extends Fragment
{
    RecyclerView rv;
    List<Orders> orders;
    String GuidID;
    private ProgressBar progress;
    private MyOrdersRecycleView adapter;
    private FirebaseDatabase mDatabase;
    private EditText searchOrder;
    private DatabaseReference dbreference;
    private Button btnComplete;
    private EmptyAdapter emptyAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_trips_in_progress, container, false);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        GuidID = user.getEmail();
        orders = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance();
        searchOrder = root.findViewById(R.id.searchorder);
        progress = root.findViewById(R.id.progress11);
        btnComplete = root.findViewById(R.id.completeorderbtn);
        dbreference = mDatabase.getReference("ORDERS");

        ReceiveOrders();

        rv = root.findViewById(R.id.rvorders);
        LinearLayoutManager lm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true);
        lm.setStackFromEnd(true);
        rv.setLayoutManager(lm);
        if(!orders.isEmpty())
        {
            emptyAdapter = new EmptyAdapter(getContext());
            rv.setAdapter(emptyAdapter);
        }
        else
        {
            adapter = new MyOrdersRecycleView(orders, getContext());
            rv.setAdapter(adapter);
        }
        rv.smoothScrollToPosition(rv.getAdapter().getItemCount());
        return  root;
    }

    public void ReceiveOrders()
    {
        progress.setVisibility(View.VISIBLE);
       dbreference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               for(DataSnapshot d:snapshot.getChildren())
               {
                   if(snapshot!=null)
                   {
                       if(d.child("guiderID").getValue().equals(GuidID))
                       {
                           if(d.child("tripstatus").getValue().equals("In Progress"))
                           {
                               orders.add(new Orders("xyz",
                                       d.child("uid").getValue(String.class),
                                       d.child("guiderID").getValue(String.class),
                                       d.child("fromlocation").getValue(String.class),
                                       d.child("tolocation").getValue(String.class),
                                       d.child("startdate").getValue(String.class),
                                       d.child("enddate").getValue(String.class),
                                       d.child("tripstatus").getValue(String.class),
                                       d.child("payment_details").getValue(String.class)));
                               //ReceiveOrdersNotification(d.child("uid").getValue(String.class));
                               adapter.notifyDataSetChanged();
                           }
                       }
                   }
                   progress.setVisibility(View.GONE);
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
               progress.setVisibility(View.GONE);
           }
       });

        searchOrder.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    public  void filter(String txt)
    {
        ArrayList<Orders> lst = new ArrayList<>();
        for(Orders item : orders)
        {
            if(item.getFrom_location().toLowerCase().contains(txt.toLowerCase()))
            {
                lst.add(item);
            }
            else if (item.getGuider_email().toLowerCase().contains(txt.toLowerCase()))
            {
                lst.add(item);
            }
            else if (item.getTo_location().toLowerCase().contains(txt.toLowerCase()))
            {
                lst.add(item);
            }
            else if (item.getEnd_date().toLowerCase().contains(txt.toLowerCase()))
            {
                lst.add(item);
            }
            else if (item.getStar_tdate().toLowerCase().contains(txt.toLowerCase()))
            {
                lst.add(item);
            }
            else if (item.getGuider_email().toLowerCase().contains(txt.toLowerCase()))
            {
                lst.add(item);
            }
        }

        adapter.filteredlst(lst);
    }

    private void ReceiveOrdersNotification(String guiderID)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel("n", "n", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getContext().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "n")
                .setContentText("Order Received")
                .setSmallIcon(R.drawable.orders)
                .setAutoCancel(true)
                .setContentText(guiderID + " just placed an order.");
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getContext());
        managerCompat.notify(999, builder.build());
    }
}
