package ro.shaii.hackitall2018;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class RestaurantMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_main);

        Toast.makeText(this,"Restaurant",Toast.LENGTH_SHORT).show();

    }
}
