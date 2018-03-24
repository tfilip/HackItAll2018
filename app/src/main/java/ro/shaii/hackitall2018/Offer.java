package ro.shaii.hackitall2018;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class Offer extends AppCompatActivity {

    private TextView titleTV;
    private TextView restaurantTV;
    private TextView telefonTV;
    private TextView addressTV;
    private TextView produsTV;
    private TextView expireTV;
    private TextView descriereTV;
    private Button acceptBTN;


    private ImageView img;


    Food food;
    restaurant restaurant;


    String foodUID;
    String restaurantUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);

        initViews();

        foodUID = getIntent().getStringExtra("201");
        restaurantUID = getIntent().getStringExtra("200");

        Log.d("TEST LOL",foodUID);
        Log.d("TEST",restaurantUID);


        final DatabaseReference foodRef = FirebaseDatabase.getInstance().getReference().child("foods").child(restaurantUID);

        foodRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               for(DataSnapshot foodSnapshot: dataSnapshot.getChildren()){

                   Log.d("ITERATE",foodSnapshot.child("foodName").getValue().toString());

                   if(foodSnapshot.child("foodName").getValue().toString().equals(foodUID)){
                       String foodName = foodSnapshot.child("foodName").getValue().toString();
                       String descriere = foodSnapshot.child("descriere").getValue().toString();
                       String numeRestaurant = foodSnapshot.child("restaurant").getValue().toString();
                       String productionDate = foodSnapshot.child("productionDate").getValue().toString();
                       String expiryDate = foodSnapshot.child("expiryDate").getValue().toString();
                       String photoUrl = foodSnapshot.child("photoURL").getValue().toString();
                       String foodUID = foodSnapshot.child("restUID").getValue().toString();
                       Log.d("TYY",photoUrl);


                       food = new Food(foodName,numeRestaurant,productionDate,expiryDate,descriere,photoUrl,foodUID);
                   }

                   if(food!=null) {
                       Picasso.get().load(food.getPhotoURL()).into(img);
                       titleTV.setText(food.getFoodName());
                       descriereTV.setText(food.getDescriere());
                       restaurantTV.setText(food.getRestaurant());
                       produsTV.setText(food.getProductionDate());
                       expireTV.setText(food.getExpiryDate());
                   }

               }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final DatabaseReference restRef = FirebaseDatabase.getInstance().getReference().child("restaurants");

       restRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot restSnapshot: dataSnapshot.getChildren()){

                   if(restSnapshot.getKey().toString().equals(restaurantUID)){

                       String address = restSnapshot.child("address").getValue().toString();
                       String telefon = restSnapshot.child("telefon").getValue().toString();
                       String nume = restSnapshot.child("nume").getValue().toString();



                       restaurant = new restaurant(address,telefon,nume);

                   }

                }

                if(restaurant!=null){
                    telefonTV.setText(restaurant.getTelefon());
                    addressTV.setText(restaurant.getAddress());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    private void initViews() {

        titleTV = findViewById(R.id.cmname);
        restaurantTV = findViewById(R.id.crest);
        telefonTV = findViewById(R.id.ctelefon);
        addressTV = findViewById(R.id.cadresa);
        produsTV = findViewById(R.id.cprodus);
        expireTV = findViewById(R.id.cexpira);
        descriereTV = findViewById(R.id.cdescriere);
        acceptBTN = findViewById(R.id.clientbuton);
        img = findViewById(R.id.clientpic);

    }


}
