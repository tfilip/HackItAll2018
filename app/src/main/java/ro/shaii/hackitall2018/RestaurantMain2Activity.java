package ro.shaii.hackitall2018;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RestaurantMain2Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Food> foodList;
    private FloatingActionButton floatingActionButton;
    private DatabaseReference databaseFood;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        floatingActionButton = findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        foodList = new ArrayList<>();


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menuLogOff:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;
        }

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();


        databaseFood = FirebaseDatabase.getInstance().getReference().child("foods").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        databaseFood.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                foodList.clear();
                for(DataSnapshot foodSnapshot: dataSnapshot.getChildren()){
                    String foodName = foodSnapshot.child("foodName").getValue().toString();
                    String descriere = foodSnapshot.child("descriere").getValue().toString();
                    String numeRestaurant = foodSnapshot.child("restaurant").getValue().toString();
                    String productionDate = foodSnapshot.child("productionDate").getValue().toString();
                    String expiryDate = foodSnapshot.child("expiryDate").getValue().toString();
                    String photoUrl = foodSnapshot.child("photoURL").getValue().toString();
                    String foodUID = foodSnapshot.child("restUID").getValue().toString();
                    Food newFood = new Food(foodName,numeRestaurant,productionDate,expiryDate,descriere,photoUrl,foodUID);
                    foodList.add(newFood);

                }

                adapter = new FoodCardAdapter(foodList,RestaurantMain2Activity.this);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
