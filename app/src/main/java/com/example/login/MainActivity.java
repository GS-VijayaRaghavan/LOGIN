package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private EditText Username;
    private EditText Password;
    private Button signin,signup;
    private TextView tv;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Username = (EditText) findViewById(R.id.username);
        Password = (EditText) findViewById(R.id.password);
        signin = (Button) findViewById(R.id.btnin);
        signup = (Button) findViewById(R.id.btnup);
        tv= (TextView) findViewById(R.id.info) ;
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(Username.getText().toString(),Password.getText().toString());
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_name = Username.getText().toString().trim();
                String user_password = Password.getText().toString().trim();
                tv.setVisibility(View.INVISIBLE);

                firebaseAuth.createUserWithEmailAndPassword(user_name,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Signed up Successfully !", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(MainActivity.this, "Enter Email_ID with '@gmail.com and 8-digit password'", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private void validate(String userName,String userPassword){
        progressDialog.setMessage("Verifying...Please Wait !");
        progressDialog.show();
       firebaseAuth.signInWithEmailAndPassword(userName,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful()){
                   progressDialog.dismiss();
                   Toast.makeText(MainActivity.this,"Sign in Successfull !",Toast.LENGTH_SHORT).show();
                   startActivity(new Intent(MainActivity.this,SecondActivity.class));
               }
               else {
                   Toast.makeText(MainActivity.this, "Sign in Failed !", Toast.LENGTH_SHORT).show();
                   progressDialog.dismiss();
                   tv.setVisibility(View.VISIBLE);
               }
           }
       });

    }

}
