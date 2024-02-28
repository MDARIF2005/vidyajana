package com.example.vidyajana;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.GrammaticalInflectionManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
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


    public static final String TAG = "TAG";
    TextView textView;
    EditText editText, editText2, editText3, editText4;

    Spinner spinner_gender, spinner_state;

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
        editText2 = findViewById(R.id.email2);
        editText3 = findViewById(R.id.Password2);
        editText4 = findViewById(R.id.Birth_date);
        spinner_gender = findViewById(R.id.gender);
        spinner_state = findViewById(R.id.spinner_states);
        button = findViewById(R.id.sign_up);
        progressBar = findViewById(R.id.ProgressBar);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), course.class));
            finish();
        }


        spinner_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(Register.this, "selected item:" + item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Gender");
        arrayList.add("male");
        arrayList.add("female");
        arrayList.add("other");
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, arrayList);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner_gender.setAdapter(adapter);

        spinner_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(Register.this, "selected item:" + item, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayList<String> arrayList2 = new ArrayList<>();
        arrayList2.add("states");
        arrayList2.add("Andra Pradesh");
        arrayList2.add("Arunachal Pradesh");
        arrayList2.add("Assam");
        arrayList2.add("Bihar");
        arrayList2.add("Chhattisgarh");
        arrayList2.add("Goa");
        arrayList2.add("Gujarat");
        arrayList2.add("Haryana");
        arrayList2.add("Himachal Pradesh");
        arrayList2.add("Jammu and Kashmir");
        arrayList2.add("Jharkhand");
        arrayList2.add("Karnataka");
        arrayList2.add("Kerala");
        arrayList2.add("Madhya Pradesh");
        arrayList2.add("Maharashtra");
        arrayList2.add("Manipur");
        arrayList2.add("Meghalaya");
        arrayList2.add("Mizoram");
        arrayList2.add("Nagaland");
        arrayList2.add("Orissa");
        arrayList2.add("Punjab");
        arrayList2.add("Rajasthan");
        arrayList2.add("Sikkim");
        arrayList2.add("Tamil Nadu");
        arrayList2.add("Telangana");
        arrayList2.add("Tripura");
        arrayList2.add("Uttar Pradesh");
        arrayList2.add("Uttarakhand");
        arrayList2.add("West Bengal");



        ArrayAdapter<String> adapter2 =
                new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, arrayList2);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner_state.setAdapter(adapter2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String email = editText2.getText().toString().trim();

                String password = editText3.getText().toString().trim();
                final String user_name = editText.getText().toString();
                final String date_of_birth = editText4.getText().toString();

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
                            user.put("fname", editText);
                            user.put("email", editText2);
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