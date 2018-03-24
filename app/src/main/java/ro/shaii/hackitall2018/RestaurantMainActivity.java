package ro.shaii.hackitall2018;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class RestaurantMainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    private EditText addressET;
    private EditText numeET;
    private EditText numarET;

    private ImageView img;
    Uri profileImage;
    String profileImageURL;

    private restaurant newRestaurant;
    private static final int CHOOSE_IMAGE = 101;
    private StorageReference mStorageRef;

    private Button saveButton;

    String address;
    String nume;
    String numar;

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
        saveButton = findViewById(R.id.saveBTN);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageChooser();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!setRestaurantData())
                    return;

                saveUserInformation();
            }
        });

    }

    private void saveUserInformation() {

        FirebaseUser user = mAuth.getCurrentUser();

        if( user!=null && !profileImageURL.isEmpty()){

            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder().setDisplayName(nume).setPhotoUri(profileImage).build();

            user.updateProfile(profile)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Profile updated",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }


        DatabaseReference ref = mDatabase.getReference("restaurants");

        restaurant newRestaurant = new restaurant(address,numar);

        if (user != null) {
            ref.child(user.getUid()).setValue(newRestaurant);
        }

    }

    private boolean setRestaurantData() {

        address = addressET.getText().toString().trim();
         numar = numarET.getText().toString().trim();
         nume = numeET.getText().toString().trim();

        if(TextUtils.isEmpty(address)){
            addressET.setError("Campul trebuie completat");
            addressET.requestFocus();
            return false;
        }

        if(TextUtils.isEmpty(numar)){
            numarET.setError("Campul trebuie completat");
            numarET.requestFocus();
            return false;
        }

        if(TextUtils.isEmpty(nume)){
            numeET.setError("Campul trebuie completat");
            numeET.requestFocus();
            return false;
        }

        return true;

    }

    private void showImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Profile Image"), CHOOSE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){
            profileImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),profileImage);
                img.setImageBitmap(bitmap);

                uploadImageToFirebaseStorage();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }

    private void uploadImageToFirebaseStorage() {

       mStorageRef = FirebaseStorage.getInstance().getReference("profilepics/"+System.currentTimeMillis()+".jpg");

        if(profileImage != null){
            mStorageRef.putFile(profileImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    profileImageURL = taskSnapshot.getDownloadUrl().toString();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }
    }


}
