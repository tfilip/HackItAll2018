package ro.shaii.hackitall2018;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RestaurantMain2Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Food> foodList;
    private FloatingActionButton floatingActionButton;

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

        loadRecyclerViewData();

    }

    private void loadRecyclerViewData() {


        Food testFood;
        restaurant testRestaurant;

        testRestaurant = new restaurant("adresa la intamplare","023145151","KFC");

        Date date1 = new Date(2018,2,23);
        Date date2 = new Date(2018,2,24);
        testFood = new Food("Snitzel",testRestaurant,date1,date2,"descriere random","https://upload.wikimedia.org/wikipedia/commons/7/72/Schnitzel.JPG");

        foodList.add(testFood);
        foodList.add(testFood);

        adapter = new FoodCardAdapter(foodList,getApplicationContext());
        recyclerView.setAdapter(adapter);

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

}
