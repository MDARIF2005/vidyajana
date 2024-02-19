package com.example.vidyajana;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Register extends AppCompatActivity {


    public static  final  String TAG="TAG";
    TextView textView,textView2,textView3;
    EditText editText,editText2,editText3,editText4;

    Spinner spinner_gender,spinner_state;

    Button button;
    ProgressBar progressBar;
    FirebaseFirestore fstore;
    String userID;
    FirebaseAuth fAuth;



    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        textView = findViewById(R.id.textView5);
        editText = findViewById(R.id.user_name);
        editText2 = findViewById(R.id.email);
        editText3 = findViewById(R.id.Password);
        editText4 = findViewById(R.id.Birth_date);
        textView2 = findViewById(R.id.Gender);
        spinner_gender = findViewById(R.id.gender);
        textView3 = findViewById(R.id.indian_states);
        spinner_state = findViewById(R.id.spinner_states);
        button = findViewById(R.id.sign_up);
        progressBar=findViewById(R.id.ProgressBar);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
         if(fAuth.getCurrentUser()!=null){
             startActivity(new Intent(getApplicationContext(), course.class));
             finish();
         }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(Register.this,
                        android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.states));

                myAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                spinner_state.setAdapter(myAdapter);


                ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(Register.this,
                        android.R.layout.simple_list_item_2, getResources().getStringArray(R.array.gender));
                myAdapter2.setDropDownViewResource(android.R.layout.simple_list_item_2);
                spinner_gender.setAdapter(myAdapter2);


                final String email = editText2.getText().toString().trim();

                String password = editText3.getText().toString().trim();
                final String user_name = editText.getText().toString();
                final String date_of_birth = editText4.getText().toString();
                String gender = textView2.getTag().toString();
                String state = textView3.getTag().toString();
                if (TextUtils.isEmpty(email)) {
                    editText2.setError("Email is Required");
                }
                if (TextUtils.isEmpty(password)) {
                    editText3.setError("Password is Required ");

                }
                if (password.length() < 6) {
                    editText3.setError("Password Must be >=6 character");
                }
                if (TextUtils.isEmpty(user_name)) {
                    editText.setError("User Name is Required");
                }
                if (TextUtils.isEmpty(date_of_birth)) {
                    editText4.setError("Date is Required");
                }
                if (TextUtils.isEmpty(gender)) {
                    textView2.setError("Gender is require");
                }
                if (TextUtils.isEmpty(state)) {
                    textView3.setError("State is Required");
                }
                progressBar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser fuser = fAuth.getCurrentUser();
                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getApplicationContext(), "Register Succesful", Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "Onfailur:Email.not sent" + e.getMessage());
                                }
                            });

                            Toast.makeText(getApplicationContext(), "User Created", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fstore.collection("user").document(userID);
                            Map<String, Object> user = new HashMap<>();
                            user.put("fname", textView);
                            user.put("email", textView2);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG, "onsucces:user profile is created for " + userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onfailure :" + e.toString());
                                }
                            });

                            startActivity(new Intent(getApplicationContext(), course.class));

                        } else {
                            Toast.makeText(Register.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                               progressBar.setVisibility(View.GONE);
                        }

                    }
                });


            }
        });





    }
}