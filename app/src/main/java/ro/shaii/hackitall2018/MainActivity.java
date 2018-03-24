package ro.shaii.hackitall2018;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Button loginButton;
    private Button registerButton;

    private EditText emailET;
    private EditText passwordET;

    private static String TAG = "MAIN_ACTIVITY";

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
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

                String email = emailET.getText().toString().trim();
                String password = passwordET.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    emailET.setError("Campul E-Mail nu poate fi liber");
                    emailET.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    emailET.setError("Campul parola nu poate fi liber");
                    emailET.requestFocus();
                    return;
                }

                if (password.length() < 6 ){
                    passwordET.setError("Parola trebuie sa contina mai mult de 6 caractere");
                    passwordET.requestFocus();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();


                        }else {

                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });



            }
        });

    }




}
