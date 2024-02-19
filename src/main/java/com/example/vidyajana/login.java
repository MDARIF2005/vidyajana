package com.example.vidyajana;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class login extends AppCompatActivity {
    public static  final  String TAG="TAG";
   TextView textView,textView2,textView3;
    EditText editText,editText2;
    Button button1,button2;
    ProgressBar progressBar;
    FirebaseFirestore firestore;
    FirebaseAuth fAuth;




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textView=findViewById(R.id.textview3);
        editText=findViewById(R.id.email);
        editText2=findViewById(R.id.Password);
        textView2=findViewById(R.id.for_get_password);
        button1=findViewById(R.id.sector);
        textView3=findViewById(R.id.textView4);
        progressBar=findViewById(R.id.progressBar);

        fAuth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();



        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                    String email = editText.getText().toString();
                    String password = editText2.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    editText.setError("user name is Required");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    editText2.setError("password is Required");
                    return;
                }
                if (password.length()<6){
                    editText2.setError("password Must be >=6");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Log in Succesful",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), homepage.class));
                        }else{
                            Toast.makeText(getApplicationContext(),"error"+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                               progressBar.setVisibility(View.GONE);
                        }

                    }
                });


            }





        });


       button2.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
        startActivity(new Intent(getApplicationContext(), Register.class));

         }


       });

    }
}