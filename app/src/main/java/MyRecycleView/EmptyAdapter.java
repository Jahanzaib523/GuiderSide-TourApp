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

public class EmptyAdapter extends  RecyclerView.Adapter<EmptyAdapter.NoOrders>
{
    Context c;

    public EmptyAdapter (Context c)
    {
        this.c=c;
    }

    @NonNull
    @Override
    public EmptyAdapter.NoOrders onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemrow= LayoutInflater.from(c).inflate(R.layout.emptyboxview,parent,false);
        return new EmptyAdapter.NoOrders(itemrow);
    }

    @Override
    public void onBindViewHolder(@NonNull NoOrders holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class NoOrders extends RecyclerView.ViewHolder
    {
        LinearLayout boxx;

        public NoOrders(@NonNull View itemView)
        {
            super(itemView);
            boxx = itemView.findViewById(R.id.emptybox);
        }
    }

    public void filteredlst(ArrayList<Orders> filter_ls)
    {

    }
}

