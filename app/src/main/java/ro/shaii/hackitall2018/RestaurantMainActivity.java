package ro.shaii.hackitall2018;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RestaurantMainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    private EditText addressET;
    private EditText numeET;
    private EditText numarET;

    private ImageView img;

    private restaurant newRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_main);

        Toast.makeText(this,"Restaurant",Toast.LENGTH_SHORT).show();

        initViews();

    }

    private void initViews(){

        addressET = findViewById(R.id.adresaET);
        numarET = findViewById(R.id.telefonET);
        numeET = findViewById(R.id.numeresET);

        img = findViewById(R.id.img);

    }
}
