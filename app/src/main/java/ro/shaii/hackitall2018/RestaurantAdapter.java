package ro.shaii.hackitall2018;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by filip on 25-Mar-18.
 */

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    private List<String> restauranteList;
    private Context context;


    public RestaurantAdapter(List<String> restauranteList, Context context){
        this.restauranteList = restauranteList;
        this.context = context;


    }

    @Override
    public RestaurantAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cardrestaurant_layout,null);
        return  new RestaurantAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RestaurantAdapter.ViewHolder holder, final int position) {

        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("restaurants");


        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    if(snapshot.getKey().equals(restauranteList.get(position))){
                        Log.d("TESTKLO",snapshot.child("nume").getValue().toString());
                        String numeRestaurant = snapshot.child("nume").getValue().toString();
                        holder.numeRestaurantTV.setText(numeRestaurant);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    @Override
    public int getItemCount() {
        return restauranteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public TextView numeRestaurantTV;

        public ViewHolder(View itemView) {
            super(itemView);
            numeRestaurantTV = itemView.findViewById(R.id.Restaurant);

        }
    }

}