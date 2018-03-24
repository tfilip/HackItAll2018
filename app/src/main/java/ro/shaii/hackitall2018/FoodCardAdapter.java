package ro.shaii.hackitall2018;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by filip on 24-Mar-18.
 */

public class FoodCardAdapter extends RecyclerView.Adapter<FoodCardAdapter.ViewHolder> {

    private List<Food> foodList;
    private Context context;


    public FoodCardAdapter(List<Food> foodList, Context context){
        this.foodList = foodList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_layout,null);
        return  new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Food food = foodList.get(position);
        if(food!=null){
            Picasso.get().load(food.getPhotoURL()).into(holder.IMG);
            holder.numeMancareTV.setText(food.getFoodName());
            holder.numeRestaurantTV.setText(food.getRest());
            holder.dataExpirareTV.setText(food.getExpiryDate());
            holder.dataProductieTV.setText(food.getProductionDate());
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("foods").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child(foodList.get(position).getFoodName());

                Log.d("TEST",databaseReference.toString());

                databaseReference.removeValue();

                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView IMG;
        public TextView numeMancareTV;
        public TextView numeRestaurantTV;
        public TextView dataProductieTV;
        public TextView dataExpirareTV;


        public ViewHolder(View itemView) {
            super(itemView);

            IMG = itemView.findViewById(R.id.mancareDefault);
            numeMancareTV = itemView.findViewById(R.id.numeMancare);
            numeRestaurantTV = itemView.findViewById(R.id.numeRestaurant);
            dataProductieTV = itemView.findViewById(R.id.dataProducere);
            dataExpirareTV = itemView.findViewById(R.id.dataExpirare);

        }
    }
}
