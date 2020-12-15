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

public class CompletedOrdersRecycleView extends  RecyclerView.Adapter<CompletedOrdersRecycleView.CompletedOrders>
{
    List<Orders> ls;
    Context c;
    Button btnComplete;
    int posi;

    public CompletedOrdersRecycleView(List<Orders> ls, Context c)
    {
        this.c=c;
        this.ls=ls;
    }

    @NonNull
    @Override
    public CompletedOrdersRecycleView.CompletedOrders onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemrow= LayoutInflater.from(c).inflate(R.layout.orderbox1,parent,false);
        return new CompletedOrdersRecycleView.CompletedOrders(itemrow);
    }

    @Override
    public void onBindViewHolder(@NonNull final CompletedOrdersRecycleView.CompletedOrders holder, final int position)
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

    public class CompletedOrders extends RecyclerView.ViewHolder
    {
        TextView name, from, to, startloc, endloc, email, inprogress, pay;
        ImageView img;
        LinearLayout boxx;

        public CompletedOrders(@NonNull View itemView)
        {
            super(itemView);
            name=itemView.findViewById(R.id.nameofuser);
            img = itemView.findViewById(R.id.orderimage);
            from = itemView.findViewById(R.id.fromlocation);
            to = itemView.findViewById(R.id.tolocation);
            startloc = itemView.findViewById(R.id.startdate);
            endloc = itemView.findViewById(R.id.enddate);
            boxx=itemView.findViewById(R.id.orderboxcompleted);
            email =itemView.findViewById(R.id.emailofguider);
            inprogress = itemView.findViewById(R.id.inprogress);
            pay = itemView.findViewById(R.id.paid);
        }
    }

    public void filteredlst(ArrayList<Orders> filter_ls)
    {
        ls = filter_ls;
        notifyDataSetChanged();
    }
}

