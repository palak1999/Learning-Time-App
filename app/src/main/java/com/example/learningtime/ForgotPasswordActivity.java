package com.example.learningtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.learningtime.databinding.ActivityForgotPasswordBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class ForgotPasswordActivity extends AppCompatActivity {

    //view binding
    private ActivityForgotPasswordBinding binding;

    //firebase auth
    private FirebaseAuth firebaseAuth;

    //progress dialog
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //init firebase auth
        firebaseAuth=FirebaseAuth.getInstance();

        //init/setup progress dialog
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("please wait");
        progressDialog.setCanceledOnTouchOutside(false);

        //handle click, go back
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //handle click, begin recovery password
        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });
    }
    private String email="";
    private void validateData() {
        //get data ie email
        email=binding.emailEt.getText().toString().trim();

        //validate data ie shouldn't empty and should be valid format
        if(email.isEmpty()){
            Toast.makeText(this, "Enter Email...", Toast.LENGTH_SHORT).show();
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show();
        }
        else{
            recoverPassword();
        }
    }

    private void recoverPassword() {
        //show progress
        progressDialog.setMessage("Sending password recovery instructions to "+email);
        progressDialog.show();

        //begin sending recovery
        firebaseAuth.sendPasswordResetEmail(email)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //sent
                        progressDialog.dismiss();
                        Toast.makeText(ForgotPasswordActivity.this, "Instructions to reset password sent to"+email,Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //failed to send
                        progressDialog.dismiss();
                        Toast.makeText(ForgotPasswordActivity.this, "Failed to sent due to "+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
