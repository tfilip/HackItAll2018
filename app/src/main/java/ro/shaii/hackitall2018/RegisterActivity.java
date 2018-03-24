package ro.shaii.hackitall2018;

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

public class RegisterActivity extends AppCompatActivity {

    private static String TAG = "REGISTER_ACTIVITY";

    private Button registerBTN;

    private EditText emailET;
    private EditText passwordET;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        initViews();

    }


    private void initViews(){
        registerBTN = findViewById(R.id.registerBTN);

        emailET = findViewById(R.id.loginET);
        passwordET = findViewById(R.id.passwordET);

        registerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }

    private void registerUser() {

        String email = emailET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();

        /*
            Verificari pentru campul de email si de parola
         */

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

        /*
            Inregistrarea utilizatorului
         */

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Inregsitarea nu a avut succes",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }


}
