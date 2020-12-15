package MyRecycleView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.semester_project.smd_project.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

import Models.Orders;

public class MyOrdersRecycleView extends  RecyclerView.Adapter<MyOrdersRecycleView.InCompletedOrders>
{
    List<Orders> ls;
    Context c;
    FirebaseDatabase mDatabase;
    DatabaseReference dbRefernce;
    Context context;
    Button btnComplete;
    int posi;

    public MyOrdersRecycleView (List<Orders> ls, Context c)
    {
        this.c=c;
        this.ls=ls;
    }

    @NonNull
    @Override
    public MyOrdersRecycleView.InCompletedOrders onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemrow= LayoutInflater.from(c).inflate(R.layout.order_box,parent,false);
        return new MyOrdersRecycleView.InCompletedOrders(itemrow);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyOrdersRecycleView.InCompletedOrders holder, final int position)
    {

            holder.name.setText(ls.get(position).getGuider_email());
            holder.from.setText(ls.get(position).getFrom_location());
            holder.to.setText(ls.get(position).getTo_location());
            holder.email.setText(ls.get(position).getGuider_name());
            holder.startloc.setText(ls.get(position).getStar_tdate());
            holder.endloc.setText(ls.get(position).getEnd_date());
            holder.inprogress.setText(ls.get(position).getOrder_status());
            holder.pay.setText(ls.get(position).getPayment());
            Picasso.get().load(ls.get(position).getImage()).into(holder.img);
            btnComplete = holder.completeOrderBtn;

            final int pos = position;
            holder.completeOrderBtn.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    dbRefernce.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            boolean val = false;
                            for(DataSnapshot d: snapshot.getChildren())
                            {
                                String key = d.getKey();
                                if(d.child("uid").getValue().equals(ls.get(position).getGuider_name()))
                                {
                                    ls.get(position).setOrder_status("Completed");
                                    dbRefernce.getRef().child(key).child("tripstatus").setValue(ls.get(position).getOrder_status());
                                    holder.inprogress.setText("Completed");
                                    holder.completeOrderBtn.setText("Completed");
                                    //holder.completeOrderBtn.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.completedbtndrawable));
                                    holder.completeOrderBtn.setVisibility(View.GONE);
                                    holder.completeOrderBtn.setEnabled(false);
                                    holder.completeOrderBtn.setFocusable(false);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            });
    }

    @Override
    public int getItemViewType(int position)
    {
        switch (ls.get(getAdaptionPosition()).getOrder_status()) {
            case "Completed":
                return 0;
            case "In Progress":
                return 1;
            default:
                return -1;
        }
    }

    public Button getButton()
    {
        return btnComplete;
    }

    public int getAdaptionPosition()
    {
        return posi;
    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    public class InCompletedOrders extends RecyclerView.ViewHolder
    {
        TextView name, from, to, startloc, endloc, email, inprogress, pay;
        ImageView img;
        Button completeOrderBtn;
        LinearLayout boxx;

        public InCompletedOrders(@NonNull View itemView)
        {
            super(itemView);
            name=itemView.findViewById(R.id.nameofuser);
            img = itemView.findViewById(R.id.orderimage);
            from = itemView.findViewById(R.id.fromlocation);
            to = itemView.findViewById(R.id.tolocation);
            startloc = itemView.findViewById(R.id.startdate);
            endloc = itemView.findViewById(R.id.enddate);
            boxx=itemView.findViewById(R.id.orderbox);
            email =itemView.findViewById(R.id.emailofguider);
            inprogress = itemView.findViewById(R.id.inprogress);
            completeOrderBtn = itemView.findViewById(R.id.completeorderbtn);
            pay = itemView.findViewById(R.id.paid);
            mDatabase = FirebaseDatabase.getInstance();
            dbRefernce = mDatabase.getReference("ORDERS");
        }
    }

    public void filteredlst(ArrayList<Orders> filter_ls)
    {
        ls = filter_ls;
        notifyDataSetChanged();
    }
}

