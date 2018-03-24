package ro.shaii.hackitall2018;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;

public class AddActivity extends AppCompatActivity {

    private static final int CHOOSE_IMAGE = 101;
    private EditText numeProdusET;
    private EditText dataProductieET;
    private EditText dataExpriareET;
    private EditText descriereET;
    private ImageView imageView;
    private Button addButton;


    private String numeMancare;
    private String dataExpirare;
    private String dataProductie;
    private String descriere;


    private Uri photo;
    private String photoURL;

    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;

    Food newFood;


    private Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        myCalendar = Calendar.getInstance();
        initViews();

        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adaugareAnunt();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adaugarePoza();
            }
        });

    }

    private void adaugarePoza() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Profile Image"), CHOOSE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){

            photo = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),photo);
                imageView.setImageBitmap(bitmap);

                uploadImageToFirebaseStorage();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void uploadImageToFirebaseStorage() {

        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference("foodpics/"+System.currentTimeMillis()+".jpg");

        if(photo != null){
            mStorageRef.putFile(photo).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    photoURL = taskSnapshot.getDownloadUrl().toString();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void adaugareAnunt() {

        numeMancare = numeProdusET.getText().toString().trim();
        descriere = descriereET.getText().toString().trim();
        dataProductie= dataProductieET.getText().toString().trim();
        dataExpirare = dataExpriareET.getText().toString().trim();

        if(TextUtils.isEmpty(numeMancare)){
            Toast.makeText(this,"Toate campurile trebuie sa fie completate", Toast.LENGTH_SHORT).show();
            numeProdusET.requestFocus();
            return;
        }


        if(TextUtils.isEmpty(descriere)){
            Toast.makeText(this,"Toate campurile trebuie sa fie completate", Toast.LENGTH_SHORT).show();
            descriereET.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(dataProductie)){
            Toast.makeText(this,"Toate campurile trebuie sa fie completate", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(dataExpirare)){
            Toast.makeText(this,"Toate campurile trebuie sa fie completate", Toast.LENGTH_SHORT).show();
            return;
        }


        Food newFood = new Food(numeMancare,FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),dataProductie,dataExpirare,descriere,photoURL);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("foods");

        databaseReference.child(mAuth.getCurrentUser().getUid()).child(newFood.getFoodName()).setValue(newFood);

        finish();

    }

    DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            dataExpriareET.setText(sdf.format(myCalendar.getTime()));
        }

    };

    DatePickerDialog.OnDateSetListener datePickerListener2 = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            dataProductieET.setText(sdf.format(myCalendar.getTime()));
        }

    };

    private void initViews(){

        numeProdusET = findViewById(R.id.preparat);
        dataExpriareET = findViewById(R.id.exp);
        dataProductieET = findViewById(R.id.prod);
        descriereET = findViewById(R.id.descriereET);
        imageView = findViewById(R.id.imgAnunt);
        addButton = findViewById(R.id.submitBTN);


        dataExpriareET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddActivity.this,datePickerListener,myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        dataProductieET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddActivity.this,datePickerListener2,myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });




    }



}
