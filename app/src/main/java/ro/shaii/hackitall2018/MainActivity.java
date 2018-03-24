package ro.shaii.hackitall2018;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button loginButton;
    private Button registerButton;

    private EditText emailET;
    private EditText passwordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();



    }


    private void initViews(){

        loginButton = findViewById(R.id.loginBTN);
        registerButton = findViewById(R.id.registerBTN);

        emailET = findViewById(R.id.loginET);
        passwordET = findViewById(R.id.passwordET);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent;
                intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }




}
