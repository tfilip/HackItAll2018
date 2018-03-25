package ro.shaii.hackitall2018;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.net.URL;

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

        Log.d("TEST LOL", foodUID);
        Log.d("TEST", restaurantUID);


        final DatabaseReference foodRef = FirebaseDatabase.getInstance().getReference().child("foods").child(restaurantUID);

        foodRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot foodSnapshot : dataSnapshot.getChildren()) {

                    Log.d("ITERATE", foodSnapshot.child("foodName").getValue().toString());

                    if (foodSnapshot.child("foodName").getValue().toString().equals(foodUID)) {
                        String foodName = foodSnapshot.child("foodName").getValue().toString();
                        String descriere = foodSnapshot.child("descriere").getValue().toString();
                        String numeRestaurant = foodSnapshot.child("restaurant").getValue().toString();
                        String productionDate = foodSnapshot.child("productionDate").getValue().toString();
                        String expiryDate = foodSnapshot.child("expiryDate").getValue().toString();
                        String photoUrl = foodSnapshot.child("photoURL").getValue().toString();
                        String foodUID = foodSnapshot.child("restUID").getValue().toString();
                        Log.d("TYY", photoUrl);


                        food = new Food(foodName, numeRestaurant, productionDate, expiryDate, descriere, photoUrl, foodUID);
                    }

                    if (food != null) {
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
                for (DataSnapshot restSnapshot : dataSnapshot.getChildren()) {

                    if (restSnapshot.getKey().toString().equals(restaurantUID)) {

                        String address = restSnapshot.child("address").getValue().toString();
                        String telefon = restSnapshot.child("telefon").getValue().toString();
                        String nume = restSnapshot.child("nume").getValue().toString();


                        restaurant = new restaurant(address, telefon, nume);

                    }

                }

                if (restaurant != null) {
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


        acceptBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (food != null) {
                    String uri = "tel:" + restaurant.getTelefon().trim();
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse(uri));
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {


                        ActivityCompat.requestPermissions(Offer.this, new String[]{Manifest.permission.CALL_PHONE}, 300);


                        return;
                    }
                    getApplicationContext().startActivity(intent);
                    adaugaRestaurant();
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case 300: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    String uri = "tel:" + restaurant.getTelefon().trim();
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse(uri));
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    startActivity(intent);
                    adaugaRestaurant();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this,"Te rugam sa accepti permisunile pentru a putea suna restaurantul",Toast.LENGTH_SHORT).show();

                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }



    }

    private void adaugaRestaurant(){

        final DatabaseReference usersReference = FirebaseDatabase.getInstance().getReference("users");

        Log.d("USER_NAME",FirebaseAuth.getInstance().getCurrentUser().getUid());

        usersReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    if(snapshot.getKey().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        FirebaseDatabase.getInstance().getReference("users").
                                child(snapshot.getKey()).child("visited").child(String.valueOf(System.currentTimeMillis())
                        ).setValue(food.getRestUID());

                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}
