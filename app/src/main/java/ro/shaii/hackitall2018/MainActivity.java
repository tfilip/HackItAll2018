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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private Button loginButton;
    private Button registerButton;

    private EditText emailET;
    private EditText passwordET;

    private static String TAG = "MAIN_ACTIVITY";

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

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
                            final FirebaseUser user = mAuth.getCurrentUser();

                            DatabaseReference ref = mDatabase.getReference("users");



                            ref.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    try {

                                        User user = new User(dataSnapshot.child("address").getValue().toString(),dataSnapshot.child("type").getValue().toString());

                                        Log.v(TAG, user.getType());

                                        if(user.getType().equals("Client")){
                                            finish();
                                            startActivity(new Intent(getApplicationContext(),ClientMainActivity.class));
                                        }else{
                                            finish();
                                            startActivity(new Intent(getApplicationContext(),RestaurantMainActivity.class));
                                        }

                                    }catch (Throwable e){
                                        Log.e(TAG,"e",e);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });



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


    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() != null){
            finish();

            Log.d(TAG, "signInWithEmail:success");
            final FirebaseUser user = mAuth.getCurrentUser();

            DatabaseReference ref = mDatabase.getReference("users");

            ref.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {

                        User user = new User(dataSnapshot.child("address").getValue().toString(),dataSnapshot.child("type").getValue().toString());

                        Log.v(TAG, user.getType());

                        if(user.getType().equals("Client")){
                            finish();
                            startActivity(new Intent(getApplicationContext(),ClientMainActivity.class));
                        }else{
                            finish();
                            startActivity(new Intent(getApplicationContext(),RestaurantMainActivity.class));
                        }

                    }catch (Throwable e){
                        Log.e(TAG,"e",e);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }
    }
}
