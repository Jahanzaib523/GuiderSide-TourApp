package Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.semester_project.smd_project.R;

import Models.Orders;
import MyRecycleView.CompletedOrdersRecycleView;

import java.util.ArrayList;
import java.util.List;

public class Fragment_CompletedOrders extends Fragment
{
    RecyclerView rv;
    List<Orders> completedorders;
    FirebaseDatabase mDatabase;
    DatabaseReference dbreference;
    CompletedOrdersRecycleView adapter;
    ProgressBar progress;
    EditText searchOrder;
    String GuiderID = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_completedorders, container, false);
        getActivity().setTitle("Profile");
        GuiderID = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        progress = root.findViewById(R.id.progressbar1);
        mDatabase = FirebaseDatabase.getInstance();
        searchOrder = root.findViewById(R.id.searchorder);
        dbreference = mDatabase.getReference().child("ORDERS");
        completedorders = new ArrayList<>();

        GetCompletedOrders();

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

        rv = root.findViewById(R.id.rvcompletedorders);
        LinearLayoutManager lm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        lm.setStackFromEnd(true);
        rv.setLayoutManager(lm);
        adapter = new CompletedOrdersRecycleView(completedorders, getContext());
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        rv.smoothScrollToPosition(rv.getAdapter().getItemCount());
        return root;
    }

    public void GetCompletedOrders()
    {
        progress.setVisibility(View.VISIBLE);
       dbreference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               for (DataSnapshot d:snapshot.getChildren())
               {
                   if(d.child("guiderID").getValue().equals(GuiderID))
                   {
                       if(d.child("tripstatus").getValue().equals("Completed"))
                       {
                           completedorders.add(new Orders("xyz",
                                   d.child("guiderID").getValue(String.class),
                                   d.child("uid").getValue(String.class),
                                   d.child("fromlocation").getValue(String.class),
                                   d.child("tolocation").getValue(String.class),
                                   d.child("startdate").getValue(String.class),
                                   d.child("enddate").getValue(String.class),
                                   d.child("tripstatus").getValue(String.class),
                                   d.child("payment_details").getValue(String.class)));
                           adapter.notifyDataSetChanged();
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
    }

    public  void filter(String txt)
    {
        ArrayList<Orders> lst = new ArrayList<>();
        for(Orders item : completedorders)
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
}
